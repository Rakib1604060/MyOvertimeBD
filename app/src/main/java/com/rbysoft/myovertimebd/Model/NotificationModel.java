package com.rbysoft.myovertimebd.Model;

import com.google.firebase.firestore.IgnoreExtraProperties;

@IgnoreExtraProperties
public class NotificationModel {

    String title;
    String details;
    String date;

    public NotificationModel(String title, String details, String date) {
        this.title = title;
        this.details = details;
        this.date = date;
    }

    public NotificationModel() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
