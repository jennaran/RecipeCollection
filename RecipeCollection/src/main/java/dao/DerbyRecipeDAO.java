
package dao;

import domain.Recipe;
import domain.User;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;

public class DerbyRecipeDAO implements RecipeDAO {
    private List<Recipe> recipes;
    private final String recipeFile;
    private int i;
    
    public DerbyRecipeDAO(UserDAO users, String recipeFile) throws Exception {
        this.i = 0;
        this.recipeFile = recipeFile;
        this.recipes = new ArrayList();
        
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
            }
            
        } catch (Exception e) {
            FileWriter writer = new FileWriter(new File(this.recipeFile));
            writer.close();
        }
        
    }

    @Override
    public void create(Recipe recipe) throws Exception {
        //no tests before Recipe-class is ok
        recipe.setUniqueID(generateId());
        this.recipes.add(recipe);
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
        return false;
    }

    @Override
    public void delete(Integer id) throws Exception {
        //not used yet, so no tests
        Recipe recipe = recipes.stream().filter(r -> Objects.equals(r.getUniqueID(), id)).findFirst().orElse(null);
        this.recipes.remove(recipe);
        saveToFile();
    }
    
    @Override
    public void delete(User user) throws Exception {
        List<Recipe> usersRecipes = listUsersAll(user);
        usersRecipes.forEach((recipe) -> {
            this.recipes.remove(recipe);
        });
        saveToFile();
    }

    @Override
    public List<Recipe> listUsersAll(User user) {
        List<Recipe> usersRecipes = recipes.stream().filter(r -> r.getUser().getUsername().equals(user.getUsername())).collect(Collectors.toList());
        return usersRecipes;
    }
    private int generateId() {
        i = i++;
        return i;
    }

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
