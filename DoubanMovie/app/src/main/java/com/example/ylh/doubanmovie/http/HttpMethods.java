package com.example.ylh.doubanmovie.http;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.ylh.doubanmovie.entity.MovieDetailEntity;
import com.example.ylh.doubanmovie.entity.MovieListItemEntity;
import com.example.ylh.doubanmovie.entity.Result;
import com.example.ylh.doubanmovie.service.MovieApi;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by ylh on 16-8-2.
 */
public class HttpMethods {
    //     public static final String BASE_URL = "https://api.douban.com/v2/movie/";
    public static final String BASE_URL = "https://api.douban.com";

    private static final int DEFALT_TIMEOUT = 5;

    private Retrofit retrofit;
    private MovieApi movieApi;

    private HttpMethods() {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(DEFALT_TIMEOUT, TimeUnit.SECONDS);
        httpClientBuilder.interceptors().add(new LoggingInterceptor());

        retrofit = new Retrofit.Builder()
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();

        movieApi = retrofit.create(MovieApi.class);
    }

    /**
     * 获取单例
     *
     * @return
     */
    public static HttpMethods getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public void loadImage(Subscriber<Bitmap> subscriber, String url) {
        movieApi.downloadPic(url)
                .subscribeOn(Schedulers.io())
                .map(new Func1<ResponseBody, Bitmap>() {
                    @Override
                    public Bitmap call(ResponseBody responseBody) {
                        Bitmap bitmap = BitmapFactory.decodeStream(responseBody.byteStream());
                        return bitmap;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void getMovieList(Subscriber<MovieListItemEntity> subscriber, String keyword) {
        movieApi.getMovieList(keyword)
                .subscribeOn(Schedulers.io())
//                .map(new HttpResultFunc<List<MovieListItemEntity>>())
//                .unsubscribeOn(Schedulers.io()) // 不知道是干嘛的
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 访问此类是创建单例
     */
    private static class SingletonHolder {
        private static final HttpMethods INSTANCE = new HttpMethods();
    }

    /**
     * log类
     */
    static class LoggingInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

            long t1 = System.nanoTime();
            System.out.println(
                    String.format("Sending request %s on %s%n%s", request.url(), chain.connection(),
                            request.headers()));

            Response response = chain.proceed(request);

            long t2 = System.nanoTime();
            System.out.println(
                    String.format("Received response for %s in %.1fms%n%s", response.request().url(),
                            (t2 - t1) / 1e6d, response.headers()));

            return response;
        }
    }

    private class HttpResultFunc<T> implements Func1<Result<T>, T> {

        @Override
        public T call(Result<T> httpResult) {
//            if (httpResult.getResultCode() != 0) {
//                throw new ApiException(httpResult.getResultCode());
//            }
            return httpResult.getSubjects();
        }
    }

    public void getMovieDetail(Subscriber<MovieDetailEntity> subscriber, String id){
        movieApi.getMovieDetail(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

}
