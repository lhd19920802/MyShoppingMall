package com.lhd.myshoppingmall.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by lihuaidong on 2017/4/23 14:54.
 * 微信：lhd520ssp
 * QQ:414320737
 * 作用：
 */
public class CacheUtils
{

    public static String getString(Context mContext, String key)
    {
        SharedPreferences sp = mContext.getSharedPreferences("shoppingmall",
                Context.MODE_PRIVATE);
        return sp.getString(key,"");
    }
    public static void putString(Context mContext, String key,String value)
    {
        SharedPreferences sp = mContext.getSharedPreferences("shoppingmall",
                Context.MODE_PRIVATE);
        sp.edit().putString(key, value).commit();
    }
}
