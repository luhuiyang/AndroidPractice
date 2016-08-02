package com.example.personalaccount.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.personalaccount.adapter.InfoAdapter;
import com.example.personalaccount.entity.Accounts;
import com.example.personalaccount.entity.CostAccount;
import com.example.personalaccount.entity.GetAccount;
import com.example.personalaccount.R;
import com.example.personalaccount.db.CostAccountDAO;
import com.example.personalaccount.db.GetAccountDAO;
import com.example.personalaccount.utils.BaseActivity;
import com.example.personalaccount.utils.LogUtil;
import com.example.personalaccount.utils.TimeUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class AccountInfo extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener, SwipeMenuListView.OnMenuItemClickListener {
    public static int GOC = 0;
    public static int SOF = 0;
    public static final String FLAG = "id";
    private Button startData, finishData;
    private ImageButton btnDataSubmit;
    private Spinner addorgetSpinner;
    private SwipeMenuListView listViewAccountInfo, item;
    private List<Accounts> accountsList = new ArrayList<Accounts>();
    private ImageButton btnBack;
    InfoAdapter adapter;
    String strType = "";

    String mdata = "";
    String startDataStr, finishDataStr;



    protected static final int START_DATE_DIALOG_ID = 0;
    protected static final int FINISH_DATE_DIALOG_ID = 1;
    private int mYear, mMonth, mDay;

    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            updateDate();
//            new TimePickerDialog(AddGetAccount.this, mTimeSetListener, mHour, mMinute, false).show();
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_info);
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        String getorcost = getIntent().getStringExtra("GOC");

        if(getorcost!=null) {
            GOC = Integer.parseInt(getorcost);
        }
        initViews();
        initEvents();
    }

    /**
     * 获取支出信息
     */
    private void initCostAccounts( ) {
        CostAccountDAO costAccountinfo = new CostAccountDAO(AccountInfo.this);
        List<CostAccount> costlistinfos = costAccountinfo.getScrollData(0, (int) costAccountinfo.getCount());
        Accounts accounts;
        accountsList.removeAll(accountsList);
        for(CostAccount costAccount:costlistinfos) {
            accounts = new Accounts(costAccount.getId(), costAccount.getMoney(),costAccount.getTime(), costAccount.getType(), costAccount.getMark(), costAccount.getLocation());
            mdata = TimeUtil.splitDateAndTime(accounts.getTime())[0];
            LogUtil.d("获取日期", mdata);
            //判断日期是否在选中的期间内，不在，则不添加进List
            if((TimeUtil.timeCompare(startData.getText().toString(), mdata)!=1)&&
                    (TimeUtil.timeCompare(mdata, finishData.getText().toString())!=1)) {
                accountsList.add(accounts);
            }
        }
        Collections.sort(accountsList);
        adapter = new InfoAdapter(AccountInfo.this, R.layout.accountinfo_item, accountsList);
        listViewAccountInfo.setAdapter(adapter);
    }

    /**
     * 获取赚钱信息
     */
    private void initGetAccounts() {
        GetAccountDAO getAccountinfo = new GetAccountDAO(AccountInfo.this);
        List<GetAccount> getlistinfos = getAccountinfo.getScrollDate(0, (int) getAccountinfo.getCount());
        Accounts accounts;
//        String mdata;
        accountsList.removeAll(accountsList);
        for(GetAccount getAccount:getlistinfos) {
            accounts = new Accounts(getAccount.getId(), getAccount.getMoney(),getAccount.getTime(), getAccount.getType(), getAccount.getMark(), getAccount.getLocation());
            mdata = TimeUtil.splitDateAndTime(accounts.getTime())[0];
            LogUtil.d("获取日期", mdata+"当前显示的日期"+startData.getText().toString());
            //判断日期是否在选中的期间内，不在，则不添加进List
            if((TimeUtil.timeCompare(startData.getText().toString(), mdata)!=1)&&
                    (TimeUtil.timeCompare(mdata, finishData.getText().toString())!=1)) {
                accountsList.add(accounts);
            }
        }
        Collections.sort(accountsList);
        adapter = new InfoAdapter(AccountInfo.this, R.layout.accountinfo_item, accountsList);
        listViewAccountInfo.setAdapter(adapter);
    }

    private void initEvents() {
        startData.setOnClickListener(this);
        finishData.setOnClickListener(this);
        listViewAccountInfo.setOnItemClickListener(this);
        addorgetSpinner.setOnItemSelectedListener(this);
        btnDataSubmit.setOnClickListener(this);
        listViewAccountInfo.setOnMenuItemClickListener(this);
        btnBack.setOnClickListener(this);
    }

    private void initViews() {
        listViewAccountInfo = (SwipeMenuListView) findViewById(R.id.account_listView);
        startData = (Button) findViewById(R.id.start_date);
        finishData = (Button) findViewById(R.id.finish_date);
        btnDataSubmit = (ImageButton) findViewById(R.id.data_submit);
        addorgetSpinner = (Spinner) findViewById(R.id.add_or_get_spinner);
        btnBack = (ImageButton) findViewById(R.id.title_back);
        if (GOC==1) {
            addorgetSpinner.setSelection(1);
        }

        startData.setText(
                new StringBuilder().append(mYear).append("-")
                        .append(mMonth).append("-")
                        .append(mDay));
        finishData.setText(
                new StringBuilder().append(mYear).append("-")
                        .append(mMonth+1).append("-")
                        .append(mDay));
//        showInfo();
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(dp2px(90));
                // set a icon
                deleteItem.setIcon(R.drawable.ic_delete);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };
        listViewAccountInfo.setMenuCreator(creator);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_date:
                showDialog(START_DATE_DIALOG_ID);
                SOF = 0;
                break;
            case R.id.finish_date:
                showDialog(FINISH_DATE_DIALOG_ID);
                SOF = 1;
                break;
            case R.id.data_submit:
