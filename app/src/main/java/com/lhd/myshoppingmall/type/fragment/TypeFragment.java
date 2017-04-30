package com.lhd.myshoppingmall.type.fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.lhd.myshoppingmall.base.BaseFragment;

import static android.content.ContentValues.TAG;

/**
 * Created by acer on 2017/4/16 11:13.
 * 作用：
 */
public class TypeFragment extends BaseFragment
{
    private TextView textView;

    @Override
    public View initView()
    {
        Log.e(TAG, "分类的Fragment的UI被初始化了");
        textView = new TextView(mContext);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(25);
        return textView;
    }

    @Override
    public void initData()
    {
        super.initData();
        Log.e(TAG, "分类的Fragment的数据被初始化了");
        textView.setText("分类内容");
    }
}
