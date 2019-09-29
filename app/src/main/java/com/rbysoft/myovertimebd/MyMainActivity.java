package com.rbysoft.myovertimebd;


import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.net.Uri;
import android.os.Bundle;


import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.rbysoft.myovertimebd.DbHelper.DbHelper;
import com.rbysoft.myovertimebd.Layout.FounderPage;

import androidx.appcompat.app.AlertDialog;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;


public class MyMainActivity extends AppCompatActivity {

    DbHelper dbHelper;

    public static BottomNavigationView navView;
    public static Context mycontext;
    public  Menu mymenu;


   String lang="en";
   Boolean isPossible=false;






    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:


                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.Fragment_Container,new Fragment_home())
                            .addToBackStack("MAINSTACK")
                            .commit();

                    return true;
                case R.id.navigation_dashboard:
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.Fragment_Container,new Fragment_Detailslist())
                            .addToBackStack("MAINSTACK")
                            .commit();

                    return true;

                case R.id.navigation_calculation:
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.Fragment_Container,new Fragment_calculator())
                            .addToBackStack("MAINSTACK")
                            .commit();
                    return  true;
                case R.id.navigation_profile:
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.Fragment_Container,new Fragment_profile())
                            .addToBackStack("MAINSTACK")
                            .commit();

                    return true;
                case R.id.navigation_notifications:
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.Fragment_Container,new Fragment_notification())
                            .addToBackStack("MAINSTACK")
                            .commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_main);
        lang=Saving.getAString(this,"Language");

        if (lang==null){
            Saving.saveAString(this,"Language","bn");

        }

        navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        this.setTitle("My Overtime BD");
        lang=Saving.getAString(this,"Language");
        if (lang.equals("bn")&& isPossible){
            UpdateResources();
        }



        dbHelper = new DbHelper(this);
        navView.setSelectedItemId(R.id.navigation_home);



    }



    private void UpdateResources() {
        mymenu.getItem(0).setTitle(getResources().getString(R.string.clearrecordb));
        mymenu.getItem(1).setTitle(getResources().getString(R.string.sharethisappb));
        mymenu.getItem(2).setTitle(getResources().getString(R.string.rateusb));
        mymenu.getItem(3).setTitle(getResources().getString(R.string.contactusb));
        mymenu.getItem(4).setTitle(getResources().getString(R.string.aboutfounderb));
        mymenu.getItem(5).setTitle(getResources().getString(R.string.exitb));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.e("MYMAIN","MENU CREATED");
        isPossible=true;
        getMenuInflater().inflate(R.menu.dev_menu, menu);
        mymenu=menu;
        if (lang.equals("bn")){

            UpdateResources();
        }


        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.reset:
                resetButton();
                break;

            case R.id.share:
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My OverTime Bd");
                    String shareMessage;
                    shareMessage = "\nTracking,Counting and Calculating Overtime data\n\n" + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "Choose one"));
                } catch(Exception e) {
                    //e.toString();
                }
                break;
            case R.id.rate:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id="+ BuildConfig.APPLICATION_ID)));
                break;
            case R.id.exit:
                finish();
                System.exit(0);
            case R.id.founderpage:
                startActivity(new Intent(MyMainActivity.this, FounderPage.class));
                break;
            case R.id.contact:
                Uri uri = Uri.parse("fb-messenger://user/320673321942965");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);

                try {
                    startActivity(intent);
                }
                catch (ActivityNotFoundException e){
                    startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("market://details?id=com.facebook.orca")));
                }
                break;

        }
        return true;
    }
    @Override
    public void onBackPressed() {

      AlertDialog.Builder alerDialogBuilder = new AlertDialog.Builder(this);

           if(lang.equals("bn")){
               alerDialogBuilder.setTitle(getResources().getString(R.string.exitb)).setIcon(R.drawable.ic_warning);
               alerDialogBuilder.setMessage(getResources().getString(R.string.doyouwanttoexitb));
           }else{
               alerDialogBuilder.setTitle(getResources().getString(R.string.exit)).setIcon(R.drawable.ic_warning);
               alerDialogBuilder.setMessage(getResources().getString(R.string.doyouwanttoexit));
           }

        alerDialogBuilder.setCancelable(true);
        alerDialogBuilder.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        alerDialogBuilder.setNegativeButton(getResources().getString(R.string.No), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


            }
        });

        AlertDialog alertDialog = alerDialogBuilder.create();
        alertDialog.show();

    }

    public void resetButton() {
        AlertDialog.Builder alerDialogBuilder = new AlertDialog.Builder(this);
        alerDialogBuilder.setTitle(getResources().getString(R.string.warning));
        alerDialogBuilder.setIcon(R.drawable.ic_warning);
        alerDialogBuilder.setMessage(getResources().getString(R.string.deletealldata));

        alerDialogBuilder.setCancelable(true);

        alerDialogBuilder.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dbHelper.ResetTable();
                Toast.makeText(MyMainActivity.this, getResources().getString(R.string.resetalldata), Toast.LENGTH_SHORT).show();
            }
        });
        alerDialogBuilder.setNegativeButton(getResources().getString(R.string.No), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog = alerDialogBuilder.create();
        alertDialog.show();
    }



}
