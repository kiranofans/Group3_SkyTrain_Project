package com.yamibo.bbs.group3_skytrain_project.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yamibo.bbs.group3_skytrain_project.R;

import java.util.ArrayList;
import java.util.List;

public class ImgPagerAdp extends PagerAdapter {
    private List<Integer> imgIdList;
    public Context context;
    private static ImageView imgView1,imgView2,imgView3,imgView4,imgView5;
    private LayoutInflater inflater;
    private static View v;

    public ImgPagerAdp(Context context, List<Integer> imgIds){
        this.context=context;
        this.inflater=(LayoutInflater)context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        imgIdList=imgIds;
    }

    @Override
    public int getCount() {
        return imgIdList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;//yes view is from object
    }

    @Override
    public Object instantiateItem(ViewGroup container,int position){
        View v=this.inflater.inflate(R.layout.pager_items,null);
        imgIdList=new ArrayList<>();
        ViewPager vp=(ViewPager)v.findViewById(R.id.pager_schedule);
        imgViewInit();

        imgView1.setImageResource(imgIdList.get(position));
        container.addView(v,0);
        return v;
    }
    @Override
    public void destroyItem(ViewGroup container,int pos,Object object){
        ((ViewPager)container).removeView((View)(object));
    }
    public void imgViewInit(){
       // imgView1=(ImageView)v.findViewById(R.id.pagerImgView);
    }
}
