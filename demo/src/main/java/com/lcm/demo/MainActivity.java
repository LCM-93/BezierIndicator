package com.lcm.demo;

import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lcm.bezierbottomIndicator.BezierBottomIndicator;
import com.lcm.bezierbottomIndicator.viewpager.CardAdapter;
import com.lcm.bezierbottomIndicator.viewpager.ShadowTransformer;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private BezierBottomIndicator bezierBottomIndicator;

    private ViewPager viewPager;
//    private List<View> viewList;
    private List<Integer> images;

    private MyAdapter myAdapter;
    private ShadowTransformer mCardShadowTransformer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        viewList = new ArrayList<>();
        images = new ArrayList<>();

        bezierBottomIndicator = (BezierBottomIndicator) findViewById(R.id.bezierBottomIndicator);
        viewPager = (ViewPager) findViewById(R.id.viewpager);


        images.add(R.mipmap.pos0);
        images.add(R.mipmap.pos1);
        images.add(R.mipmap.pos2);
        images.add(R.mipmap.pos3);

//        ImageView imageView1 = new ImageView(getApplicationContext());
//        imageView1.setImageResource(R.mipmap.pos0);
//        viewList.add(imageView1);
//        ImageView imageView2 = new ImageView(getApplicationContext());
//        imageView2.setImageResource(R.mipmap.pos1);
//        viewList.add(imageView2);
//        ImageView imageView3 = new ImageView(getApplicationContext());
//        imageView3.setImageResource(R.mipmap.pos2);
//        viewList.add(imageView3);
//        ImageView imageView4 = new ImageView(getApplicationContext());
//        imageView4.setImageResource(R.mipmap.pos3);
//        viewList.add(imageView4);

        myAdapter = new MyAdapter(images);

        mCardShadowTransformer = new ShadowTransformer(viewPager, myAdapter);
        viewPager.setAdapter(myAdapter);
        viewPager.setPageTransformer(false,mCardShadowTransformer);
        viewPager.setOffscreenPageLimit(3);

        bezierBottomIndicator.setCircularColors(Color.parseColor("#FCC04D"), Color.parseColor("#00C3E2"), Color.parseColor("#FE626D"), Color.parseColor("#966ACF"));

        bezierBottomIndicator.setViewPager(viewPager);

    }


}
