package com.example.vasuchand.mobilecomputingclass.Login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.vasuchand.mobilecomputingclass.MainActivity;
import com.example.vasuchand.mobilecomputingclass.R;
import com.example.vasuchand.mobilecomputingclass.Session;

/**
 * Created by Vasu Chand on 10/2/2016.
 */
public class splashscreen  extends AppCompatActivity
{
    private static int SPLASH_TIME_OUT = 3000;
    Session session;
    private Context context = splashscreen.this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);

        getWindow().getDecorView().setBackgroundColor(ContextCompat.getColor(context, R.color.white));


        session = new Session();
        //String status= session.getPreferences(Splashscreen.this,"status");
        // Log.d("status", status);
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {

                int status = session.get_id(splashscreen.this, "login");


                //Toast.makeText(getApplicationContext(), "Your mobile number!", Toast.LENGTH_LONG).show();

                if (status==1) {
                    //Toast.makeText(getApplicationContext(), "Your mobile number!", Toast.LENGTH_LONG).show();

                    Intent i = new Intent(splashscreen.this, MainActivity.class);
                    startActivity(i);
                }
                else {
                    Intent i = new Intent(splashscreen.this, LoginActivity.class);
                    startActivity(i);
                }

                // overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
        startActivity(intent);
        finish();
        System.exit(0);
    }


}
