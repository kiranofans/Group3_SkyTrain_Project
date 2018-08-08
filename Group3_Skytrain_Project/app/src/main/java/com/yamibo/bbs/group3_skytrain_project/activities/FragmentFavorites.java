package com.yamibo.bbs.group3_skytrain_project.activities;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ToggleButton;

import com.yamibo.bbs.group3_skytrain_project.R;
import com.yamibo.bbs.group3_skytrain_project.adapter.MultiViewRecAdapter;
import com.yamibo.bbs.group3_skytrain_project.models.BaseModel;
import com.yamibo.bbs.group3_skytrain_project.models.Favorites;
import com.yamibo.bbs.group3_skytrain_project.models.TranslinkFeed;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentFavorites extends Fragment implements MultiViewRecAdapter.OnItemClickListener{
    private RecyclerView recView;
    private MultiViewRecAdapter recAdp;
    private List<BaseModel> faveList;
    private static ToggleButton faveOnBtnImg;
    private View view;
    private ImageView faveImg;
    private int[] imgResIds;
    private String[] titleArr;
    public FragmentFavorites() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return view=inflater.inflate(R.layout.fragment_favorites, container, false);
    }

    @Override
    public void onViewCreated(View v, Bundle savedInstanceState){
        super.onViewCreated(v,savedInstanceState);
        recView=(RecyclerView)v.findViewById(R.id.faveRecView);
        recView.setLayoutManager(new GridLayoutManager(getContext(),2));
        faveImg=(ImageView)v.findViewById(R.id.faveImgView);
        faveOnBtnImg=(ToggleButton) v.findViewById(R.id.faveOnImgView);
        faveList=new ArrayList<>();


         getFavedEvent();

    }
    private void getFavedEvent(){
        //FragmentEventFeed fragEvent=new FragmentEventFeed();
       /* int pos=+1;
        int imgs=getArguments().getBundle("CATEGORY_ICON").getInt("CATEGORY_ICON");
        String eventTitle=getArguments().getString("EVENT_TITLE");
        String category=getArguments().getString("CATEGORY");
        int position=getArguments().getInt("FAVED_ITEM_POS");*/
        imgResIds= new int[]{R.mipmap.public_transit, R.drawable.translink,R.mipmap.public_transit_1};
        titleArr=new String[]{"public transit 1","TransLink Logo","TransLink SkyTrain"};
        for(int i=0;i<imgResIds.length;i++){

            Favorites faves=new Favorites(titleArr[i],"",imgResIds[i]);
            faveList.add(faves);
        }
        //Favorites favedFeeds=new Favorites(eventTitle,category,imgs);
        recAdp=new MultiViewRecAdapter(faveList,getContext());
        recView.setAdapter(recAdp);

    }
    private void faveBtnOnClicks(){
        faveOnBtnImg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    faveImg.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        faveBtnOnClicks();

    }
}
