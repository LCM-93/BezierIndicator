package com.lcm.bezierbottomIndicator;

import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private BezierBottomIndicator bezierBottomIndicator;

    private ViewPager viewPager;
    private List<View> viewList;

    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewList = new ArrayList<>();

        bezierBottomIndicator = (BezierBottomIndicator) findViewById(R.id.bezierBottomIndicator);
        viewPager = (ViewPager) findViewById(R.id.viewpager);


        ImageView imageView1 = new ImageView(getApplicationContext());
        imageView1.setImageResource(R.mipmap.pos0);
        viewList.add(imageView1);
        ImageView imageView2 = new ImageView(getApplicationContext());
        imageView2.setImageResource(R.mipmap.pos1);
        viewList.add(imageView2);
        ImageView imageView3 = new ImageView(getApplicationContext());
        imageView3.setImageResource(R.mipmap.pos2);
        viewList.add(imageView3);
        ImageView imageView4 = new ImageView(getApplicationContext());
        imageView4.setImageResource(R.mipmap.pos3);
        viewList.add(imageView4);

        myAdapter = new MyAdapter();
        viewPager.setAdapter(myAdapter);

        bezierBottomIndicator.setCircularColors(Color.parseColor("#FCC04D"), Color.parseColor("#00C3E2"), Color.parseColor("#FE626D"), Color.parseColor("#966ACF"));

        bezierBottomIndicator.setViewPager(viewPager);

    }

    class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(viewList.get(position));
            return viewList.get(position);
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
    }

}
