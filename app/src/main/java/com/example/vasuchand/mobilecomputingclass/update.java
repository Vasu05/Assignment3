package com.example.vasuchand.mobilecomputingclass;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class update extends Activity {
    TextView t1,t2;
    Button b1;
    database db;
    Session session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        t1 =(TextView)findViewById(R.id.update_mob);
        t2 = (TextView)findViewById(R.id.update_name);
        b1 = (Button)findViewById(R.id.btn_update);
        db = new  database(this);
        session = new Session();
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String a = t1.getText().toString();
                String b = t2.getText().toString();
                String e =  session.getPreferences(update.this,"email");
                if(!a.equals("")|| !a.isEmpty() || !b.isEmpty()||!b.equals(""))
                {
                      db.updateContact(b,a,e);
                    Intent intent = new Intent(update.this, MainActivity.class);
                    //intent.putExtra("loging2","t");


                    startActivity(intent);
                    finish();
                }

            }
        });
    }
}
