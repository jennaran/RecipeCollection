
package domain;

import java.util.Arrays;
import java.util.List;
/**
 * This is a class for recipes
 */
public class Recipe {
    private final String name;
    private List<String> ingredients;
    private List<String> instructions;
    private final User user;
    
    /**
    * Sets constructors
    *
    * @param name recipe's name
    * @param user who owns the recipe
    * 
    */
    public Recipe(String name, User user) {
        this.name = name;
        this.user = user;
    }
    /**
    * Returns recipe's name
    * 
    * @return name
    */ 
    public String getName() {
        return name;
    }
    /**
    * Returns the owner of the recipe
    * 
    * @return the owner
    */ 
    public User getUser() {
        return user;
    }
    /**
    * This method modifies the list of ingredients into String form by adding _
    * between all the ingredients so that it can be used
    * properly in other methods
    *
    * @return ingredients as String
    */
    public String getIngredientsString() {
        String result = "";
        result = ingredients.stream().map((ingredient) -> ingredient + "_").reduce(result, String::concat);
        return result.substring(0, result.length() - 1);
    }
    /**
    * Returns ingredients as a list
    * 
    * @return a list of ingredients
    */ 
    public List<String> getIngredientsList() {
        return this.ingredients;
    }
    /**
    * Sets ingredients to a list
    * 
    * @param ingredients Ingredients given as a String where they are separated with _
    */ 
    public void setIngredients(String ingredients) {
        this.ingredients = Arrays.asList(ingredients.split("_"));
    }
    /**
    * This method modifies the list of instructions into String form by adding _
    * between all the ingredients so that it can be used
    * properly in other methods
    * 
    * @return name
    */ 
    public String getInstruction() {
        String result = "";
        result = instructions.stream().map((instruction) -> instruction + "_").reduce(result, String::concat);
        return result.substring(0, result.length() - 1);
    }
    /**
    * Sets instructions to a list
    * 
    * @param instruction instructions given as a String where they are separated with _
    */ 
    public void setInstruction(String instruction) {
        this.instructions = Arrays.asList(instruction.split("_"));
    }
}
