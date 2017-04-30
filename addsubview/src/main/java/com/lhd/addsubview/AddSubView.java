package com.lhd.addsubview;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by lihuaidong on 2017/4/23 18:22.
 * 微信：lhd520ssp
 * QQ:414320737
 * 作用：
 */
public class AddSubView extends LinearLayout
{
    private final Context mContext;
    private ImageView iv_sub;
    private TextView tv_value;
    private ImageView iv_add;
    private int value = 1;
    private int minValue = 1;
    private int maxValue = 5;

    public AddSubView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        mContext = context;
        View.inflate(context, R.layout.add_sub_view, this);
        initViews();
        int value = getValue();
        setValue(value);
        initListener();

    }
    private void initViews()
    {
        iv_sub = (ImageView) findViewById(R.id.iv_sub);
        tv_value = (TextView) findViewById(R.id.tv_value);
        iv_add = (ImageView) findViewById(R.id.iv_add);
    }


    private void initListener()
    {
        iv_sub.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                sub();

            }
        });
        iv_add.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                add();
            }
        });
//                Toast.makeText(mContext, "当前产品数量" + value, Toast.LENGTH_SHORT).show();

    }

    private void add()
    {
        if (value < maxValue)
        {
            value++;

        }
            setValue(value);
        if(onNumberChangeListener!=null) {

            onNumberChangeListener.onNumberChange(value);
        }
    }

    private void sub()
    {
        if (value > minValue)
        {
            value--;
        }
        setValue(value);
        if(onNumberChangeListener!=null) {

            onNumberChangeListener.onNumberChange(value);
        }
    }


    public int getValue()
    {
        String valueStr = tv_value.getText().toString().trim();
        if (!TextUtils.isEmpty(valueStr))
        {
            value = Integer.parseInt(valueStr);
        }
        return value;
    }

    public void setValue(int value)
    {
        this.value = value;
        tv_value.setText(value + "");
    }

    public interface OnNumberChangeListener
    {
        public void onNumberChange(int value);
    }

    private OnNumberChangeListener onNumberChangeListener;

    public void setOnNumberChangeListener(OnNumberChangeListener onNumberChangeListener)
    {
        this.onNumberChangeListener = onNumberChangeListener;
    }
}
