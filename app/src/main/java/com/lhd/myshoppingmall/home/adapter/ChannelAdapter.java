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
 * Created by lihuaidong on 2017/4/17 13:43.
 * 微信：lhd520ssp
 * QQ:414320737
 * 作用：频道适配器
 */
public class ChannelAdapter extends BaseAdapter
{
    private final Context mContext;
    private final List<ResultBeanData.ResultBean.ChannelInfoBean> channel_info;

    public ChannelAdapter(Context mContext, List<ResultBeanData.ResultBean.ChannelInfoBean> channel_info)

    {
        this.mContext=mContext;
        this.channel_info=channel_info;
    }

    @Override
    public int getCount()
    {
        return channel_info.size();
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
        if(convertView==null)
        {
            viewHolder=new ViewHolder();
            convertView = View.inflate(mContext, R.layout.item_channel, null);
            viewHolder.iv_channel = (ImageView) convertView.findViewById(R.id.iv_channel);
            viewHolder.tv_channel = (TextView) convertView.findViewById(R.id.tv_channel);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ResultBeanData.ResultBean.ChannelInfoBean channelInfoBean = channel_info.get(position);
        Glide.with(mContext).load(Constants.BASE_URL_IMAGE + channelInfoBean.getImage()).into
                (viewHolder.iv_channel);
        viewHolder.tv_channel.setText(channelInfoBean.getChannel_name());
        return convertView;
    }
    static class ViewHolder
    {
        ImageView iv_channel;
        TextView tv_channel;
    }
}
