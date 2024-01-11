package com.example.moneymanagerruap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ViewHolder> {
    Context context;
    ArrayList<ExpensesJava> expensesJavaArrayList;

    public ExpenseAdapter(Context context, ArrayList<ExpensesJava> expensesJavaArrayList) {
        this.context = context;
        this.expensesJavaArrayList = expensesJavaArrayList;
    }

    @NonNull
    @Override
    public ExpenseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.exp_recycle_view,parent,false);
        return new ExpenseAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseAdapter.ViewHolder holder, int position) {
        ExpensesJava expensesJava=expensesJavaArrayList.get(position);
        holder.date.setText(expensesJava.getDate());
        holder.amount.setText(expensesJava.getAmount());
        holder.purpose.setText(expensesJava.getPurpose());
    }

    @Override
    public int getItemCount() {
        return expensesJavaArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView date,purpose,amount;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            date=itemView.findViewById(R.id.date2_one);
            purpose=itemView.findViewById(R.id.purpose_one);
            amount=itemView.findViewById(R.id.amount2_one);
        }
    }
}
