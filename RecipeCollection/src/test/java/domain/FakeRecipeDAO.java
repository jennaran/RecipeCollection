
package domain;

import dao.RecipeDAO;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
/**
 * Simulates DerbyRecipeDAO for testing
 */
public class FakeRecipeDAO implements RecipeDAO{
    private List<Recipe> recipes = new ArrayList<>();
    /**
    * Adds new recipe to the recipe list
    * 
    * @param recipe new recipe
    * @throws java.lang.Exception 
    * 
    */
    @Override
    public void create(Recipe recipe) throws Exception {
        this.recipes.add(recipe);
    }
    /**
    * Deletes given user's recipes from list
    * 
    * @param user user whose recipes are going to be deleted
    * @throws java.lang.Exception 
    * 
    */
    @Override
    public void delete(User user) throws Exception {
      List<Recipe> usersRecipes = listUsersAll(user);
        usersRecipes.forEach((recipe) -> {
            this.recipes.remove(recipe);
        });
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
        List<Recipe> usersRecipes = recipes.stream().filter(r -> r.getUser().getUsername().equals(user.getUsername())).collect(Collectors.toList());
        return usersRecipes;
    }
    /**
    * Deletes a recipe from list
    * 
    * @param name name of the recipe being deleted
    * @param user name of the user who owns the recipe
    * @throws java.lang.Exception  
    * 
    */
    @Override
    public void delete(String name, User user) throws Exception {
        List<Recipe> usersRecipes = listUsersAll(user);
        Recipe recipe = usersRecipes.stream().filter(r -> r.getName().equals(name)).findFirst().orElse(null);
        this.recipes.remove(recipe);
    }
    
}
