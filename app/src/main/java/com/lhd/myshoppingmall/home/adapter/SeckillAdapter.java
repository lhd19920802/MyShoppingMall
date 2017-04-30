package com.lhd.myshoppingmall.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lhd.myshoppingmall.R;
import com.lhd.myshoppingmall.home.bean.ResultBeanData;
import com.lhd.myshoppingmall.utils.Constants;

import java.util.List;

/**
 * Created by lihuaidong on 2017/4/20 19:28.
 * 微信：lhd520ssp
 * QQ:414320737
 * 作用：
 */
public class SeckillAdapter extends RecyclerView.Adapter
{
    private final Context mContext;
    private final List<ResultBeanData.ResultBean.SeckillInfoBean.ListBean> list;

    public SeckillAdapter(Context mContext, List<ResultBeanData.ResultBean.SeckillInfoBean
            .ListBean> list)

    {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return new ViewHolder(mContext, View.inflate(mContext, R.layout.item_seckill, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.setData(list.get(position));
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder

    {

        private ImageView iv_figure;
        private TextView tv_cover_price;
        private TextView tv_origin_price;


        public ViewHolder(final Context mContext, View itemView)
        {
            super(itemView);
            iv_figure = (ImageView) itemView.findViewById(R.id.iv_figure);
            tv_cover_price = (TextView) itemView.findViewById(R.id.tv_cover_price);
            tv_origin_price = (TextView) itemView.findViewById(R.id.tv_origin_price);

            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    //                    Toast.makeText(mContext, "秒杀=="+getLayoutPosition(),
                    // Toast.LENGTH_SHORT).show();
                    if (onSeckillRecycleView != null)
                    {
                        onSeckillRecycleView.onItemClick(getLayoutPosition());
                    }
                }
            });



        }

        public void setData(ResultBeanData.ResultBean.SeckillInfoBean.ListBean listBean)
        {
            Glide.with(mContext).load(Constants.BASE_URL_IMAGE + listBean.getFigure()).into
                    (iv_figure);
            tv_cover_price.setText(listBean.getCover_price());
            tv_origin_price.setText(listBean.getOrigin_price());

        }
    }

    public interface OnSeckillRecycleView
    {
        public void onItemClick(int position);
    }

    private OnSeckillRecycleView onSeckillRecycleView;

    public void setOnSeckillRecycleView(OnSeckillRecycleView onSeckillRecycleView)
    {
        this.onSeckillRecycleView = onSeckillRecycleView;
    }
}
