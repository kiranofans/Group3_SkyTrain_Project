package com.yamibo.bbs.group3_skytrain_project.models;

import Utils.RecViewConstants;

public class Favorites implements BaseModel {
    private String imgUrl;
    private String faveTitle;
    private String category;
    private int imgResId;

    public Favorites(String faveTitle,String category,int imgResId) {
        this.faveTitle=faveTitle;
        this.category=category;
        this.imgResId=imgResId;
    }

    @Override
    public int getViewType() {
        return RecViewConstants.ViewType.FAVE_TYPE;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getFaveTitle() {
        return faveTitle;
    }

    public void setFaveTitle(String faveTitle) {
        this.faveTitle = faveTitle;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getImgResId() {
        return imgResId;
    }

    public void setImgResId(int imgResId) {
        this.imgResId = imgResId;
    }
}
