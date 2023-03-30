package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    public EditText txtName;
    public TextView lblName;
    public String name;
    public static boolean firstTime = true;
    public static boolean play = false;

    private ScoreBoard db;
    private Cursor cursor;

    public static player p;

    ///////////////////////////////86,400,000 MILISECONDS IN ONE DAY/////////////////////
    //////////////////////////////432,000,000 MILISECONDS IN 5 DAYS//////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, FireNotification.class);
        PendingIntent pendingIntent = PendingIntent.getService(this, 1000, intent,PendingIntent.FLAG_CANCEL_CURRENT);

        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 432000000, pendingIntent);


        db = new ScoreBoard(this);

        lblName = findViewById(R.id.tvMsg);
        txtName = findViewById(R.id.etUsername);


        try {
            FileInputStream obIn = openFileInput("mytextfile.txt");
            InputStreamReader inputReader = new InputStreamReader(obIn);
            char[] inputBuffer = new char[100];
            String sUser = "";
            int charRead;
            while ((charRead = inputReader.read(inputBuffer)) > 0) {
                sUser += String.copyValueOf(inputBuffer, 0, charRead);
            }
            inputReader.close();
            txtName.setText(sUser);
        }
            catch (Exception e)
        {
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT);
        }

    }


    public void startGame(View v) {
        lblName = findViewById(R.id.tvMsg);
        txtName = findViewById(R.id.etUsername);
        String sName = txtName.getText().toString();
        //reads if file has username
        p = new player(txtName.getText().toString(), 0);
        db.open();
        db.createScore(p);
        db.close();
        if(sName.length() > 0)
        {
            play = true;
            firstTime = false;
            //Saves username to file name localUsername
            try{
                FileOutputStream obOut = openFileOutput("mytextfile.txt", MODE_PRIVATE);
                OutputStreamWriter outputWrite = new OutputStreamWriter(obOut);
                outputWrite.write(txtName.getText().toString());
                System.out.println("USERNAME: " + txtName.getText().toString());
                outputWrite.close();
                firstTime = false;
//                Toast.makeText(this, txtName.getText(), Toast.LENGTH_SHORT).show();
            }
            catch (Exception e) {
                e.printStackTrace();
            }

            Intent intent;
            intent = new Intent(this, GameActivity.class);
            this.startActivity(intent);
        }
        else
        {
            lblName.setText("You must enter a name");
            lblName.setTextColor(Color.RED);
        }

    }



}