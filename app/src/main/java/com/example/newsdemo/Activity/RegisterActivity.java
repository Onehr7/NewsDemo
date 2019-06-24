package com.example.newsdemo.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.example.newsdemo.R;
import com.example.newsdemo.login.Code;
import com.example.newsdemo.login.DBOpenHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity {

    private String realCode;
    private DBOpenHelper mDBOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        mDBOpenHelper = new DBOpenHelper(this);

        //将验证码用图片的形式显示出来
        mIvRegisteractivityShowcode.setImageBitmap(Code.getInstance().createBitmap());
        realCode = Code.getInstance().getCode().toLowerCase();
    }

    @BindView(R.id.rl_registeractivity_top)
    RelativeLayout mRlRegisteractivityTop;
    @BindView(R.id.iv_registeractivity_back)
    ImageView mIvRegisteractivityBack;
    @BindView(R.id.ll_registeractivity_body)
    LinearLayout mLlRegisteractivityBody;
    @BindView(R.id.et_registeractivity_username)
    EditText mEtRegisteractivityUsername;
    @BindView(R.id.et_registeractivity_password1)
    EditText mEtRegisteractivityPassword1;
    @BindView(R.id.et_registeractivity_password2)
    EditText mEtRegisteractivityPassword2;
    @BindView(R.id.et_registeractivity_phoneCodes)
    EditText mEtRegisteractivityPhonecodes;
    @BindView(R.id.iv_registeractivity_showCode)
    ImageView mIvRegisteractivityShowcode;


    @OnClick({
            R.id.iv_registeractivity_back,
            R.id.iv_registeractivity_showCode,
            R.id.bt_registeractivity_register
    })
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_registeractivity_back: //返回登录页面
                Intent intent1 = new Intent(this, loginActivity.class);
                startActivity(intent1);
                finish();
                break;
            case R.id.iv_registeractivity_showCode:    //改变随机验证码的生成
                mIvRegisteractivityShowcode.setImageBitmap(Code.getInstance().createBitmap());
                realCode = Code.getInstance().getCode().toLowerCase();
                break;
            case R.id.bt_registeractivity_register:    //注册按钮
                //获取用户输入的用户名、密码、验证码
                String username = mEtRegisteractivityUsername.getText().toString().trim();
                String password = mEtRegisteractivityPassword1.getText().toString().trim();
                String repassword = mEtRegisteractivityPassword2.getText().toString().trim();
                String phoneCode = mEtRegisteractivityPhonecodes.getText().toString().toLowerCase();

                //判断是否存在此用户名
                DBOpenHelper dbOpenHelper = new DBOpenHelper(RegisterActivity.this);
                SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
                int flag = 0;
                Cursor cursor = db.query("user",null,null,null,null,null,null);
                if(cursor.moveToFirst()){
                    do{
                        String Username = cursor.getString(cursor.getColumnIndex("name"));

                        if(username.equals(Username)){
                            flag = 1;
                        }
                    }while(cursor.moveToNext());
                    cursor.close();
                }

                //注册验证
                if(flag == 0){
                if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(phoneCode) ) {
                    if (phoneCode.equals(realCode)) {
                        //将用户名和密码加入到数据库中
                        if(password.equals(repassword)){
                            mDBOpenHelper.add(username, password);
                            SharedPreferences sp = getSharedPreferences("login", Context.MODE_PRIVATE);
                            sp.edit().putString("username", username).apply();
                            sp.edit().putString("password", password).apply();

                            Intent intent2 = new Intent(this, MainActivity.class);
                            startActivity(intent2);
                            finish();
                            Toast.makeText(this,  "验证通过，注册成功", Toast.LENGTH_SHORT).show();
                            db.close();
                        }
                        else{
                            Toast.makeText(this,  "两次输入密码不一致，请重新注册", Toast.LENGTH_SHORT).show();
                        }

                    }
                    else {
                        Toast.makeText(this, "验证码错误,注册失败", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(this, "未完善信息，注册失败", Toast.LENGTH_SHORT).show();
                }
                }

                else {
                    Toast.makeText(this, "此用户已被注册，请重新输入", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
