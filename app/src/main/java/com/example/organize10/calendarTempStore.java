package com.example.organize10;

import java.util.Calendar;
import java.util.Date;


public  class calendarTempStore {
    private static  Calendar cal;
    public calendarTempStore(){
        this.cal = cal;
    }

    public Calendar getDate(){
        return cal;
    }
    public void setDate(Calendar calendar){
        this.cal = calendar;
    }

    }


