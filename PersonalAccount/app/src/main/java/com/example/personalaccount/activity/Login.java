package com.example.personalaccount.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.example.personalaccount.db.PasswordDAO;
import com.example.personalaccount.utils.BaseActivity;
import com.example.personalaccount.ui.ClearEditText;
import com.example.personalaccount.R;

public class Login extends BaseActivity {

    private Button btnLogin;
    private PasswordDAO pwdDAO = new PasswordDAO(Login.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        if (pwdDAO.getCount()==0) {
            Intent intent = new Intent(Login.this, MainActivity.class);
            startActivity(intent);
        } else {
            init();
            final ClearEditText passwordEditText = (ClearEditText) findViewById(R.id.passworded);
            btnLogin.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {

                    if(TextUtils.isEmpty(passwordEditText.getText())){
                        passwordEditText.setShakeAnimation();
                        Toast.makeText(Login.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Intent intent = new Intent(Login.this, MainActivity.class);
                    PasswordDAO passwordDao = new PasswordDAO(Login.this);

                    //判断密码是否正确
                    if (passwordDao.find().getPassword().equals(passwordEditText.getText().toString())) {
                        startActivity(intent);
                    } else {
                        passwordEditText.setShakeAnimation();
                        Toast.makeText(Login.this, "请输入正确的密码", Toast.LENGTH_SHORT).show();
                        passwordEditText.setText("");
                    }
                }
            });
        }
    }

    private void init() {
        btnLogin = (Button) findViewById(R.id.btnLogin);

    }

}
