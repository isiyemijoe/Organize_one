package com.example.organize10;

import android.graphics.Color;

import java.util.Comparator;

public class filter {


    public static Comparator<todo> comparePiriority= new Comparator<todo>() {
        @Override
        public int compare(todo o1, todo o2) {
            return o2.getPriority().compareTo(o1.getPriority());
        }
    };

    Comparator<todo> compareTime = new Comparator<todo>() {
        @Override
        public int compare(todo o1, todo o2) {
            return o1.getTime().compareTo(o2.getTime());
        }
    };
}
