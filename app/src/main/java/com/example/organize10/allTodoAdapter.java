package com.example.organize10;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class allTodoAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.myViewHolder> {
    List<todo> mData;;
    Context mContext;



    public allTodoAdapter(List<todo> mData, Context context) {
        this.mData = mData;
        this.mContext = context;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.todo_item, parent,false);
        return new RecyclerViewAdapter.myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.myViewHolder holder, int position) {
        Calendar calendar = Calendar.getInstance();
        holder.todoName.setText(mData.get(position).getName());
        if (mData.get(position).getDay().equals(calendar.get(Calendar.DAY_OF_MONTH))) {
            holder.date.setText(mData.get(position).getTime());
        } else {
            holder.date.setText(mData.get(position).getDay());
        }

        if (mData.get(position).getIsCompleted() == true) {
            holder.isCompletedIcon.setVisibility(View.VISIBLE);
        } else {
            holder.isCompletedIcon.setVisibility(View.GONE);
            holder.cardView.setElevation(8.0f);
        }


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder {
        TextView todoName;
        TextView date;
        ImageView isCompletedIcon;
        CardView cardView;
        ImageView sortICON;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            todoName = itemView.findViewById(R.id.list_name);
            date = itemView.findViewById(R.id.list_date);
            isCompletedIcon = itemView.findViewById(R.id.isCompleted);
            cardView = itemView.findViewById(R.id.cardView);
            sortICON = itemView.findViewById(R.id.sort_icon);

        }
    }
}
