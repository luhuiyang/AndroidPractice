package com.example.ylh.rxjavaandretrofit.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ylh.rxjavaandretrofit.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import rx.Subscriber;
import rx.functions.Action1;

public class MainActivity extends Activity {

    @BindView(R.id.listview)
    ListView listView;

    List<Class> activitylists = new ArrayList<>();

    private Context context;
    private ItemAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        ButterKnife.bind(this);
        initList();
        initView();
//        rx.Observable.from(names)
//                .subscribe(new Action1<String>() {
//                    @Override
//                    public void call(String s) {
//                        Log.d("tag", s);
//                    }
//                });

    }

    private void initView() {
        adapter = new ItemAdapter();
        listView.setAdapter(adapter);
    }

    private void initList() {
        activitylists.add(OnlyUsingRetrofitActivity.class);
        activitylists.add(UsingRetrofitAndRxJavaActivity.class);
        activitylists.add(HelloWorldActivity.class);
        activitylists.add(FlatMapActivity.class);
        activitylists.add(ErrorScheduleSubscribeActivity.class);
    }

    @OnItemClick(R.id.listview)
    void onClick(int position, View view) {
        Intent intent = new Intent(this, activitylists.get(position));
        startActivity(intent);
    }

    private class ItemAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return activitylists.size();
        }

        @Override
        public Object getItem(int i) {
            return activitylists.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            TextView textView = new TextView(context);
            textView.setText(activitylists.get(i).getName());
            return textView;
        }
    }
}
