package com.example.ylh.rxjavaandretrofit.http;

import com.example.ylh.rxjavaandretrofit.data.HttpResult;
import com.example.ylh.rxjavaandretrofit.data.MovieEntity;
import com.example.ylh.rxjavaandretrofit.myinterface.RxJavaMovieService;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by ylh on 16-7-4.
 */
public class HttpMethods {

    public static final String BASE_URL = "https://api.douban.com/v2/movie/";

    private static final int DEFAULT_TIMEOUT = 5;

    private Retrofit retrofit;
    private RxJavaMovieService movieService;

    /**
     * 构造犯法私有
     */
    private HttpMethods() {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        retrofit = new Retrofit.Builder()
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();

        movieService = retrofit.create(RxJavaMovieService.class);
    }

    /**
     * 在访问HttpMethods时创建单例
     */
    private static class SingletonHolder{
        private static final HttpMethods INSTANCE = new HttpMethods();
    }

    /**
     *
     * 获取单例
     * @return
     */
    public static HttpMethods getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 用于获取豆瓣电影Top250的数据
     * @param subscriber 由调用者传过来的观察者对象
     * @param start 起始位置
     * @param count 获取长度
     */
    public void getTopMovie(Subscriber<MovieEntity> subscriber, int start, int count) {
        movieService.getTopMovie(start, count)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    private class HttpResultFunc<T> implements Func1<HttpResult<T>, T> {
        @Override
        public T call(HttpResult<T> httpResult) {
            if (httpResult.getResultCode() != 0) {
//                throw  new E
            }
            return httpResult.getData();
        }
    }
}
