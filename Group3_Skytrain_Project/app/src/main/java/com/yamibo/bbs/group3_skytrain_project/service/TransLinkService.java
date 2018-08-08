package com.yamibo.bbs.group3_skytrain_project.service;

import com.yamibo.bbs.group3_skytrain_project.models.Route;
import com.yamibo.bbs.group3_skytrain_project.models.Stop;
import com.yamibo.bbs.group3_skytrain_project.models.TranslinkFeed;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TransLinkService {
    //String SERVICE_ENDPOINT = "http://api.translink.ca";
    @Headers("Accept: application/json")
    @GET("/rttiapi/v1/stops")
    Call<List<Stop>> getStop( @Query("apikey") String apikey, @Query("lat") double lat,
                              @Query("long") double longt);
    Call<List<TranslinkFeed>> getTransFeed(@Query("apikey") String apikey);

    @Headers("Accept: application/json")
    @GET("/rttiapi/v1/stops/{stop}/estimates")
    Call<List<Route>> getRoutes(@Path("stop") String stop, @Query("apikey") String apikey, @Query("timeframe") int timeframe );
}
