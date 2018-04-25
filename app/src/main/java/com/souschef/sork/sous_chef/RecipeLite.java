package com.souschef.sork.sous_chef;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by Robin on 2018-04-23.
 */

public class RecipeLite implements Serializable{
    public String name;;
    public List<String> instructions;
    public List<String> instructionsDescription;
    public Map<String, String> ingredients;

    public RecipeLite(String name, List<String> instructions, List<String> instructionsDescription, Map<String, String> ingredients) {
        this.name = name;
        this.instructions = instructions;
        this.ingredients = ingredients;
        this.instructionsDescription  = instructionsDescription;
    }

}
