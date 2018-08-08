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
import com.yamibo.bbs.group3_skytrain_project.models.Stop;

import java.util.ArrayList;
import java.util.List;

public class CardAdapter_NearbyStops extends RecyclerView.Adapter<CardAdapter_NearbyStops.ViewHolder> {
    List<Stop> mItems = new ArrayList<>();
    public static int HIGHLIGHTED = -1;

    public CardAdapter_NearbyStops(List<Stop> mItems) {
        this.mItems = mItems;
    }

    public void addData(Stop stop) {
        mItems.add(stop);
        notifyDataSetChanged();
    }

    public void clear() {
        mItems.clear();
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public CardAdapter_NearbyStops.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_stop, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CardAdapter_NearbyStops.ViewHolder holder, final int position) {
        Stop stop = mItems.get(position);
        String stopName = stop.getOnStreet()+" FS " + stop.getAtStreet();
        holder.stop_name.setText(stopName);
        holder.stop_no.setText(""+stop.getStopNo());
        holder.dist.setText(""+stop.getDistance() + " m");
        holder.routes.setText(stop.getRoutes());
        TextView tv =
                (TextView) holder.mView.findViewById(R.id.item_card).findViewById(R.id.stop_name);
        TextView tv2 =
                (TextView) holder.mView.findViewById(R.id.item_card).findViewById(R.id.stop_no);
        int color = tv.getResources().getColor(android.R.color.primary_text_light);
        if(position == HIGHLIGHTED)
        {
            tv.setTextColor(Color.BLACK);
            tv2.setTextColor(Color.BLACK);
            tv.setTypeface(null, Typeface.BOLD);
            tv2.setTypeface(null, Typeface.BOLD);
            tv2.setTextSize(15);

        }



        else
        {
            tv.setTextColor(color);
            tv2.setTextColor(color);
            tv2.setTextSize(13);
            tv.setTypeface(null, Typeface.NORMAL);
            tv2.setTypeface(null, Typeface.NORMAL);
        }


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, StopDetailActivity.class);
                intent.putExtra("stopNo", ""+ mItems.get(position).getStopNo());
                intent.putExtra("stopName", mItems.get(position).getOnStreet() + "\n" + " FS " +"\n" + mItems.get(position).getAtStreet());
                context.startActivity(intent);
            }
        });
        //TODO: add mTwoPane code to add marker on map


    }



    @Override
    public int getItemCount() {
        return mItems.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        public TextView stop_name;
        public TextView dist;
        public TextView routes;
        public TextView stop_no;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            stop_name = (TextView) itemView.findViewById(R.id.stop_name);
            dist = (TextView) itemView.findViewById(R.id.dist);
            routes = (TextView) itemView.findViewById(R.id.routes);
            stop_no = (TextView) itemView.findViewById(R.id.stop_no);
        }
    }
}
