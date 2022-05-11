package com.example.fixit;

import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class contents implements Serializable {
    String ID;
    String imgID;
    String title;
    String time;


    @RequiresApi(api = Build.VERSION_CODES.N)
    public contents(){

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        time = simpleDateFormat.format(calendar.getTime());

    }

    public contents(String ID,String imgID, String title) {
        this.ID = ID;
        this.imgID = imgID;
        this.title = title;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public String getImgID() {
        return imgID;
    }

    public void setImgID(String imgID) {
        this.imgID = imgID;
    }
}
