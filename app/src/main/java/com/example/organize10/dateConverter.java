package com.example.organize10;

import java.text.DateFormatSymbols;

public class dateConverter {
    int month;
    DateFormatSymbols symbols = new DateFormatSymbols();

            public String  convertShortMonth( int month){
                String[] sMonths =    symbols.getShortMonths();
                return  sMonths[month];
            }

    public  String convertMonth(int month){
        String[] longMonth = symbols.getMonths();
        return longMonth[month];
    }

}
