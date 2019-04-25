
package domain;

import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class ServiceRecipeTest {
    FakeUserDAO userDAO;
    FakeRecipeDAO recipeDAO;
    Service service;
    
    User user1;
    User user2;
    Recipe recipe1;
    
    public ServiceRecipeTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws Exception {
        this.userDAO = new FakeUserDAO();
        this.recipeDAO = new FakeRecipeDAO();
        
        this.user1 = new User("test1", "test1");
        this.user2 = new User("user2", "user2");
        userDAO.create(user1);
        userDAO.create(user2);
        
        this.recipe1 = new Recipe("Testipiirakka", user1);
        recipe1.setIngredients("test_pie");
        recipe1.setInstruction("mix_eat");
        recipeDAO.create(recipe1);
        
        Recipe recipe2 = new Recipe("Testileivos", user1);
        recipe2.setIngredients("test_leivos");
        recipe2.setInstruction("mix_eat");
        recipeDAO.create(recipe2);
        
        this.service = new Service(userDAO, recipeDAO);
        this.service.logIn("test1", "test1");
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void findsUsersExistingRecipe() {
        Recipe newRecipe = this.service.findUsersRecipeByName("Testipiirakka");
        Assert.assertTrue(recipe1.getUser().equals(newRecipe.getUser()) && newRecipe.getUser().equals(recipe1.getUser()));
        assertEquals(recipe1.getIngredientsString(), newRecipe.getIngredientsString());
        assertEquals(recipe1.getInstruction(), newRecipe.getInstruction());
        assertEquals(recipe1.getName(), newRecipe.getName());
    }
    
    @Test
    public void doesNotFindUsersNonExistingRecipe() {
        Recipe newRecipe = this.service.findUsersRecipeByName("Suklaakakku");
        assertEquals(null, newRecipe);
    }
    
    @Test
    public void returnsRecipesIngrediensCorrectly() {
        List<String> newIngredients = this.service.getRecipeIngredienstByRecipeName("Testipiirakka");
        String newResult = "";
        newResult = newIngredients.stream().map((instruction) -> instruction + "_").reduce(newResult, String::concat);
	newResult = newResult.substring(0, newResult.length() - 1);
        
        assertEquals(recipe1.getIngredientsString(), newResult);
    }
    
    @Test
    public void doesNotListNonExistingRecipesIngredients() {
        List<String> newIngredients = this.service.getRecipeIngredienstByRecipeName("Suklaakakku");
        assertEquals(null, newIngredients);
    }
    
    @Test
    public void returnsRecipesInstructionsCorrectly() {
        String newInstructions = this.service.getRecipeInstructionsByRecipeName("Testipiirakka");
        newInstructions = newInstructions.replace("\n", "_");
        
        assertEquals(recipe1.getInstruction(), newInstructions);
    }
    
    @Test
    public void doesNotListNonExistingRecipesInstructions() {
        String newInstructions = this.service.getRecipeInstructionsByRecipeName("Suklaakakku");
        assertEquals(null, newInstructions);
    }
    
    @Test
    public void listUsersAllRecipes() {
        List<String> recipes = this.service.userRecipeNames();
        assertTrue(recipes.size() == 2);
        assertEquals("Testipiirakka", recipes.get(0));
        assertEquals("Testileivos", recipes.get(1));
    }
    
    @Test
    public void listUsersAllNoRecipes() {
        this.service.logOut();
        this.service.logIn("user2", "user2");
        List<String> recipes = this.service.userRecipeNames();
        assertTrue(recipes.isEmpty());
    }
    
    @Test
    public void createANewRecipe() {
        List<String> ingredients = new ArrayList<>();
        ingredients.add("cake");
        ingredients.add("berries");
        ingredients.add("salt");
        
        String instruction = "bake_decorate_season";
        assertTrue(this.service.createNewRecipe("Koekakku", ingredients, instruction));
    }
}
