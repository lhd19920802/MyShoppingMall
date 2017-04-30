package com.lhd.myshoppingmall.shoppingcart.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lhd.myshoppingmall.R;
import com.lhd.myshoppingmall.home.bean.GoodsBean;
import com.lhd.myshoppingmall.shoppingcart.utils.CartStorage;
import com.lhd.myshoppingmall.shoppingcart.view.AddSubView;
import com.lhd.myshoppingmall.utils.Constants;

import java.util.List;

/**
 * Created by lihuaidong on 2017/4/23 20:37.
 * 微信：lhd520ssp
 * QQ:414320737
 * 作用：
 */
public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.ViewHolder>
{


    private final Context mContext;
    private final List<GoodsBean> goodsBeanList;
    private final CheckBox checkboxAll;
    private final TextView tvShopcartTotal;
    //删除状态下的全选
    private final CheckBox cbAll;

    public ShoppingCartAdapter(Context mContext, List<GoodsBean> goodsBeanList, CheckBox
            checkboxAll, TextView tvShopcartTotal, CheckBox cbAll)
    {
        this.mContext = mContext;
        this.goodsBeanList = goodsBeanList;
        this.checkboxAll = checkboxAll;
        this.tvShopcartTotal = tvShopcartTotal;
        this.cbAll = cbAll;

        showTotalPrice();
        setListener();
        checkAll();
    }

    private void setListener()
    {
        setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(int position)
            {
                GoodsBean goodsBean = goodsBeanList.get(position);
                goodsBean.setIsSelected(!goodsBean.isSelected());


                //更新界面
                notifyItemChanged(position);


                //设置全选非全选按钮的状态
                checkAll();
                //计算总价格
                showTotalPrice();
                //                //保存到本地
                //                CartStorage.getInstance().updateData(goodsBean);
            }
        });
        checkboxAll.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //获取全选非全选的状态
                boolean checked = checkboxAll.isChecked();
                //根据状态设置所有得到商品状态
                checkAll_none(checked);

                showTotalPrice();
            }
        });
        cbAll.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //获取全选非全选的状态
                boolean checked = cbAll.isChecked();
                //根据状态设置所有得到商品状态
                checkAll_none(checked);

            }
        });

    }

    public void checkAll_none(boolean checked)
    {
        for (int i = 0; i < goodsBeanList.size(); i++)
        {
            GoodsBean goodsBean = goodsBeanList.get(i);
            goodsBean.setIsSelected(checked);
            notifyItemChanged(i);
        }

    }

    public void checkAll()
    {
        if (goodsBeanList != null && goodsBeanList.size() > 0)
        {
            int num = 0;
            for (int i = 0; i < goodsBeanList.size(); i++)
            {

                GoodsBean goodsBean = goodsBeanList.get(i);
                if (!goodsBean.isSelected())
                {
                    //设置为非全选
                    checkboxAll.setChecked(false);
                    cbAll.setChecked(false);

                }
                else
                {
                    num++;
                }
            }
            if (goodsBeanList.size() == num)
            {
                //设置为全选
                checkboxAll.setChecked(true);
                cbAll.setChecked(true);

            }
        }
        else
        {
            checkboxAll.setChecked(false);
            cbAll.setChecked(false);
        }
    }

    public void showTotalPrice()
    {
        tvShopcartTotal.setText("￥" + getTotalPrice());
    }

    public double getTotalPrice()
    {
        double totalPrice = 0.0;
        if (goodsBeanList != null && goodsBeanList.size() > 0)
        {
            for (int i = 0; i < goodsBeanList.size(); i++)
            {
                GoodsBean goodsBean = goodsBeanList.get(i);
                if (goodsBean.isSelected())
                {
                    totalPrice = totalPrice + Double.parseDouble(goodsBean.getCover_price()) *
                                              goodsBean.getNumber();
                }
            }
        }

        return totalPrice;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = View.inflate(mContext, R.layout.item_shop_cart, null);
        return new ViewHolder(mContext, itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        holder.setData(goodsBeanList.get(position));
    }

    @Override
    public int getItemCount()
    {
        return goodsBeanList.size();
    }

    public void deleteData()
    {
        if (goodsBeanList != null && goodsBeanList.size() > 0)
        {
            for (int i = 0; i < goodsBeanList.size(); i++)
            {
                GoodsBean goodsBean = goodsBeanList.get(i);
                if (goodsBean.isSelected())
                {

                    //从内存中删除
                    goodsBeanList.remove(goodsBean);
                    //保存到本地
                    CartStorage.getInstance().deleteData(goodsBean);
                    //更新界面
                    notifyItemRemoved(i);


                    //别忘记i--
                    i--;
                }
            }
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {

        private CheckBox cb_gov;
        private ImageView iv_gov;
        private TextView tv_desc_gov;
        private TextView tv_price_gov;
        private AddSubView ddSubView;

        public ViewHolder(Context mContext, View itemView)
        {
            super(itemView);
            cb_gov = (CheckBox) itemView.findViewById(R.id.cb_gov);
            iv_gov = (ImageView) itemView.findViewById(R.id.iv_gov);
            tv_desc_gov = (TextView) itemView.findViewById(R.id.tv_desc_gov);
            tv_price_gov = (TextView) itemView.findViewById(R.id.tv_price_gov);
            ddSubView = (AddSubView) itemView.findViewById(R.id.ddSubView);

            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if (onClickListener != null)
                    {
                        onClickListener.onClick(getLayoutPosition());
                    }
                }
            });


        }

        public void setData(final GoodsBean goodsBean)
        {
            cb_gov.setChecked(goodsBean.isSelected());
            Glide.with(mContext).load(Constants.BASE_URL_IMAGE + goodsBean.getFigure()).into
                    (iv_gov);
            tv_desc_gov.setText(goodsBean.getName());
            tv_price_gov.setText("￥" + goodsBean.getCover_price());

            ddSubView.setValue(goodsBean.getNumber());

            ddSubView.setMinValue(1);
            ddSubView.setMaxValue(6);

            ddSubView.setOnNumberChangeListener(new AddSubView.OnNumberChangeListener()
            {
                @Override
                public void onNumberChange(int value)
                {
                    goodsBean.setNumber(value);

                    //内存

                    //本地
                    CartStorage.getInstance().updateData(goodsBean);
                    showTotalPrice();
                    notifyDataSetChanged();

                }
            });

        }
    }

    public interface OnClickListener
    {
        public void onClick(int position);
    }

    private OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener)
    {
        this.onClickListener = onClickListener;
    }
}
