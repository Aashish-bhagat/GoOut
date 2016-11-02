package com.fortefort.goout;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import com.inmobi.ads.*;
import com.inmobi.sdk.*;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {


    long startTime;
    long LONG_PRESS_THRESHOLD = 450;
    boolean isPressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InMobiSdk.init(this, "f8a1a41a520343c39962e79b25ee4072"); //'this' is used specify context
        ServerHandle.init(this);

        ImageButton button = (ImageButton) findViewById(R.id.calltoaction);

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
                        Intent startFilterIntent = new Intent(getBaseContext(), FilterActivity.class);
                        startActivity(startFilterIntent);
                    }
                    else{
                        JSONObject json = new JSONObject();
                        try {
                            json.put("cash: ", "kuldeep");
                            json.put("LOL","meh");
                            }catch(Exception e){e.printStackTrace();}
                        ServerHandle h = new ServerHandle();
                        h.execute(json);
                    }

                }
                else return false;
                return true;
            }
        });

    }

    @Override
    protected void onResume(){
        super.onResume();
        InMobiBanner banner = (InMobiBanner)findViewById(R.id.banner);
        banner.load();
        //System.out.println("YAYR");
    }
}
