
package domain;

import java.util.Arrays;
import java.util.List;

public class Recipe {
    private long uniqueID;
	private String name;
	private List<String> ingredients;
	private String instruction;
	private String comment;
	public long getUniqueID() {
		return uniqueID;
	}
	public void setUniqueID(long uniqueID) {
		this.uniqueID = uniqueID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
		return instruction;
	}
	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	@Override
	public String toString() {
		return "Recipe [uniqueID=" + uniqueID + ", name=" + name + ", ingredients=" + ingredients + ", instruction="
				+ instruction + ", comment=" + comment + "]";
	}
}
