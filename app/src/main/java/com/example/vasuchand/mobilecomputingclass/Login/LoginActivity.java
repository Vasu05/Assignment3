package com.example.vasuchand.mobilecomputingclass.Login;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.example.vasuchand.mobilecomputingclass.MainActivity;
import com.example.vasuchand.mobilecomputingclass.R;
import com.example.vasuchand.mobilecomputingclass.Session;
import com.example.vasuchand.mobilecomputingclass.database;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity  {
    EditText email;
    EditText password;
    Button Submit ;
    TextView waytosignup;
    private database mydb ;
    Session session;
    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = (EditText)findViewById(R.id.input_email);
        password = (EditText)findViewById(R.id.input_password);
        waytosignup = (TextView)findViewById(R.id.link_signup);
        Submit = (Button)findViewById(R.id.btn_login);
        mydb = new database(this);
        session = new Session();
        Submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });
        waytosignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Signup.class);
                startActivity(intent);
            }
        });
    }

    public void login()
    {
        if (!validate()) {
            onLoginFailed();
            return;
        }

        final String get_email = email.getText().toString();
        final String get_password = password.getText().toString();
        Cursor rs = mydb.getData(get_email);
        if(rs!=null && rs.getCount()>0) {
            rs.moveToFirst();

            String nam = rs.getString(rs.getColumnIndex(database.CONTACTS_COLUMN_NAME));
            String phon = rs.getString(rs.getColumnIndex(database.CONTACTS_COLUMN_PHONE));
            String emai = rs.getString(rs.getColumnIndex(database.CONTACTS_COLUMN_EMAIL));
            String stree = rs.getString(rs.getColumnIndex(database.CONTACTS_COLUMN_Password));
            rs.close();


            if (stree.equals(get_password) || stree == get_password) {
                session.setLogin(this, "login", 1);
                session.setEmail(this, "email", get_email);
                session.setMob(this, "mob", phon);
                session.setName(this, "name", nam);
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                //intent.putExtra("loging2","t");


                startActivity(intent);
                finish();
            }
            else{
                Toast.makeText(getApplicationContext(), "Wrong Password !", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(getApplicationContext(), "Invalid credentials", Toast.LENGTH_SHORT).show();

        }



    }
    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        Submit.setEnabled(true);
    }
    public boolean validate() {
        boolean valid = true;

        String get_email = email.getText().toString();
        String get_password = password.getText().toString();

        if (get_email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(get_email).matches()) {
            email.setError("enter a valid email address");
            valid = false;
        } else {
            email.setError(null);
        }

        if (get_password.isEmpty() ) {
            password.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            password.setError(null);
        }

        return valid;
    }
}

