package com.example.personalaccount.ui;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Administrator on 2015/2/9.
 */
public class GetorCostAdapter extends PagerAdapter {

    private List<View> getorcostviews;
    private Context context;

    public GetorCostAdapter(List<View> getorcostviews, Context context) {
        this.getorcostviews = getorcostviews;
        this.context = context;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView(getorcostviews.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ((ViewPager) container).addView(getorcostviews.get(position));
        return  getorcostviews.get(position);
    }

    @Override
    public int getCount() {
        return getorcostviews.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == object);
    }
}
