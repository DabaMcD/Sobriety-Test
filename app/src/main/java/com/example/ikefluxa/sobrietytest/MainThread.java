package com.example.ikefluxa.sobrietytest;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

class MainThread extends Thread {
    private static final int MAX_FPS = 30;
    private SurfaceHolder surfaceHolder;
    private GameScreen gameScreen;
    private boolean running;
    private static Canvas canvas;

    void setRunning(boolean running) {
        this.running = running;
    }
    MainThread(SurfaceHolder surfaceHolder, GameScreen gameScreen) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.gameScreen = gameScreen;
    }
    @Override
    public void run() {
        long startTime;
        long timeMillis;
        long waitTime;
        long targetTime = 1000/MAX_FPS;
        while(running) {
            startTime = System.nanoTime();
            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    this.gameScreen.draw(canvas);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if(canvas != null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    } catch (Exception e) {e.printStackTrace();}
                }
            }
            timeMillis = (System.nanoTime() - startTime) / 1000000;
            waitTime = targetTime - timeMillis;
            try {
                if(waitTime < 0) {
                    this.sleep(waitTime);
                }
            } catch (Exception e) {e.printStackTrace();}
        }
    }
}
