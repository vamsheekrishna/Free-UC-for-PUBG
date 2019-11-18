package com.example.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

public class CallReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent) {

        Bundle bundle = intent.getExtras();
        String phoneNumber= bundle.getString("incoming_number");
        Toast.makeText(context, "incoming_number: "+phoneNumber, Toast.LENGTH_SHORT).show();

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        alertDialog.setTitle("REMINDER!");
        alertDialog.setMessage("Turn off alarm by pressing off");

        alertDialog.setNegativeButton("Off", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context, "OFF", Toast.LENGTH_SHORT).show();
            }
        });
        //alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        alertDialog.show();
        // line you have to add
        //alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_TOAST);
    }
}
