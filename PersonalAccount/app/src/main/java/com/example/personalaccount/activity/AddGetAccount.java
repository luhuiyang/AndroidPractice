package com.example.personalaccount.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
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

import com.example.personalaccount.entity.CostAccount;
import com.example.personalaccount.entity.GetAccount;
import com.example.personalaccount.R;
import com.example.personalaccount.db.CostAccountDAO;
import com.example.personalaccount.db.GetAccountDAO;
import com.example.personalaccount.ui.ClearEditText;
import com.example.personalaccount.utils.BaseActivity;

import java.util.Calendar;
import java.util.List;
import android.os.Handler;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class AddGetAccount extends BaseActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

//    private ViewPager vp;
//    private GetorCostAdapter adapter;
//    private List<View> getorcostviews;

    public static final int SHOW_LOCATION = 0;

    private LocationManager locationManager;

    private String provider;


    protected static final int DATE_DIALOG_ID = 0;
    protected static final int TIME_DIALOG_ID = 1;
    private Spinner addorgetSpinner,sortSpinner1, sortSpinner2;
    public String addorgetOption, sortOption;
    private Button btnSubmit, btnExist;
    private ImageButton btnBack;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private ClearEditText mMoney;
    private EditText markContent;
    private TextView mlocation, timeTextView, dateTextView;
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
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            updateDisplay();
//            new TimePickerDialog(AddGetAccount.this, mTimeSetListener, mHour, mMinute, false).show();
        }
    };

    LocationListener locationListener = new LocationListener() {

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onProviderDisabled(String provider) {
        }

        @Override
        public void onLocationChanged(Location location) {
            // 更新当前设备的位置信息
            showLocation(location);
        }
    };

    private Handler handler = new Handler() {

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SHOW_LOCATION:
                    String currentPosition = (String) msg.obj;
                    mlocation.setText(currentPosition);
                    break;
                default:
                    break;
            }
        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addgetaccount);
        initViews();
        initEvents();
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        updateDisplay();
    }

    private void updateDisplay() {
        dateTextView.setText(
                new StringBuilder().append(mYear).append("-")
                        .append(mMonth+1).append("-")
                        .append(mDay).append(" "));
        timeTextView.setText(
                new StringBuilder().append(mHour).append("-")
                        .append(mMinute));
    }

    private void initEvents() {
        dateTextView.setOnClickListener(this);
        timeTextView.setOnClickListener(this);
        addorgetSpinner.setOnItemSelectedListener(this);
        sortSpinner1.setOnItemSelectedListener(new Sort1OnItemSelectedListener());
        sortSpinner2.setOnItemSelectedListener(new Sort2OnItemSelectedListener());
        btnSubmit.setOnClickListener(this);
        btnExist.setOnClickListener(this);
        mlocation.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        setPricePoint(mMoney);
    }

    private void initViews() {
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
        btnBack = (ImageButton) findViewById(R.id.title_back);


//        LayoutInflater inflater = LayoutInflater.from(this);

//        getorcostviews = new ArrayList<View>();
//        getorcostviews.add(inflater.inflate(R.layout.cost_select, null));
//        getorcostviews.add(inflater.inflate(R.layout.get_select, null));

//        adapter = new GetorCostAdapter(getorcostviews, this);
//        vp = (ViewPager) findViewById(R.id.getorcost_viewpager);
//        vp.setAdapter(adapter);

    }

    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this, mDateSetListener, mYear, mMonth, mDay);
            case TIME_DIALOG_ID:
                return new TimePickerDialog(this, mTimeSetListener, mHour, mMinute, false);
            default:
                break;

        }
        return null;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (view.getId()) {
            case R.id.add_or_get_spinner:
                addorgetOption = parent.getItemAtPosition(position).toString();
//                markContent.setText(addorgetOption);
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
                String realLocation;
                if (mlocation.getText().toString().equals("点击获取当前位置")) {
                    realLocation = "";
                } else {
                    realLocation = mlocation.getText().toString();
                }
                if(!money.isEmpty()&&addorgetOption.equals("支出")) {
                    CostAccountDAO costAccountDAO = new CostAccountDAO(AddGetAccount.this);
                    CostAccount costAccount = new CostAccount(costAccountDAO.getMaxId()+1,
                            Double.parseDouble(money),
                            dateTextView.getText().toString()+timeTextView.getText().toString(),
                            sortOption,
                            markContent.getText().toString(),
                            realLocation);
                    costAccountDAO.add(costAccount);
                    Toast.makeText(AddGetAccount.this, "一笔新的支出已记录！", Toast.LENGTH_SHORT).show();
                    finish();

                }else if(!money.isEmpty()&&addorgetOption.equals("收入")) {
                    GetAccountDAO getAccountDAO = new GetAccountDAO(AddGetAccount.this);
                    GetAccount getAccount = new GetAccount(getAccountDAO.getMaxId()+1,
                            Double.parseDouble(money),
                            dateTextView.getText().toString()+timeTextView.getText().toString(),
                            sortOption,
                            markContent.getText().toString(),
                            realLocation);
                    getAccountDAO.add(getAccount);
                    Toast.makeText(AddGetAccount.this, "一笔新的收入已记录！", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(AddGetAccount.this, "没有金额是不能记账的噢~",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_exist:
                finish();
                break;
            case R.id.location_textview:
                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                // 获取所有可用的位置提供器
                List<String> providerList = locationManager.getProviders(true);
                if (providerList.contains(LocationManager.GPS_PROVIDER)) {
                    provider = LocationManager.GPS_PROVIDER;
                } else if (providerList.contains(LocationManager.NETWORK_PROVIDER)) {
                    provider = LocationManager.NETWORK_PROVIDER;
                } else {
                    // 当没有可用的位置提供器时，弹出Toast提示用户
                    Toast.makeText(this, "No location provider to use",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                Location location = locationManager.getLastKnownLocation(provider);
                if (location != null) {
                    // 显示当前设备的位置信息
                    showLocation(location);
                }
                locationManager.requestLocationUpdates(provider, 5000, 1,
                        locationListener);
                break;
            case R.id.title_back:
                finish();
                break;
            default:
                break;

        }
    }

    private void showLocation(final Location location) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // 组装反向地理编码的接口地址
                    StringBuilder url = new StringBuilder();
                    url.append("http://maps.googleapis.com/maps/api/geocode/json?latlng=");
                    url.append(location.getLatitude()).append(",")
                            .append(location.getLongitude());
                    url.append("&sensor=false");
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpGet httpGet = new HttpGet(url.toString());
                    // 在请求消息头中指定语言，保证服务器会返回中文数据
                    httpGet.addHeader("Accept-Language", "zh-CN");
                    HttpResponse httpResponse = httpClient.execute(httpGet);
                    if (httpResponse.getStatusLine().getStatusCode() == 200) {
                        HttpEntity entity = httpResponse.getEntity();
                        String response = EntityUtils.toString(entity, "utf-8");
                        JSONObject jsonObject = new JSONObject(response);
                        // 获取results节点下的位置信息
                        JSONArray resultArray = jsonObject.getJSONArray("results");
                        if (resultArray.length() > 0) {
                            JSONObject subObject = resultArray.getJSONObject(0);
                            // 取出格式化后的位置信息
                            String address = subObject.getString("formatted_address");
                            Message message = new Message();
                            message.what = SHOW_LOCATION;
                            message.obj = address;
                            handler.sendMessage(message);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

//    mMoney.setRawInputType(Configuration.KEYBOARD_12KEY);
//    mMoney.addTextChangedListener(newTextWatcher()
//    {
//        @Override
//                publicvoidonTextChanged(CharSequence s,intstart,intbefore,intcount) {
//        // TODO Auto-generated method stub
//        DecimalFormat formatVal =newDecimalFormat("##.##");
//        String formatted = formatVal.format(s);
//        amount.setText(formatted);
//    }

    protected void onDestroy() {
        super.onDestroy();
        if (locationManager != null) {
            // 关闭程序时将监听器移除
            locationManager.removeUpdates(locationListener);
        }
    }

    public static void setPricePoint(final EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + 3);
                        editText.setText(s);
                        editText.setSelection(s.length());
                    }
                }
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    editText.setText(s);
                    editText.setSelection(2);
                }

                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        editText.setText(s.subSequence(0, 1));
                        editText.setSelection(1);
                        return;
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }

        });

    }

    private class  Sort1OnItemSelectedListener implements AdapterView.OnItemSelectedListener {
        //选择省份，触发类别1下拉列表框
        @Override
        public void onItemSelected(AdapterView<?> adapter,View view,int position,long id) {
            //使用ArrayAdapter转换数据
            AddGetAccount.this.adaptersort2 =new ArrayAdapter<CharSequence>(
                    AddGetAccount.this,
                    android.R.layout.simple_spinner_item,
                    AddGetAccount.this.sort2[position]);
            //使用adapterCity数据适配器填充spinCity
            AddGetAccount.this.sortSpinner2.setAdapter(AddGetAccount.this.adaptersort2);

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
//            Toast.makeText(getApplicationContext(), sInfo, Toast.LENGTH_LONG).show();
        }
        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            //没有选择时执行
        }
    }
}
