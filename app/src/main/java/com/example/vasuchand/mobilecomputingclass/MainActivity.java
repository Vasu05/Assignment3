package com.example.vasuchand.mobilecomputingclass;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vasuchand.mobilecomputingclass.Login.LoginActivity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static Context context;
    private Button start,stop,logout,delete,update;
    private ImageView preview,preview2;
    private static final int CAMERA_REQUEST = 1888;
    private static final int CAMERA_REQUEST2 = 2001;
    public static final int MEDIA_TYPE_IMAGE = 1;
    public boolean writedisk = false;
    private static final String IMAGE_DIRECTORY_NAME = "MC CLASS2";
    public static final int REQUEST_PERMISSIONS_CODE = 128;
    private Uri fileUri,fileUri2;
    private TextView email,name,mobile;
    Session session;
    boolean doubleBackToExitPressedOnce = false;
    database db;
    String e;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start =(Button)findViewById(R.id.b1);
        stop  = (Button)findViewById(R.id.b2);
        logout = (Button)findViewById(R.id.a1);
        delete = (Button)findViewById(R.id.a2);
        update = (Button)findViewById(R.id.a3);
        preview = (ImageView)findViewById(R.id.imgPreview);
        preview2 = (ImageView)findViewById(R.id.imgPreview2);
        email = (TextView)findViewById(R.id.useremail);
        name = (TextView)findViewById(R.id.username);
        mobile = (TextView)findViewById(R.id.usermob);
        context = this;

        session = new Session();
        String n = session.getPreferences(MainActivity.this, "name");
         e = session.getPreferences(MainActivity.this, "email");
        String d = session.getPreferences(MainActivity.this, "mob");
        email.setText(e);
        name.setText(n);
        mobile.setText(d);
        db = new database(this);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                fileUri2 = getOutputMediaFileUri2(MEDIA_TYPE_IMAGE);

                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri2);
                startActivityForResult(cameraIntent, CAMERA_REQUEST2);
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, update.class);
                //intent.putExtra("loging2","t");
                startActivity(intent);
              
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                session.setLogin(MainActivity.this,"login",0);
                session.setMob(MainActivity.this, "mob", "");
                session.setEmail(MainActivity.this, "email", "");
                session.setName(MainActivity.this,"name","");
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                session.setLogin(MainActivity.this, "login", 0);
                session.setMob(MainActivity.this, "mob", "");
                session.setEmail(MainActivity.this, "email", "");
                session.setName(MainActivity.this, "name", "");
                db.deleteContact(e);
                Toast.makeText(getApplicationContext(), "Deleted Successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        callWriteOnSDCard();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("file_uri", fileUri);
        outState.putParcelable("file_uri2", fileUri2);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);


        fileUri = savedInstanceState.getParcelable("file_uri");
        fileUri2 = savedInstanceState.getParcelable("file_uri2");
    }
    @Override
    public void onBackPressed() {
        //  DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (doubleBackToExitPressedOnce) {
            finish();
            // /  super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click back again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
//            Bitmap mphoto = (Bitmap) data.getExtras().get("data");
//            mimageView.setImageBitmap(mphoto);
            previewCapturedImage();
        }
        else if (requestCode == CAMERA_REQUEST2 && resultCode == RESULT_OK) {
//            Bitmap mphoto = (Bitmap) data.getExtras().get("data");
//            mimageView.setImageBitmap(mphoto);
            previewCapturedImage2();
        }
    }

    /**
     * Creating file uri to store image/video
     */
    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type,0));
    }

    /**
     * returning image
     */

    public Uri getOutputMediaFileUri2(int type) {
        return Uri.fromFile(getOutputMediaFile(type,1));
    }

    private static File getOutputMediaFile(int type,int a) {

        // External sdcard location
        File mediaStorageDir;
        if(a==0)
        {
             mediaStorageDir = new File(
                    context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                    IMAGE_DIRECTORY_NAME);
        }
        else
        {
            mediaStorageDir = new File(
                    Environment
                            .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                    IMAGE_DIRECTORY_NAME);
            Log.v("name" , String.valueOf(mediaStorageDir));
        }

            // Create the storage directory if it does not exist
            if (!mediaStorageDir.exists()) {
                if (!mediaStorageDir.mkdirs()) {
                    Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
                            + IMAGE_DIRECTORY_NAME + " directory");
                    return null;
                }
            }

            // Create a media file name
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                    Locale.getDefault()).format(new Date());
            File mediaFile;
            if (type == MEDIA_TYPE_IMAGE) {
                mediaFile = new File(mediaStorageDir.getPath() + File.separator
                        + "IMG_" + timeStamp + ".jpg");
            } else {
                return null;
            }


            return mediaFile;

    }

    private void previewCapturedImage2() {
        try {


            preview2.setVisibility(View.VISIBLE);
            BitmapFactory.Options options = new BitmapFactory.Options();

            options.inSampleSize = 8;

            final Bitmap bitmap = BitmapFactory.decodeFile(fileUri2.getPath(),
                    options);

            preview2.setImageBitmap(bitmap);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
    private void previewCapturedImage() {
        try {


            preview.setVisibility(View.VISIBLE);
            BitmapFactory.Options options = new BitmapFactory.Options();

            options.inSampleSize = 8;

            final Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(),
                    options);

            preview.setImageBitmap(bitmap);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void callWriteOnSDCard() {
        //Log.i(TAG, "callWriteOnSDCard()");

        if( ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ){

            if( ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) ){
                callDialog( "Permission Required", new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE} );
            }
            else{
                ActivityCompat.requestPermissions( this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSIONS_CODE );
            }
        }
        else{
            writedisk = true;
            // getData("Engineering");
            // createDeleteFolder();
        }

    }

    private void callDialog( String message, final String[] permissions ){
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        //Setting Dialog Title
        alertDialog.setTitle(R.string.GPSAlertDialogTitle);

        //Setting Dialog Message
        alertDialog.setMessage(R.string.GPSAlertDialogMessage);

        //On Pressing Setting button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                ActivityCompat.requestPermissions(MainActivity.this, permissions, REQUEST_PERMISSIONS_CODE);
                dialog.cancel();
            }
        });

        //On pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }

}
