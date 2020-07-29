package com.example.organize10;

import android.graphics.Color;

import androidx.lifecycle.MutableLiveData;

import com.github.sundeepk.compactcalendarview.domain.Event;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class TodoRepo {
    private static TodoRepo instance;
    private ArrayList<todo> dataSet = new ArrayList<>();


    public static TodoRepo getInstance() {
        if (instance == null) {
            instance = new TodoRepo();
        }
        return instance;
    }

    public MutableLiveData<List<todo>> getTodos() {
        MutableLiveData<List<todo>> data = new MutableLiveData<>();
        data.setValue(dataSet);
        return data;
    }


    public void addTodo(todo todo) {
        dataSet.add(todo);
    }

    public void setTodo() {
        Date date = new Date();
        Event event = new Event(Color.GREEN, date.getTime());
        dataSet.add(new todo("testt", "rest", date, 10, Color.GREEN, 0.0, false, "13 January 2020", "13 January", event));
        dataSet.add(new todo("testt", "rest", date, 10, Color.GREEN, 0.0, false, "13 January 2020", "13 January", event));
        dataSet.add(new todo("testt", "rest", date, 10, Color.GREEN, 0.0, false, "14 January 2020", "14 January", event));
        dataSet.add(new todo("testt", "rest", date, 10, Color.GREEN, 0.0, false, "14 January 2020", "14 January", event));


    }
}