//                StringBuilder sb =new StringBuilder().append(mYear).append("年")
//                        .append(mMonth+1).append("月")
//                        .append(mDay+1).append("日");
                if (TimeUtil.timeCompare(finishData.getText().toString(), startData.getText().toString())!=1) {
                    Toast.makeText(AccountInfo.this, "终止时间不能小于起始时间噢~", Toast.LENGTH_SHORT).show();
                }else {
                    if (GOC == 0){
                        initCostAccounts();
                    } else {
                        initGetAccounts();
                    }
                }

                break;
            case R.id.title_back:
                finish();
                break;
            default:
                break;
        }
    }

    //点击item，将item中的值传入新的activity
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        Toast.makeText(AccountInfo.this, "修改功能暂未开放", Toast.LENGTH_SHORT).show();
        int sid = Integer.valueOf(((TextView) view.findViewById(R.id.account_id)).getText().toString());
        double smoney = Double.valueOf(((TextView) view.findViewById(R.id.account_money)).getText().toString());
        String stime = ((TextView) view.findViewById(R.id.account_time)).getText().toString();
        String stype = ((TextView) view.findViewById(R.id.account_sort)).getText().toString();
        String smark = ((TextView) view.findViewById(R.id.account_mark)).getText().toString();
        String slocation  = ((TextView) view.findViewById(R.id.account_location)).getText().toString();
        Accounts oneAccount = new Accounts(sid, smoney, stime, stype, smark, slocation);
        AlterAccount.actionStart(AccountInfo.this, oneAccount, GOC+"");
//        finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch ((int)id) {
            case 0:
                GOC = 0;
                initCostAccounts();
                break;
            case 1:
                GOC = 1;
                initGetAccounts();
                break;
        }
        LogUtil.d("监听监听",id+"");
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void updateDate() {
        if (SOF==0) {
            startData.setText(
                    new StringBuilder().append(mYear).append("-")
                            .append(mMonth+1).append("-")
                            .append(mDay));
        }else {
            StringBuilder sb =new StringBuilder().append(mYear).append("-")
                                .append(mMonth+1).append("-")
                                .append(mDay+1);
            if (TimeUtil.timeCompare(sb.toString(), startData.getText().toString())!=1) {
                Toast.makeText(AccountInfo.this, "终止时间不能小于起始时间噢~", Toast.LENGTH_SHORT).show();
//                finishData.setText(
//                        new StringBuilder().append(mYear).append("年")
//                                .append(mMonth+1).append("月")
//                                .append(mDay).append("日"));

            } else {
                finishData.setText(
                        new StringBuilder().append(mYear).append("-")
                                .append(mMonth+1).append("-")
                                .append(mDay));
            }
        }

    }

    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case START_DATE_DIALOG_ID:
                mYear = TimeUtil.getDate(startData.getText().toString())[0];
                mMonth = TimeUtil.getDate(startData.getText().toString())[1]-1;
                mDay = TimeUtil.getDate(startData.getText().toString())[2];
                return new DatePickerDialog(this, mDateSetListener, mYear, mMonth, mDay);
            case FINISH_DATE_DIALOG_ID:
                mYear = TimeUtil.getDate(finishData.getText().toString())[0];
                mMonth = TimeUtil.getDate(finishData.getText().toString())[1]-1;
                mDay = TimeUtil.getDate(finishData.getText().toString())[2];
                return new DatePickerDialog(this, mDateSetListener, mYear, mMonth, mDay);
            default:
                break;

        }
        return null;
    }



    public static void actionStart(Context context,String status) {

        Intent intent = new Intent(context, AccountInfo.class);
        intent.putExtra("GOC", status);
        context.startActivity(intent);

    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

    @Override
    public boolean onMenuItemClick(int position, SwipeMenu swipeMenu, int index) {
        int accountId = accountsList.get(position).getId();
        if(GOC==0) {
            CostAccountDAO costAccountDAO = new CostAccountDAO(AccountInfo.this);
            costAccountDAO.delete(accountId);
            Toast.makeText(AccountInfo.this, "一条支出记录已删除成功！", Toast.LENGTH_SHORT).show();

        }else{
            GetAccountDAO getAccountDAO = new GetAccountDAO(AccountInfo.this);
            getAccountDAO.delete(accountId);
            Toast.makeText(AccountInfo.this, "一条收入记录已删除成功！", Toast.LENGTH_SHORT).show();
        }
        accountsList.remove(position);
        adapter.notifyDataSetChanged();
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (GOC == 0) {
            initCostAccounts();
        } else if (GOC == 1) {
            initGetAccounts();
        }
    }
}
