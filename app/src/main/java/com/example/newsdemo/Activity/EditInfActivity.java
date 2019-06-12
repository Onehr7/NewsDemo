package com.example.newsdemo.Activity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.newsdemo.R;
import com.example.newsdemo.login.DBOpenHelper;

/**
 * @description:实现修改密码功能
 */
public class EditInfActivity extends AppCompatActivity {

    private EditText editTextoldPassword,editTextnewPassword,editTextrenewPassword;
    private ImageView imageViewback;
    private Button btSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_inf);

        imageViewback = findViewById(R.id.iv_editpassword_back);
        imageViewback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditInfActivity.this , MainActivity.class);
                intent.putExtra("fragment_flag",R.id.layout_myhome);
                startActivity(intent);
                finish();
            }
        });



        btSave = findViewById(R.id.bt_save_password);
        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sp = getSharedPreferences("login", Context.MODE_PRIVATE);
                String password = sp.getString("password", null);
                String username = sp.getString("username", null);

                editTextoldPassword = findViewById(R.id.et_old_password);
                String oldpassword = editTextoldPassword.getText().toString().trim();

                editTextnewPassword = findViewById(R.id.et_new_password);
                String newpassword = editTextnewPassword.getText().toString().trim();

                editTextrenewPassword = findViewById(R.id.et_new_password2);
                String renewpassword = editTextrenewPassword.getText().toString().trim();

                if(!password.equals(oldpassword)){
                    Toast.makeText(EditInfActivity.this, "旧密码不匹配，请重新输入", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(!newpassword.equals(renewpassword)){
                        Toast.makeText(EditInfActivity.this, "请输入一样的新密码", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        DBOpenHelper dbOpenHelper = new DBOpenHelper(EditInfActivity.this);
                        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
                        ContentValues values = new ContentValues();
                        values.put("password",newpassword);
                        String whereClause = "name=?";
                        String[] whereArgs = {String.valueOf(username)};
                        db.update("user",values,whereClause,whereArgs);

                        Toast.makeText(EditInfActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void update(SQLiteDatabase db){




    }
}
