package com.example.ylh.rxjavaandretrofit.myinterface;

import com.example.ylh.rxjavaandretrofit.data.MovieEntity;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by ylh on 16-7-1.
 */
public interface MovieService {
    @GET("top250")
    Call<MovieEntity> getTopMovie(@Query("start") int start, @Query("count") int count);
}
