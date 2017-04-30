package com.lhd.myshoppingmall.shoppingcart.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.lhd.myshoppingmall.R;
import com.lhd.myshoppingmall.base.BaseFragment;
import com.lhd.myshoppingmall.home.bean.GoodsBean;
import com.lhd.myshoppingmall.shoppingcart.adapter.ShoppingCartAdapter;
import com.lhd.myshoppingmall.shoppingcart.pay.PayResult;
import com.lhd.myshoppingmall.shoppingcart.pay.SignUtils;
import com.lhd.myshoppingmall.shoppingcart.utils.CartStorage;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

/**
 * Created by acer on 2017/4/16 11:13.
 * 作用：
 */
public class ShoppingCartFragment extends BaseFragment implements View.OnClickListener
{

    private TextView tvShopcartEdit;
    private RecyclerView recyclerview;
    private LinearLayout llCheckAll;
    private CheckBox checkboxAll;
    private TextView tvShopcartTotal;
    private Button btnCheckOut;
    private LinearLayout llDelete;
    private CheckBox cbAll;
    private Button btnDelete;
    private Button btnCollection;

    private LinearLayout ll_empty_shopcart;
    private ImageView ivEmpty;
    private TextView tvEmptyCartTobuy;

    private ShoppingCartAdapter adapter;

    //编辑状态
    private static final int ACTION_EDIT = 1;
    //完成状态
    private static final int ACTION_COMPLETE = 2;

    @Override
    public View initView()
    {
        Log.e("TAG", "购物车的Fragment的UI被初始化了");
        View view = View.inflate(mContext, R.layout.fragment_shopping_cart, null);
        tvShopcartEdit = (TextView) view.findViewById(R.id.tv_shopcart_edit);
        recyclerview = (RecyclerView) view.findViewById(R.id.recyclerview);
        llCheckAll = (LinearLayout) view.findViewById(R.id.ll_check_all);
        checkboxAll = (CheckBox) view.findViewById(R.id.checkbox_all);
        tvShopcartTotal = (TextView) view.findViewById(R.id.tv_shopcart_total);
        btnCheckOut = (Button) view.findViewById(R.id.btn_check_out);
        llDelete = (LinearLayout) view.findViewById(R.id.ll_delete);
        cbAll = (CheckBox) view.findViewById(R.id.cb_all);
        btnDelete = (Button) view.findViewById(R.id.btn_delete);
        btnCollection = (Button) view.findViewById(R.id.btn_collection);

        ll_empty_shopcart = (LinearLayout) view.findViewById(R.id.ll_empty_shopcart);
        ivEmpty = (ImageView) view.findViewById(R.id.iv_empty);
        tvEmptyCartTobuy = (TextView) view.findViewById(R.id.tv_empty_cart_tobuy);

        btnCheckOut.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnCollection.setOnClickListener(this);

        setListener();
        return view;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        showData();
    }

