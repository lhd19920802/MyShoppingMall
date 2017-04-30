package com.lhd.myshoppingmall.home.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.lhd.myshoppingmall.R;
import com.lhd.myshoppingmall.base.BaseFragment;
import com.lhd.myshoppingmall.home.adapter.HomeFragmentAdapter;
import com.lhd.myshoppingmall.home.bean.ResultBeanData;
import com.lhd.myshoppingmall.utils.Constants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by acer on 2017/4/16 11:11.
 * 作用：
 */
public class HomeFragment extends BaseFragment
{

    private static final String TAG = HomeFragment.class.getSimpleName();
    private RecyclerView rvHome;
    private ImageView ib_top;
    private TextView tv_search_home;
    private TextView tv_message_home;
    private ResultBeanData.ResultBean result;

    private HomeFragmentAdapter adapter;

    @Override
    public View initView()
    {
        Log.e(TAG, "主页的Fragment的UI被初始化了");
        View view = View.inflate(mContext, R.layout.fragment_home, null);
        rvHome = (RecyclerView) view.findViewById(R.id.rv_home);
        ib_top = (ImageView) view.findViewById(R.id.ib_top);
        tv_search_home = (TextView) view.findViewById(R.id.tv_search_home);
        tv_message_home = (TextView) view.findViewById(R.id.tv_message_home);

        //设置点击事件
        initListener();
        return view;
    }

    @Override
    public void initData()
    {
        super.initData();
        Log.e(TAG, "主页的Fragment的数据被初始化了");
        getDataFromNet();
    }

    private void getDataFromNet()
    {

        String url = Constants.HOME_URL;
        OkHttpUtils.get().url(url).build().execute(new StringCallback()
        {
            @Override
            public void onError(Call call, Exception e, int id)
            {
                Log.e("TAG", "onError==" + e.getMessage());
            }

            @Override
            public void onResponse(String response, int id)
            {
                Log.e("TAG", "onResponse==" + response);
                processData(response);

            }


        });
    }

    private void processData(String json)
    {
        ResultBeanData resultBeanData = JSON.parseObject(json, ResultBeanData.class);
        result = resultBeanData.getResult();
        Log.e("TAG", "解析成功==" + result.getAct_info().get(0).getName());
        if (result != null)
        {
            //有数据 设置适配器
            adapter = new HomeFragmentAdapter(mContext, result);
            rvHome.setAdapter(adapter);
            GridLayoutManager manager = new GridLayoutManager(mContext, 1);
            manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup()
            {
                @Override
                public int getSpanSize(int position)
                {
                    if (position <= 3)
                    {
                        ib_top.setVisibility(View.GONE);
                    }
                    else
                    {
                        ib_top.setVisibility(View.VISIBLE);
                    }
                    return 1;
                }
            });
            //设置布局管理者
            rvHome.setLayoutManager(manager);

        }
        else
        {

        }
    }

    private void initListener()
    {
        //置顶的监听
        ib_top.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //回到顶部
                rvHome.scrollToPosition(0);
            }
        });

        //搜素的监听
        tv_search_home.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(mContext, "搜索", Toast.LENGTH_SHORT).show();
            }
        });

        //消息的监听
        tv_message_home.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(mContext, "进入消息中心", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
