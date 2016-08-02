package com.example.ylh.rxjavaandretrofit.activity;

import android.app.Service;
import android.content.Context;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ylh.rxjavaandretrofit.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.exceptions.Exceptions;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class ErrorScheduleSubscribeActivity extends AppCompatActivity {
    private Context context;

    @BindView(R.id.button)
    Button button;
    @BindView(R.id.textView)
    TextView textView;

    @OnClick(R.id.button)
    void onClick(View v){
        error();
//        schedule();
    }

    private void schedule() {
        Observable.just("lalala")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        Toast.makeText(context, "completedimg", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(context, "completedimg", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(String s) {
                        textView.setText(s);
                    }
                });
    }

    String control(String s){
        int b = 0;
//        try {
        b = 1 / 0;
//        } catch (Exception e) {
//            throw e;
//        }
        return s + b;
    }

    private void error() {
        Observable.just("Hello world！", "1", "2")
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        return control(s);
                    }
                })
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        Toast.makeText(context, "completed", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(context, "error! ouch!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(String s) {
                        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
                    }
                });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error_schedule_subscribe);
        ButterKnife.bind(this);
        context = this;
    }
}
