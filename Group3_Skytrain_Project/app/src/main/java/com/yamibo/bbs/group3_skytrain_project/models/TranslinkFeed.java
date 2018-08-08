package com.yamibo.bbs.group3_skytrain_project.models;

import org.json.JSONArray;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.List;

import Utils.RecViewConstants;
@Root
public class TranslinkFeed implements BaseModel,Serializable{

    private String timeStamp,status,location,schedule,title;
    private String category;
    private int travelTime,speedKmph,linkedId,imgResId;

    @Element
    private String feedsContent;
    public TranslinkFeed (){
        //put am empty constructor just in case of declaration
    }

    public TranslinkFeed(String title,String timeStamp,String feedsContent,String category){
        this.title=title;
        this.timeStamp=timeStamp;
        this.feedsContent=feedsContent;
        this.category=category;
    }

    public TranslinkFeed(String status) {
        this.status = status;
       /* this.feed = feed;
        this.items = items;*/
    }
    public TranslinkFeed(String title,int imgResId){
        this.title=title;
        this.imgResId=imgResId;
    }

    @Override
    public int getViewType() {
        return RecViewConstants.ViewType.FEED_TYPE;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public int getTravelTime() {
        return travelTime;
    }

    public void setTravelTime(int travelTime) {
        this.travelTime = travelTime;
    }

    public int getSpeedKmph() {
        return speedKmph;
    }

    public void setSpeedKmph(int speedKmph) {
        this.speedKmph = speedKmph;
    }

    public int getLinkedId() {
        return linkedId;
    }

    public void setLinkedId(int linkedId) {
        this.linkedId = linkedId;
    }

    public String getFeedsContent() {
        return feedsContent;
    }

    public void setFeedsContent(String feedsContent) {
        this.feedsContent = feedsContent;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
