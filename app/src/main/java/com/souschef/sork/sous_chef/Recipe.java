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
    public List<Instruction> instructions;
    public Map<String, String> ingredients;

    public Recipe(String name, int rating, String portions, String time, Bitmap cover, List<Instruction> instructions, Map<String, String> ingredients) {
        this.name = name;
        this.rating = rating;
        this.portions = portions;
        this.time = time;
        this.cover = cover;
        this.instructions = instructions;
        this.ingredients = ingredients;
    }

    public RecipeLite getLite() {
        return new RecipeLite(name, instructions, ingredients);
    }

    public static List<Recipe> getSampleRecipes(Resources resources) {
        ArrayList<Recipe> recipes = new ArrayList<>();

        ArrayList<Instruction> instructions1 = new ArrayList<>();
        instructions1.add(new Instruction("Timer test 1", "Test timer", new Instruction.Timer(0, 0, 10, "The test timer 1 has expired"))); // TODO Temp
        instructions1.add(new Instruction("Timer test 2", "Test timer", new Instruction.Timer(0, 0, 5, "The test timer 2 has expired"))); // TODO Temp
        instructions1.add(new Instruction("Boil water", "Set four liters of water to boil", null));
        instructions1.add(new Instruction("Salt water", "Put two teaspoons of salt in the water", null));
        instructions1.add(new Instruction("Chop onion", "Chop the onion", null));
        instructions1.add(new Instruction("Put spaghetti into water", "Put one kg of spaghetti into the water when it boils", null));
        instructions1.add(new Instruction("Set a timer", "Set a timer to eleven minutes. When it rings put the spaghetti into a colander", new Instruction.Timer(0, 11, 0, "Your spaghetti should be ready now, put it into a colander.")));
        instructions1.add(new Instruction("Fry onion", "Fry the chopped onion on low temperature for about 3 minutes", new Instruction.Timer(0, 3, 0, "The onion should be ready now")));
        instructions1.add(new Instruction("Remove onion", "Put the fried onion into a pot", null));
        instructions1.add(new Instruction("Fry minced meat", "Fry the minced meat on medium-high temperature for about 6 minutes", new Instruction.Timer(0, 6, 0, "The minced meat should be ready now")));
        instructions1.add(new Instruction("Put it in a pot", "Put the minced meat in the same pot as the onion", null));
        instructions1.add(new Instruction("Add the crushed tomato", "Add one kg of crushed tomato to the pot", null));
        instructions1.add(new Instruction("Let it simmer", "Let it simmer for five minutes", new Instruction.Timer(0, 5, 0, "The dish has now simmered for 5 minutes")));
        instructions1.add(new Instruction("Add calf fund", "Add two tablespoons of calf fund", null));
        instructions1.add(new Instruction("Add spices", "Add one tablespoon of oregano, one tablespoon of basil, two teespoons of sage and a pinch of white pepper", null));
        instructions1.add(new Instruction("Stir and serve", "Stir so that it evens out and it is ready for serving", null));

        /*
        ArrayList<String> instructions1 = new ArrayList<String>();
        instructions1.add("Boil water");
        instructions1.add("Salt water");
        instructions1.add("Chop onion");
        instructions1.add("Put spaghetti into water");
        instructions1.add("Set a timer"); //
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
        instructionsDescription.add("Set a timer to eleven minutes. When it rings put the spaghetti into a colander");//
        instructionsDescription.add("Fry the chopped onion on low temperature for about 3 minutes");
        instructionsDescription.add("Put the fried onion into a pot");//
        instructionsDescription.add("Fry the minced meat on medium-high temperature for about 6 minutes");
        instructionsDescription.add("Put the minced meat in the same pot as the onion");
        instructionsDescription.add("Add one kg of crushed tomato to the pot");//
        instructionsDescription.add("Let it simmer for five minutes");
        instructionsDescription.add("Add two tablespoons of calf fund");
        instructionsDescription.add("Add one tablespoon of oregano, one tablespoon of basil, two teespoons of sage and a pinch of white pepper");
        instructionsDescription.add("Stir so that it evens out and it is ready for serving");
        */

        Bitmap bitmap1 = BitmapFactory.decodeResource(resources, R.drawable.spaghetti);
        Recipe recipe1 = new Recipe("Spaghetti Bolognese", 4, "4-6", "30 min", bitmap1, instructions1, null);

        Bitmap bitmap2 = BitmapFactory.decodeResource(resources, R.drawable.soup);
        Recipe recipe2 = new Recipe("White Bean Blender Soup", 3, "2-4", "50 min", bitmap2, null, null);

        Bitmap bitmap3 = BitmapFactory.decodeResource(resources, R.drawable.pancake);
        Recipe recipe3 = new Recipe("Homemade Japanese Pancakes", 5, "4-6", "30 min", bitmap3, null, null);


        Bitmap bitmap4 = BitmapFactory.decodeResource(resources, R.drawable.muffins);
        Recipe recipe4 = new Recipe("Chocolate Chip Muffins", 3, "6-8", "40 min", bitmap4, null, null);



        recipes.add(recipe2);
        recipes.add(recipe1);
        recipes.add(recipe4);
        recipes.add(recipe3);

        return recipes;
    }
}
