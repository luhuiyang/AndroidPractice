package com.example.ylh.doubanmovie;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.ylh.doubanmovie.entity.MovieDetailEntity;
import com.example.ylh.doubanmovie.http.HttpMethods;

import rx.Subscriber;

public class MovieDetailActivity extends AppCompatActivity {

    MovieDetailEntity movieDetailEntity;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        initData();
    }

    private void initData() {
        id = getIntent().getStringExtra("id");
        Subscriber<MovieDetailEntity> subscriber = new Subscriber<MovieDetailEntity>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(MovieDetailEntity movieDetailEntity) {
                movieDetailEntity = movieDetailEntity;
            }
        };
        HttpMethods.getInstance().getMovieDetail(subscriber, id);
    }

    public static void actionStart(Context context, String id) {
        Intent intent = new Intent(context, MovieDetailActivity.class);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }
}
