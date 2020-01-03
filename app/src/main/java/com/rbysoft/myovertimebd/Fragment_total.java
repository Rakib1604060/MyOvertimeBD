package com.rbysoft.myovertimebd;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.rbysoft.myovertimebd.DbHelper.DbHelper;

import java.util.Calendar;

public class Fragment_total extends Fragment {

    View rootview;
    private  TextView tv,rg_tv,d_tv,n_tv,h_tv,T_tv,ptv,prg_tv,pd_tv,pn_tv,ph_tv,pT_tv;
    private Double Regular,Day,Night,Off,All;
     String lang="";
     private TextView shitext,holidtext,holidtext2,daytext,daytext1,regtext,nightext,totaltext,htext,shitext1,regtext1,nightext1,totaltext1,htext1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootview = LayoutInflater.from(container.getContext()).inflate(R.layout.activity_total_info, container, false);
        Calendar mCalendar= Calendar.getInstance();
        int month=(mCalendar.get(Calendar.MONTH));
        int year=(mCalendar.get(Calendar.YEAR));
        int pyear=year;

        InitializeComponents();
        String CmonthName,PmonthName;
        CmonthName=theMonth(month);
        lang=Saving.getAString(getActivity(),"Language");

        DbHelper dbHelper = new DbHelper(getActivity());
        tv =rootview.findViewById(R.id.ttext);
        if(month==0){
            PmonthName=theMonth(11);
            pyear=pyear-1;


        }else{
            PmonthName=theMonth(month-1);
        }

        ptv = rootview.findViewById(R.id.ttext2);
        if (lang.equals("bn")){
            updateResource();
            tv.setText(getResources().getString(R.string.currentmontb)+" -"+CmonthName+", "+String.valueOf(year));

            ptv.setText(getResources().getString(R.string.previousmonthb)+" - "+PmonthName+", "+String.valueOf(pyear));

        }else{
            tv.setText(getResources().getString(R.string.currentmont)+" -"+CmonthName+", "+String.valueOf(year));
            ptv.setText(getResources().getString(R.string.previousmonth)+" - "+PmonthName+", "+String.valueOf(pyear));
        }

        rg_tv = rootview.findViewById(R.id.rghr);
        d_tv = rootview.findViewById(R.id.dhr);
        n_tv = rootview.findViewById(R.id.nhr);
        h_tv = rootview.findViewById(R.id.hlhr);
        T_tv = rootview.findViewById(R.id.thr);

        prg_tv = rootview.findViewById(R.id.prghr);
        pd_tv = rootview.findViewById(R.id.pdhr);
        pn_tv = rootview.findViewById(R.id.pnhr);
        ph_tv = rootview.findViewById(R.id.phlhr);
        pT_tv = rootview.findViewById(R.id.pthr);




        Cursor result,result_d,result_n,result_o;

        result= dbHelper.getResultRegular(month+1);
        if(result.moveToFirst()) {
            Regular= result.getDouble(result.getColumnIndex("mTotal"));
        }
        result_d = dbHelper.getResultDay(month+1);
        if(result_d.moveToFirst()) {
            Day= result_d.getDouble(result.getColumnIndex("mTotal"));
        }
        result_n = dbHelper.getResultNight(month+1);
        if(result_n.moveToFirst()) {
            Night= result_n.getDouble(result.getColumnIndex("mTotal"));
        }
        result_o = dbHelper.getResultOff(month+1);
        if(result_o.moveToFirst()) {
            Off= result_o.getDouble(result.getColumnIndex("mTotal"));
        }
        All=Regular+Day+Night+Off;
        rg_tv.setText(Regular.toString());
        d_tv.setText(Day.toString());
        n_tv.setText(Night.toString());
        h_tv.setText(Off.toString());
        T_tv.setText(All.toString());

        ///For Previous Month

        Cursor presult,presult_d,presult_n,presult_o;
        if (month==0){
            month=12;
        }

        presult= dbHelper.getResultRegular(month);
        if(presult.moveToFirst()) {
            Regular= presult.getDouble(presult.getColumnIndex("mTotal"));
        }
        presult_d = dbHelper.getResultDay(month);
        if(presult_d.moveToFirst()) {
            Day= presult_d.getDouble(result.getColumnIndex("mTotal"));
        }
        presult_n = dbHelper.getResultNight(month);
        if(presult_n.moveToFirst()) {
            Night= presult_n.getDouble(result.getColumnIndex("mTotal"));
        }
        presult_o = dbHelper.getResultOff(month);
        if(presult_o.moveToFirst()) {
            Off= presult_o.getDouble(result.getColumnIndex("mTotal"));
        }
        All=Regular+Day+Night+Off;
        prg_tv.setText(Regular.toString());
        pd_tv.setText(Day.toString());
        pn_tv.setText(Night.toString());
        ph_tv.setText(Off.toString());
        pT_tv.setText(All.toString());

        return rootview;

    }

    private void InitializeComponents() {
        shitext=rootview.findViewById(R.id.shif);

        regtext=rootview.findViewById(R.id.reg);
        nightext=rootview.findViewById(R.id.night1);
        totaltext=rootview.findViewById(R.id.total1);
        daytext=rootview.findViewById(R.id.day1);
        daytext1=rootview.findViewById(R.id.day2);
        htext=rootview.findViewById(R.id.hou);
        shitext1=rootview.findViewById(R.id.shift2);

        regtext1=rootview.findViewById(R.id.reg2);
        nightext1=rootview.findViewById(R.id.night2);
        totaltext1=rootview.findViewById(R.id.total2);
        htext1=rootview.findViewById(R.id.hou3);
        holidtext=rootview.findViewById(R.id.holid1);
        holidtext2=rootview.findViewById(R.id.holid2);



    }

    private void updateResource() {
        tv.setText(getResources().getString(R.string.currentmontb));
        shitext.setText(getResources().getString(R.string.shiftb));
        daytext.setText(getResources().getString(R.string.dayb));
        daytext1.setText(getResources().getString(R.string.dayb));
        regtext.setText(getResources().getString(R.string.regularb));
        nightext.setText(getResources().getString(R.string.nightb));
        totaltext.setText(getResources().getString(R.string.totalb));
        htext.setText(getResources().getString(R.string.hourb));
        shitext1.setText(getResources().getString(R.string.shiftb));
        regtext1.setText(getResources().getString(R.string.regularb));
        nightext1.setText(getResources().getString(R.string.nightb));
        totaltext1.setText(getResources().getString(R.string.totalb));
        htext1.setText(getResources().getString(R.string.hourb));

        holidtext.setText(getResources().getString(R.string.holidayb));
        holidtext2.setText(getResources().getString(R.string.holidayb));
    }

    public String theMonth(int month){

     lang=Saving.getAString(getActivity(),"Language");

       if (lang.equals("bn")){
          String monthNames[] = {"জানুয়ারী", "ফেব্রুয়ারী", "মার্চ", "এপ্রিল", "মে", "জুন", "জুলাই", "আগষ্ট", "সেপ্টেম্বর", "অক্টোবর", "নভেম্বর", "ডিসেম্বর"};
           return monthNames[month];

       }else{
         String monthNames[] = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
           return monthNames[month];
       }


}

}
