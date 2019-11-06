package com.example.myapplication.models;

import android.content.Context;
import android.content.SharedPreferences;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Utilities {

    private static final String MY_PREFS = "myPrefs";
    private static final String NAME = "name";
    private static final String MOBILE_NO = "mobile";
    private static final String SCORE = "score";
    private static final String EMAIL_ID = "email";

    public static void generateProfile(Context context, UserViewModel mViewModel) {
        SharedPreferences myPrefs = Objects.requireNonNull(context).getSharedPreferences(MY_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = myPrefs.edit();
        edit.putString(NAME, mViewModel.getName());
        edit.putString(MOBILE_NO, mViewModel.getEmail());
        edit.putString(EMAIL_ID, mViewModel.getMobile());
        edit.putInt(SCORE, mViewModel.getScore());
        edit.apply();
    }

    public static boolean isNewUser(Context context) {
        SharedPreferences myPrefs = Objects.requireNonNull(context).getSharedPreferences(MY_PREFS, Context.MODE_PRIVATE);
        return myPrefs.contains(NAME);
    }

    public static UserViewModel getUser(Context context) {
        if (isNewUser(context)) {
            SharedPreferences myPrefs = Objects.requireNonNull(context).getSharedPreferences(MY_PREFS, Context.MODE_PRIVATE);
            UserViewModel userViewModel = new UserViewModel();
            userViewModel.setName(myPrefs.getString(NAME, null));
            userViewModel.setMobile(myPrefs.getString(MOBILE_NO, null));
            userViewModel.setEmail(myPrefs.getString(EMAIL_ID, null));
            userViewModel.setScore(myPrefs.getInt(SCORE, 0));
            return userViewModel;
        }
        return null;
    }

    public static void updateCredit(Integer integer, Context context) {
        SharedPreferences myPrefs = Objects.requireNonNull(context).getSharedPreferences(MY_PREFS, Context.MODE_PRIVATE);
        Integer credit = myPrefs.getInt(SCORE, 0);
        SharedPreferences.Editor edit = myPrefs.edit();
        edit.putInt(SCORE, credit+integer);
        edit.apply();
    }

    public static boolean internetConnectionAvailable(int timeOut) {
        InetAddress inetAddress = null;
        try {
            Future<InetAddress> future = Executors.newSingleThreadExecutor().submit(new Callable<InetAddress>() {
                @Override
                public InetAddress call() {
                    try {
                        return InetAddress.getByName("google.com");
                    } catch (UnknownHostException e) {
                        return null;
                    }
                }
            });
            inetAddress = future.get(timeOut, TimeUnit.MILLISECONDS);
            future.cancel(true);
        } catch (InterruptedException e) {
        } catch (ExecutionException e) {
        } catch (TimeoutException e) {
        }
        return inetAddress!=null && !inetAddress.equals("");
    }
}
