package com.example.finalproject;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GameOverActivity extends AppCompatActivity {

    private TextView tvFinalScore;

    private ScoreBoard db;
    private Cursor cursor;

    public TextView tvFirst;
    public TextView tvSecond;
    public TextView tvThird;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over_activity);

        tvFirst = findViewById(R.id.tvFirstPlace);
        tvSecond = findViewById(R.id.tvSecondPlace);
        tvThird = findViewById(R.id.tvThirdPlace);

        db = new ScoreBoard(this);

        getLeaderBoard();

        tvFinalScore = findViewById(R.id.tvFinalScore);
        tvFinalScore.setText("Score: " + GameActivity.nScore);
    }


    public void onHomeClick(View v)
    {
        Intent intent = new Intent(this, MainActivity.class);
        this.startActivity(intent);
    }

    public void onAgainClick(View v)
    {
        Intent intent = new Intent(this, GameActivity.class);
        this.startActivity(intent);
    }


    public void getLeaderBoard() {
        db.open();
        cursor = db.getTop3();
        if (cursor.moveToFirst())
        {
            Log.i("Row: ", cursor.getString(1) + ", " + cursor.getString(2));
        }
        tvFirst.setText("1. " +cursor.getString(1) + " " + cursor.getString(2));
        if(cursor.moveToNext())
        {
            tvSecond.setText("2. "+cursor.getString(1) + " " + cursor.getString(2));
            if(cursor.moveToNext())
            {
                tvThird.setText("3. "+cursor.getString(1) + " " + cursor.getString(2));
            }
        }

        db.close();
    }


}