    private void setListener()
    {
        //设置默认为编辑状态
        tvShopcartEdit.setTag(ACTION_EDIT);
        tvShopcartEdit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int action = (int) v.getTag();
                if (action == ACTION_EDIT)
                {

                    showDelete();

                }
                else
                {
                    hideDelete();

                }
            }
        });
    }

    private void showDelete()
    {
        //设置状态和文本
        tvShopcartEdit.setTag(ACTION_COMPLETE);
        tvShopcartEdit.setText("完成");
        //显示删除视图
        llDelete.setVisibility(View.VISIBLE);
        //隐藏结算视图
        llCheckAll.setVisibility(View.GONE);
        //设置为全不选
        adapter.checkAll_none(false);
        adapter.checkAll();
    }

    private void hideDelete()
    {
        //设置状态和文本
        tvShopcartEdit.setTag(ACTION_EDIT);
        tvShopcartEdit.setText("编辑");
        //隐藏删除视图
        llDelete.setVisibility(View.GONE);
        //显示结算视图
        llCheckAll.setVisibility(View.VISIBLE);
        //设置为全选
        adapter.checkAll_none(true);
        adapter.checkAll();
        adapter.showTotalPrice();

    }

    @Override
    public void initData()
    {
        super.initData();
        Log.e("TAG", "购物车的Fragment的数据被初始化了");
        showData();
        //        for (int i = 0; i < goodsBeanList.size(); i++)
        //        {
        //
        //            Log.e("TAG", "购物车数据"+goodsBeanList.get(i).toString());
        //        }

    }

    @Override
    public void onClick(View v)
    {
        if (v == btnCheckOut)
        {
            // Handle clicks for btnCheckOut
            pay(v);
        }
        else if (v == btnDelete)
        {
            // Handle clicks for btnDelete
            adapter.deleteData();
            adapter.checkAll();
            if (adapter.getItemCount() == 0)
            {
                showEmpty();
            }
        }
        else if (v == btnCollection)
        {
            // Handle clicks for btnCollection
        }
    }

    private void showData()
    {
        List<GoodsBean> goodsBeanList = CartStorage.getInstance().getAllData();

        if (goodsBeanList != null && goodsBeanList.size() > 0)
        {
            //有数据 隐藏空购物车布局


            ll_empty_shopcart.setVisibility(View.GONE);

            tvShopcartEdit.setVisibility(View.VISIBLE);

            llCheckAll.setVisibility(View.VISIBLE);

            adapter = new ShoppingCartAdapter(mContext, goodsBeanList, checkboxAll,
                    tvShopcartTotal, cbAll);
            recyclerview.setAdapter(adapter);

            //设置布局管理者
            recyclerview.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager
                    .VERTICAL, false));
        }
        else
        {
            //没有数据
            showEmpty();
        }


    }

    public void showEmpty()
    {
        ll_empty_shopcart.setVisibility(View.VISIBLE);
        tvShopcartEdit.setVisibility(View.GONE);
        llDelete.setVisibility(View.GONE);
    }

    ////////支付宝集成/////////

    // 商户PID
    public static final String PARTNER = "2088911876712776";
    // 商户收款账号
    public static final String SELLER = "chenlei@atguigu.com";
    // 商户私钥，pkcs8格式
    public static final String RSA_PRIVATE =
            "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAJcKRtBl63jQ0jDb\n" +
                                             "xpiq3LxFxFY2LGQFvwZQ83r/zOOyJ/VTr68nQgM0491d9/ZvhmFgtLbJeeChypGu\n" +
                                             "cRFJhtCjPNvDzmJBzkQQi45ehi/9p+5W7pnUXXVyw7K4ij7ld/ztO0H1401TDiLJ\n" +
                                             "tBFt/WdRiZnB+CsRfNj7j5B5Nry7AgMBAAECgYEAkSwoej+r/zi+/oUPMkfv1+o6\n" +
                                             "1x5EtnKrc5e1HsDlHulq7KreDrzt/wUECw45uhzhjZEUwSePxG/dsv524MXKFB77\n" +
                                             "9IUc+/aj5MRoxniDHhp2fDbhrhLO/bqUIsLnvdtfL+SGSRVvOlYdq8a4JnaFngDE\n" +
                                             "CsIwiUc5cWsNq1QEyykCQQDGtXqdh5wngq8VUJGlK4XgWl9CbqZ13W7jIDQRfIuS\n" +
                                             "NgOftCRmvor593OoZq1wIaHriQMEJomLX1oPdxJ7Dla1AkEAwpZpEv2J99lHPAN7\n" +
                                             "B7o3a39VOfy77/DYnlW7PErXY6wdOOftZZ+umnSDBF3M8pi7aVWsEJIAfn39kq+L\n" +
                                             "R477rwJAO2HGd8cr6j7KlcMTE1oTUv2O1Cp/AmAfIC7igItIpnQP1y0dZ7rvi03g\n" +
                                             "uWWd1RGn8txqE1Z06jqfjlmWI0IgtQJBALjFiHru487EtF6pU28QBUX/QFdyxKbf\n" +
                                             "hWI8ODHVQ1ey/ICnwoYILg0ea7Z+t3j5TKSGJIALk4qJanqaVGAbxqMCQQCDXv62\n" +
                                             "gqPItlCPtmubZa28V8TqYzKP97Z8Zf5Ok/WT65nlOHzR9nIWecIU4wcOVJu6NreW\n" +
                                             "kaww5PcxaVNSCxyp";
    //公钥要放在服务器上
    // 支付宝公钥
    public static final String RSA_PUBLIC =
            "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCXCkbQZet40NIw28aYqty8RcRWNixkBb8GUPN6" +
                                            "/8zjsif1U6+vJ0IDNOPdXff2b4ZhYLS2yXngocqRrnERSYbQozzbw85iQc5EEIuOXoYv" +
                                            "/afuVu6Z1F11csOyuIo+5Xf87TtB9eNNUw4iybQRbf1nUYmZwfgrEXzY+4+QeTa8uwIDAQAB";
    private static final int SDK_PAY_FLAG = 1;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler()
    {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                case SDK_PAY_FLAG:
                {
                    PayResult payResult = new PayResult((String) msg.obj);
                    /**
                     * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                     * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                     * docType=1) 建议商户依赖异步通知
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息

                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000"))
                    {
                        Toast.makeText(mContext, "支付成功", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000"))
                        {
                            Toast.makeText(mContext, "支付结果确认中", Toast.LENGTH_SHORT).show();

                        }
                        else
                        {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(mContext, "支付失败", Toast.LENGTH_SHORT).show();

                        }
                    }
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };

    /**
     * call alipay sdk pay. 调用SDK支付
     */
    public void pay(View v)
    {
        if (TextUtils.isEmpty(PARTNER) || TextUtils.isEmpty(RSA_PRIVATE) || TextUtils.isEmpty
                (SELLER))
        {
            new AlertDialog.Builder(mContext).setTitle("警告").setMessage("需要配置PARTNER | " +
                                                                        "RSA_PRIVATE| " +
                                                                        "SELLER")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialoginterface, int i)
                {
                    //
                    getActivity().finish();
                }
            }).show();
            return;
        }
        String orderInfo = getOrderInfo("购买的商品", "一双绣花鞋", adapter.getTotalPrice() + "");

        /**
         * 特别注意，这里的签名逻辑需要放在服务端，切勿将私钥泄露在代码中！
         */
        String sign = sign(orderInfo);
        try
        {
            /**
             * 仅需对sign 做URL编码
             */
            sign = URLEncoder.encode(sign, "UTF-8");
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }

        /**
         * 完整的符合支付宝参数规范的订单信息
         */
        final String payInfo = orderInfo + "&sign=\"" + sign + "\"&" + getSignType();

        Runnable payRunnable = new Runnable()
        {

            @Override
            public void run()
            {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(getActivity());
                // 调用支付接口，获取支付结果
                String result = alipay.pay(payInfo, true);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     * get the sdk version. 获取SDK版本号
     */
    public void getSDKVersion()
    {
        PayTask payTask = new PayTask((Activity) mContext);
        String version = payTask.getVersion();
        Toast.makeText(mContext, version, Toast.LENGTH_SHORT).show();
    }


    /**
     * create the order info. 创建订单信息
     */
    private String getOrderInfo(String subject, String body, String price)
    {

        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + PARTNER + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + getOutTradeNo() + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + subject + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + body + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + price + "\"";

        // 服务器异步通知页面路径
        orderInfo += "&notify_url=" + "\"" + "http://notify.msp.hk/notify.htm" + "\"";

        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"30m\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        orderInfo += "&return_url=\"m.alipay.com\"";

        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
        // orderInfo += "&paymethod=\"expressGateway\"";

        return orderInfo;
    }

    /**
     * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
     */
    private String getOutTradeNo()
    {
        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss", Locale.getDefault());
        Date date = new Date();
        String key = format.format(date);

        Random r = new Random();
        key = key + r.nextInt();
        key = key.substring(0, 15);
        return key;
    }

    /**
     * sign the order info. 对订单信息进行签名
     *
     * @param content 待签名订单信息
     */
    private String sign(String content)
    {
        return SignUtils.sign(content, RSA_PRIVATE);
    }

    /**
     * get the sign type we use. 获取签名方式
     */
    private String getSignType()
    {
        return "sign_type=\"RSA\"";
    }

}
