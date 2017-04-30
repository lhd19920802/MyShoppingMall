package com.lhd.myshoppingmall.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lhd.myshoppingmall.R;
import com.lhd.myshoppingmall.home.bean.GoodsBean;
import com.lhd.myshoppingmall.shoppingcart.utils.CartStorage;
import com.lhd.myshoppingmall.utils.Constants;

public class GoodsInfoActivity extends Activity implements View.OnClickListener
{

    private GoodsBean goodsBean;

    private ImageButton ibGoodInfoBack;
    private ImageButton ibGoodInfoMore;
    private ImageView ivGoodInfoImage;
    private TextView tvGoodInfoName;
    private TextView tvGoodInfoDesc;
    private TextView tvGoodInfoPrice;
    private TextView tvGoodInfoStore;
    private TextView tvGoodInfoStyle;
    private WebView wbGoodInfoMore;
    private LinearLayout llGoodsRoot;
    private TextView tvGoodInfoCallcenter;
    private TextView tvGoodInfoCollection;
    private TextView tvGoodInfoCart;
    private Button btnGoodInfoAddcart;
    private TextView tv_more_share;
    private TextView tv_more_search;
    private TextView tv_more_home;


    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2017-04-23 10:08:48 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews()
    {
        ibGoodInfoBack = (ImageButton) findViewById(R.id.ib_good_info_back);
        ibGoodInfoMore = (ImageButton) findViewById(R.id.ib_good_info_more);
        ivGoodInfoImage = (ImageView) findViewById(R.id.iv_good_info_image);
        tvGoodInfoName = (TextView) findViewById(R.id.tv_good_info_name);
        tvGoodInfoDesc = (TextView) findViewById(R.id.tv_good_info_desc);
        tvGoodInfoPrice = (TextView) findViewById(R.id.tv_good_info_price);
        tvGoodInfoStore = (TextView) findViewById(R.id.tv_good_info_store);
        tvGoodInfoStyle = (TextView) findViewById(R.id.tv_good_info_style);
        wbGoodInfoMore = (WebView) findViewById(R.id.wb_good_info_more);
        llGoodsRoot = (LinearLayout) findViewById(R.id.ll_goods_root);
        tvGoodInfoCallcenter = (TextView) findViewById(R.id.tv_good_info_callcenter);
        tvGoodInfoCollection = (TextView) findViewById(R.id.tv_good_info_collection);
        tvGoodInfoCart = (TextView) findViewById(R.id.tv_good_info_cart);
        btnGoodInfoAddcart = (Button) findViewById(R.id.btn_good_info_addcart);
        tv_more_share = (TextView)findViewById(R.id.tv_more_share);
        tv_more_search = (TextView)findViewById(R.id.tv_more_search);
        tv_more_home = (TextView)findViewById(R.id.tv_more_home);


        ibGoodInfoBack.setOnClickListener(this);
        ibGoodInfoMore.setOnClickListener(this);
        btnGoodInfoAddcart.setOnClickListener(this);
        tv_more_share.setOnClickListener(this);
        tv_more_search.setOnClickListener(this);
        tv_more_home.setOnClickListener(this);
        tvGoodInfoCallcenter.setOnClickListener(this);
        tvGoodInfoCollection.setOnClickListener(this);
        tvGoodInfoCart.setOnClickListener(this);
    }

    /**
     * Handle button click events<br />
     * <br />
     * Auto-created on 2017-04-23 10:08:48 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    @Override
    public void onClick(View v)
    {
        if (v == ibGoodInfoBack)
        {
            // Handle clicks for ibGoodInfoBack
            finish();
        }
        else if (v == ibGoodInfoMore)
        {
            // Handle clicks for ibGoodInfoMore
            Toast.makeText(GoodsInfoActivity.this, "更多", Toast.LENGTH_SHORT).show();
        }
        else if (v == btnGoodInfoAddcart)
        {
            // Handle clicks for btnGoodInfoAddcart
            Toast.makeText(GoodsInfoActivity.this, "加入购物车成功", Toast.LENGTH_SHORT).show();
            CartStorage.getInstance().addData(goodsBean);
        }
        else if (v == tv_more_share)
        {
            Toast.makeText(GoodsInfoActivity.this, "分享", Toast.LENGTH_SHORT).show();
            // Handle clicks for btnGoodInfoAddcart
        }
        else if (v == tv_more_search)
        {
            Toast.makeText(GoodsInfoActivity.this, "搜索", Toast.LENGTH_SHORT).show();
            // Handle clicks for btnGoodInfoAddcart
        }
        else if (v == tv_more_home)
        {
            Toast.makeText(GoodsInfoActivity.this, "首页", Toast.LENGTH_SHORT).show();
            // Handle clicks for btnGoodInfoAddcart
        }
        else if (v == tvGoodInfoCallcenter)
        {
            Toast.makeText(GoodsInfoActivity.this, "联系客服", Toast.LENGTH_SHORT).show();
            // Handle clicks for btnGoodInfoAddcart
        }
        else if (v == tvGoodInfoCollection)
        {
            Toast.makeText(GoodsInfoActivity.this, "收藏", Toast.LENGTH_SHORT).show();
            // Handle clicks for btnGoodInfoAddcart
        }
        else if (v == tvGoodInfoCart)
        {
            Toast.makeText(GoodsInfoActivity.this, "购物车", Toast.LENGTH_SHORT).show();
            // Handle clicks for btnGoodInfoAddcart
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_info);
        findViews();
        goodsBean = (GoodsBean) getIntent().getSerializableExtra("goodsbean");
        setDataForView();
    }


    private void setDataForView()
    {
        Glide.with(this).load(Constants.BASE_URL_IMAGE + goodsBean.getFigure()).into(ivGoodInfoImage);
        tvGoodInfoName.setText(goodsBean.getName());
        tvGoodInfoPrice.setText(goodsBean.getCover_price());

        setWebView(goodsBean.getProduct_id());
    }

    private void setWebView(String product_id)
    {
        if(product_id!=null) {
            wbGoodInfoMore.loadUrl("https://www.oschina.net/");

            WebSettings settings = wbGoodInfoMore.getSettings();
            settings.setJavaScriptEnabled(true);
            settings.setUseWideViewPort(true);
            settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

            wbGoodInfoMore.setWebViewClient(new WebViewClient());

        }
    }
}
