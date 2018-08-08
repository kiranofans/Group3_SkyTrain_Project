package com.yamibo.bbs.group3_skytrain_project.map;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yamibo.bbs.group3_skytrain_project.R;

public class MapFragment extends Fragment {

    public MapFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.g_map,
                container, false);
        return rootView;
    }

    public static MapFragment newInstance () {
        MapFragment fragment = new MapFragment();

        return fragment;
    }

}
