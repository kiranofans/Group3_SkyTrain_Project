package com.yamibo.bbs.group3_skytrain_project.activities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Message;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.yamibo.bbs.group3_skytrain_project.R;

import java.io.Serializable;
import java.util.Calendar;
public class FragmentTripPlan extends Fragment_Tabs {
    private View v;
    Calendar calendar = Calendar.getInstance();
    public FragmentTripPlan(){}
    private TextView mDiaplayDate;
    private  static final String TAG="MainActivity";
    private DatePickerDialog.OnDateSetListener mDateSetLinser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceStat){
        v=inflater.inflate(R.layout.tab_trip_planner,container,false);
        return v;
    }
    @Override
    public void onViewCreated(View v,Bundle savedInstanceStat){

        EditText deps = (EditText)v.findViewById(R.id.clbtn);
        EditText dest = (EditText)v.findViewById(R.id.dlbtn);
        mDiaplayDate =(TextView)v.findViewById(R.id.selectDate);
        mDiaplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(getActivity(),
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetLinser,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        mDateSetLinser = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                String date = month +"/" +day +"/"+year;
                mDiaplayDate.setText(date);
                String depstx = deps.getText().toString();
                String desttx = dest.getText().toString();
                String message1 = depstx;
                String message2 = desttx;
                String message3 = date;
                if((message1 == null)&&( message2 == null)){
                    Toast.makeText(getActivity(),"please enter city", Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent1 = new Intent();
                    intent1.putExtra("Message1", message1);
                    intent1.putExtra("Message2", message2);
                    intent1.putExtra("Message3", message3);
                    intent1.setClass(getActivity().getApplicationContext(), TripPlanShowing.class);
                    startActivity(intent1);
                }
            }
        };
    }
}