package com.lhd.myshoppingmall.app;

import android.app.Application;
import android.content.Context;

import com.zhy.http.okhttp.OkHttpUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by lihuaidong on 2017/4/16 13:03.
 * 微信：lhd520ssp
 * QQ:414320737
 * 作用：代表整个软件
 */
public class MyApplication extends Application
{
    private static Context mContext;

    public static Context getmContext()
    {
        return mContext;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        mContext=this;
        initOkHttpClient();
    }

    private void initOkHttpClient()
    {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                //                .addInterceptor(new LoggerInterceptor("TAG"))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                        //其他配置
                .build();

        OkHttpUtils.initClient(okHttpClient);
    }
}
