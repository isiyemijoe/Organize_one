package com.example.organize10;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;

import com.allyants.notifyme.NotifyMe;

import java.util.Calendar;
import java.util.Date;

public class Notify {
    String mtitle;
    String mContent = "It's time to get this done once and for all!";
    Context mContext;
    Calendar cal = Calendar.getInstance();
    Date time =  new Date();



    public Notify(String mtitle, Context Context ,Date time ){
        this.mtitle = mtitle;
        this.mContext = Context;
        this.time = time;
        cal.setTime(this.time);

        NotifyMe notifyMe = new NotifyMe.Builder(mContext)
                .title(mtitle)
                .content(mContent)
                .color(255,0,0,255)
                .led_color(255,255,255,255)
                .time(cal.getTime())
                .addAction(new Intent(),"Snooze", false)
                .key("test")
                .addAction(new Intent(),"Completed",false)
                .addAction(new Intent(), "Dismiss",true,false)
                .large_icon(R.drawable.ic_priority_icon)
                .build();

    }
}
