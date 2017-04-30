package com.lhd.myshoppingmall.home.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lhd.myshoppingmall.R;
import com.lhd.myshoppingmall.home.bean.ResultBeanData;
import com.lhd.myshoppingmall.utils.Constants;

import java.util.List;

/**
 * Created by lihuaidong on 2017/4/22 16:52.
 * 微信：lhd520ssp
 * QQ:414320737
 * 作用：
 */
public class HotAdapter extends BaseAdapter
{
    private final Context mContext;
    private final List<ResultBeanData.ResultBean.HotInfoBean> hot_info;

    public HotAdapter(Context mContext, List<ResultBeanData.ResultBean.HotInfoBean> hot_info)
    {
        this.mContext = mContext;
        this.hot_info = hot_info;
    }

    @Override
    public int getCount()
    {
        return hot_info.size();
    }

    @Override
    public Object getItem(int position)
    {
        return null;
    }

    @Override
    public long getItemId(int position)
    {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder viewHolder;
        if (convertView == null)
        {
            viewHolder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.item_hot_grid_view, null);
            viewHolder.iv_hot = (ImageView) convertView.findViewById(R.id.iv_hot);
            viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.tv_price = (TextView) convertView.findViewById(R.id.tv_price);

            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        ResultBeanData.ResultBean.HotInfoBean hotInfoBean = hot_info.get(position);
        Glide.with(mContext).load(Constants.BASE_URL_IMAGE + hotInfoBean.getFigure()).into
                (viewHolder.iv_hot);
        viewHolder.tv_name.setText(hotInfoBean.getName());
        viewHolder.tv_price.setText("￥"+hotInfoBean.getCover_price());
        return convertView;
    }

    static class ViewHolder

    {
        ImageView iv_hot;
        TextView tv_name;
        TextView tv_price;
    }
}
