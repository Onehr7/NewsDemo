package com.example.newsdemo.Activity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.newsdemo.R;
import com.example.newsdemo.login.DBOpenHelper;

/**
 * @description:
 */
public class FindpasswordActivity extends AppCompatActivity {

    private ImageView ivFindoutPassword;
    private EditText etUsername;
    private Button btSelect;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.findout_password);

        //返回登录页面
        ivFindoutPassword = findViewById(R.id.iv_findoutpassword_back);
        ivFindoutPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FindpasswordActivity.this, loginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //获取输入的用户名信息
        etUsername = findViewById(R.id.et_findoutpassword);

        //查询对应用户名的密码
        btSelect = findViewById(R.id.bt_findoutpassword);
        btSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBOpenHelper dbOpenHelper = new DBOpenHelper(FindpasswordActivity.this);
                    SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
                    String username  = etUsername.getText().toString();
                    int flag = 0;

                    Cursor cursor = db.query("user",null,null,null,null,null,null);
                    if(cursor.moveToFirst()){
                        do{
                            String Username = cursor.getString(cursor.getColumnIndex("name"));
                            String password = cursor.getString(cursor.getColumnIndex("password"));

                            if(username.equals(Username)){
                                flag = 1;
                                Toast.makeText(FindpasswordActivity.this, "您的密码为："+password,Toast.LENGTH_SHORT).show();
                            }
                        }while(cursor.moveToNext());

                        if(flag == 0){
                            Toast.makeText(FindpasswordActivity.this, "未找到该用户，请前往注册",Toast.LENGTH_SHORT).show();
                        }

                    cursor.close();
                }
            }
        });
    }
}
