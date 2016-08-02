package com.example.ylh.rxjavaandretrofit.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ylh.rxjavaandretrofit.R;
import com.example.ylh.rxjavaandretrofit.data.MovieEntity;
import com.example.ylh.rxjavaandretrofit.http.HttpMethods;
import com.example.ylh.rxjavaandretrofit.myinterface.MovieService;
import com.example.ylh.rxjavaandretrofit.myinterface.RxJavaMovieService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class UsingRetrofitAndRxJavaActivity extends Activity {

    @BindView(R.id.click_me_BN) Button clickMeBn;

    @BindView(R.id.result_TV) TextView resultTV;

    private Context context;

    private Subscriber subscriber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_only_using_retrofit);
        context = this;
        ButterKnife.bind(this);
    }

    @OnClick(R.id.click_me_BN)
    void onClick(View view) {
//        getMovieOld();
        getMovie();
    }

    /**
     * 网络请求
     */
    public void getMovieOld() {
        String baseUrl = "https://api.douban.com/v2/movie/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        RxJavaMovieService movieService = retrofit.create(RxJavaMovieService.class);

        movieService.getTopMovie(0, 10)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MovieEntity>() {
                    @Override
                    public void onCompleted() {
                        Toast.makeText(context, "Get Top Movie Completed", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        resultTV.setText(e.getMessage());
                    }

                    @Override
                    public void onNext(MovieEntity movieEntity) {
                        resultTV.setText(movieEntity.toString());
                    }
                });
    }

    private void getMovie() {
        subscriber = new Subscriber<MovieEntity>() {
            @Override
            public void onCompleted() {
                Toast.makeText(context, "Get Top Movie Completed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {
                resultTV.setText(e.getMessage());
            }

            @Override
            public void onNext(MovieEntity movieEntity) {
                resultTV.setText(movieEntity.toString());
            }
        };
        HttpMethods.getInstance().getTopMovie(subscriber, 0, 10);
    }
}
