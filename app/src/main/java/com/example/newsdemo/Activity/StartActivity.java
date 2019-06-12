package com.example.newsdemo.Activity;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import com.example.newsdemo.R;

public class StartActivity extends Activity {

    private ImageView imageView;
    private AnimationDrawable animationDrawable;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);

        int duration = 0;

        imageView = findViewById(R.id.imageView);

        imageView.setBackgroundResource(R.drawable.start_frame_animation);
        animationDrawable = (AnimationDrawable) imageView.getBackground();

        imageView.post(new Runnable() {
            @Override
            public void run() {
                animationDrawable.start();
            }
        });

        for (int i = 0; i < animationDrawable.getNumberOfFrames(); i++) {
            duration += animationDrawable.getDuration(i);
        }

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent();

                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                //不允许返回之前开机动画intent
                intent.setClass(StartActivity.this, loginActivity.class);
                startActivity(intent);
            }
        }, duration);
    }
}