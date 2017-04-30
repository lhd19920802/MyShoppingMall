package com.lhd.myshoppingmall.shoppingcart.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lhd.myshoppingmall.app.MyApplication;
import com.lhd.myshoppingmall.home.bean.GoodsBean;
import com.lhd.myshoppingmall.utils.CacheUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lihuaidong on 2017/4/23 12:40.
 * 微信：lhd520ssp
 * QQ:414320737
 * 作用：
 */
public class CartStorage
{
    private static final String JSON_CART = "json_cart";
    private static CartStorage instance;
    private final Context mContext;

    //SparseArray的性能优于HashMap
    private SparseArray<GoodsBean> sparseArray;

    private CartStorage(Context mContext)
    {
        this.mContext = mContext;
        sparseArray = new SparseArray<>(100);

        listToSparseArray();
    }
    public static CartStorage getInstance()
    {
        if (instance == null)
        {
            return new CartStorage(MyApplication.getmContext());
        }
        return instance;
    }

    private void listToSparseArray()
    {
        List<GoodsBean> goodsBeanList = getAllData();

            for (int i = 0; i < goodsBeanList.size(); i++)
            {
                GoodsBean goodsBean = goodsBeanList.get(i);
                sparseArray.put(Integer.parseInt(goodsBean.getProduct_id()), goodsBean);
            }


    }

    public List<GoodsBean> getAllData()
    {
        List<GoodsBean> goodsBeanList = new ArrayList<>();
        String json = CacheUtils.getString(mContext, JSON_CART);
        if (!TextUtils.isEmpty(json))
        {
            goodsBeanList = new Gson().fromJson(json, new TypeToken<List<GoodsBean>>()
            {
            }.getType());
        }

        return goodsBeanList;

    }




    //增加
    public void addData(GoodsBean goodsBean)
    {
        //如果已有 直接加+1
        GoodsBean tempData = sparseArray.get(Integer.parseInt(goodsBean.getProduct_id()));
        if (tempData != null)
        {
            //内存中已存在
            tempData.setNumber(tempData.getNumber() + 1);
        }
        else
        {
            tempData = goodsBean;
            tempData.setNumber(1);
        }

        //同步到内存中
        sparseArray.put(Integer.parseInt(goodsBean.getProduct_id()), tempData);
        //保存到本地
        saveLocal();
    }


    public void deleteData(GoodsBean goodsBean)
    {
        sparseArray.delete(Integer.parseInt(goodsBean.getProduct_id()));
        saveLocal();
    }

    public void updateData(GoodsBean goodsBean)
    {
        sparseArray.put(Integer.parseInt(goodsBean.getProduct_id()), goodsBean);
        saveLocal();
    }
    private void saveLocal()
    {
        List<GoodsBean> goodsBeanList=sparseToList();
        Log.e("TAG", "goodsBeanList=="+goodsBeanList.toString());
        String json=new Gson().toJson(goodsBeanList);
        Log.e("TAG", "json==" + json);
        CacheUtils.putString(mContext, JSON_CART, json);
    }

//    private List<GoodsBean> sparseArrayToList()
//    {
//        List<GoodsBean> goodsBeanList = new ArrayList<>();
//        for (int i = 0; i < sparseArray.size(); i++)
//        {
//            GoodsBean goodsBean = sparseArray.get(i);
//            goodsBeanList.add(goodsBean);
//        }
//        return goodsBeanList;
//    }

    private List<GoodsBean> sparseToList()
    {
        List<GoodsBean> goodsBeanList = new ArrayList<>();
        for (int i = 0; i < sparseArray.size(); i++)
        {
            GoodsBean goodsBean = sparseArray.valueAt(i);
            goodsBeanList.add(goodsBean);
        }
        return goodsBeanList;
    }

}
