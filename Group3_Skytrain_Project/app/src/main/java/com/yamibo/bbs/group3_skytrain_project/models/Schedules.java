package com.yamibo.bbs.group3_skytrain_project.models;

public class Schedules {

    public Schedules(Schedule[] schedule) {
        this.schedule = schedule;
    }

    public Schedule[] getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule[] schedule) {
        this.schedule = schedule;
    }

    private Schedule[] schedule;


}
