package com.example.newsdemo.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.newsdemo.R;

/**
 * @description: 意见反馈功能实现
 */
public class FeedbackActivity extends AppCompatActivity {

    private ImageView imageViewFeedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        imageViewFeedback = findViewById(R.id.iv_feedback_back);
        imageViewFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FeedbackActivity.this , MainActivity.class);
                intent.putExtra("fragment_flag",R.id.layout_myhome);
                startActivity(intent);
                finish();
            }
        });
    }
}
