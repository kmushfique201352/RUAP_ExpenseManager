package com.example.rajuk_expensemanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FlatOwnerAdapter extends RecyclerView.Adapter<FlatOwnerAdapter.ViewHolder> {

    Context context;
    ArrayList<FlatOwner> flatOwnerList;

    public FlatOwnerAdapter(Context context, ArrayList<FlatOwner> flatOwnerList) {
        this.context = context;
        this.flatOwnerList = flatOwnerList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FlatOwner flatOwner=flatOwnerList.get(position);
        holder.flat.setText(flatOwner.flatNo);
        holder.name.setText(flatOwner.name);
        holder.amount.setText(flatOwner.idOwner);
    }

    @Override
    public int getItemCount() {
        return flatOwnerList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView flat,name,amount;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            flat=itemView.findViewById(R.id.flat_tv);
            name=itemView.findViewById(R.id.name_tv);
            amount=itemView.findViewById(R.id.amount_tv);
        }
    }
}
