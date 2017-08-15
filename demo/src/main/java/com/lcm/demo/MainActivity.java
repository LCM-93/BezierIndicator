package com.lcm.demo;

import android.graphics.Color;
import android.os.SystemClock;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.lcm.bezierbottomIndicator.BezierBottomIndicator;
import com.lcm.bezierbottomIndicator.viewpager.BezierViewPager;
import com.lcm.bezierbottomIndicator.viewpager.CardAdapter;
import com.lcm.bezierbottomIndicator.viewpager.ShadowTransformer;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private BezierBottomIndicator bezierBottomIndicator;

    private BezierViewPager viewPager;
    private List<Integer> images;

    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        images = new ArrayList<>();

        bezierBottomIndicator = (BezierBottomIndicator) findViewById(R.id.bezierBottomIndicator);
        viewPager = (BezierViewPager) findViewById(R.id.viewpager);


        images.add(R.mipmap.pos0);
        images.add(R.mipmap.pos1);
        images.add(R.mipmap.pos2);
        images.add(R.mipmap.pos3);


        myAdapter = new MyAdapter(images);

        viewPager.setAdapter(myAdapter);
        viewPager.setOffscreenPageLimit(3);
//        viewPager.showShadowTransformer(0.1f);


        bezierBottomIndicator.setCircularColors(Color.parseColor("#FCC04D"), Color.parseColor("#00C3E2"), Color.parseColor("#FE626D"), Color.parseColor("#966ACF"));

        bezierBottomIndicator.setViewPager(viewPager);


    }


}
