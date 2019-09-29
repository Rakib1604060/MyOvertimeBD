package com.rbysoft.myovertimebd;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.Calendar;

public class Fragment_addhour extends Fragment {

    View rootview;
    MaterialCalendarView materialCalendarView;
    AdView mAdView;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootview = LayoutInflater.from(container.getContext()).inflate(R.layout.activity_calendar_date1, container, false);

        MobileAds.initialize(getActivity(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = rootview.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        materialCalendarView =rootview.findViewById(R.id.calendarView1);
        materialCalendarView.setSelectedDate(Calendar.getInstance());

        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                String Mdate  = date.getDay()+"-"+(date.getMonth()+1)+"-"+date.getYear();
                Fragment myfragment=new Fragment_Second();
                Bundle bundle=new Bundle();
                bundle.putString("DATEOFNOW",Mdate);
                Log.e("MYSTRING",Mdate);
                myfragment.setArguments(bundle);

                 getActivity().getSupportFragmentManager().beginTransaction()
                         .replace(R.id.Fragment_Container,myfragment)
                         .commit();

            }
        });

        return rootview;

    }


}