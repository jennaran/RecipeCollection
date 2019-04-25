
package dao;

import domain.Recipe;
import domain.User;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;
/**
 * Provides methods for managing recipe.txt (the file where recipes are saved)
 */
public class DerbyRecipeDAO implements RecipeDAO {
    private List<Recipe> recipes;
    private List<Recipe> allRecipes;
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
        this.allRecipes = new ArrayList();
        
        try {
            Scanner reader = new Scanner(new File(recipeFile));
            while (reader.hasNextLine()) {
                String[] parts = reader.nextLine().split(";");
                int id = Integer.valueOf(parts[0]);
                User user = users.listAll().stream().filter(u -> u.getUsername().equals(parts[4])).findFirst().orElse(null);
                
                Recipe recipe = new Recipe(id, parts[1], user);
                recipe.setIngredients(parts[2]);
                recipe.setInstruction(parts[3]);
                
                recipes.add(recipe);
                allRecipes.add(recipe);
            }
            
        } catch (FileNotFoundException | NumberFormatException e) {
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
        recipe.setUniqueID(generateId());
        this.recipes.add(recipe);
        this.allRecipes.add(recipe);
        saveToFile();
    }
    
    @Override
    public Recipe searchByKey(Integer id) {
        //delete maybe? no tests
        //searchByRecipe
        return this.recipes.stream().filter(r -> r.getUniqueID() == id).findFirst().orElse(null);
    }
    
    @Override
    public boolean update(Recipe recipe) throws Exception {
        //not done no tests
        //propably calls delete(id) and create(recipe)
        return false;
    }
    /**
    * Deletes a recipe
    * 
    * @param id id of the recipe being deleted
    * @throws java.lang.Exception 
    * 
    * @see dao.DerbyRecipeDAO#saveToFile() 
    * 
    */
    @Override
    public void delete(Integer id) throws Exception {
        //not used yet, so no tests
        Recipe recipe = recipes.stream().filter(r -> Objects.equals(r.getUniqueID(), id)).findFirst().orElse(null);
        this.recipes.remove(recipe);
        saveToFile();
    }
    /**
    * Deletes given user's recipes
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
        if (usersRecipes == null) {
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
        List<Recipe> usersRecipes = new ArrayList<>();
        usersRecipes = recipes.stream().filter(r -> r.getUser().getUsername().equals(user.getUsername())).collect(Collectors.toList());
        
        return usersRecipes;
    }
    /**
    * Generates id
    * 
    * @return id = size of the list containing all (also the deleted) recipes
    * 
    */
    private int generateId() {
        return this.allRecipes.size();
    }
    /**
    * Writes changes to the file
    * 
    * @throws java.lang.Exception 
    * 
    */
    public void saveToFile() throws Exception {
        try (FileWriter writer = new FileWriter(new File(recipeFile))) {
            for (Recipe r : recipes) {
                writer.write(r.getUniqueID() + ";" + r.getName() + ";" + r.getIngredientsString() + ";" + r.getInstruction() + ";" + r.getUser().getUsername() + "\n");
            }
        } catch (Exception e) {
            System.out.println("voi ei :(");
        }
    }

}
