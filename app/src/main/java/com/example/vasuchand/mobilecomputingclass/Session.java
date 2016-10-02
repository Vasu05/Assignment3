package com.example.vasuchand.mobilecomputingclass;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Vasu Chand on 10/1/2016.
 */
public class Session {
    private static String Module_Pref="ModulePreference";

    public void setLogin(Context context, String key, int value) {

        SharedPreferences.Editor editor = context.getSharedPreferences(Module_Pref, Context.MODE_PRIVATE).edit();
        editor.putInt(key,value);
        editor.commit();

    }
    public void setName(Context context, String key, String value) {

        SharedPreferences.Editor editor = context.getSharedPreferences(Module_Pref, Context.MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.commit();

    }
    public void setEmail(Context context, String key, String value) {

        SharedPreferences.Editor editor = context.getSharedPreferences(Module_Pref, Context.MODE_PRIVATE).edit();
        editor.putString(key,value);
        editor.commit();

    }
    public void setMob(Context context, String key, String value) {

        SharedPreferences.Editor editor = context.getSharedPreferences(Module_Pref, Context.MODE_PRIVATE).edit();
        editor.putString(key,value);
        editor.commit();

    }
    public  String getPreferences(Context context, String key) {

        SharedPreferences prefs = context.getSharedPreferences(Module_Pref, Context.MODE_PRIVATE);
        String position = prefs.getString(key, "...");
        return position;
    }
    public  int get_id(Context context, String key) {

        SharedPreferences prefs = context.getSharedPreferences(Module_Pref, Context.MODE_PRIVATE);
        int position = prefs.getInt(key, -1);
        return position;
    }

}
