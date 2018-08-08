package com.yamibo.bbs.group3_skytrain_project.models;

import java.util.List;

public class Route {
    public String getRouteNo() {
        return RouteNo;
    }

    public void setRouteNo(String routeNo) {
        RouteNo = routeNo;
    }

    public String getRouteName() {
        return RouteName;
    }

    public void setRouteName(String routeName) {
        RouteName = routeName;
    }


    public Route(String routeNo, String routeName, List<Schedule> schedules) {
        Schedules = schedules;
        RouteNo = routeNo;
        RouteName = routeName;

    }

    private String RouteNo;
    private String RouteName;

    public List<Schedule> getSchedules() {
        return Schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        Schedules = schedules;
    }

    private List<Schedule> Schedules;  // add mutators with @JsonProperty annotation

}
