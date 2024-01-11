package com.example.moneymanagerruap;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;

public class NetworkChangeListener extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(!Common.isConnectedToInternet(context)){
            AlertDialog.Builder builder=new AlertDialog.Builder(context);
            View layout_dialog= LayoutInflater.from(context).inflate(R.layout.connection_page,null);
            builder.setView(layout_dialog);

            AppCompatButton rtyBtn=layout_dialog.findViewById(R.id.retryBtn);

            AlertDialog dialog=builder.create();
            dialog.show();
            dialog.setCancelable(false);

            dialog.getWindow().setGravity(Gravity.CENTER);

            rtyBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    onReceive(context,intent);
                }
            });
        }
    }
}