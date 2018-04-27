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
    public int rating;
    public String portions;
    public String time;
    public Bitmap cover;
    public List<String> instructions;
    public List<String> instructionsDescription;
    public Map<String, String> ingredients;

    public Recipe(String name, int rating, String portions, String time, Bitmap cover, List<String> instructions, List<String> instructionsDescription, Map<String, String> ingredients) {
        this.name = name;
        this.rating = rating;
        this.portions = portions;
        this.time = time;
        this.cover = cover;
        this.instructions = instructions;
        this.ingredients = ingredients;
        this.instructionsDescription = instructionsDescription;
    }

    public RecipeLite getLite() {
        return new RecipeLite(name, instructions, instructionsDescription, ingredients);
    }

    public static List<Recipe> getSampleRecipes(Resources resources) {
        ArrayList<Recipe> recipes = new ArrayList<>();
        ArrayList<String> instructions1 = new ArrayList<String>();
        instructions1.add("Boil water");
        instructions1.add("Salt water");
        instructions1.add("Chop onion");
        instructions1.add("Put spaghetti into water");
        instructions1.add("Set a timer");
        instructions1.add("Fry onion");
        instructions1.add("Remove onion");
        instructions1.add("Fry minced meat");
        instructions1.add("Put it in a pot");
        instructions1.add("Add the crushed tomato");
        instructions1.add("Let it simmer");
        instructions1.add("Add calf fund");
        instructions1.add("Add spices");
        instructions1.add("Stir and serve");

        ArrayList<String> instructionsDescription = new ArrayList<String>();
        instructionsDescription.add("Set four liters of water to boil");
        instructionsDescription.add("Put two teaspoons of salt in the water");
        instructionsDescription.add("Chop the onion");
        instructionsDescription.add("Put one kg of spaghetti into the water when it boils");
        instructionsDescription.add("Set a timer to eleven minutes. When it rings put the spaghetti into a colander");
        instructionsDescription.add("Fry the chopped onion on low temperature for about 3 minutes");
        instructionsDescription.add("Put the fried onion into a pot");
        instructionsDescription.add("Fry the minced meat on medium-high temperature for about 6 minutes");
        instructionsDescription.add("Put the minced meat in the same pot as the onion");
        instructionsDescription.add("Add  one kg of crushed tomato to the pot");
        instructionsDescription.add("Let it simmer for five minutes");
        instructionsDescription.add("Add two tablespoons of calf fund");
        instructionsDescription.add("Add one tablespoon of oregano, one tablespoon of basil, two teespoons of sage and a pinch of white pepper");
        instructionsDescription.add("Stir so that it evens out and it is ready for serving");

        Bitmap bitmap1 = BitmapFactory.decodeResource(resources, R.drawable.spaghetti);
        Recipe recipe1 = new Recipe("Spaghetti Bolognese", 4, "4-6", "30 min", bitmap1, instructions1, instructionsDescription, null);
        recipes.add(recipe1);

        Bitmap bitmap2 = BitmapFactory.decodeResource(resources, R.drawable.soup);
        Recipe recipe2 = new Recipe("White Bean Blender Soup", 3, "2-4", "50 min", bitmap2, null, null, null);
        recipes.add(recipe2);

        Bitmap bitmap3 = BitmapFactory.decodeResource(resources, R.drawable.pancake);
        Recipe recipe3 = new Recipe("Homemade Japanese Pancakes", 5, "4-6", "30 min", bitmap3, null, null, null);
        recipes.add(recipe3);

        Bitmap bitmap4 = BitmapFactory.decodeResource(resources, R.drawable.muffins);
        Recipe recipe4 = new Recipe("Chocolate Chip Muffins", 3, "6-8", "40 min", bitmap4, null, null, null);
        recipes.add(recipe4);


        return recipes;
    }
}
