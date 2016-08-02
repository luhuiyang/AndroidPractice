package com.example.ylh.rxjavaandretrofit.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ylh.rxjavaandretrofit.R;
import com.example.ylh.rxjavaandretrofit.data.MovieEntity;
import com.example.ylh.rxjavaandretrofit.myinterface.MovieService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OnlyUsingRetrofitActivity extends Activity {

    @BindView(R.id.click_me_BN) Button clickMeBn;

    @BindView(R.id.result_TV) TextView resultTV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_only_using_retrofit);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.click_me_BN)
    void onClick(View view) {
        getMovie();
    }
    /**
     * 网络请求
     */
    public void getMovie() {
        String baseUrl = "https://api.douban.com/v2/movie/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MovieService movieService = retrofit.create(MovieService.class);
        Call<MovieEntity> call = movieService.getTopMovie(0, 10);
        Log.d("url == ", call.request().url().toString());
        call.enqueue(new Callback<MovieEntity>() {
            @Override
            public void onResponse(Call<MovieEntity> call, Response<MovieEntity> response) {
                MovieEntity date = response.body();
                resultTV.setText(date.getTitle());
            }

            @Override
            public void onFailure(Call<MovieEntity> call, Throwable t) {
                resultTV.setText(t.getMessage());
            }
        });
    }
}
