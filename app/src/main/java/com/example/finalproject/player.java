package com.example.finalproject;

public class player {

    public long id;
    public String name = "";
    public int score = 0;

    public player(long id, String name, int score)
    {
        this.id = id;
        this.name = name;
        this.score = score;
    }

    public player(String name, int score)
    {
        id = -1;
        this.name = name;
        this.score = score;

    }

    public void addPoint()
    {
        this.score += 1;
    }

}
