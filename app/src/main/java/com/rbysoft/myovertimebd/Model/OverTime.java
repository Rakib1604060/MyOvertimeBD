package com.rbysoft.myovertimebd.Model;

import java.io.Serializable;

public class OverTime implements Serializable {

    int id;
    String Date;
    Double Regular,day,night,off;
    int leave;

    public int getLeave() {
        return leave;
    }

    public void setLeave(int leave) {
        this.leave = leave;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public double getRegular() {
        return Regular;
    }

    public void setRegular(Double regular) {
        Regular = regular;
    }

    public Double getDay() {
        return day;
    }

    public void setDay(Double day) {
        this.day = day;
    }

    public Double getNight() {
        return night;
    }

    public void setNight(Double night) {
        this.night = night;
    }

    public Double getOff() {
        return off;
    }

    public void setOff(Double off) {
        this.off = off;
    }
}
