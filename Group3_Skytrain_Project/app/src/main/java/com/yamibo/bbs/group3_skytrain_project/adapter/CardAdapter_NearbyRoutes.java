package com.yamibo.bbs.group3_skytrain_project.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yamibo.bbs.group3_skytrain_project.R;
import com.yamibo.bbs.group3_skytrain_project.activities.StopDetailActivity;
import com.yamibo.bbs.group3_skytrain_project.models.Route;
import com.yamibo.bbs.group3_skytrain_project.models.Schedule;
import com.yamibo.bbs.group3_skytrain_project.models.Stop;

import java.util.ArrayList;
import java.util.List;

public class CardAdapter_NearbyRoutes extends RecyclerView.Adapter<CardAdapter_NearbyRoutes.ViewHolder> {
    List<Route> mItems = new ArrayList<>();
    public static int HIGHLIGHTED = -1;

    public CardAdapter_NearbyRoutes(List<Route> mItems) {
        this.mItems = mItems;
    }

    public void addData(Route route) {
        mItems.add(route);
        notifyDataSetChanged();
    }

    public void clear() {
        mItems.clear();
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_route, parent, false);
        CardAdapter_NearbyRoutes.ViewHolder viewHolder = new CardAdapter_NearbyRoutes.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Route route = mItems.get(position);
        holder.route_no_tv.setText(route.getRouteNo());
        holder.dest.setText(route.getSchedules().get(0).getDestination());
        holder.nextin.setText("");

        for(int i = 0; i< route.getSchedules().size(); i++)
            if(route.getSchedules().get(i).getExpectedCountdown()>0)
            {
                holder.nextin.append(" " + route.getSchedules().get(i).getExpectedCountdown());
                if(i<route.getSchedules().size()-1)
                    holder.nextin.append(",");
            }

        switch (route.getSchedules().get(0).getScheduleStatus()){
            case "+":
                holder.status.setText("Ahead of Schedule");
                holder.status.setTextColor(Color.parseColor("#CC0000"));
                break;
            case "-":
                holder.status.setText("Delay");
                holder.status.setTextColor(Color.parseColor("#CC0000"));
                break;
            default:
                holder.status.setText("On time");
                holder.status.setTextColor(Color.parseColor("#007F00"));
                break;
        }



    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        public TextView route_no_tv;
        public TextView dest;
        public TextView nextin;
        public TextView status;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            status = mView.findViewById(R.id.status);
            route_no_tv = (TextView) mView.findViewById(R.id.route_no);
            dest = mView.findViewById(R.id.route_name);
            nextin = mView.findViewById(R.id.next_in);
        }
    }
}