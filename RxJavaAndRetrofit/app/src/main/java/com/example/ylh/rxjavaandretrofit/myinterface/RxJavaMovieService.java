package com.example.ylh.rxjavaandretrofit.myinterface;

import com.example.ylh.rxjavaandretrofit.data.MovieEntity;


import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;


/**
 * Created by ylh on 16-7-1.
 */
public interface RxJavaMovieService {
    @GET("top250")
    Observable<MovieEntity> getTopMovie(@Query("start") int start, @Query("count") int count);
}
