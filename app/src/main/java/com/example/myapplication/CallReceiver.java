package com.example.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.example.myapplication.callerdialog.CallerActivity;
import com.example.myapplication.callerdialog.PhoneCallListener;

public class CallReceiver extends BroadcastReceiver {
    PhoneCallListener customPhoneListener;
    @Override
    public void onReceive(final Context context, Intent intent) {

        TelephonyManager telephony = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        if( null == customPhoneListener)
            customPhoneListener = new PhoneCallListener(context);

        assert telephony != null;
        telephony.listen(customPhoneListener, PhoneStateListener.LISTEN_CALL_STATE);

        Bundle bundle = intent.getExtras();
        assert bundle != null;
        String phoneNumber= bundle.getString("incoming_number");
        Log.d("CallReceiver", "onReceive"+phoneNumber);
    }
}
