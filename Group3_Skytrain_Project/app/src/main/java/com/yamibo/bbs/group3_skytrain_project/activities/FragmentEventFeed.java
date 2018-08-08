package com.yamibo.bbs.group3_skytrain_project.activities;


import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.yamibo.bbs.group3_skytrain_project.R;
import com.yamibo.bbs.group3_skytrain_project.adapter.MultiViewRecAdapter;
import com.yamibo.bbs.group3_skytrain_project.models.BaseModel;
import com.yamibo.bbs.group3_skytrain_project.models.TranslinkFeed;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import Utils.VolleySingleton;

public class FragmentEventFeed extends Fragment implements MultiViewRecAdapter.OnItemClickListener{
    private RecyclerView eventRecView;
    private MultiViewRecAdapter recAdapter;
    private List<BaseModel> feedsList;
    private ProgressDialog dialog;
    private static final String RSS_EVENT_FEED_LINK=
            "https://api.rss2json.com/v1/api.json?rss_url=https%3A%2F%2Fwww.translink.ca%2Fen%2FUtilities%2FTL-Event-RSS-Feed.aspx";
    private ToggleButton faveBtnOff;
    private static String eventTitle,eventCategory;

    private static int imgId;
    private static int pos=0;
    public FragmentEventFeed() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_event_feed, container, false);
    }
    @Override
    public void onViewCreated(View v, Bundle savedInstanceState){
        super.onViewCreated(v,savedInstanceState);
        dialog=new ProgressDialog(getContext());

        eventRecView=(RecyclerView) v.findViewById(R.id.event_feed_rec);
        eventRecView.setLayoutManager(new LinearLayoutManager(getContext()));

        faveBtnOff=(ToggleButton) v.findViewById(R.id.faveOffBtn);
        feedsList=new ArrayList<>();

        getEventFeed();
    }
    private void getEventFeed(){
        dialog.setMessage("Loading...");
        dialog.show();
        JsonObjectRequest feedRequest = new JsonObjectRequest(Request.Method.GET, RSS_EVENT_FEED_LINK,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                dialog.setMessage("Loading...");
                dialog.show();
                try {
                    JSONArray items=response.getJSONArray("items");
                    for(int i=0;i<items.length();i++){
                        JSONObject itemsObj=items.getJSONObject(i);

                        TranslinkFeed feeds = new TranslinkFeed(itemsObj.getString("title"),
                                "Publish Date: "+itemsObj.getString("pubDate"),
                                itemsObj.getString("content"), itemsObj.getString("categories"));
                        feedsList.add(feeds);
                    }
                    recAdapter = new MultiViewRecAdapter(feedsList, getContext());
                    eventRecView.setAdapter(recAdapter);
                    dialog.hide();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", error.getMessage());
            }
        });
        VolleySingleton.getInstance(getContext()).addToRequestQueue(feedRequest);
    }
    private void setFaveBtn(){
        faveBtnOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Bundle bundle = new Bundle();
                if(isChecked){
                    imgId=R.drawable.ic_news_feed;

                    Intent intent=new Intent(getActivity().getBaseContext(),MainActivity.class);

                    bundle.putString("CATEGORY",eventCategory);
                    bundle.putString("EVENT_TITLE",eventTitle);
                    bundle.putInt("FAVED_ITEM_POS",getId());
                    bundle.putInt("VALUE", pos);
                    bundle.putInt("CATEGORY_ICON",imgId);

                }
            }
        });
    }
    @Override
    public void onItemClick(int position) {
        pos=position;
        eventTitle=((TextView) eventRecView.findViewHolderForAdapterPosition(position)
                .itemView.findViewById(R.id.titleTv)).getText().toString();
        eventCategory=((TextView) eventRecView.findViewHolderForAdapterPosition(position)
                .itemView.findViewById(R.id.categoryTV)).getText().toString();
        setFaveBtn();
    }
}
