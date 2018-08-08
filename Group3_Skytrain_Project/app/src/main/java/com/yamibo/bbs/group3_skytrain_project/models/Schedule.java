package com.yamibo.bbs.group3_skytrain_project.models;

import Utils.RecViewConstants;

public class Schedule implements BaseModel {
    private int imgIds;

    public Schedule(String destination, int expectedCountdown, String scheduleStatus) {
        Destination = destination;
        ExpectedCountdown = expectedCountdown;
        ScheduleStatus = scheduleStatus;
    }

    public String getDestination() {
        return Destination;
    }

    public void setDestination(String destination) {
        Destination = destination;
    }

    public int getExpectedCountdown() {
        return ExpectedCountdown;
    }

    public void setExpectedCountdown(int expectedCountdown) {
        ExpectedCountdown = expectedCountdown;
    }

    private String Destination;
    private int ExpectedCountdown;

    public String getScheduleStatus() {
        return ScheduleStatus;
    }

    public void setScheduleStatus(String scheduleStatus) {
        ScheduleStatus = scheduleStatus;
    }

    private String ScheduleStatus;

    public Schedule(int imgIds){
        this.imgIds=imgIds;
    }
    @Override
    public int getViewType() {
        return RecViewConstants.ViewType.SCHEDULE;
    }

    public int getImgIds() {
        return imgIds;
    }

    public void setImgIds(int imgIds) {
        this.imgIds = imgIds;
    }
}
