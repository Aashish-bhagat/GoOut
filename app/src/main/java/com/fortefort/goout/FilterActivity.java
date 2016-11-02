package com.fortefort.goout;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class FilterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        final FrameLayout frame = (FrameLayout) findViewById(R.id.framef);
        final ValueAnimator animator = ValueAnimator.ofInt(0,100);

        frame.post(new Runnable() {
            @Override
            public void run() {

                final float height = frame.getHeight();
                final float width = frame.getWidth();
                final float finalwidth = width/5f ;
                final float finalheight = height/5f ;
                final float topC = (frame.getTop()-height/2f)*0.9f;
                final float finaltopC = frame.getTop()-finalheight/2f;
                System.out.printf(height+" "+width+"\n"+finalwidth+" "+finalheight);
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int namef = (Integer) animation.getAnimatedValue();
                        frame.setScaleX((width - (width-finalwidth)*namef/100f)/width);
                        frame.setScaleY((height - (height - finalheight)*namef/100f)/height);
                        frame.setTranslationY(finaltopC+((topC-finaltopC) *namef/100f));
                    }
                });
                animator.setDuration(400);
                animator.start();
            }
        });

        final FrameLayout dd = (FrameLayout) findViewById(R.id.filters);
        dd.post(new Runnable() {
            @Override
            public void run() {
              ImageView iv = (ImageView) findViewById(R.id.shadowView);
                iv.setScaleY(1.35f*dd.getHeight()/iv.getHeight());
                iv.setTranslationY(dd.getHeight()*0.053f);
            }
        });
    }

}
