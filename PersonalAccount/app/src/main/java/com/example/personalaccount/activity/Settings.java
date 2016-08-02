package com.example.personalaccount.activity;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.personalaccount.R;
import com.example.personalaccount.db.PasswordDAO;
import com.example.personalaccount.ui.ClearEditText;
import com.example.personalaccount.utils.BaseActivity;
import com.example.personalaccount.utils.Password;

public class Settings extends BaseActivity implements View.OnClickListener {

    //页面控件
    private ClearEditText oldPassword, newPassword;
    Button btnSubmit, btnExist;
    private ImageButton btnBack;
    LinearLayout oldPasswordLinearLayout;

    // 数据库相关的
    PasswordDAO passwordDAO = new PasswordDAO(Settings.this);
    Password password = new Password();
    String newPwd, oldPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initView();
        initEvent();
    }

    private void initView() {
        oldPasswordLinearLayout = (LinearLayout) findViewById(R.id.ole_password_linearlayout);
        oldPassword = (ClearEditText) findViewById(R.id.old_password);
        newPassword = (ClearEditText) findViewById(R.id.new_password);
        btnExist = (Button) findViewById(R.id.btn_exist);
        btnSubmit = (Button) findViewById(R.id.btn_submit);
        btnBack = (ImageButton) findViewById(R.id.title_back);
        if (passwordDAO.getCount()==0) {
            oldPasswordLinearLayout.setVisibility(View.GONE);
        }
    }

    private void initEvent() {
        btnExist.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        btnBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_exist:
                finish();
                break;
            case R.id.btn_submit:
                checkOldPassword();
                break;
            case R.id.title_back:
                finish();
                break;
            default:
                break;
        }
    }

    private void checkOldPassword() {

        newPwd = newPassword.getText().toString();
        oldPwd = oldPassword.getText().toString();
        if ((passwordDAO.getCount()==0)&&(!newPwd.equals(""))) {
            Toast.makeText(Settings.this, "密码设置成功啦~", Toast.LENGTH_SHORT).show();
            password.setPassword(newPwd);
            passwordDAO.add(password);
            finish();
        } else if ((passwordDAO.getCount()==0)&&(newPwd.equals(""))) {
            Toast.makeText(Settings.this, "当前未设置密码噢~", Toast.LENGTH_SHORT).show();

        } else if ((oldPwd.equals(passwordDAO.find().getPassword()))&&(!newPwd.equals(""))) {
            Toast.makeText(Settings.this, "密码更改成功啦~", Toast.LENGTH_SHORT).show();
            password.setPassword(newPwd);
            passwordDAO.update(password);
            finish();
        }
        else if (((passwordDAO.getCount()!=0)&&(oldPwd==""))||passwordDAO.getCount()!=0&&!oldPwd.equals(passwordDAO.find().getPassword())) {
            Toast.makeText(Settings.this, "旧的密码不正确，请重新输入噢~", Toast.LENGTH_SHORT).show();
            oldPassword.requestFocus(); //焦点返回旧密码输入框
            oldPassword.setShakeAnimation();
            oldPassword.setText("");
            newPassword.setText("");
        }
        else if (oldPwd.equals(passwordDAO.find().getPassword())&&newPwd.equals("")) {
            Toast.makeText(Settings.this, "密码设置已被取消~", Toast.LENGTH_SHORT).show();
            passwordDAO.delete(oldPwd);
            finish();
        }
    }
}
