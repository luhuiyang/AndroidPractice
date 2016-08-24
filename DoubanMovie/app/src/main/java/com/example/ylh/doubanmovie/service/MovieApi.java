package com.example.ylh.doubanmovie.service;

import com.example.ylh.doubanmovie.entity.MovieDetailEntity;
import com.example.ylh.doubanmovie.entity.MovieListItemEntity;
import com.example.ylh.doubanmovie.entity.Result;
import com.example.ylh.doubanmovie.http.HttpMethods;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by ylh on 16-8-2.
 */
public interface MovieApi {

    //下载文件
    @GET
    Observable<ResponseBody> downloadPic(@Url String fileUrl);

    // 获取电影列表
    @GET("/v2/movie/search")
    Observable<MovieListItemEntity> getMovieList(@Query("q") String keyword);


    //获取电影信息
    @GET("/v2/movie/subject/")
    Observable<MovieDetailEntity> getMovieDetail(@Path("id") String id);

}
