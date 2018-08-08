package com.yamibo.bbs.group3_skytrain_project.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.yamibo.bbs.group3_skytrain_project.R;
import com.yamibo.bbs.group3_skytrain_project.adapter.MultiViewRecAdapter;
import com.yamibo.bbs.group3_skytrain_project.models.BaseModel;
import com.yamibo.bbs.group3_skytrain_project.models.Stop;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Utils.VolleySingleton;

public class BusStopNoActivities extends AppBaseActivity {
    private static String STOPS_API_URL="http://api.translink.ca/rttiapi/v1/stops?apikey=fH8nhLCTC142J3YXmtLC&lat=49.248523&long=-123.108800&radius=500";
    private String ESTIMATE_STOP_URL="http://api.translink.ca/rttiapi/v1/stops/51042/estimates?apikey=fH8nhLCTC142J3YXmtLC";
    //returns stop #51042 6 buses in next 24 hours

    private static String usrInput;
    private List<BaseModel> stopsList;
    private EditText inputText;
    private RecyclerView stopRecView;
    private MultiViewRecAdapter recAdapter;
    private ProgressDialog dialog;

    private static int userIn;
    private static int stopNum=0;
    private static BusStopNoActivities busStop;

    public static BusStopNoActivities getInstance(){
        if(busStop==null){
            busStop=new BusStopNoActivities();
        }
        return busStop;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_stop_no);
        Button searchbtn =(Button) findViewById(R.id.search1);
        inputText=(EditText) findViewById(R.id.inputstop);
        stopsList=new ArrayList<>();
        stopRecView =(RecyclerView)findViewById(R.id.stop_detailRecView);
        stopRecView.setLayoutManager(new LinearLayoutManager(BusStopNoActivities.this));
        dialog=new ProgressDialog(BusStopNoActivities.this);
        dialog.setCanceledOnTouchOutside(false);
        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getBusStops();
                if(getBusStops()!=0){
                    getStopDetail();
                }else if(usrInput.isEmpty()){
                    Toast.makeText(BusStopNoActivities.this,
                            "You haven't enter anything yet",Toast.LENGTH_SHORT).show();
                    dialog.hide();
                }else if(getBusStops()==0){
                    Toast.makeText(BusStopNoActivities.this,
                            "Oops! Not in service",Toast.LENGTH_SHORT).show();
                    dialog.hide();
                }
            }

        });
    }
    private void getStopDetail(){
        dialog.setMessage("Loading...");
        dialog.show();
        JsonArrayRequest request=new JsonArrayRequest(Request.Method.GET, ESTIMATE_STOP_URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        JSONObject stopEstimateObj=new JSONObject();
                        String routeName= ""; String direction="";
                        try {
                        for(int i=0;i<response.length();i++){

                              stopEstimateObj=response.getJSONObject(i);
                               routeName = stopEstimateObj.getString("RouteName");
                            direction=stopEstimateObj.getString("Direction");

                       }
                        JSONArray schedules=stopEstimateObj.getJSONArray("Schedules");
                       for(int i=0;i<schedules.length();i++){
                           JSONObject shceduleObj=schedules.getJSONObject(i);
                           String destination=shceduleObj.getString("Destination");
                           String leaveTime=shceduleObj.getString("ExpectedLeaveTime");
                           int countdownMins=shceduleObj.getInt("ExpectedCountdown");
                           String routeNum=shceduleObj.getString("RouteNo");

                           if(getBusStops()!=0){
                               Stop stops=new Stop(stopNum,leaveTime,countdownMins,routeNum,
                                       routeName,destination,direction);
                               stopsList.add(stops);

                           }else{
                               Toast.makeText(BusStopNoActivities.this,"Service Error!",
                                       Toast.LENGTH_SHORT).show();
                               dialog.hide();
                           }
                          /* Stop stops=new Stop(stopNum,leaveTime,countdownMins,routeNum,
                                   routeName,destination,direction);*/

                       }
                            dialog.hide();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        recAdapter=new MultiViewRecAdapter(stopsList,BusStopNoActivities.this);
                        stopRecView.setAdapter(recAdapter);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers=new HashMap<>();
                headers.put("Accept","application/json");
                return super.getHeaders();
            }
        };
        VolleySingleton.getInstance(BusStopNoActivities.this).addToRequestQueue(request);
    }
    public int getBusStops(){
        dialog.setMessage("Searching..."); dialog.show();
        usrInput=inputText.getText().toString();//user input
        if(!usrInput.isEmpty()){
            userIn=Integer.parseInt(usrInput);
            dialog.hide();
        }else{
            Toast.makeText(BusStopNoActivities.this,"You haven't enter anything yet",Toast.LENGTH_SHORT).show();
        }

        JsonArrayRequest request=new JsonArrayRequest(Request.Method.GET, STOPS_API_URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try{
                            /*JSONObject jObj=new JSONObject();
                            String msg=jObj.getString("Message");
                            String errorCode=jObj.getString("Code");
                            if(jObj.has(msg)||jObj.has(errorCode)){
                                if(errorCode.equals("3005")){
                                    Toast.makeText(BusStopNoActivities.this,
                                            "No stop estimates found so far",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }else{*/
                            for(int i=0;i<response.length();i++){
                                    JSONObject stopObj=response.getJSONObject(i);
                                    int stopNo=stopObj.getInt("StopNo");

                                    if(userIn!=stopNo){
                                        Toast.makeText(BusStopNoActivities.this,
                                                "Sorry, can't find this stop number",
                                                Toast.LENGTH_SHORT).show();
                                        dialog.hide();
                                    }
                                    stopNum=stopNo;

                            }
                            dialog.hide();

                        }catch (JSONException je){
                            je.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers=new HashMap<>();
                headers.put("Content-Type","application/json");
                headers.put("Accept","application/json");
                return super.getHeaders();
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(request);
        return stopNum;
    }
}
