package com.example.ylh.doubanmovie.tools;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.ylh.doubanmovie.http.HttpMethods;

import java.io.File;

import retrofit2.Retrofit;
import rx.Subscriber;

/**
 * Created by ylh on 16-8-2.
 */
public class ImageUtils {

    public static void display(ImageView imageView, String url) {
        getBitMap(url, imageView);
    }


    private static void getBitMap(String url, final ImageView imageView) {
        Subscriber subscriber = new Subscriber<Bitmap>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Bitmap bitmap) {
                imageView.setImageBitmap(bitmap);
            }
        };
        HttpMethods.getInstance().loadImage(subscriber, url);
    }

    public static final String ANDROID_RESOURCE = "android.resource://";
    public static final String FOREWARD_SLASH = "/";

    /**
     * 静态内部类单例模式
     */
    private static class ImageUtilsHolder{
        private static final ImageUtils INSTANCE = new ImageUtils();
    }

    private ImageUtils() {

    }

    public static final ImageUtils getInstance() {
        return ImageUtilsHolder.INSTANCE;
    }

    //直接加载网络图片
    public void displayImage(Context context, String url, ImageView imageView) {
        Glide
                .with(context)
                .load(url)
                .centerCrop()
                .crossFade()
                .into(imageView);
    }
    //加载sd卡图片
    public void displayImage(Context context, File file, ImageView imageView) {
        Glide
                .with(context)
                .load(file)
                .centerCrop()
                .into(imageView);
    }
    //加载sd卡问卷并设置大小
    public void displayImage(Context context, File file, ImageView imageView, int width, int height) {
        Glide
                .with(context)
                .load(file)
                .override(width, height)
                .centerCrop()
                .into(imageView);
    }
    //加载网络图片并设置大小
    public void displayImage(Context context, String url, ImageView imageView, int width, int height) {
        Glide
                .with(context)
                .load(url)
                .centerCrop()
                .override(width, height)
                .crossFade()
                .into(imageView);
    }
    //加载drawable图片
    public void displayImage(Context context, int resId, ImageView imageView) {
        Glide
                .with(context)
                .load(resourceIdToUri(context, resId))
                .crossFade()
                .into(imageView);
    }

    //将资源id转为Uri
    public Uri resourceIdToUri(Context context, int resourceId) {
        return Uri.parse(ANDROID_RESOURCE + context.getPackageName() + FOREWARD_SLASH + resourceId);
    }

}
