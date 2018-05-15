package com.souschef.sork.sous_chef;

/**
 * Created by Robin on 2018-04-25.
 */

public interface Commands {

    public void next();
    public void previous();
    public void repeat();
    public void showIngredients();
    public void returnToInstruction();
    /*
    Starts the recipe, reads first instruction
     */
    public void start();
    public void startTimer();
    public void pauseTimer();
    public void resetTimer();

}
