package com.souschef.sork.sous_chef;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by Robin on 2018-04-23.
 */

public class RecipeLite implements Serializable{
    public String name;;
    public List<Instruction> instructions;
    public Map<String, String> ingredients;

    public RecipeLite(String name, List<Instruction> instructions, Map<String, String> ingredients) {
        this.name = name;
        this.instructions = instructions;
        this.ingredients = ingredients;
    }

}
