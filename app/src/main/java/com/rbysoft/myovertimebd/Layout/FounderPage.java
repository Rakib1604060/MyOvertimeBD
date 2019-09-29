package com.rbysoft.myovertimebd.Layout;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;


import com.rbysoft.myovertimebd.R;

public class FounderPage extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_founder_page);
    }

    public void call(View view) {
        if(view.getId()==R.id.sfb){
            try{
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://profile/100001511853652"));
                startActivity(intent);

            }catch(Exception e){

                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.facebook.com/saifulbip")));

            }

        }
        else if(view.getId()==R.id.smail){
            Intent mEmail= new Intent(Intent.ACTION_SEND);
            mEmail.setData(Uri.parse("mailto:"));
            mEmail.setType("text/plain");
            mEmail.putExtra(Intent.EXTRA_EMAIL,new String[]{"saifulbip@gmail.com"});
            mEmail.putExtra(Intent.EXTRA_SUBJECT,"About My Overtime BD Application");
            mEmail.putExtra(Intent.EXTRA_TEXT,"");
            mEmail.setPackage("com.google.android.gm");
            try {
                startActivity(mEmail);
            }
            catch (ActivityNotFoundException e){
                startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("market://details?id=com.google.android.gm")));
            }

        }
    }
}
