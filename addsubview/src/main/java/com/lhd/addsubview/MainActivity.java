package com.lhd.addsubview;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends Activity
{

    private AddSubView add_sub;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        add_sub = (AddSubView)findViewById(R.id.add_sub);
        add_sub.setOnNumberChangeListener(new AddSubView.OnNumberChangeListener()
        {
            @Override
            public void onNumberChange(int value)
            {
//                Log.e("TAG", "value=="+value);
                Toast.makeText(MainActivity.this, "当前商品的数量==" + value, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
