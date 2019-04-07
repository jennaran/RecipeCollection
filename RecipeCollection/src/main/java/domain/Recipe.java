
package domain;

import java.util.Arrays;
import java.util.List;

public class Recipe {
    private Integer uniqueID;
    private  String name;
    private List<String> ingredients;
    private List<String> instructions;
    private  User user;

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

    public void setName(String name) {
        this.name = name;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
	
    public String getIngredients() {
        String result = "";
	for (String ingredient : ingredients) {
            result += ingredient + "_";
        }
	return result.substring(0, result.length()-1);
		
    }
    public void setIngredients(String ingredients) {
        //vaikka aineksia lisätään enterillä, voidaan ne String muotoon tallentaessa erottaa _ ja -
	this.ingredients = Arrays.asList(ingredients.split("_"));
        
    }
	
    public String getInstruction() {
    String result = "";
	for (String instruction : instructions) {
            result += instruction + "_";
        }
	return result.substring(0, result.length()-1);
    }
    
    public void setInstruction(String instruction) {
	this.instructions = Arrays.asList(instruction.split("_"));
    }
    
    @Override
    public String toString() {
        return "Recipe [uniqueID=" + uniqueID + ", name=" + name + ", ingredients=" + ingredients + ", instruction="
			+ instructions  +  "]";   
    }
}
