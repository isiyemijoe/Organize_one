package com.example.organize10;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class allTodo extends AppCompatActivity {
    RecyclerView recyclerView;
    allTodoAdapter adapter;
    viewModel mViewModel = new viewModel();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_todo);
        recyclerView = findViewById(R.id.allTodoRecyclerView);
        adapter = new allTodoAdapter(mViewModel.getTodo().getValue(), this.getApplicationContext());
        recyclerView.setAdapter(adapter);
    }
}
