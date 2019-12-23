package com.rbysoft.myovertimebd.DbHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.rbysoft.myovertimebd.Model.OverTime;

import java.util.ArrayList;


public class DbHelper extends SQLiteOpenHelper {

    SQLiteDatabase sqLiteDatabase;
    public static final String DATABASE_NAME = "MyOverTimeBD.db";
    public static final String OVERTIME_TABLE_NAME = "OverTime_Table";
    public static final String OVERTIME_COLUMN_ID = "id";
    public static final String OVERTIME_COLUMN_DATE = "Date";
    public static final String OVERTIME_COLUMN_REGULAR = "Regular";
    public static final String OVERTIME_COLUMN_DAY = "Day";
    public static final String OVERTIME_COLUMN_NIGHT = "Night";
    public static final String OVERTIME_COLUMN_OFF= "Off";
    public static final String  OVERTIME_COLUMN_LEAVE="Leave";


    private static final int DATABASE_VERSION = 4;

    public DbHelper( Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+OVERTIME_TABLE_NAME+" " +
                "(" +
                ""+OVERTIME_COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ""+OVERTIME_COLUMN_DATE+" text UNIQUE NOT NULL," +
                ""+OVERTIME_COLUMN_REGULAR+" DOUBLE NOT NULL," +
                ""+OVERTIME_COLUMN_DAY+" DOUBLE NOT NULL," +
                ""+OVERTIME_COLUMN_NIGHT+" DOUBLE NOT NULL," +
                ""+OVERTIME_COLUMN_OFF+" DOUBLE NOT NULL, " +
                ""+OVERTIME_COLUMN_LEAVE+" INTEGER DEFAULT 0 )"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+OVERTIME_TABLE_NAME);
        onCreate(db);
    }

    public long addDataRegular(OverTime tOvertime){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(OVERTIME_COLUMN_DATE,tOvertime.getDate());
        contentValues.put(OVERTIME_COLUMN_REGULAR, tOvertime.getRegular());
        contentValues.put(OVERTIME_COLUMN_DAY,0 /*tOvertime.getDay()*/);
        contentValues.put(OVERTIME_COLUMN_NIGHT,0/*tOvertime.getNight()*/);
        contentValues.put(OVERTIME_COLUMN_OFF,0/*tOvertime.getOff()*/);

        try {
            return db.insert(""+OVERTIME_TABLE_NAME+"", null, contentValues);
        }catch (SQLException e){
            return -1;
        }
    }
    public long addDataLeave(OverTime over){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(OVERTIME_COLUMN_DATE,over.getDate());
        contentValues.put(OVERTIME_COLUMN_REGULAR,0);
        contentValues.put(OVERTIME_COLUMN_DAY,0);
        contentValues.put(OVERTIME_COLUMN_NIGHT,0/*tOvertime.getNight()*/);
        contentValues.put(OVERTIME_COLUMN_OFF,0/*tOvertime.getOff()*/);
        contentValues.put(OVERTIME_COLUMN_LEAVE,over.getLeave());

        try {
            return db.insert(""+OVERTIME_TABLE_NAME+"", null, contentValues);
        }catch (SQLException e){
            return -1;
        }

    }
    public long addDataDay(OverTime tOvertime){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(OVERTIME_COLUMN_DATE,tOvertime.getDate());
        contentValues.put(OVERTIME_COLUMN_REGULAR,0);
        contentValues.put(OVERTIME_COLUMN_DAY,tOvertime.getDay());
        contentValues.put(OVERTIME_COLUMN_NIGHT,0/*tOvertime.getNight()*/);
        contentValues.put(OVERTIME_COLUMN_OFF,0/*tOvertime.getOff()*/);
        try {
            return db.insert(""+OVERTIME_TABLE_NAME+"", null, contentValues);
        }catch (SQLException e){
            return -1;
        }
    }
    public long addDataNight(OverTime tOvertime){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(OVERTIME_COLUMN_DATE,tOvertime.getDate());
        contentValues.put(OVERTIME_COLUMN_REGULAR, 0/*tOvertime.getRegular()*/);
        contentValues.put(OVERTIME_COLUMN_DAY,0 /*tOvertime.getDay()*/);
        contentValues.put(OVERTIME_COLUMN_NIGHT,tOvertime.getNight());
        contentValues.put(OVERTIME_COLUMN_OFF,0/*tOvertime.getOff()*/);
        try {
            return db.insert(""+OVERTIME_TABLE_NAME+"", null, contentValues);
        }catch (SQLException e){
            return -1;
        }
    }
    public long addDataOff(OverTime tOvertime){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(OVERTIME_COLUMN_DATE,tOvertime.getDate());
        contentValues.put(OVERTIME_COLUMN_REGULAR,0 /*tOvertime.getRegular()*/);
        contentValues.put(OVERTIME_COLUMN_DAY,0 /*tOvertime.getDay()*/);
        contentValues.put(OVERTIME_COLUMN_NIGHT,0/*tOvertime.getNight()*/);
        contentValues.put(OVERTIME_COLUMN_OFF,tOvertime.getOff());
        try {
            return db.insert(""+OVERTIME_TABLE_NAME+"", null, contentValues);
        }catch (SQLException e){
            return -1;
        }
    }

    public Cursor getResultRegular(int month){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(
                "SELECT SUM(Regular) AS mTotal FROM "+OVERTIME_TABLE_NAME+" where Date like '%-"+month+"-%'", null);
        return cursor;
    }
    public Cursor getResultDay(int month){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(
                "SELECT SUM(Day) AS mTotal FROM "+OVERTIME_TABLE_NAME+" where Date like '%-"+month+"-%'", null);
        return cursor;
    }
    public Cursor getResultNight(int month){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(
                "SELECT SUM(Night) AS mTotal FROM "+OVERTIME_TABLE_NAME+" where Date like '%-"+month+"-%'", null);
        return cursor;
    }
    public Cursor getResultOff(int month){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(
                "SELECT SUM(Off) AS mTotal FROM "+OVERTIME_TABLE_NAME+" where Date like '%-"+month+"-%'", null);
        return cursor;
    }

    public Cursor getMonthResult(int month){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(
                "SELECT SUM(Regular+Night+Day+Off) AS mTotal FROM "+OVERTIME_TABLE_NAME+" where Date like '%-"+month+"-%'", null);
        return cursor;
    }

    /*public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db,OVERTIME_TABLE_NAME);
        return numRows;
    }*/
    public Integer updateData(OverTime tOvertime){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(OVERTIME_COLUMN_DATE,tOvertime.getDate());
        contentValues.put(OVERTIME_COLUMN_REGULAR, tOvertime.getRegular());
        contentValues.put(OVERTIME_COLUMN_DAY, tOvertime.getDay());
        contentValues.put(OVERTIME_COLUMN_NIGHT,tOvertime.getNight());
        contentValues.put(OVERTIME_COLUMN_OFF,tOvertime.getOff());
        try {
            return db.update(""+OVERTIME_TABLE_NAME+"", contentValues, " "+OVERTIME_COLUMN_ID+" = ? ",
                    new String[] { Integer.toString(tOvertime.getId()) } );
        }catch (Exception ex){
            return -1;
        }
    }
    public Integer deleteData(Integer Id) {
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            return db.delete(""+OVERTIME_TABLE_NAME+""," "+OVERTIME_COLUMN_ID+" = ? ",
                    new String[] { Integer.toString(Id) });
        }
        catch (SQLException e){
            return -1;
        }
    }
    public ArrayList<OverTime>GetTwoMonth(String date1,String date2){

        ArrayList<OverTime>temp=new ArrayList<>();
        SQLiteDatabase db=this.getReadableDatabase();
        String sql="SELECT * FROM "+OVERTIME_TABLE_NAME+" where Date like '%-"+date1+"%' OR Date like '%-"+date2+"%' ORDER BY "+OVERTIME_COLUMN_ID+" DESC ;";
        Cursor res=db.rawQuery(sql,null);
        res.moveToFirst();
        while (!res.isAfterLast()){

            OverTime tOvertime= new OverTime();

            tOvertime.setId(res.getInt(res.getColumnIndex(OVERTIME_COLUMN_ID)));
            tOvertime.setDate(res.getString(res.getColumnIndex(OVERTIME_COLUMN_DATE)));
            tOvertime.setRegular(res.getDouble(res.getColumnIndex(OVERTIME_COLUMN_REGULAR)));
            tOvertime.setDay(res.getDouble(res.getColumnIndex(OVERTIME_COLUMN_DAY)));
            tOvertime.setNight(res.getDouble(res.getColumnIndex(OVERTIME_COLUMN_NIGHT)));
            tOvertime.setOff(res.getDouble(res.getColumnIndex(OVERTIME_COLUMN_OFF)));
            tOvertime.setLeave(res.getInt(res.getColumnIndex(OVERTIME_COLUMN_LEAVE)));

            temp.add(tOvertime);
            res.moveToNext();

        }
        return temp;
    }


    public ArrayList<OverTime> getAllInfo(){

        ArrayList<OverTime> array_list = new ArrayList<OverTime>();
        SQLiteDatabase db = this.getReadableDatabase();
        String sql="SELECT * FROM "+OVERTIME_TABLE_NAME+" ORDER BY "+OVERTIME_COLUMN_ID+" DESC ;";

        Cursor res = db.rawQuery(sql, null);
        res.moveToFirst();

        while (!res.isAfterLast()){

            OverTime tOvertime= new OverTime();

            tOvertime.setId(res.getInt(res.getColumnIndex(OVERTIME_COLUMN_ID)));
            tOvertime.setDate(res.getString(res.getColumnIndex(OVERTIME_COLUMN_DATE)));
            tOvertime.setRegular(res.getDouble(res.getColumnIndex(OVERTIME_COLUMN_REGULAR)));
            tOvertime.setDay(res.getDouble(res.getColumnIndex(OVERTIME_COLUMN_DAY)));
            tOvertime.setNight(res.getDouble(res.getColumnIndex(OVERTIME_COLUMN_NIGHT)));
            tOvertime.setOff(res.getDouble(res.getColumnIndex(OVERTIME_COLUMN_OFF)));
            tOvertime.setLeave(res.getInt(res.getColumnIndex(OVERTIME_COLUMN_LEAVE)));

            array_list.add(tOvertime);
            res.moveToNext();
        }
        return array_list;
    }
    public void ResetTable(){
        sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL("DELETE FROM OverTime_Table");
        sqLiteDatabase.close();
    }
}
