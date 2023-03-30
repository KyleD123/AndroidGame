package com.example.finalproject;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

public class GameActivity  extends AppCompatActivity {
    //for screem size
    private int screenHeight;
    private int screenWidth;

    //for objects
    private ImageView player;
    private ImageView enemy;
    private TextView txtScore;
    public static int nScore;

    //x positions for enemy
    private float enemyX;

    private ScoreBoard db;
    private Cursor cursor;

    private Handler handler = new Handler();
    private Timer timer = new Timer();



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);
        player = findViewById(R.id.player);
        enemy = findViewById(R.id.enemy);
        WindowManager mgr = getWindowManager();
        Display display = mgr.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x; //Screen width 1080
        screenHeight = size.y; //Screen height 1794
        txtScore = findViewById(R.id.tvScore);
        nScore = 0;

        enemyX = screenWidth + 200f;
        enemy.setX(enemyX);
        enemy.setY(900);

        db = new ScoreBoard(this);

        player.setY(900);
        player.setX(225);

        if(MainActivity.play)
        {
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            attack();
                        }
                    });
                }
            },1000,20);
        }
    }

    private int nCounter = 1;
    public void attack()
    {
        if(nCounter > 0)
        {
            enemy.setImageResource(R.drawable.sock1);
            nCounter--;
        }
        else
        {
            enemy.setImageResource(R.drawable.sock2);
            nCounter++;
        }
        enemyX -= 10;

        if(enemy.getX() + enemy.getWidth() < 0)
        {
            enemyX = screenWidth + 200f;
        }
        else if(collision())
        {
            timer.cancel();
            System.out.println("DEAD!!!!!!");
            enemy.setY(-1000f);
            db.open();
            db.updateScore(MainActivity.p);
            db.close();
            Intent intent;
            intent = new Intent(this, GameOverActivity.class);
            this.startActivity(intent);
            this.finish();
        }
        enemy.setX(enemyX);
        enemy.setY(900);


    }

    public static boolean inAir = false;
    public void playerClick(View v)
    {
        findViewById(R.id.gameScreen).setEnabled(false);
        jump();

    }


    public boolean jump()
    {
        nScore++;
        txtScore.setText("Score: " + nScore);
        MainActivity.p.score = nScore;
        player.setImageResource(R.drawable.player2);
        player.animate().translationY(enemy.getHeight() + 350).setDuration(1000).withEndAction(new Runnable() {
            @Override
            public void run() {
                player.animate().translationY(900).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.gameScreen).setEnabled(true);
                        player.setImageResource(R.drawable.player1);
                    }
                });

                inAir = false;

            }
        });
        return inAir;
    }



    public boolean collision()
    {
        boolean bReturn = player.getX() < enemy.getX() + enemy.getWidth()  -110 &&
                            player.getX() + player.getWidth() -110 > enemy.getX() &&
                              player.getY() < enemy.getY() + enemy.getHeight() -110 &&
                                player.getY() + player.getHeight() -110 > enemy.getY();
        return bReturn;
    }


}
