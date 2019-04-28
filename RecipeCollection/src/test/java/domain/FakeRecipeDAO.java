
package domain;

import dao.RecipeDAO;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FakeRecipeDAO implements RecipeDAO{
    private List<Recipe> recipes = new ArrayList<>();

    @Override
    public void create(Recipe recipe) throws Exception {
        this.recipes.add(recipe);
    }

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

    @Override
    public void delete(String name, User user) throws Exception {
        List<Recipe> usersRecipes = listUsersAll(user);
        Recipe recipe = usersRecipes.stream().filter(r -> r.getName().equals(name)).findFirst().orElse(null);
        this.recipes.remove(recipe);
    }
    
}
