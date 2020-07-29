package com.example.organize10;

import android.graphics.Color;

import java.util.Comparator;

public class priority {
    public static double initializePriority(String priority) {
        double grade = 0;
        switch (priority) {
            case "priority":
                grade = 2.0;
                break;
            case "urgent":
                grade = 2.5;
                break;
            case "notPriority":
                grade = 1;
                break;
            case "notUrgent":
                grade = 0;
                break;
            default:
                grade = 0;
        }
        return grade;

    }

    public static int priorityColor(double priority){

        int color = Color.GREEN;
        if(priority >=2.0){
            color = Color.RED;
        }
        else if(priority <= 2.0){
            color = Color.GREEN;
        }
        return color;

    }
}
