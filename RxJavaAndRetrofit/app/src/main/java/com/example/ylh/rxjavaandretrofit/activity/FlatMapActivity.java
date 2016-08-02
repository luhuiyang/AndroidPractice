package com.example.ylh.rxjavaandretrofit.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.ylh.rxjavaandretrofit.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

public class FlatMapActivity extends AppCompatActivity {
    private Context context;

    @BindView(R.id.button)
    Button button;
    @OnClick(R.id.button)
    void onClick(View v){
        flatMap();
    }

    List<String> a = new ArrayList<String>();
    Observable<List<String>> query (String s) {

        return Observable.just(a);
    }
    private void flatMap() {
        a.add("11111");
        a.add("22222");
        a.add("33333");
        a.add("33342333");
        a.add("33sdfsd333");
        a.add("3334r23333");
        query("3")
                .flatMap(new Func1<List<String>, Observable<String>>() {
                    @Override
                    public Observable<String> call(List<String> urls) {
                        return Observable.from(urls);
                    }
                })
                .filter(new Func1<String, Boolean>() {
                    @Override
                    public Boolean call(String s) {
                        return s.contains("3");
                    }
                })
                .take(1)
                .doOnNext(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        //// TODO: 16-8-1 ex: save file in background
                    }
                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
                    }
                });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flat_map);
        ButterKnife.bind(this);
        context = this;

    }
}
