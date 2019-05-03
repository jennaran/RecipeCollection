
package domain;
    
import dao.RecipeDAO;
import dao.UserDAO;
import java.util.List;
import java.util.stream.Collectors;
/**
 * This class provides methods for managing users and recipes
 */
public class Service {
    
    private final UserDAO userDao;
    private final RecipeDAO recipeDAO;
    private User loggenInUser;

    public Service(UserDAO userDao, RecipeDAO recipeDAO) {
        this.userDao = userDao;
        this.recipeDAO = recipeDAO;
    }
    /**
    * This method is for creating a new recipe for the user who is logged in
    *
    * @param   name   recipe's name given by the user
    * @param   listIngredients   list of ingredients given by the user
    * @param   instructionWrong   instructions given by the user
    * 
    * @see domain.Recipe#Recipe(java.lang.String, domain.User) 
    * @see domain.Service#ingredientsStringAddingLine(java.util.List) 
    * @see domain.Service#instructionsStringAddingLine(java.lang.String) 
    * @see domain.Recipe#setIngredients(java.lang.String) 
    * @see domain.Recipe#setInstruction(java.lang.String) 
    * @see dao.RecipeDAO#create(domain.Recipe) 
    * 
    * @return true - if creating a recipe works
    */
    public boolean createNewRecipe(String name, List<String> listIngredients, String instructionWrong) {
        String recipeWithName = userRecipeNames().stream().filter(n -> n.equals(name)).findFirst().orElse(null);
        if (recipeWithName != null) {
            return false;
        }
        Recipe recipe = new Recipe(name, loggenInUser);
        String ingredients = ingredientsStringAddingLine(listIngredients);
        String instruction = instructionsStringAddingLine(instructionWrong);
        
        recipe.setIngredients(ingredients);
        recipe.setInstruction(instruction);
        try {
            recipeDAO.create(recipe);
        } catch (Exception e) {
            System.out.println("An exception occurred in creating a new recipe: " + e.getMessage());
            return false;
        }
        return true;
    } 
    /**
    * This method is for updating a  recipe from the user who is logged in
    *
    * @param oldName recipe's old name
    * @param newName recipe's new name
    * @param   listIngredients   list of ingredients given by the user
    * @param   instructionWrong   instructions given by the user
    * 
    * @see domain.Service#userRecipeNames() 
    * @see dao.RecipeDAO#delete(java.lang.String, domain.User) 
    * @see domain.Service#createNewRecipe(java.lang.String, java.util.List, java.lang.String) 
    * 
    * @return true - if updating a recipe works
    */
    public boolean update(String oldName, String newName, List<String> listIngredients, String instructionWrong) {
        String recipeWithName = userRecipeNames().stream().filter(n -> n.equals(newName)).findFirst().orElse(null);
        if (recipeWithName == null || recipeWithName.equals(oldName)) {
            try {
                this.recipeDAO.delete(oldName, this.loggenInUser);
            } catch (Exception e) {
                System.out.println("An exception occurred in updating a recipe: " + e.getMessage());
                return false;
            }
            
            return createNewRecipe(newName, listIngredients, instructionWrong);
            
        } else {
            return false;
        }
    } 
    /**
    * Modifies given list to a form where it can be saved to the recipe class
    *
    * @param   listIngredients   list of ingredients given by the user (or from createNewRecipe)
    * 
    * @return Ingredients as String
    */
    public String ingredientsStringAddingLine(List<String> listIngredients) {
        String ingredients = "";
        ingredients = listIngredients.stream().map((ingredient) -> ingredient + "_").reduce(ingredients, String::concat);
        return ingredients.substring(0, ingredients.length() - 1);
    }
    /**
    * Modifies given String to a form where it can be saved to the recipe class
    *
    * @param   wrong   instructions given by the user (or from createNewRecipe)
    * 
    * @return Instructions as String
    */
    public String instructionsStringAddingLine(String wrong) {
        String instructions = "";
        instructions = wrong.replace("\n", "_");
        return instructions;
    }
    /**
    * This method is for finding a specific recipe from the user who is logged in
    *
    * @param   name   recipe's name given by the user 
    * 
    * @see dao.RecipeDAO#listUsersAll(domain.User) 
    * 
    * @return The recipe with the given name
    */
    public Recipe findUsersRecipeByName(String name) {
        List<Recipe> recipes = this.recipeDAO.listUsersAll(loggenInUser);
        Recipe recipe = recipes.stream().filter(r -> r.getName().equals(name)).findFirst().orElse(null);
        return recipe;
    }
    /**
    * This method is for finding the ingredients 
    * of the given user's recipe
    *
    * @param   name   recipe's name given by the user
    * 
    * @see domain.Service#findUsersRecipeByName(java.lang.String) 
    * @see domain.Recipe#getIngredientsList() 
    * 
    * @return List of the ingredients
    */
    public List<String> getRecipeIngredienstByRecipeName(String name) {
        Recipe recipe = findUsersRecipeByName(name); 
        if (recipe == null) {
            return null;
        }
        List<String> ingredientsList = recipe.getIngredientsList();
        return ingredientsList;
    }
    /**
    * This method is for finding the instructions 
    * of the given user's recipe
    *
    * @param   name   recipe's name given by the user
    * 
    * @see domain.Service#findUsersRecipeByName(java.lang.String) 
    * @see domain.Recipe#getInstruction() 
    * 
    * @return Instructions as String without "_"
    */
    public String getRecipeInstructionsByRecipeName(String name) {
        Recipe recipe = findUsersRecipeByName(name);
        if (recipe == null) {
            return null;
        }
        String instructions = recipe.getInstruction();
        instructions = instructions.replace("_", "\n");
        return instructions;
    }
    /**
    * This method is for deleting a recipe
    *
    * @param   name   recipe's name given by the user
    * 
    * @see dao.RecipeDAO#delete(java.lang.String, domain.User) 
    */
    public void deleteRecipe(String name) {
        try {
            this.recipeDAO.delete(name, loggenInUser);
        } catch (Exception e) {
            System.out.println("An exception occurred in deleting a recipe: " + e.getMessage());
        }
    }
    /**
    * This method is for creating a new user with a unique username
    *
    * @param   username   username of the new user
    * @param   password   password of the new user
    * 
    * @see dao.UserDAO#searchByUsername(java.lang.String)
    * @see dao.UserDAO#create(domain.User) 
    * 
    * @return true - if creating a user works
    */
    public boolean createNewUser(String username, String password) {
        if (!username.isEmpty() && !password.isEmpty() && !username.contains(";") && !password.contains(";") && userDao.searchByUsername(username) == null) {
            User user = new User(username, password);
            try {
                userDao.create(user);
            } catch (Exception e) {
                System.out.println("An exception occurred in creating a new user: " + e.getMessage());
                return false;
            }
            return true;
        }
        return false;
    }
    /**
    * This method is for login.
    * It sets a value for the loggedInUser
    *
    * @param   username   user's username
    * @param   password   user's password
    * 
    * @see dao.UserDAO#searchByUsername(java.lang.String) 
    * @see domain.User#getPassword() 
    * 
    * @return true - if login works (=user exists)
    */
    public boolean logIn(String username, String password) {
        User user = userDao.searchByUsername(username);
        if (user != null) {
            if (user.getPassword().equals(password)) {
                this.loggenInUser = user;
                return true;
            }
        } 
        return false;
    }
     /**
    * This method returns logged in user
    * 
    * @return User that is currently logged in
    */
    public User getLoggenInUser() {
        return loggenInUser;
    }
     /**
    * This method is for loging out
    */
    public void logOut() {
        this.loggenInUser = null;
    }
    /**
    * This method is for deleting the loggedIn user and all their recipes
    *
    * @see dao.DerbyUserDAO#delete(domain.User) 
    * @see dao.DerbyRecipeDAO#delete(domain.User) 
    * @see domain.Service#logOut() 
    * 
    * @return true - if deleting user and recipes works
    */
    public boolean deleteAccount() {
        try {
            userDao.delete(this.loggenInUser);
            this.recipeDAO.delete(loggenInUser);
        } catch (Exception e) {
            System.out.println("An exception occurred in deletin an account: " + e.getMessage());
            return false;
        }
        logOut();
        return true;
    }
    /**
    * This method is for listing the names of the loggedInUser's recipes
    *
    * @see dao.DerbyRecipeDAO#listUsersAll(domain.User) 
    * 
    * @return List of the recipe names
    */
    public List<String> userRecipeNames() {
        List<Recipe> recipes = this.recipeDAO.listUsersAll(loggenInUser);
        
        List<String> recipeNames = recipes.stream().map(r -> r.getName()).collect(Collectors.toList());
        return recipeNames;
    }
}
