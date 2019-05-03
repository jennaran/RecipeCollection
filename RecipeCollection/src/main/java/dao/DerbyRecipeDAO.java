
package dao;

import domain.Recipe;
import domain.User;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
/**
 * Provides methods for managing recipe.txt (the file where recipes are saved)
 */
public class DerbyRecipeDAO implements RecipeDAO {
    private List<Recipe> recipes;
    private final String recipeFile;
    
    /**
    * Sets constructors
    * 
    * @param recipeFile recipe.txt
    * @param users userDAO
    * @throws java.lang.Exception 
    * 
    */
    public DerbyRecipeDAO(UserDAO users, String recipeFile) throws Exception {
        this.recipeFile = recipeFile;
        this.recipes = new ArrayList();
        
        try {
            Scanner reader = new Scanner(new File(recipeFile));
            while (reader.hasNextLine()) {
                String[] parts = reader.nextLine().split(";");
                User user = users.listAll().stream().filter(u -> u.getUsername().equals(parts[3])).findFirst().orElse(null);
                
                Recipe recipe = new Recipe(parts[0], user);
                recipe.setIngredients(parts[1]);
                recipe.setInstruction(parts[2]);
                
                recipes.add(recipe);
            }
            
        } catch (FileNotFoundException | NumberFormatException e) {
            System.out.println("An exception occurred in reading from the recipe file: " + e.getMessage());
            FileWriter writer = new FileWriter(new File(this.recipeFile));
            writer.close();
        }
        
    }
    /**
    * Adds new recipe to the recipe list and saves it to the file
    * 
    * @param recipe new recipe
     * @throws java.lang.Exception
    * 
    * @see dao.DerbyRecipeDAO#saveToFile() 
    * 
    */
    @Override
    public void create(Recipe recipe) throws Exception {
        this.recipes.add(recipe);
        saveToFile();
    }
    /**
    * Deletes a recipe and saves changes
    * 
    * @param name name of the recipe being deleted
    * @param user name of the user who owns the recipe
    * @throws java.lang.Exception 
    * 
    * @see dao.DerbyRecipeDAO#listUsersAll(domain.User) 
    * @see dao.DerbyRecipeDAO#saveToFile() 
    * 
    */
    @Override
    public void delete(String name, User user) throws Exception {
        List<Recipe> usersRecipes = listUsersAll(user);
        Recipe recipe = usersRecipes.stream().filter(r -> r.getName().equals(name)).findFirst().orElse(null);
        this.recipes.remove(recipe);
        saveToFile();
    }
    /**
    * Deletes given user's recipes and saves changes
    * 
    * @param user user whose recipes are going to be deleted
    * @throws java.lang.Exception 
    * 
    * @see dao.DerbyRecipeDAO#saveToFile() 
    * 
    */
    @Override
    public void delete(User user) throws Exception {
        List<Recipe> usersRecipes = listUsersAll(user);
        if (usersRecipes.isEmpty()) {
            return;
        }
        usersRecipes.forEach((recipe) -> {
            this.recipes.remove(recipe);
        });
        saveToFile();
    }
    /**
    * Lists all recipes own by given user
    * 
    * @param user Whose recipes we want to list
    * 
    * @return list of the recipes
    * 
    */
    @Override
    public List<Recipe> listUsersAll(User user) {
        return recipes.stream().filter(r -> r.getUser().getUsername().equals(user.getUsername())).collect(Collectors.toList());
    }
    /**
    * Writes changes to the file
    * 
    */
    public void saveToFile() {
        try (FileWriter writer = new FileWriter(new File(recipeFile))) {
            for (Recipe r : recipes) {
                writer.write(r.getName() + ";" + r.getIngredientsString() + ";" + r.getInstruction() + ";" + r.getUser().getUsername() + "\n");
            }
        } catch (Exception e) {
            System.out.println("An exception occurred in saving recipes into a file: " + e.getMessage());
            
        }
    }

}
