
package domain;

import dao.RecipeDAO;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class FakeRecipeDAO implements RecipeDAO{
    private List<Recipe> recipes = new ArrayList<>();
    private List<Recipe> allRecipes = new ArrayList<>();

    @Override
    public void create(Recipe recipe) throws Exception {
        recipe.setUniqueID(allRecipes.size()+1);
        this.recipes.add(recipe);
        this.allRecipes.add(recipe);
    }

    @Override
    public Recipe searchByKey(Integer id) {
        return this.recipes.stream().filter(r -> r.getUniqueID() == id).findFirst().orElse(null);
    }

    @Override
    public boolean update(Recipe object) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Integer id) throws Exception {
        Recipe recipe = recipes.stream().filter(r -> Objects.equals(r.getUniqueID(), id)).findFirst().orElse(null);
        this.recipes.remove(recipe);    }

    @Override
    public void delete(User user) throws Exception {
      List<Recipe> usersRecipes = listUsersAll(user);
        usersRecipes.forEach((recipe) -> {
            this.recipes.remove(recipe);
        });
    }

    @Override
    public List<Recipe> listUsersAll(User user) {
        List<Recipe> usersRecipes = recipes.stream().filter(r -> r.getUser().getUsername().equals(user.getUsername())).collect(Collectors.toList());
        return usersRecipes;
    }
    
}
