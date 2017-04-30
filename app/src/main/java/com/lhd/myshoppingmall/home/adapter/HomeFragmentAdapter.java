package com.lhd.myshoppingmall.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lhd.myshoppingmall.R;
import com.lhd.myshoppingmall.app.GoodsInfoActivity;
import com.lhd.myshoppingmall.home.bean.GoodsBean;
import com.lhd.myshoppingmall.home.bean.ResultBeanData;
import com.lhd.myshoppingmall.utils.Constants;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerClickListener;
import com.youth.banner.listener.OnLoadImageListener;
import com.zhy.magicviewpager.transformer.ScaleInTransformer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lihuaidong on 2017/4/17 9:53.
 * 微信：lhd520ssp
 * QQ:414320737
 * 作用：
 */
public class HomeFragmentAdapter extends RecyclerView.Adapter
{

    /**
     * 广告条幅类型
     */
    public static final int BANNER = 0;

    /**
     * 频道类型
     */
    public static final int CHANNEL = 1;
    /**
     * 活动类型
     */
    public static final int ACT = 2;
    /**
     * 秒杀类型
     */
    public static final int SECKILL = 3;
    /**
     * 推荐类型
     */
    public static final int RECOMMEND = 4;
    /**
     * 热卖
     */
    public static final int HOT = 5;
    private static final String GOODS_BEAN = "goodsbean";
    private final Context mContext;
    private final ResultBeanData.ResultBean result;
    private final LayoutInflater mLayoutInflater;

    /**
     * 当前类型
     */
    private int currentType = BANNER;

    public HomeFragmentAdapter(Context mContext, ResultBeanData.ResultBean result)
    {
        this.mContext = mContext;
        this.result = result;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    //相当于getView中的创建ViewHolder部分
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewBType)
    {
        if (viewBType == BANNER)
        {
            return new BannerViewHolder(mContext, mLayoutInflater.inflate(R.layout
                    .banner_viewpager, null));
        }
        else if (viewBType == CHANNEL)
        {
            return new ChannelViewHolder(mContext, mLayoutInflater.inflate(R.layout.channel_item,
                    null));
        }
        else if (viewBType == ACT)
        {
            return new ActViewHolder(mContext, mLayoutInflater.inflate(R.layout.act_item, null));
        }
        else if (viewBType == SECKILL)
        {
            return new SeckillViewHolder(mContext, mLayoutInflater.inflate(R.layout.seckill_item,
                    null));
        }
        else if (viewBType == RECOMMEND)
        {
            return new RecommendViewHolder(mContext, mLayoutInflater.inflate(R.layout.recommend_item,
                    null));
        }
        else if (viewBType == HOT)
        {
            return new HotViewHolder(mContext, mLayoutInflater.inflate(R.layout.hot_item,
                    null));
        }
        return null;
    }

