package com.example.ylh.rxjavaandretrofit.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.ylh.rxjavaandretrofit.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;

public class RxJavaExampleActivity extends AppCompatActivity {

    private String[] names = {"lili", "hehe", "dklfjdsjf"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java_example);
        test();
        ButterKnife.bind(this);
    }

    private void test() {
        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Log.d("tag", " completed");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                Log.d("tag", s);
            }
        };
        rx.Observable.create(new rx.Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext(names[1]);
                subscriber.onNext(names[2]);
                subscriber.onNext(names[1]);
                subscriber.onCompleted();
            }
        })
                .subscribe(subscriber);
    }

}
