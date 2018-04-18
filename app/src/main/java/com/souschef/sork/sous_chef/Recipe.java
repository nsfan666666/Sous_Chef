package com.souschef.sork.sous_chef;

import android.graphics.Bitmap;

import java.util.List;
import java.util.Map;

/**
 * Created by Robin on 2018-04-18.
 */

public class Recipe {
    public String name;
    public int difficulty;
    public String portions;
    public String time;
    public Bitmap cover;
    public List<String> list;
    public Map<String, String> ingredients;

    public Recipe(String name, int difficulty, String portions, String time, Bitmap cover) {
        this.name = name;
        this.difficulty = difficulty;
        this.portions = portions;
        this.time = time;
        this.cover = cover;
    }

}
