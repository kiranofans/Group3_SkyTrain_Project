package com.yamibo.bbs.group3_skytrain_project.activities;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yamibo.bbs.group3_skytrain_project.R;
import com.yamibo.bbs.group3_skytrain_project.models.BaseModel;

import Utils.RecViewConstants;


public class ExtendedViewPager extends ViewPager implements BaseModel{
    private String title;
    private int imgResId,backBtnId,forthBtnId;
    private LayoutInflater inflater;
    private ExtendedViewPager extendedVP;
    private FragmentTransit.ZoomableScheduleAdapter scheduleAdp;
    private FragmentTransit.ZoomableMapAdapter mapAdp;
    public ExtendedViewPager(Context context) {
        super(context);
    }

    public ExtendedViewPager(Context context,String title,FragmentTransit.ZoomableScheduleAdapter scheduleAdp,
                             int backBtnId,int forthBtnId) {
        super(context);
        this.title=title;
        this.scheduleAdp=scheduleAdp;
        this.backBtnId=backBtnId;
        this.forthBtnId=forthBtnId;
    }

    public ExtendedViewPager(Context context,String title) {
        super(context);
        this.title=title;
        this.mapAdp=new FragmentTransit.ZoomableMapAdapter();
        this.scheduleAdp=new FragmentTransit.ZoomableScheduleAdapter();

    }

    public ExtendedViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
        if (v instanceof TouchImgView1) {
            //
            // canScrollHorizontally is not supported for Api < 14. To get around this issue,
            // ViewPager is extended and canScrollHorizontallyFroyo, a wrapper around
            // canScrollHorizontally supporting Api >= 8, is called.
            //
            return ((TouchImgView1) v).canScrollHorizontallyFroyo(-dx);

        } else {
            return super.canScroll(v, checkV, dx, x, y);
        }
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImgResId() {
        return imgResId;
    }

    public void setImgResId(int imgResId) {
        this.imgResId = imgResId;
    }

    public int getBackBtnId() {
        return backBtnId;
    }

    public void setBackBtnId(int backBtnId) {
        this.backBtnId = backBtnId;
    }

    public int getForthBtnId() {
        return forthBtnId;
    }

    public void setForthBtnId(int forthBtnId) {
        this.forthBtnId = forthBtnId;
    }

    @Override
    public int getViewType() {
        return RecViewConstants.ViewType.VIEW_PAGER_TYPE;
    }

    public FragmentTransit.ZoomableScheduleAdapter getScheduleAdp() {
        return scheduleAdp;
    }

    public void setScheduleAdp(FragmentTransit.ZoomableScheduleAdapter scheduleAdp) {
        this.scheduleAdp = scheduleAdp;
    }

    public FragmentTransit.ZoomableMapAdapter getMapAdp() {
        return mapAdp;
    }

    public void setMapAdp(FragmentTransit.ZoomableMapAdapter mapAdp) {
        this.mapAdp = mapAdp;
    }
}