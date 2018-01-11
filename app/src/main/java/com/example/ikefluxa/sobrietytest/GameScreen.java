package com.example.ikefluxa.sobrietytest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Ike&Fluxa on 1/5/2018.
 */

public class GameScreen extends SurfaceView implements SurfaceHolder.Callback {
    private MainThread thread;
    int angle;
    int angleSpeed;
    int radius;
    String sobriety;
    int drunkness;
    int noDrunkness;
    double radiusInc;
    double x;
    double y;
    public GameScreen(Context context) {
        super(context);
        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);
        angle = 0;
        angleSpeed = 1;
        radius = Constants.SCREEN_WIDTH / (400 / 125);
        sobriety = "pending";
        drunkness = 0;
        noDrunkness = 0;
        radiusInc = 1;
        x = (Math.sin(Math.toRadians(angle)) * radius)+Constants.SCREEN_WIDTH / (400 / 175);
        y = (Math.cos(Math.toRadians(angle)) * radius)+Constants.SCREEN_WIDTH / (400 / 275);
    }
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new MainThread(getHolder(), this);
        thread.setRunning(true);
        thread.start();
    }
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while(retry) {
            try {
                thread.setRunning(false);
                thread.join();
            } catch (Exception e) {e.printStackTrace();}
            retry = false;
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                angle -= angleSpeed;
                x = (Math.sin(Math.toRadians(angle)) * radius)+Constants.SCREEN_WIDTH / (400 / 175);
                y = (Math.cos(Math.toRadians(angle)) * radius)+Constants.SCREEN_WIDTH / (400 / 275);
                angle += angleSpeed;
                if(event.getX() > x && event.getX() < x + Constants.SCREEN_WIDTH / 8 && event.getY() > y && event.getY() < y + Constants.SCREEN_WIDTH / 8){
                    sobriety = "sober";
                } else {
                    sobriety = "drunk";
                }
        }
        return true;
    }
    public void update() {

    }
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        Paint paint = new Paint();
        if(sobriety == "pending"){
            paint.setColor(Color.RED);
            canvas.drawRect(-1, -1, Constants.SCREEN_WIDTH + 1, Constants.SCREEN_HEIGHT + 1, paint);
            paint.setTextSize(Constants.SCREEN_HEIGHT / 15);
            paint.setColor(Color.GREEN);
            paint.setTextAlign(Paint.Align.CENTER);
            canvas.drawText("Are you drunk?", Constants.SCREEN_WIDTH / 2, Constants.SCREEN_HEIGHT / 5, paint);
            paint.setTextSize((int)(Constants.SCREEN_HEIGHT / 3.5));
            canvas.drawText("YES", Constants.SCREEN_WIDTH / 2, (float) (Constants.SCREEN_HEIGHT / 1.7), paint);
            x = (Math.sin(Math.toRadians(angle)) * radius)+Constants.SCREEN_WIDTH / (400 / 175);
            y = (Math.cos(Math.toRadians(angle)) * radius)+Constants.SCREEN_WIDTH / (400 / 275);
            paint.setColor(Color.BLUE);
            canvas.drawRect((float)x, (float)y, (float)x + Constants.SCREEN_WIDTH / 8, (float)y + Constants.SCREEN_WIDTH / 8, paint);
            paint.setColor(Color.BLACK);
            paint.setTextSize(Constants.SCREEN_HEIGHT / 20);
            paint.setTextAlign(Paint.Align.CENTER);
            canvas.drawText("no", (float)x + Constants.SCREEN_WIDTH / 16, (float)y + Constants.SCREEN_WIDTH / 16 + Constants.SCREEN_HEIGHT / 60, paint);
            angle += angleSpeed;
            radius -= radiusInc;
            if(radius<Constants.SCREEN_WIDTH / 5){
                radiusInc = -1;
            }
            if(radius>Constants.SCREEN_WIDTH / 3){
                radiusInc = 1;
            }
        } else if(sobriety == "sober"){
            if(noDrunkness<50){
                paint.setColor(Color.BLACK);
                canvas.drawRect(-1, -1, Constants.SCREEN_WIDTH + 1, Constants.SCREEN_HEIGHT + 1, paint);
                paint.setTextSize(Constants.SCREEN_WIDTH / 4);
                paint.setColor(Color.RED);
                paint.setTextAlign(Paint.Align.CENTER);
                canvas.drawText("Are you", Constants.SCREEN_WIDTH / 2, Constants.SCREEN_HEIGHT / 2, paint);
                canvas.drawText("sure?", Constants.SCREEN_WIDTH / 2, Constants.SCREEN_HEIGHT / 2 + (int)(Constants.SCREEN_WIDTH / (4 / 1.5)), paint);
            }
            if(noDrunkness==50){
                sobriety = "pending";
                angleSpeed *= 2;
            }
            if(noDrunkness<100&&noDrunkness>50){
                paint.setColor(Color.BLACK);
                canvas.drawRect(-1, -1, Constants.SCREEN_WIDTH + 1, Constants.SCREEN_HEIGHT + 1, paint);
                paint.setTextSize(Constants.SCREEN_WIDTH / 4);
                paint.setColor(Color.RED);
                paint.setTextAlign(Paint.Align.CENTER);
                canvas.drawText("Tell the", Constants.SCREEN_WIDTH / 2, Constants.SCREEN_HEIGHT / 2, paint);
                canvas.drawText("truth.", Constants.SCREEN_WIDTH / 2, Constants.SCREEN_HEIGHT / 2 + (int)(Constants.SCREEN_WIDTH / (4 / 1.5)), paint);
            }
            if(noDrunkness==100){
                sobriety = "pending";
                angleSpeed *= 2;
            }
            if(noDrunkness>100&&noDrunkness<150){
                paint.setColor(Color.BLACK);
                canvas.drawRect(-1, -1, Constants.SCREEN_WIDTH + 1, Constants.SCREEN_HEIGHT + 1, paint);
                paint.setTextSize((float)(Constants.SCREEN_WIDTH / 3.5));
                paint.setColor(Color.RED);
                paint.setTextAlign(Paint.Align.CENTER);
                canvas.drawText("100%", Constants.SCREEN_WIDTH / 2, Constants.SCREEN_HEIGHT / 2, paint);
                canvas.drawText("sure?", Constants.SCREEN_WIDTH / 2, Constants.SCREEN_HEIGHT / 2 + (int)(Constants.SCREEN_WIDTH / (3.5 / 1.5)), paint);
            }
            if(noDrunkness==150){
                sobriety = "pending";
                angleSpeed *= 2;
            }
            if(noDrunkness>150&&noDrunkness<200){
                paint.setColor(Color.BLACK);
                canvas.drawRect(-1, -1, Constants.SCREEN_WIDTH + 1, Constants.SCREEN_HEIGHT + 1, paint);
                paint.setTextSize((float)(Constants.SCREEN_WIDTH / 4));
                paint.setColor(Color.RED);
                paint.setTextAlign(Paint.Align.CENTER);
                canvas.drawText("1000%", Constants.SCREEN_WIDTH / 2, Constants.SCREEN_HEIGHT / 2, paint);
                canvas.drawText("sure???", Constants.SCREEN_WIDTH / 2, Constants.SCREEN_HEIGHT / 2 + (int)(Constants.SCREEN_WIDTH / (4 / 1.5)), paint);
            }
            if(noDrunkness==200){
                sobriety = "pending";
                angleSpeed *= 2;
            }
            if(noDrunkness>200){
                paint.setColor(Color.rgb((int)(Math.random() * 255), (int)(Math.random() * 255), (int)(Math.random() * 255)));
                canvas.drawRect(-1, -1, Constants.SCREEN_WIDTH + 1, Constants.SCREEN_HEIGHT + 1, paint);
                paint.setTextSize(Constants.SCREEN_WIDTH / 8);
                paint.setTextAlign(Paint.Align.CENTER);
                paint.setColor(Color.BLACK);
                canvas.drawText("You aren't drunk!", Constants.SCREEN_WIDTH / 2, Constants.SCREEN_HEIGHT / 6, paint);
                canvas.drawText("Yay!!!!!!!!", Constants.SCREEN_WIDTH / 2, Constants.SCREEN_HEIGHT / 6 + (int)(Constants.SCREEN_WIDTH / (8 / 1.5)), paint);
                paint.setTextSize(Constants.SCREEN_WIDTH / 3);
                canvas.drawText("YAY!", Constants.SCREEN_WIDTH / 2, (float)(Constants.SCREEN_HEIGHT / 1.5), paint);
            }
            noDrunkness++;
        } else if(sobriety == "drunk"){
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setColor(Color.BLACK);
            canvas.drawRect(-1, -1, Constants.SCREEN_WIDTH + 1, Constants.SCREEN_HEIGHT + 1, paint);
            paint.setTextSize(Constants.SCREEN_WIDTH / 5);
            paint.setColor(Color.RED);
            canvas.drawText("Oh nose!", Constants.SCREEN_WIDTH / 2, Constants.SCREEN_HEIGHT / 2, paint);
            if(drunkness>40){
                paint.setColor(Color.BLACK);
                canvas.drawRect(-1, -1, Constants.SCREEN_WIDTH + 1, Constants.SCREEN_HEIGHT + 1, paint);
                paint.setTextSize(Constants.SCREEN_WIDTH / (400 / 65));
                paint.setColor(Color.RED);
                canvas.drawText("You're drunk!", Constants.SCREEN_WIDTH / 2, Constants.SCREEN_HEIGHT / 2, paint);
            }
            if(drunkness>80){
                paint.setColor(Color.BLACK);
                canvas.drawRect(-1, -1, Constants.SCREEN_WIDTH + 1, Constants.SCREEN_HEIGHT + 1, paint);
                paint.setTextSize(Constants.SCREEN_WIDTH / (400 / 130));
                paint.setColor(Color.RED);
                canvas.drawText("Panic!", Constants.SCREEN_WIDTH / 2, Constants.SCREEN_HEIGHT / 2, paint);
            }
            if(drunkness>120){
                paint.setColor(Color.BLACK);
                canvas.drawRect(-1, -1, Constants.SCREEN_WIDTH + 1, Constants.SCREEN_HEIGHT + 1, paint);
                for(int i = Constants.SCREEN_WIDTH / 4;i<Constants.SCREEN_WIDTH;i+=Constants.SCREEN_WIDTH / 2){
                    for(int j = Constants.SCREEN_HEIGHT / 16;j<Constants.SCREEN_HEIGHT;j+=Constants.SCREEN_HEIGHT / 8){
                        paint.setTextSize(Constants.SCREEN_HEIGHT / (400 / 30));
                        paint.setColor(Color.WHITE);
                        canvas.drawText("Panic!", i, j, paint);
                    }
                }
            }
            if(drunkness>150){
                paint.setColor(Color.BLACK);
                canvas.drawRect(-1, -1, Constants.SCREEN_WIDTH + 1, Constants.SCREEN_HEIGHT + 1, paint);
            }
            if(drunkness>180){
                paint.setColor(Color.BLACK);
                canvas.drawRect(-1, -1, Constants.SCREEN_WIDTH + 1, Constants.SCREEN_HEIGHT + 1, paint);
                for(int i = Constants.SCREEN_WIDTH / 4;i<Constants.SCREEN_WIDTH;i+=Constants.SCREEN_WIDTH / 2){
                    for(int j = Constants.SCREEN_HEIGHT / 16;j<Constants.SCREEN_HEIGHT;j+=Constants.SCREEN_HEIGHT / 8){
                        paint.setTextSize(Constants.SCREEN_HEIGHT / (400 / 30));
                        paint.setColor(Color.WHITE);
                        canvas.drawText("Panic!", i, j, paint);
                    }
                }
            }
            if(drunkness>210){
                paint.setColor(Color.BLACK);
                canvas.drawRect(-1, -1, Constants.SCREEN_WIDTH + 1, Constants.SCREEN_HEIGHT + 1, paint);
            }
            if(drunkness>230){
                paint.setColor(Color.BLACK);
                canvas.drawRect(-1, -1, Constants.SCREEN_WIDTH + 1, Constants.SCREEN_HEIGHT + 1, paint);
                for(int i = Constants.SCREEN_WIDTH / 4;i<Constants.SCREEN_WIDTH;i+=Constants.SCREEN_WIDTH / 2){
                    for(int j = Constants.SCREEN_HEIGHT / 16;j<Constants.SCREEN_HEIGHT;j+=Constants.SCREEN_HEIGHT / 8){
                        paint.setTextSize(Constants.SCREEN_HEIGHT / (400 / 30));
                        paint.setColor(Color.WHITE);
                        canvas.drawText("Panic!", i, j, paint);
                    }
                }
            }
            if(drunkness>250){
                paint.setColor(Color.BLACK);
                canvas.drawRect(-1, -1, Constants.SCREEN_WIDTH + 1, Constants.SCREEN_HEIGHT + 1, paint);
            }
            if(drunkness>270){
                paint.setColor(Color.BLACK);
                canvas.drawRect(-1, -1, Constants.SCREEN_WIDTH + 1, Constants.SCREEN_HEIGHT + 1, paint);
                for(int i = Constants.SCREEN_WIDTH / 4;i<Constants.SCREEN_WIDTH;i+=Constants.SCREEN_WIDTH / 2){
                    for(int j = Constants.SCREEN_HEIGHT / 16;j<Constants.SCREEN_HEIGHT;j+=Constants.SCREEN_HEIGHT / 8){
                        paint.setTextSize(Constants.SCREEN_HEIGHT / (400 / 30));
                        paint.setColor(Color.WHITE);
                        canvas.drawText("Panic!", i, j, paint);
                    }
                }
            }
            if(drunkness>290){
                paint.setColor(Color.BLACK);
                canvas.drawRect(-1, -1, Constants.SCREEN_WIDTH + 1, Constants.SCREEN_HEIGHT + 1, paint);
                paint.setTextSize(Constants.SCREEN_WIDTH / (400 / 93));
                paint.setColor(Color.RED);
                canvas.drawText("You died.", Constants.SCREEN_WIDTH / 2, Constants.SCREEN_HEIGHT / 2, paint);
            }
            drunkness += 1;
        }
    }
}
