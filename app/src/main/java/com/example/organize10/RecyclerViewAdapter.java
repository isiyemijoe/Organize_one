package com.example.organize10;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.myViewHolder> implements Filterable {
    List<todo> mData;
    List<todo> mDataFiltered;
    Context mContext;
    Date today = new Date();
    Calendar calendar = GregorianCalendar.getInstance();
    TodoRepo repo = new TodoRepo();


    public RecyclerViewAdapter(List<todo> mData, Context context) {
        this.mData = mData;
        this.mDataFiltered = new ArrayList<>(mData);
        this.mContext = context;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.todo_item, parent, false);
        return new myViewHolder(view);
    }

    public RecyclerViewAdapter() {
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, final int position) {
        holder.todoName.setText(mDataFiltered.get(position).getName());
        calendar.setTime(today);

        if (mDataFiltered.get(position).getDay().equals(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)))) {
            holder.date.setText(mDataFiltered.get(position).getTime());
        } else {
            holder.date.setText(mDataFiltered.get(position).getDay());
        }

        if (mData.get(position).getIsCompleted() == true) {
            holder.isCompletedIcon.setVisibility(View.VISIBLE);
        } else {
            holder.isCompletedIcon.setVisibility(View.GONE);
            holder.cardView.setElevation(8.0f);
        }


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(mContext);
                dialog.setContentView(R.layout.details_dialog);
                CircleImageView priority = dialog.findViewById(R.id.dialog_priority);
                TextView taskname = dialog.findViewById(R.id.dialog_taskname);
                TextView description = dialog.findViewById(R.id.dialog_description);
                final ImageView isComplete = dialog.findViewById(R.id.dialog_iscompete);
                Button close = dialog.findViewById(R.id.dialog_dismis);
                TextView time = dialog.findViewById(R.id.dialog_time);


                //    priority.setCircleBackgroundColorResource( );
                priority.setCircleBackgroundColor(mDataFiltered.get(position).getColor());


                //priority.setBackgroundColor(mDataFiltered.get(position).getColor());
                description.setText(mDataFiltered.get(position).getDescription());
                String sTime = mDataFiltered.get(position).getMonthKey() + " . " + mDataFiltered.get(position).getTime();
                time.setText(sTime);
                if (mDataFiltered.get(position).getIsCompleted() == true) {
                    taskname.setText(mDataFiltered.get(position).getName());
                    taskname.setPaintFlags(taskname.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    Glide.with(mContext).load(R.drawable.ic_check_circle_black_24dp).into(isComplete);

                } else {
                    taskname.setText(mDataFiltered.get(position).getName());
                    Glide.with(mContext).load(R.drawable.ic_check_circle_white_24dp).into(isComplete);
                }

                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                isComplete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mDataFiltered.get(position).getIsCompleted()) {
                            mDataFiltered.get(position).setIsCompleted(false);
                            Glide.with(mContext).load(R.drawable.ic_check_circle_white_24dp).into(isComplete);
                            notifyDataSetChanged();
                        } else {
                            mDataFiltered.get(position).setIsCompleted(true);
                            Glide.with(mContext).load(R.drawable.ic_check_circle_black_24dp).into(isComplete);
                            notifyDataSetChanged();
                        }
                    }
                });

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataFiltered.size();
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String key = constraint.toString();

                if (key.isEmpty()) {
                    mDataFiltered = mData;
                } else {
                    List<todo> isFiltered = new ArrayList<>();

                    for (todo row : mData) {
                        if (row.getKey().contains(key)) {
                            isFiltered.add(row);
                        }
                    }
                    mDataFiltered = isFiltered;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mDataFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                mDataFiltered = (List<todo>) results.values;
                notifyDataSetChanged();
            }
        };
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

