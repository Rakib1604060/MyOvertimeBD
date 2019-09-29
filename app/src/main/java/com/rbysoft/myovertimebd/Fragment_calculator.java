package com.rbysoft.myovertimebd;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.gms.ads.AdView;
import com.rbysoft.myovertimebd.DbHelper.DbHelper;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class Fragment_calculator extends Fragment {

    View rootview;
    EditText B_salary,Ot_hour;
    DbHelper dbHelper;
    private AdView mAdView;
    private ClipboardManager myClipboard;
    private ClipData myClip;
    TextView OT_rate,OT_ammount;
    DecimalFormat df2;
    double Bsal,Thour,Ot_rate,Ot_ammont,fetchHour;
    Button calculatebutton,calculator;
    String lang;
    TextView calculatetext,basictext,toov,ho,takatext,t1,t2,overtimeamtext,hourlytext;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootview = LayoutInflater.from(container.getContext()).inflate(R.layout.activity_calculator, container, false);
        lang=Saving.getAString(getActivity(),"Language");
         InitializeComponents();
         myClipboard = (ClipboardManager)getActivity().getSystemService(Context.CLIPBOARD_SERVICE);

        Calendar mCalendar= Calendar.getInstance();
        int month=(mCalendar.get(Calendar.MONTH));
        dbHelper =new DbHelper(getActivity());
        calculatebutton=rootview.findViewById(R.id.calculate);
        if (lang.equals("bn")){
            updateResource();

        }

        df2 = new DecimalFormat( "#,###,###,##0.00" );

        B_salary = rootview.findViewById(R.id.bsalary);
        Ot_hour =rootview.findViewById(R.id.othr);

        OT_rate = rootview.findViewById(R.id.otr);
        OT_ammount = rootview.findViewById(R.id.ota);
        //Totalsalary = findViewById(R.id.ott);

        Cursor fetchData = dbHelper.getMonthResult(month+1);
        if(fetchData.moveToFirst()) {
            fetchHour= fetchData.getDouble(fetchData.getColumnIndex("mTotal"));
        }

        FetchingData();



        OT_ammount.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                String amount=String.valueOf(Ot_ammont);
                // Toast.makeText(Calculator.this, amount, Toast.LENGTH_SHORT).show();

                if (amount.equals("0.00")|| amount.equals("")|| amount==null){
                    Toast.makeText(getActivity(), "Nothing Copied!! ", Toast.LENGTH_SHORT).show();
                    return false;
                }else{
                    int addamount=0;
                    int additional=0;
                    if (amount.contains(".")){

                        String finalstring="";

                        finalstring =amount.substring(0,amount.indexOf(".")+3);
                        Toast.makeText(getActivity(), finalstring, Toast.LENGTH_SHORT).show();
                        amount=finalstring;
                    }

                    myClip = ClipData.newPlainText("text",amount);
                    myClipboard.setPrimaryClip(myClip);
                    Toast.makeText(getActivity(), "Copied Successfully!", Toast.LENGTH_SHORT).show();
                    return false;

                }

            }
        });

       calculatebutton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               calculate();
           }
       });

       calculator.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               scalculator();
           }
       });

        return rootview;

    }

    private void updateResource() {
        calculatetext.setText(getResources().getString(R.string.calculateb));
        basictext.setText(getResources().getString(R.string.basicsalaryb));
        toov.setText(getResources().getString(R.string.totalovertimeb));
        ho.setText(getResources().getString(R.string.hourb));
        takatext.setText(getResources().getString(R.string.takab));
        t1.setText(getResources().getString(R.string.takab));
        t2.setText(getResources().getString(R.string.takab));
        overtimeamtext.setText(getResources().getString(R.string.Overtimeamountb));
        hourlytext.setText(getResources().getString(R.string.hourlyotrateb));
        calculator.setText(getResources().getString(R.string.calculatorb));
        calculatebutton.setText(getResources().getString(R.string.calculateb));


    }

    private void InitializeComponents() {
        calculatetext=rootview.findViewById(R.id.stv);
        basictext=rootview.findViewById(R.id.basicsalaryy);
        toov=rootview.findViewById(R.id.totalovert);
        ho=rootview.findViewById(R.id.hourrr);
        takatext=rootview.findViewById(R.id.takaa);
        t1=rootview.findViewById(R.id.ota);
        t2=rootview.findViewById(R.id.otr);
        overtimeamtext=rootview.findViewById(R.id.overtimeamount);
        hourlytext=rootview.findViewById(R.id.hourly);
        calculator=rootview.findViewById(R.id.scalcu);



    }


    private void FetchingData() {
        AlertDialog.Builder alerDialogBuilder = new AlertDialog.Builder(getActivity());
        if (lang.equals("bn")){
            alerDialogBuilder.setTitle(getString(R.string.warningb)).setIcon(R.drawable.ic_warning);

            alerDialogBuilder.setMessage(getString(R.string.doyouwantfetchb));
            alerDialogBuilder.setPositiveButton(getString(R.string.yesb), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Ot_hour.setText(df2.format(fetchHour));
                }
            });


            alerDialogBuilder.setNegativeButton(getString(R.string.Nob), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

        }else{

            alerDialogBuilder.setTitle(getString(R.string.warning)).setIcon(R.drawable.ic_warning);

            alerDialogBuilder.setMessage(getString(R.string.doyouwantfetch));
            alerDialogBuilder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Ot_hour.setText(df2.format(fetchHour));
                }
            });


            alerDialogBuilder.setNegativeButton(getString(R.string.No), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
        }


        alerDialogBuilder.setCancelable(true);



        AlertDialog alertDialog = alerDialogBuilder.create();
        alertDialog.show();

    }


    public void calculate() {
        if(inpuntValid()) {
            CloseKeyBoard();
            totalCaluculate();
        }
    }

    private boolean inpuntValid() {
        if(B_salary.getText().toString().length()==0){
            //Toast.makeText(this, "Input Basic Salary", Toast.LENGTH_SHORT).show();
            B_salary.setError("Input Basic Salary");
            return false;
        }
        if(Ot_hour.getText().toString().length()==0){
            //Toast.makeText(this, "Input Overtime Hour", Toast.LENGTH_SHORT).show();
            Ot_hour.setError("Input Overtime Hour");
            return false;
        }
        return true;
    }

    private void totalCaluculate() {
        Bsal = Double.parseDouble(B_salary.getText().toString());
        Thour = Double.parseDouble(Ot_hour.getText().toString());

        //Formula


        Ot_rate=(Bsal/208) *2;
        Ot_ammont =Ot_rate * Thour;
        //Total_salary = Bsal + Ot_ammont;

        OT_rate.setText(df2.format(Ot_rate)+" Taka");
        OT_ammount.setText(df2.format(Ot_ammont)+" Taka");
//        Totalsalary.setText(Total_salary+" Taka");
    }

    @SuppressLint("NewApi")
    private void CloseKeyBoard() {
        View view =getActivity().getCurrentFocus();
        if(view!=null){
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }

    public void scalculator() {


        ArrayList<HashMap<String, Object>> items = new ArrayList<HashMap<String, Object>>();

        final PackageManager pm = getActivity().getPackageManager();
        List<PackageInfo> packs = pm.getInstalledPackages(0);
        for (PackageInfo pi : packs) {
            if (pi.packageName.toString().toLowerCase().contains("calcul")) {
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("appName", pi.applicationInfo.loadLabel(pm));
                map.put("packageName", pi.packageName);
                items.add(map);
            }
        }
        if (items.size() >= 1) {
            String packageName = (String) items.get(0).get("packageName");
            Intent i = pm.getLaunchIntentForPackage(packageName);
            if (i != null) startActivity(i);
        } else {
            // Application not found
        }

    }

    }
