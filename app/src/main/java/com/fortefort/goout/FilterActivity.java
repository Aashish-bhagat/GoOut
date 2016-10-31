package com.fortefort.goout;

import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

public class FilterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        final FrameLayout frame = (FrameLayout) findViewById(R.id.framef);
        final int height = frame.getHeight();
        final int width = frame.getWidth();
        final float finalwidth = width/10f ;
        final float finalheight = height/10f ;
        ValueAnimator animator = ValueAnimator.ofFloat(0,1);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
              float namef = (float) animation.getAnimatedFraction();
                frame.setScaleX(width - (width-finalwidth)*namef);
                frame.setScaleY(height - (height - finalheight)*namef);
                //frame.setTranslationY();
            }
        });
    }

}
