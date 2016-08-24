package com.example.ylh.rxjavaandretrofit.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ylh.rxjavaandretrofit.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;
import rx.subjects.PublishSubject;

import static android.text.TextUtils.isEmpty;

public class DoubleBindingActivity extends AppCompatActivity {

    @BindView(R.id.et_1)
    EditText et1;
    @BindView(R.id.et_2)
    EditText et2;
    @BindView(R.id.result_two_num)
    TextView resultTwoNum;

    Context context;
    Observable observable;
    PublishSubject publishSubject;
    Subscription subscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_double_binding);
        ButterKnife.bind(this);
        publishSubject = PublishSubject.create();
        subscription = publishSubject
                .asObservable()
                .subscribe(new Action1<Float>() {
                    @Override
                    public void call(Float aFloat) {
                        resultTwoNum.setText(String.valueOf(aFloat));
                    }
                });
//        两种方式都可以
        publishSubject
                .asObservable()
                .subscribe(new Subscriber<Float>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Float aFloat) {

                        resultTwoNum.setText(String.valueOf(aFloat));
                    }
                });
        textsum();

//        et2.requestFocus();
    }

    @OnTextChanged({R.id.et_1, R.id.et_2})
    public void textsum() {
        float num1 = 0;
        float num2 = 0;
        if (!isEmpty(et1.getText().toString())) {
            num1 = Float.parseFloat(et1.getText().toString());
        }
        if (!isEmpty(et2.getText().toString())) {
            num2 = Float.parseFloat(et2.getText().toString());
        }
        publishSubject.onNext(num1 + num2);
    }
}
