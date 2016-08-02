package com.example.personalaccount.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.personalaccount.entity.Accounts;
import com.example.personalaccount.entity.CostAccount;
import com.example.personalaccount.entity.GetAccount;
import com.example.personalaccount.R;
import com.example.personalaccount.db.CostAccountDAO;
import com.example.personalaccount.db.GetAccountDAO;
import com.example.personalaccount.ui.ClearEditText;
import com.example.personalaccount.utils.BaseActivity;
import com.example.personalaccount.utils.LogUtil;
import com.example.personalaccount.utils.TimeUtil;

/**
 * Created by Administrator on 2015/3/10.
 */
public class AlterAccount extends BaseActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    protected static final int DATE_DIALOG_ID = 0;
    protected static final int TIME_DIALOG_ID = 1;
    private static int GOC = 0;
    private Spinner addorgetSpinner,sortSpinner1, sortSpinner2;
    public String addorgetOption, sortOption;
    String[] sortString;
    private Button btnSubmit, btnExist;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private ClearEditText mMoney;
    private EditText markContent;
    private TextView mlocation, timeTextView, dateTextView, hiddenId;
    private ImageButton btnBack;
    //定义类别2数据，用于二级菜单
    private String[][] sort2=new String[][]{
            {"小类别11","小类别12","小类别13","小类别14"},
            {"小类别21","小类别22","小类别23","小类别24"},
            {"小类别31","小类别32","小类别33","小类别34"},
            {"小类别41","小类别42","小类别43","小类别44"}};
    //声明数组适配器，用来填充类别1列表
    private ArrayAdapter<CharSequence> adaptersort2 = null;
    private TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            mHour =hourOfDay;
            mMinute = minute;
            updateDisplay();
        }
    };
    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear+1;
            mDay = dayOfMonth;
            updateDisplay();
