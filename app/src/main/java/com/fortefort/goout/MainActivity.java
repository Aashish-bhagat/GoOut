package com.fortefort.goout;

import android.graphics.Matrix;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {


    long startTime;
    long LONG_PRESS_THRESHOLD = 1000;
    boolean isPressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageButton button = (ImageButton) findViewById(R.id.calltoaction);

        ImageView iv = (ImageView) findViewById(R.id.imageView);
        final Matrix origMatrix = iv.getImageMatrix();
        final Matrix rotatedMatrix = iv.getImageMatrix();
        System.out.println(rotatedMatrix);
        rotatedMatrix.postRotate(180.0f, iv.getDrawable().getBounds().width()/2, iv.getDrawable().getBounds().height()/2);
        System.out.println(rotatedMatrix);

        final RotateAnimation spinn = new RotateAnimation(0,180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        spinn.setDuration(LONG_PRESS_THRESHOLD);
        spinn.setInterpolator(new LinearInterpolator());
        spinn.setAnimationListener(new Animation.AnimationListener() {
            ImageView iv = (ImageView)findViewById(R.id.imageView);
            @Override
            public void onAnimationStart(Animation animation) {}
            @Override
            public void onAnimationRepeat(Animation animation) {}
            @Override
            public void onAnimationEnd(Animation animation) {
                if(isPressed) iv.setRotation(180f);
            }

        });

        button.setOnTouchListener(new View.OnTouchListener() {
            ImageView iv = (ImageView) findViewById(R.id.imageView);
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                ImageButton btn = (ImageButton) v;
                if(event.getAction() == MotionEvent.ACTION_DOWN){

                    btn.setImageResource(R.mipmap.buttonactive);
                    startTime = System.currentTimeMillis();
                    iv.startAnimation(spinn);
                    isPressed = true;
                }
                else if(event.getAction() == MotionEvent.ACTION_UP){

                    iv.clearAnimation();
                    iv.setRotation(0); isPressed = false;

                    btn.setImageResource(R.mipmap.buttonpassive);
                    if(System.currentTimeMillis()-startTime>LONG_PRESS_THRESHOLD){
                        Toast.makeText(getBaseContext(), "LongPress", Toast.LENGTH_LONG).show();
                    }

                }

                return true;
            }
        });

    }

    public void onRelease(View v){
        ImageButton btn;

    }

}
