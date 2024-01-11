package com.example.moneymanagerruap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SCAdapter extends RecyclerView.Adapter<SCAdapter.ViewHolder> {
    Context context;
    ArrayList<Month> monthArrayList;

    public SCAdapter(Context context, ArrayList<Month> monthArrayList) {
        this.context = context;
        this.monthArrayList = monthArrayList;
    }

    @NonNull
    @Override
    public SCAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.sc_recycle_view,parent,false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull SCAdapter.ViewHolder holder, int position) {
        Month month=monthArrayList.get(position);
        holder.date.setText(month.getDate());
        holder.month.setText(month.getMonth());
        holder.amount.setText(month.getAmount());
        holder.year.setText(month.getYear());
        holder.mrh.setText(month.getMRH());
    }

    @Override
    public int getItemCount() {
        return monthArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView month,amount,date,year,mrh;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            month=itemView.findViewById(R.id.month_one);
            amount=itemView.findViewById(R.id.amount_one);
            date=itemView.findViewById(R.id.date_one);
            year=itemView.findViewById(R.id.year_one);
            mrh=itemView.findViewById(R.id.mrh_one);
        }
    }
}