//            new TimePickerDialog(AddGetAccount.this, mTimeSetListener, mHour, mMinute, false).show();
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addgetaccount);
        initViews();
        initEvents();
        addorgetSpinner.setEnabled(false);
    }

    //启动本Activity的方法，供AccountInfo调用
    public static void actionStart(Context context,Accounts accounts,String status) {

        Intent intent = new Intent(context, AlterAccount.class);
        intent.putExtra("oneaccount", accounts);

        intent.putExtra("getorcost", status);
        context.startActivity(intent);

    }

    private void initViews() {
        Accounts oneAccount = (Accounts) getIntent().getSerializableExtra("oneaccount");
        String getorcost = getIntent().getStringExtra("getorcost");
        sortString = oneAccount.getType().split(" ");
        LogUtil.d(sortString[0], sortString[1]);

        LogUtil.d("接受数据", oneAccount.getMoney()+"");
        addorgetSpinner = (Spinner) findViewById(R.id.add_or_get_spinner);
        sortSpinner1 = (Spinner) findViewById(R.id.item_select1);
        sortSpinner2 = (Spinner) findViewById(R.id.item_select2);
        btnSubmit = (Button) findViewById(R.id.btn_submit);
        mMoney = (ClearEditText) findViewById(R.id.input_money);
        markContent = (EditText) findViewById(R.id.extra_content);
        mlocation = (TextView) findViewById(R.id.location_textview);
        dateTextView = (TextView) findViewById(R.id.date_textview);
        timeTextView = (TextView) findViewById(R.id.time_textview);
        btnExist = (Button) findViewById(R.id.btn_exist);
        hiddenId = (TextView) findViewById(R.id.hidden_id);

        java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
        hiddenId.setText(oneAccount.getId()+"");
        mMoney.setText(String.valueOf(df.format(oneAccount.getMoney())));
        markContent.setText(oneAccount.getMark().toString());
        mlocation.setText(oneAccount.getLocation().toString());
        dateTextView.setText(TimeUtil.splitDateAndTime(oneAccount.getTime())[0]);
        timeTextView.setText(TimeUtil.splitDateAndTime(oneAccount.getTime())[1]);
        btnBack = (ImageButton) findViewById(R.id.title_back);
        if (getorcost.equals("1")) {
            GOC = 1;
            addorgetSpinner.setSelection(1);
        }

//        sort.setSelection(1); //设置Spinner初始值

    }

    private void initEvents() {
        dateTextView.setOnClickListener(this);
        timeTextView.setOnClickListener(this);
        sortSpinner1.setOnItemSelectedListener(new Sort1OnItemSelectedListener());
        sortSpinner2.setOnItemSelectedListener(new Sort2OnItemSelectedListener());
        btnSubmit.setOnClickListener(this);
        btnExist.setOnClickListener(this);
        btnBack.setOnClickListener(this);
    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (view.getId()) {
            case R.id.add_or_get_spinner:
//                addorgetSpinner.setClickable(false);
//                addorgetSpinner.setEnabled(false);
                addorgetOption = parent.getItemAtPosition(position).toString();
                break;
            default:
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.date_textview:
                showDialog(DATE_DIALOG_ID);
                break;
            case R.id.time_textview:
                showDialog(TIME_DIALOG_ID);
                break;
            case R.id.btn_submit:
                addorgetOption = addorgetSpinner.getSelectedItem().toString();
                sortOption = sortSpinner1.getSelectedItem().toString()+" "+sortSpinner2.getSelectedItem().toString();
                String money = mMoney.getText().toString();
                if(!money.isEmpty()&&GOC==0) {
                    CostAccountDAO costAccountDAO = new CostAccountDAO(AlterAccount.this);
                    CostAccount costAccount = new CostAccount(Integer.valueOf(hiddenId.getText().toString()),
                            Double.parseDouble(money),
                            dateTextView.getText().toString()+" "+timeTextView.getText().toString(),
//                            sortSpinner.getSelectedItem().toString(),
                            sortOption,
                            markContent.getText().toString(),
                            mlocation.getText().toString());
                    costAccountDAO.update(costAccount);
                    Toast.makeText(AlterAccount.this, "一条支出记录已修改成功！", Toast.LENGTH_SHORT).show();
                    finish();

                }else if(!money.isEmpty()&&GOC==1) {
                    GetAccountDAO getAccountDAO = new GetAccountDAO(AlterAccount.this);
                    GetAccount getAccount = new GetAccount(Integer.valueOf(hiddenId.getText().toString()),
                            Double.parseDouble(money),
                            dateTextView.getText().toString()+" "+timeTextView.getText().toString(),
                            sortOption,
                            markContent.getText().toString(),
                            mlocation.getText().toString());
                    getAccountDAO.update(getAccount);
                    Toast.makeText(AlterAccount.this, "一条收入记录已修改成功！", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(AlterAccount.this, "修改也要有金额噢~",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_exist:
                finish();
                break;
            case R.id.title_back:
                finish();
                break;
            default:
                break;
        }
    }

    private void updateDisplay() {
        dateTextView.setText(
                new StringBuilder().append(mYear).append("-")
                        .append(mMonth).append("-")
                        .append(mDay).append(" "));
        timeTextView.setText(
                new StringBuilder().append(mHour).append("-")
                        .append(mMinute).append("-"));
    }

    private class  Sort1OnItemSelectedListener implements AdapterView.OnItemSelectedListener {
        //选择省份，触发类别1下拉列表框
        @Override
        public void onItemSelected(AdapterView<?> adapter,View view,int position,long id) {
            //使用ArrayAdapter转换数据
            AlterAccount.this.adaptersort2 =new ArrayAdapter<CharSequence>(
                    AlterAccount.this,
                    android.R.layout.simple_spinner_item,
                    AlterAccount.this.sort2[position]);
            //使用adapterCity数据适配器填充spinCity
            AlterAccount.this.sortSpinner2.setAdapter(AlterAccount.this.adaptersort2);

        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            //没有选择时执行
        }
    }
    //OnItemSelected监听器
    private class Sort2OnItemSelectedListener implements AdapterView.OnItemSelectedListener {
        //选择类别1，触发显示选择的类别
        @Override
        public void onItemSelected(AdapterView<?> adapter,View view,int position,long id) {
            //获取选择的项的值
            String sInfo=adapter.getItemAtPosition(position).toString();
            Toast.makeText(getApplicationContext(), sInfo, Toast.LENGTH_LONG).show();
        }
        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            //没有选择时执行
        }
    }
}
