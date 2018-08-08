package com.yamibo.bbs.group3_skytrain_project.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yamibo.bbs.group3_skytrain_project.R;
import com.yamibo.bbs.group3_skytrain_project.activities.ExtendedViewPager;
import com.yamibo.bbs.group3_skytrain_project.activities.TouchImgView1;
import com.yamibo.bbs.group3_skytrain_project.models.BaseModel;
import Utils.RecViewConstants;

import com.yamibo.bbs.group3_skytrain_project.models.Favorites;
import com.yamibo.bbs.group3_skytrain_project.models.Stop;
import com.yamibo.bbs.group3_skytrain_project.models.TranslinkFeed;

import java.util.ArrayList;
import java.util.List;

public class MultiViewRecAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private static List<? extends BaseModel> baseList;
    private LayoutInflater inflater;
    private View v;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public MultiViewRecAdapter(List<? extends BaseModel> list, Context context) {
        this.baseList = list;
        /**baseList is a multi-type list which means one recView adapter
         * can adapt multiple defined types (models)*/
        this.inflater = LayoutInflater.from(context);
    }

    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case RecViewConstants.ViewType.STOPS_TYPE:
                v = LayoutInflater.from(parent.getContext()).inflate
                    (R.layout.list_bus_stop_info, parent, false);
                return new StopsHolder(v,viewType);
            case RecViewConstants.ViewType.FEED_TYPE:
                v = LayoutInflater.from(parent.getContext()).inflate
                    (R.layout.list_trans_feed, parent, false);
                return new TransFeedHolder(v,viewType);
            case RecViewConstants.ViewType.FAVE_TYPE:
                v=LayoutInflater.from(parent.getContext()).inflate
                        (R.layout.list_favorites,parent,false);
                return new FavoriteHolder(v,viewType);
            case RecViewConstants.ViewType.VIEW_PAGER_TYPE:
                v= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view_pagers,
                        parent,false);
                return new ViewPagerHolder(v,viewType);
        }
        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.bind(baseList.get(position));
    }
    @Override
    public int getItemViewType(int position) {
        return baseList.get(position).getViewType();
    }

    public int getItemCount() {
        return baseList.size();
    }

    public class TransFeedHolder extends BaseViewHolder<TranslinkFeed>{
        private TextView timeStampTv,contentTv,titleTv,categoryTv;
        private ImageView icFeedsImgView;
        private CardView card;
        public TransFeedHolder(View itemView,int viewType){
            super(itemView);
            contentTv=(TextView)itemView.findViewById(R.id.contentTV);
            titleTv=(TextView)itemView.findViewById(R.id.titleTv);
            timeStampTv=(TextView)itemView.findViewById(R.id.timeStampTxt);
            icFeedsImgView=(ImageView)itemView.findViewById(R.id.imgView_feed);
            categoryTv=(TextView)itemView.findViewById(R.id.categoryTV);

            card=(CardView)itemView.findViewById(R.id.feedsCardView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
        @Override
        public void bind(TranslinkFeed object) {
            titleTv.setText(object.getTitle());
            contentTv.setText(object.getFeedsContent());
            timeStampTv.setText(object.getTimeStamp());
            categoryTv.setText(object.getCategory());

            if(categoryTv.getText().toString().equals("News")){
                icFeedsImgView.setImageResource(R.drawable.ic_news_feed);
            }else if(categoryTv.getText().toString().equals("")){
                switch(itemView.getId()){
                    case R.id.action_media:
                        icFeedsImgView.setImageResource(R.drawable.ic_media);
                    break;
                    case R.id.action_event:
                        icFeedsImgView.setImageResource(R.drawable.ic_event);
                    break;
                }
            }

        }
    }
    public class StopsHolder extends BaseViewHolder<Stop> {
        private TextView stopNumTv,stopNameTv,atStreeTv,
                departTv,nextBusTv, directionBoundTv,countdownTv;

        public StopsHolder(View itemView,int viewType) {
            super(itemView);
            stopNameTv = (TextView) itemView.findViewById(R.id.stopNameTv);
            stopNumTv=(TextView)itemView.findViewById(R.id.stop_num);
            atStreeTv=(TextView)itemView.findViewById(R.id.atStreetTv);
            departTv=(TextView)itemView.findViewById(R.id.departTv);
            nextBusTv=(TextView)itemView.findViewById(R.id.nextBusTv);
            directionBoundTv=(TextView)itemView.findViewById(R.id.directionTv);
            countdownTv=(TextView)itemView.findViewById(R.id.countDownTv);
        }

        @Override
        public void bind(Stop object) {
            stopNumTv.setText(object.getStopNo());
            stopNameTv.setText(object.getStopsName());
            atStreeTv.setText(object.getAtStreet());
            departTv.setText(object.getExpectedLeaveTime());
            countdownTv.setText(object.getExpectedCountDown());
            nextBusTv.setText(object.getNextBus());
        }
    }

    //Faves
    public class FavoriteHolder extends BaseViewHolder<Favorites>{
        private TextView titleTv,categoryTv;
        private ImageView icImgView,mainImg;
        public FavoriteHolder(View itemView,int viewType) {
            super(itemView);
            titleTv=(TextView)itemView.findViewById(R.id.faveTitleTv);
            categoryTv=(TextView)itemView.findViewById(R.id.faveCategory);
         //   icImgView=(ImageView)itemView.findViewById(R.id.faveOnImgView);
            mainImg=(ImageView)itemView.findViewById(R.id.faveImgView);
        }

        @Override
        public void bind(Favorites object) {
            titleTv.setText(object.getFaveTitle());
            categoryTv.setText(object.getCategory());
            Picasso.with(itemView.getContext()).load(object.getImgResId()).fit()
                    .centerInside().into(mainImg);
           // icImgView.setImageResource(object.getImgResId());
        }
    }

    public class ViewPagerHolder extends BaseViewHolder<ExtendedViewPager>{

        List<BaseModel> imgList =new ArrayList<>();
        private ExtendedViewPager extendedScheduleVP,extendedMapVP;
        private TextView pagersTitle;
        private Context context;
        private ViewGroup container;
        public ViewPagerHolder(View itemView,int viewType) {
            super(itemView);
            extendedScheduleVP =(ExtendedViewPager)itemView.findViewById(R.id.extendedSchedulePager);
            extendedMapVP=(ExtendedViewPager)itemView.findViewById(R.id.extendedSchedulePager);

            pagersTitle=(TextView)itemView.findViewById(R.id.pagerTitles);
            //extendedScheduleVP=new ExtendedViewPager(context);
        }

        @Override
        public void bind(ExtendedViewPager object) {
            int pos=getAdapterPosition();
            extendedScheduleVP.setId(pos);
            //extendedMapVP.setId(pos);
            pagersTitle.setText(object.getTitle());
           String title =pagersTitle.getText().toString();
            //pagersTitle.setText("SkyTrain And SeaBus Schedules");
            switch (title){
                case "SkyTrain And SeaBus Schedules":
                    extendedScheduleVP.setAdapter(object.getScheduleAdp());
                    break;
                case "SkyTrain, SeaBus, And Bus Maps":
                    extendedScheduleVP.setAdapter(new ZoomableMapAdapter());
                    break;
            }

        }
    }

    /** Will create viewHolder for each type of lists */
    static class ZoomableScheduleAdapter extends PagerAdapter {
        private static int[] schedules={R.drawable.seabus_schedule1,R.drawable.seabus_schedule2
                ,R.drawable.schedule3};
        @Override
        public int getCount() {
            return schedules.length;
        }

        @Override
        public View instantiateItem(ViewGroup container, int position) {
            TouchImgView1 schedule=new TouchImgView1(container.getContext());

            schedule.setImageResource(schedules[position]);
            container.addView(schedule, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            return schedule;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

    }
    static class ZoomableMapAdapter extends PagerAdapter {

        private static int[] maps = { R.drawable.skytrain_map, R.drawable.sea_bus,
                R.mipmap.bline_sea_bus};
        @Override
        public int getCount() {
            return maps.length;
        }

        @Override
        public View instantiateItem(ViewGroup container, int position) {
            TouchImgView1 img = new TouchImgView1(container.getContext());

            img.setImageResource(maps[position]);
            container.addView(img, LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            return img;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

    }
}
