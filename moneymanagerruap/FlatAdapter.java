package com.example.moneymanagerruap;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FlatAdapter extends RecyclerView.Adapter<FlatAdapter.ViewHolder> {
    Context context;
    ArrayList<Flat> flatArrayList;

    public FlatAdapter(Context context, ArrayList<Flat> flatArrayList) {
        this.context = context;
        this.flatArrayList = flatArrayList;
    }

    @NonNull
    @Override
    public FlatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.flat_recycle_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FlatAdapter.ViewHolder holder, int position) {
        Flat flat=flatArrayList.get(position);
        holder.date.setText(flat.getDate());
        holder.flat.setText(flat.getFlatNo());
        holder.name.setText(flat.getName());

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,GivenSCActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("name",flat.getName());
                intent.putExtra("flat",flat.getFlatNo());
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return flatArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView date,flat,name;
        RelativeLayout button;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            date=itemView.findViewById(R.id.doj_one);
            flat=itemView.findViewById(R.id.flat_one);
            name=itemView.findViewById(R.id.name_one);
            button=itemView.findViewById(R.id.buttonNext);
        }
    }
}
