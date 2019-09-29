package com.rbysoft.myovertimebd;

import android.content.Context;
import android.content.SharedPreferences;

public class Saving {


    public static void saveAString(Context context,String stringname,String value){
        SharedPreferences sharedPreferences=context.getSharedPreferences("MYOVERTIMEBD.userdetails",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor =sharedPreferences.edit();
        editor.putString(stringname,value);
        editor.apply();
    }

    public  static  String getAString(Context context,String stringname){
        SharedPreferences sharedPreferences=context.getSharedPreferences("MYOVERTIMEBD.userdetails",Context.MODE_PRIVATE);
             return    sharedPreferences.getString(stringname,null);
    }

    public static  void SaveABoolean(Context context,String name,boolean value){
        SharedPreferences sharedPreferences=context.getSharedPreferences("MYOVERTIMEBD.userdetails",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor =sharedPreferences.edit();
        editor.putBoolean(name,value);
        editor.apply();

    }


    public  static  boolean getAboolean(Context context,String stringname){
        SharedPreferences sharedPreferences=context.getSharedPreferences("MYOVERTIMEBD.userdetails",Context.MODE_PRIVATE);
        return    sharedPreferences.getBoolean(stringname,false);
    }

}