    //绑定数据部分
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        if (getItemViewType(position) == BANNER)
        {
            //广告条幅
            BannerViewHolder bannerViewHolder = (BannerViewHolder) holder;
            bannerViewHolder.setData(result.getBanner_info());

        }
        else if (getItemViewType(position) == CHANNEL)
        {
            //广告条幅
            ChannelViewHolder channelViewHolder = (ChannelViewHolder) holder;
            channelViewHolder.setData(result.getChannel_info());

        }
        else if (getItemViewType(position) == ACT)
        {
            //广告条幅
            ActViewHolder actViewHolder = (ActViewHolder) holder;
            actViewHolder.setData(result.getAct_info());

        }
        else if (getItemViewType(position) == SECKILL)
        {
            //广告条幅
            SeckillViewHolder seckillViewHolder = (SeckillViewHolder) holder;
            seckillViewHolder.setData(result.getSeckill_info());

        }
        else if (getItemViewType(position) == RECOMMEND)
        {
            //广告条幅
           RecommendViewHolder recommendViewHolder = (RecommendViewHolder) holder;
            recommendViewHolder.setData(result.getRecommend_info());

        }
        else if (getItemViewType(position) == HOT)
        {
            //广告条幅
           HotViewHolder hotViewHolder = (HotViewHolder) holder;
            hotViewHolder.setData(result.getHot_info());

        }
    }
    class HotViewHolder extends RecyclerView.ViewHolder
    {

        private final Context mcontext;
        private TextView tv_more_hot;
        private GridView gv_hot;
        private HotAdapter adapter;
        public HotViewHolder(final Context mContext, View itemView)
        {
            super(itemView);
            this.mcontext=mContext;
            tv_more_hot = (TextView) itemView.findViewById(R.id.tv_more_hot);
            gv_hot = (GridView) itemView.findViewById(R.id.gv_hot);


        }

        public void setData(final List<ResultBeanData.ResultBean.HotInfoBean> hot_info)
        {
            //设置适配器
            adapter = new HotAdapter(mContext,hot_info);
            gv_hot.setAdapter(adapter);

            gv_hot.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                {
                    Toast.makeText(mContext, "position==" + position, Toast.LENGTH_SHORT).show();
                    ResultBeanData.ResultBean.HotInfoBean hotInfoBean = hot_info.get(position);
                    GoodsBean goodsBean = new GoodsBean();
                    goodsBean.setCover_price(hotInfoBean.getCover_price());
                    goodsBean.setFigure(hotInfoBean.getFigure());
                    goodsBean.setName(hotInfoBean.getName());
                    goodsBean.setProduct_id(hotInfoBean.getProduct_id());
                    startGoodsInfoActivity(goodsBean);
                }
            });
        }
    }

    class RecommendViewHolder extends RecyclerView.ViewHolder

    {

        private final Context mContext;
        private TextView tv_more_recommend;
        private GridView gv_recommend;
        private RecommendAdapter adapter;

        public RecommendViewHolder(final Context mContext, View itemView)
        {
            super(itemView);
            this.mContext = mContext;
            tv_more_recommend = (TextView) itemView.findViewById(R.id.tv_more_recommend);
            gv_recommend = (GridView) itemView.findViewById(R.id.gv_recommend);

        }


        public void setData(final List<ResultBeanData.ResultBean.RecommendInfoBean> recommend_info)
        {
            adapter=new RecommendAdapter(mContext,recommend_info);
            gv_recommend.setAdapter(adapter);
            gv_recommend.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                {
                    Toast.makeText(mContext, "position=="+position, Toast.LENGTH_SHORT).show();
                    GoodsBean goodsBean = new GoodsBean();
                    ResultBeanData.ResultBean.RecommendInfoBean recommendInfoBean =
                            recommend_info.get(position);
                    goodsBean.setCover_price(recommendInfoBean.getCover_price());
                    goodsBean.setFigure(recommendInfoBean.getFigure());
                    goodsBean.setName(recommendInfoBean.getName());
                    goodsBean.setProduct_id(recommendInfoBean.getProduct_id());
                    startGoodsInfoActivity(goodsBean);
                }
            });
        }
    }

    class SeckillViewHolder extends RecyclerView.ViewHolder
    {

        private final Context mContext;
        private TextView tv_time_seckill;
        private TextView tv_more_seckill;
        private RecyclerView rv_seckill;
        private SeckillAdapter adapter;

        private long dt = 0;
        private Handler handler = new Handler()
        {
            public void handleMessage(Message msg)
            {
                dt -= 1000;
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
                String seckillTime = simpleDateFormat.format(new Date(dt));

                tv_time_seckill.setText(seckillTime);

                handler.removeMessages(0);
                handler.sendEmptyMessageDelayed(0, 1000);
                if (dt <= 0)
                {
                    handler.removeCallbacksAndMessages(null);
                }
            }
        };

        public SeckillViewHolder(Context mContext, View itemView)
        {
            super(itemView);
            this.mContext = mContext;
            tv_time_seckill = (TextView) itemView.findViewById(R.id.tv_time_seckill);
            tv_more_seckill = (TextView) itemView.findViewById(R.id.tv_more_seckill);
            rv_seckill = (RecyclerView) itemView.findViewById(R.id.rv_seckill);

        }

        public void setData(final ResultBeanData.ResultBean.SeckillInfoBean seckill_info)
        {
            //设置适配器
            adapter = new SeckillAdapter(mContext, seckill_info.getList());
            rv_seckill.setAdapter(adapter);
            //设置布局管理者
            rv_seckill.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager
                    .HORIZONTAL, false));

            adapter.setOnSeckillRecycleView(new SeckillAdapter.OnSeckillRecycleView()
            {
                @Override
                public void onItemClick(int position)
                {
                    Toast.makeText(mContext, "秒杀==" + position, Toast.LENGTH_SHORT).show();
                    ResultBeanData.ResultBean.SeckillInfoBean.ListBean listBean = seckill_info
                            .getList().get(position);
                    GoodsBean goodsBean = new GoodsBean();

                    goodsBean.setCover_price(listBean.getCover_price());
                    goodsBean.setFigure(listBean.getFigure());
                    goodsBean.setName(listBean.getName());
                    goodsBean.setProduct_id(listBean.getProduct_id());
                    startGoodsInfoActivity(goodsBean);
                }
            });

            dt = Integer.parseInt(seckill_info.getEnd_time()) - Integer.parseInt(seckill_info.getStart_time());

            handler.sendEmptyMessageDelayed(0, 1000);
        }
    }

    class ActViewHolder extends RecyclerView.ViewHolder

    {
        private final Context mContext;
        private ViewPager act_viewpager;

        public ActViewHolder(Context mContext, View itemView)
        {
            super(itemView);
            this.mContext = mContext;
            act_viewpager = (ViewPager) itemView.findViewById(R.id.act_viewpager);


        }

        public void setData(final List<ResultBeanData.ResultBean.ActInfoBean> act_info)
        {
            act_viewpager.setPageMargin(60);
            act_viewpager.setOffscreenPageLimit(3);//>=3

            //setPageTransformer 决定动画效果
            act_viewpager.setPageTransformer(true, new ScaleInTransformer());
            //设置适配器
            act_viewpager.setAdapter(new PagerAdapter()
            {
                @Override
                public int getCount()
                {
                    return act_info.size();
                }

                @Override
                public boolean isViewFromObject(View view, Object object)
                {
                    return view == object;
                }

                @Override
                public Object instantiateItem(ViewGroup container, final int position)
                {
                    ImageView imageView = new ImageView(mContext);
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    Glide.with(mContext).load(Constants.BASE_URL_IMAGE + act_info.get(position).getIcon_url()).into(imageView);
                    container.addView(imageView);
                    imageView.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v)
                        {
                            Toast.makeText(mContext, "position==" + position, Toast.LENGTH_SHORT).show();
                        }
                    });
                    return imageView;
                }

                @Override
                public void destroyItem(ViewGroup container, int position, Object object)
                {
                    //                    super.destroyItem(container, position, object);
                    container.removeView((View) object);
                }
            });
        }
    }

    class ChannelViewHolder extends RecyclerView.ViewHolder
    {

        private final Context mContext;
        private GridView gv_channel;
        private ChannelAdapter adapter;

        public ChannelViewHolder(final Context mContext, View itemView)
        {
            super(itemView);
            this.mContext = mContext;
            gv_channel = (GridView) itemView.findViewById(R.id.gv_channel);
            gv_channel.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                {
                    Toast.makeText(mContext, "position==" + position, Toast.LENGTH_SHORT).show();
                }
            });
        }

        public void setData(List<ResultBeanData.ResultBean.ChannelInfoBean> channel_info)
        {
            //设置适配器
            adapter = new ChannelAdapter(mContext, channel_info);
            gv_channel.setAdapter(adapter);
        }
    }

    class BannerViewHolder extends RecyclerView.ViewHolder

    {

        private final Context mContext;
        private Banner banner;

        public BannerViewHolder(final Context mContext, View itemView)
        {
            super(itemView);
            this.mContext = mContext;
            banner = (Banner) itemView.findViewById(R.id.banner);

            banner.setOnBannerClickListener(new OnBannerClickListener()
            {
                @Override
                public void OnBannerClick(int position)
                {
                    Toast.makeText(mContext, "position==" + position, Toast.LENGTH_SHORT).show();
//                    startGoodsInfoActivity();
                }
            });
        }

        public void setData(List<ResultBeanData.ResultBean.BannerInfoBean> banner_info)
        {
            List<String> imageUrls = new ArrayList<>();
            for (int i = 0; i < banner_info.size(); i++)
            {
                String imageUrl = banner_info.get(i).getImage();
                imageUrls.add(imageUrl);
            }
            //设置循环指示点
            banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
            //设置手风琴效果
            banner.setBannerAnimation(Transformer.Accordion);
            banner.setImages(imageUrls, new OnLoadImageListener()
            {
                @Override
                public void OnLoadImage(ImageView view, Object url)
                {
                    Glide.with(mContext).load(Constants.BASE_URL_IMAGE + url).into(view);
                }
            });
        }
    }

    private void startGoodsInfoActivity(GoodsBean goodsBean)
    {
        Intent intent = new Intent(mContext, GoodsInfoActivity.class);
        intent.putExtra(GOODS_BEAN, goodsBean);
        mContext.startActivity(intent);
    }


    @Override
    public int getItemViewType(int position)
    {

        switch (position)
        {
            case BANNER:
                currentType = BANNER;
                break;
            case CHANNEL:
                currentType = CHANNEL;
                break;
            case ACT:
                currentType = ACT;
                break;
            case SECKILL:
                currentType = SECKILL;
                break;
            case RECOMMEND:
                currentType = RECOMMEND;
                break;
            case HOT:
                currentType = HOT;
                break;
        }
        return currentType;
    }

    @Override
    public int getItemCount()
    {
        return 6;
    }
}
