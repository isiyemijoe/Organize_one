package com.example.organize10;

import android.graphics.Color;
import android.util.EventLog;

import com.github.sundeepk.compactcalendarview.domain.Event;

import java.util.Comparator;
import java.util.Date;

public class todo{
   private String name;
    private String Description;
    private String month;
    private String year;
    private  String day;
    private  int uptime;
    private int color;
    private String time;
    private  boolean isCompleted;
    private String monthKey;
    Date date;
    Event event;
    private  Double priority;

    public String getMonthKey() {
        return monthKey;
    }

    public void setMonthKey(String monthKey) {
        this.monthKey = monthKey;
    }

    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public boolean getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(boolean completed) {
        isCompleted = completed;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public int getUptime() {
        return uptime;
    }

    public void setUptime(int uptime) {
        this.uptime = uptime;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Double getPriority() {
        return priority;
    }

    public void setPriority(Double priority) {
        this.priority = priority;
    }


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public todo(String name, String description, Date date, int uptime, int color, Double priority, boolean isCompleted, String key, String monthKey, Event event) {
        this.name = name;
        Description = description;
        this.event = event;
        this.isCompleted = isCompleted;
        this.date = date;
        this.uptime = uptime;
        this.color = color;
        this.time = time;
        this.priority = priority;
        this.key = key;
        this.monthKey = monthKey;
        this.day = String.valueOf(date.getDay());
    }

    Comparator<todo> comparePiriority= new Comparator<todo>() {
        @Override
        public int compare(todo o1, todo o2) {
            return o1.getPriority().compareTo(o2.getPriority());
        }
    };



}
