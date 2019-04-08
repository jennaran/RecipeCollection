
package domain;

import java.util.Arrays;
import java.util.List;

public class Recipe {
    private Integer uniqueID;
    private final String name;
    private List<String> ingredients;
    private List<String> instructions;
    private final User user;

    public Recipe(Integer uniqueID, String name, User user) {
        this.uniqueID = uniqueID;
        this.name = name;
        this.user = user;
    }

    public Recipe(String name, User user) {
        this.name = name;
        this.user = user;
    }

    public void setUniqueID(Integer uniqueID) {
        this.uniqueID = uniqueID;
    }
        
    public Integer getUniqueID() {
        return uniqueID;
    }
    
    public String getName() {
        return name;
    }

    public User getUser() {
        return user;
    }
	
    public String getIngredients() {
        String result = "";
        result = ingredients.stream().map((ingredient) -> ingredient + "_").reduce(result, String::concat);
	return result.substring(0, result.length() - 1);
	}
    
    public void setIngredients(String ingredients) {
	this.ingredients = Arrays.asList(ingredients.split("_"));
        
    }
	
    public String getInstruction() {
    String result = "";
    result = instructions.stream().map((instruction) -> instruction + "_").reduce(result, String::concat);
	return result.substring(0, result.length() - 1);
    }
    
    public void setInstruction(String instruction) {
	this.instructions = Arrays.asList(instruction.split("_"));
    }
}
