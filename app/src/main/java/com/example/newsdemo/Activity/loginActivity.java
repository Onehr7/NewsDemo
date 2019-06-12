package com.example.newsdemo.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newsdemo.Fragment.MyHomeFragment;
import com.example.newsdemo.R;
import com.example.newsdemo.login.DBOpenHelper;
import com.example.newsdemo.login.User;

import java.util.ArrayList;


public class loginActivity extends AppCompatActivity {



    private DBOpenHelper mDBOpenHelper;
    private EditText mEtLoginactivityUsername;
    private EditText mEtLoginactivityPassword;
    private TextView mTvforgrtPassword;

    private Button blogin;
    private Button bregist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences sp = getSharedPreferences("login", Context.MODE_PRIVATE);
        String flag = sp.getString("username", null);
        if(flag != null){
            Intent intent = new Intent(loginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        else {
            mDBOpenHelper = new DBOpenHelper(this);
            mEtLoginactivityUsername = findViewById(R.id.tv_loginactivity_username);
            mEtLoginactivityPassword = findViewById(R.id.tv_loginactivity_password);
            blogin = findViewById(R.id.bt_loginactivity_login);
            blogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String username = mEtLoginactivityUsername.getText().toString().trim();
                    String Upassword = mEtLoginactivityPassword.getText().toString().trim();
                    if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(Upassword)) {
                        ArrayList<User> data = mDBOpenHelper.getAllData();
                        boolean match = false;
                        for(int i=0;i<data.size();i++) {
                            User user = data.get(i);
                            if (username.equals(user.getName()) && Upassword.equals(user.getPassword())){
                                match = true;
                                break;
                            }else{
                                match = false;
                            }
                        }
                        if (match) {

                            SharedPreferences sp = getSharedPreferences("login", Context.MODE_PRIVATE);
                            sp.edit().putString("username", mEtLoginactivityUsername.getText().toString()).apply();
                            sp.edit().putString("password", mEtLoginactivityPassword.getText().toString()).apply();

                            Toast.makeText(loginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(loginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();//销毁此Activity
                        }else {
                            Toast.makeText(loginActivity.this, "用户名或密码不正确，请重新输入", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(loginActivity.this, "请输入你的用户名或密码", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            bregist = findViewById(R.id.tv_loginactivity_register);
            bregist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(loginActivity.this, RegisterActivity.class));
                }
            });

            mTvforgrtPassword = findViewById(R.id.tv_loginactivity_forget);
            mTvforgrtPassword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(loginActivity.this, FindpasswordActivity.class));

                }
            });
        }

    }
}




