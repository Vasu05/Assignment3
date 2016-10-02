package com.example.vasuchand.mobilecomputingclass.Login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vasuchand.mobilecomputingclass.R;
import com.example.vasuchand.mobilecomputingclass.database;

public class Signup extends AppCompatActivity {

    EditText nameText;
    EditText emailText;
    EditText mobileText;
    EditText passText;
    Button next;
    TextView linktologin;
    private database mydb ;
    Context mContext = Signup.this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        nameText =   (EditText)findViewById(R.id.input_name);
        emailText =  (EditText)findViewById(R.id.input_email);
        mobileText = (EditText)findViewById(R.id.input_mobile);
        passText =   (EditText)findViewById(R.id.input_password);
        next     =   (Button)findViewById(R.id.btn_signup);
        linktologin = (TextView)findViewById(R.id.link_login);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });
        mydb = new database(this);

        linktologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                Intent i = new Intent(getApplicationContext(),
                        LoginActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    public  void signup()
    {
        if (!validate()) {
            onSignupFailed();
            return;
        }
        final String name = nameText.getText().toString();
        final String email = emailText.getText().toString();
        final String password = passText.getText().toString();
        final String mobile = mobileText.getText().toString();
        if(mydb.insertContact(name, mobile, email, password)) {
            Toast.makeText(getApplicationContext(), "Thanks for Sign up", Toast.LENGTH_SHORT).show();
            nameText.setText("");
            emailText.setText("");
            passText.setText("");
            mobileText.setText("");
        }


    }
    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        next.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = nameText.getText().toString();
        String email = emailText.getText().toString();
        String password = passText.getText().toString();
        String mobile = mobileText.getText().toString();

        if (name.isEmpty() ) {
            nameText.setError("at least 3 characters");
            valid = false;
        } else {
            nameText.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError("enter a valid email address");
            valid = false;
        } else {
            emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            passText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            passText.setError(null);
        }

        if (mobile.isEmpty() || mobile.length() <10 && mobile.length() > 10) {
            mobileText.setError("Should be 10 digits");
            valid = false;
        } else {
            mobileText.setError(null);
        }

        return valid;
    }
}
