package com.example.covidvaccinationapplication1.Admin;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.example.covidvaccinationapplication1.R;

import java.util.ArrayList;

public class ModelAdapter extends RecyclerView.Adapter<ModelAdapter.ViewHolder> {

    public ArrayList<AdminModel> arrayList;

    public ModelAdapter(Context context, ArrayList<AdminModel> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        holder.txtLocation.setText(arrayList.get(i).getLocation());
        holder.txtPin.setText(arrayList.get(i).getPin());
        holder.txtDate.setText(arrayList.get(i).getDate());
        holder.txtTime.setText(arrayList.get(i).getTime());

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayList.remove(i);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class  ViewHolder extends RecyclerView.ViewHolder {

        TextView txtLocation, txtDate, txtPin, txtTime;
        ImageView imgDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtLocation = itemView.findViewById(R.id.txtLocation);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtPin = itemView.findViewById(R.id.txtPin);
            txtTime = itemView.findViewById(R.id.txtTime);
            imgDelete = itemView.findViewById(R.id.imgDelete);

        }
    }
}
