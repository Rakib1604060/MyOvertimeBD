package com.rbysoft.myovertimebd;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.Locale;



public class Fragment_home extends Fragment {
  CardView addhour,calculateot,detailslist,total;

    View rootview;
    TextView addhourrtext,detailslisttext,totalrectext,otcaltext;
    AdView myad;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootview=LayoutInflater.from(container.getContext()).inflate(R.layout.activity_main,container,false);
        initializeComponents();
        String lang=Saving.getAString(getActivity(),"Language");
        if (lang==null){
            lang="bn";
            Saving.saveAString(getActivity(),"Language","bn");
            UpdateResource();
        }
        if (lang.equals("bn")){
            UpdateResource();
        }

        MobileAds.initialize(getActivity(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        myad = rootview.findViewById(R.id.myadview);
        AdRequest adRequest = new AdRequest.Builder().build();
        myad.loadAd(adRequest);




  // Onclick Listener To the cardview of the mainActivity
        addhour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getActivity(), "OT BUTTON", Toast.LENGTH_SHORT).show();
              getActivity().getSupportFragmentManager().beginTransaction()
                      .replace(R.id.Fragment_Container,new Fragment_addhour())
                      .addToBackStack("MAINSTACK")
                      .commit();
            }
        });
// Total record crdview
        total.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           getActivity().getSupportFragmentManager().beginTransaction()
                   .replace(R.id.Fragment_Container,new Fragment_total())
                   .addToBackStack("MAINSTACK")
                   .commit();



            }
        });

        detailslist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.Fragment_Container,new Fragment_Detailslist())
                        .addToBackStack("MAINSTACK")
                        .commit();
            }
        });

        calculateot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.Fragment_Container,new Fragment_calculator())
                        .addToBackStack("MAINSTACK")
                        .commit();
            }
        });



        return  rootview;
    }

    private void UpdateResource() {

        addhourrtext.setText(getResources().getString(R.string.addhourb));
        detailslisttext.setText(getResources().getString(R.string.detailslistb));
        totalrectext.setText(getResources().getString(R.string.totalrecordb));
        otcaltext.setText(getResources().getString(R.string.otcalculatorb));
    }


    private void initializeComponents() {
        addhour=rootview.findViewById(R.id.adhourid);
        total=rootview.findViewById(R.id.totalrecordid);
        detailslist=rootview.findViewById(R.id.detailslistid);
        calculateot=rootview.findViewById(R.id.otcalculatorid);
        addhourrtext=rootview.findViewById(R.id.addhourr);

        detailslisttext=rootview.findViewById(R.id.detlistt);
        totalrectext=rootview.findViewById(R.id.totalrec);
        otcaltext=rootview.findViewById(R.id.otcal);

    }



}
