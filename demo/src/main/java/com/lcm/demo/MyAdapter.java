package com.lcm.demo;

import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lcm.bezierIndicator.viewpager.CardAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * ****************************************************************
 * Author: LCM
 * Date: 2017/8/14 下午5:22
 * Desc:
 * *****************************************************************
 */

public class MyAdapter extends PagerAdapter implements CardAdapter {

    private float mBaseElevation ;
    private List<Integer> images;
    private List<CardView> cardViewList;

    public MyAdapter(List<Integer> images) {
        this.images = images;
        cardViewList = new ArrayList<>();
        for (int i = 0; i < images.size(); i++) {
            cardViewList.add(null);
        }
    }

    @Override
    public float getBaseElevation() {
        return mBaseElevation;
    }

    @Override
    public CardView getCardViewAt(int position) {
        return cardViewList.get(position);
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        cardViewList.set(position, null);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext())
                .inflate(R.layout.item_viewpager, container, false);
        container.addView(view);

        bind(images.get(position), view);

        CardView cardView = (CardView) view.findViewById(R.id.cardView);

        if (mBaseElevation == 0) {
            mBaseElevation = cardView.getCardElevation();
        }

        cardView.setMaxCardElevation(mBaseElevation * MAX_ELEVATION_FACTOR);
        cardViewList.set(position, cardView);
        return view;
    }

    @Override
    public int getItemPosition(Object object) {

        return POSITION_NONE;
    }


    private void bind(int img, View view) {
        ImageView imageView = (ImageView) view.findViewById(R.id.iv_image);
        imageView.setImageResource(img);
    }
}
