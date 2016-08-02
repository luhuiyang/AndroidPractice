package com.example.personalaccount.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.personalaccount.R;
import com.example.personalaccount.db.CostAccountDAO;
import com.example.personalaccount.db.GetAccountDAO;
import com.example.personalaccount.utils.ActivityCollector;
import com.example.personalaccount.utils.BaseActivity;
import com.example.personalaccount.utils.LogUtil;
import com.example.personalaccount.utils.TimeUtil;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private Button btnAdd, btnHistory, btnSetPassword, btnExit;
//    private TextView totalTextView;
    double totalCostMoney = 0.00;
    double totalGetMoney = 0.00;
    String finishTime = TimeUtil.showNowTime();
    String startTime = TimeUtil.showStartTime();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initEvents();
    }

    // 事件初始化
    private void initEvents() {
        btnAdd.setOnClickListener(this);
        btnHistory.setOnClickListener(this);
        btnSetPassword.setOnClickListener(this);
        btnExit.setOnClickListener(this);
    }

    // view初始化
    private void initView() {
        btnAdd = (Button) findViewById(R.id.btn_add);
        btnHistory = (Button) findViewById(R.id.btn_history);
        btnSetPassword = (Button) findViewById(R.id.btn_setpassword);
        btnExit = (Button) findViewById(R.id.btn_exit);
//        totalTextView = (TextView) findViewById(R.id.total_textView);

        LogUtil.d("时间分割", startTime+"----"+finishTime);



    }

    //点击事件
    @Override
    public void onClick(View v) {
        Intent intent;
        switch(v.getId()) {
            case R.id.btn_add:
                intent = new Intent(MainActivity.this, AddGetAccount.class);
                startActivity(intent);
                break;
            case R.id.btn_history:
                intent = new Intent(MainActivity.this, AccountInfo.class);
                startActivity(intent);
                break;
            case R.id.btn_setpassword:
                intent = new Intent(MainActivity.this, Settings.class);
                startActivity(intent);
                break;
            case R.id.btn_exit:
                ActivityCollector.finishAll();
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityCollector.finishAll();
    }

    @Override
    protected void onResume() {
        super.onResume();
        CostAccountDAO costAccountinfo = new CostAccountDAO(MainActivity.this);
        GetAccountDAO getAccountinfo = new GetAccountDAO(MainActivity.this);
        totalCostMoney = costAccountinfo.gettotal(startTime, finishTime);
        totalGetMoney = getAccountinfo.gettotal(startTime, finishTime);
//        DecimalFormat formatVal =newDecimalFormat("##.##");
//        String formatted = formatVal.format(s);
        java.text.DecimalFormat df = new java.text.DecimalFormat("0.00");
//        btnAdd.setText("花费￥ " + String.valueOf(df.format(totalCostMoney)) + "元");
//        btnHistory.setText("收入￥ " + String.valueOf(df.format(totalGetMoney)) + "元");
        LogUtil.d("收入费用", totalGetMoney+"");
    }
}
