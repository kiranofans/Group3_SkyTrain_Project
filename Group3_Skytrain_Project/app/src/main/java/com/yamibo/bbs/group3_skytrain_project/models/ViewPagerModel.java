package com.yamibo.bbs.group3_skytrain_project.models;

import android.content.Context;

import Utils.RecViewConstants;

public class ViewPagerModel implements BaseModel{
    private int pagerId,position;

    public ViewPagerModel(int pagerId){
        this.pagerId=pagerId;
    }

    public int getPagerId() {
        return pagerId;
    }

    public int getPosition() {
        return position;
    }

    public void setPagerId(int pagerId) {
        this.pagerId = pagerId;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public int getViewType() {
        return RecViewConstants.ViewType.VIEW_PAGER_TYPE;
    }


}
