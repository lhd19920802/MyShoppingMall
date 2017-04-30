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
 * Created by lihuaidong on 2017/4/21 15:43.
 * 微信：lhd520ssp
 * QQ:414320737
 * 作用：
 */
public class RecommendAdapter extends BaseAdapter
{
    private final Context mContext;
    private final List<ResultBeanData.ResultBean.RecommendInfoBean> recommend_info;

    public RecommendAdapter(Context mContext, List<ResultBeanData.ResultBean.RecommendInfoBean>
            recommend_info)

    {
        this.mContext = mContext;
        this.recommend_info = recommend_info;
    }

    @Override
    public int getCount()
    {
        return recommend_info.size();
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
            convertView = View.inflate(mContext, R.layout.item_recommend_grid_view, null);
            viewHolder = new ViewHolder();
            viewHolder.iv_recommend = (ImageView) convertView.findViewById(R.id.iv_recommend);
            viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.tv_price = (TextView) convertView.findViewById(R.id.tv_price);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //根据位置得到对应的数据
        ResultBeanData.ResultBean.RecommendInfoBean recommendInfoBean = recommend_info.get
                (position);
        Glide.with(mContext).load(Constants.BASE_URL_IMAGE + recommendInfoBean.getFigure()).into
                (viewHolder.iv_recommend);
        viewHolder.tv_name.setText(recommendInfoBean.getName());
        viewHolder.tv_price.setText("￥" + recommendInfoBean.getCover_price());

        return convertView;
    }

    static class ViewHolder
    {
        ImageView iv_recommend;
        TextView tv_name;
        TextView tv_price;

    }
}
