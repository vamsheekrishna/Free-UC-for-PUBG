package com.example.myapplication.callerdialog;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Handler;
import android.provider.CallLog;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

public class PhoneCallListener extends PhoneStateListener {
    public static final String PHONE_NUMBER = "phone number";
    private Context context;
    private int prev_state=0;
    private static final String TAG = "CustomPhoneStateListener";

    private String incoming_nr = "";
    public PhoneCallListener(Context c) {
        Log.i("CallRecorder", "PhoneListener constructor");
        context = c;
    }

    public void onCallStateChanged (int state, String incomingNumber)
    {

       /* if(!TextUtils.isEmpty(incomingNumber)){
            // here for Outgoing number make null to get incoming number
            //CallBroadcastReceiver.numberToCall = null;
            getincomno = incomingNumber;
            Log.i("CallRecorder", "incomingNumber "+incomingNumber);
        }

        switch (state) {
            case TelephonyManager.CALL_STATE_IDLE:

                Log.i("CallRecorder", "CALL_STATE_IDLE "+incomingNumber);
                break;
            case TelephonyManager.CALL_STATE_RINGING:
                Log.d("CallRecorder", "CALL_STATE_RINGING");
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                displayAlert(context);
                Log.d("CallRecorder", "CALL_STATE_OFFHOOK");
                break;
        }*/

        if(incomingNumber!=null&&incomingNumber.length()>0)
            incoming_nr=incomingNumber;

        switch(state){
            case TelephonyManager.CALL_STATE_RINGING:
                Log.d(TAG, "CALL_STATE_RINGING");
                prev_state=state;
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                Log.d(TAG, "CALL_STATE_OFFHOOK");
                prev_state=state;
                break;
            case TelephonyManager.CALL_STATE_IDLE:
                Log.d(TAG, "CALL_STATE_IDLE==>"+incoming_nr);
                if((prev_state==TelephonyManager.CALL_STATE_OFFHOOK)){
                    prev_state=state;
                    displayAlert(context);
                    //Answered Call which is ended
                }
                if((prev_state==TelephonyManager.CALL_STATE_RINGING)){
                    prev_state = state;
                    displayAlert(context);
                    //Rejected or Missed call
                }
                break;

        }
    }
    private void displayAlert(Context context) {
        Intent intent = new Intent(context, CallerActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(PHONE_NUMBER, incoming_nr);
        context.startActivity(intent);
    }
}
