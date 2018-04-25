package com.souschef.sork.sous_chef;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Robin on 2018-04-18.
 */

public class Recipe{
    public String name;
    public int difficulty;
    public String portions;
    public String time;
    public Bitmap cover;
    public List<String> instructions;
    public List<String> instructionsDescription;
    public Map<String, String> ingredients;

    public Recipe(String name, int difficulty, String portions, String time, Bitmap cover, List<String> instructions, List<String> instructionsDescription, Map<String, String> ingredients) {
        this.name = name;
        this.difficulty = difficulty;
        this.portions = portions;
        this.time = time;
        this.cover = cover;
        this.instructions = instructions;
        this.ingredients = ingredients;
        this.instructionsDescription = instructionsDescription;
    }

    public RecipeLite getLite() {
        return new RecipeLite(name, instructions, ingredients);
    }

    public static List<Recipe> getSampleRecipes(Resources resources) {
        ArrayList<Recipe> recipes = new ArrayList<>();
        Bitmap bitmap1 = BitmapFactory.decodeResource(resources, R.drawable.spaghetti);
        ArrayList<String> instructions1 = new ArrayList<>();
        instructions1.add("Koka vatten");
        instructions1.add("Hacka lök");
        instructions1.add("Fräs gül lök");
        instructions1.add("Stek köttfärs");
        instructions1.add("Stek köttfärs");
        instructions1.add("Stek köttfärs");
        instructions1.add("Stek köttfärs");
        instructions1.add("Stek köttfärs");
        instructions1.add("Stek köttfärs");

        Recipe recipe1 = new Recipe("Spaghetti Bolognese", 2, "4-6", "30 min", bitmap1, instructions1, null, null);
        recipes.add(recipe1);

        Bitmap bitmap2 = BitmapFactory.decodeResource(resources, R.drawable.biff);
        Recipe recipe2 = new Recipe("Lammfärsbiffar", 2, "4-6", "30 min", bitmap2, null, null, null);
        recipes.add(recipe2);

        Bitmap bitmap3 = BitmapFactory.decodeResource(resources, R.drawable.soppa);
        Recipe recipe3 = new Recipe("Delikatessoppa", 2, "4-6", "30 min", bitmap3, null, null, null);
        recipes.add(recipe3);

        Bitmap bitmap4 = BitmapFactory.decodeResource(resources, R.drawable.ryggbiff);
        Recipe recipe4 = new Recipe("Ryggbiff", 2, "4-6", "30 min", bitmap4, null, null, null);
        recipes.add(recipe4);

        Bitmap bitmap5 = BitmapFactory.decodeResource(resources, R.drawable.hamburgare);
        Recipe recipe5 = new Recipe("Hamburgare", 2, "4-6", "30 min", bitmap5, null, null, null);
        recipes.add(recipe5);

        return recipes;
    }
}
