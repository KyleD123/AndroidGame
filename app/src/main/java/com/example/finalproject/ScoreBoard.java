package com.example.finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ScoreBoard  extends SQLiteOpenHelper {

    public static final String DB_NAME = "score.db";
    public static final String TABLE_NAME = "Score";
    public static final String ID = "_id";
    public static final String NAME = "name";
    public static final String SCORE = "score";
    public static final int DB_VERSION = 4;

    public SQLiteDatabase sqlDB;

    public ScoreBoard(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public void open() throws SQLException {
        sqlDB = this.getWritableDatabase();
    }

    public void close()
    {
        sqlDB.close();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sCreate = "CREATE TABLE " +
                TABLE_NAME + " (" +
                ID + " integer primary key autoincrement, " +
                NAME + " text not null, " +
                SCORE + " integer);";

        sqLiteDatabase.execSQL(sCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldV, int newV) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }


    public long createScore(player p)
    {
        ContentValues cvs = new ContentValues();
        cvs.put(NAME, p.name);
        cvs.put(SCORE, p.score);

        long autoID = sqlDB.insert(TABLE_NAME, null, cvs);

        p.id = autoID;
        return p.id;
    }

    public boolean updateScore(player p)
    {
        if(p.id < 0)
        {
            return false;
        }
        else
        {
            ContentValues cvs = new ContentValues();
//            cvs.put(NAME, p.name);
            cvs.put(SCORE, p.score);
            return sqlDB.update(TABLE_NAME, cvs,  ID + " = " + p.id, null) > 0;
        }
    }

    public Cursor getTop3()
    {
//        String[] sFields = new String[]{ID, NAME, SCORE};
//        Cursor cursor = sqlDB.query(TABLE_NAME, sFields, SCORE + " ")
        String top3 = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + SCORE + " DESC " + " LIMIT 3;";

        Cursor cursor = sqlDB.rawQuery(top3, null);

        return cursor;
    }


    public Cursor getAllScores()
    {
        String select = "SELECT * FROM " + TABLE_NAME;
        return sqlDB.rawQuery(select, null);
    }

    public Cursor getByName(String name)
    {
        String[] sFields = new String[]{ID, NAME, SCORE};
        Cursor cursor = sqlDB.query(TABLE_NAME, sFields, NAME + " = " + name, null, null, null, null);
        if(cursor != null)
        {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor getByScore(int score)
    {
        String[] sFields = new String[]{ID, NAME, SCORE};
        Cursor cursor = sqlDB.query(TABLE_NAME, sFields, SCORE + " = " + score, null, null, null, null);
        if(cursor != null)
        {
            cursor.moveToFirst();
        }
        return cursor;
    }







}
