package com.example.finalyearproject;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    LayoutInflater inflater;
    List<Habits> habits;

    Adapter (Context context, List<Habits> habits){
        this.inflater = LayoutInflater.from(context);
        this.habits = habits;
    }

    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_list_view, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {
        String title = habits.get(position).getName();
        holder.textViewTitle.setText(title);

        String count = "0/" + habits.get(position).getCount();
        holder.textViewCounter.setText(count);

        String sessionTracking = habits.get(position).getSessionTracking();
        holder.textViewSessionTracking.setText(sessionTracking);

    }

    @Override
    public int getItemCount() {
        return habits.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView textViewTitle, textViewCounter, textViewSessionTracking;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewCounter = itemView.findViewById(R.id.textViewCounter);
            textViewSessionTracking = itemView.findViewById(R.id.textViewSessionTracking);

            //Handeling click on items
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(v.getContext(), habit_details.class);
                    i.putExtra("ID", habits.get(getAdapterPosition()).getID());//to get data from database to new activity
                    v.getContext().startActivity(i);
                }
            });
        }
    }
}
