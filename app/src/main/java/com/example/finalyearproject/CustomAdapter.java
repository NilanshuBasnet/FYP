package com.example.finalyearproject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    private ArrayList habitTitle, goalCount, trackSession;

    CustomAdapter(Context context, ArrayList habitTitle, ArrayList goalCount,
                   ArrayList trackSession){
        this.context = context;
        this.habitTitle = habitTitle;
        this.goalCount = goalCount;
        this.trackSession = trackSession;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.custom_list_view,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.habitTitleTxt.setText(String.valueOf(habitTitle.get(position)));
        holder.goalCountTxt.setText(String.valueOf(goalCount.get(position)));
        holder.trackSessionTxt.setText(String.valueOf(trackSession.get(position)));
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(context, habit_details.class);
                i.putExtra("title", String.valueOf(habitTitle.get(position)));
                i.putExtra("totalCount", String.valueOf(goalCount.get(position)));
                i.putExtra("trackingStatus", String.valueOf(trackSession.get(position)));
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return habitTitle.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView habitTitleTxt, goalCountTxt, trackSessionTxt;
        ConstraintLayout mainLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            habitTitleTxt = itemView.findViewById(R.id.textViewTitle);
            goalCountTxt = itemView.findViewById(R.id.textViewCounter);
            trackSessionTxt = itemView.findViewById(R.id.textViewSessionTracking);
            mainLayout = itemView.findViewById(R.id.mainLayout);

        }
    }
}
