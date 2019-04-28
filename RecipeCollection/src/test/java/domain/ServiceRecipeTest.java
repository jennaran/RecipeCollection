
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
    /**
    * Tests that user can search their recipe by its name
    *
    * @see domain.Service#findUsersRecipeByName(java.lang.String) 
    */
    @Test
    public void findsUsersExistingRecipe() {
        Recipe newRecipe = this.service.findUsersRecipeByName("Testipiirakka");
        Assert.assertTrue(recipe1.getUser().equals(newRecipe.getUser()) && newRecipe.getUser().equals(recipe1.getUser()));
        assertEquals(recipe1.getIngredientsString(), newRecipe.getIngredientsString());
        assertEquals(recipe1.getInstruction(), newRecipe.getInstruction());
        assertEquals(recipe1.getName(), newRecipe.getName());
    }
    /**
    * Tests that searching for a non-existing recipe returns null
    *
    * @see domain.Service#findUsersRecipeByName(java.lang.String) 
    */
    @Test
    public void doesNotFindUsersNonExistingRecipe() {
        Recipe newRecipe = this.service.findUsersRecipeByName("Suklaakakku");
        assertEquals(null, newRecipe);
    }
    /**
    * Tests that getRecipeIngredienstByRecipeName works correctly - lists existing recipe's ingredients
    *
    * @see domain.Service#getRecipeIngredienstByRecipeName(java.lang.String)  
    */
    @Test
    public void returnsRecipesIngrediensCorrectly() {
        List<String> newIngredients = this.service.getRecipeIngredienstByRecipeName("Testipiirakka");
        String newResult = "";
        newResult = newIngredients.stream().map((instruction) -> instruction + "_").reduce(newResult, String::concat);
	newResult = newResult.substring(0, newResult.length() - 1);
        
        assertEquals(recipe1.getIngredientsString(), newResult);
    }
    /**
    * Tests that getRecipeIngredienstByRecipeName works correctly - doesn't list non-existing recipe's ingredients
    *
    * @see domain.Service#getRecipeIngredienstByRecipeName(java.lang.String)  
    */
    @Test
    public void doesNotListNonExistingRecipesIngredients() {
        List<String> newIngredients = this.service.getRecipeIngredienstByRecipeName("Suklaakakku");
        assertEquals(null, newIngredients);
    }
    /**
    * Tests that getRecipeInstructionsByRecipeName works correctly - lists existing recipe's instructions
    *
    * @see domain.Service#getRecipeInstructionsByRecipeName(java.lang.String)   
    */
    @Test
    public void returnsRecipesInstructionsCorrectly() {
        String newInstructions = this.service.getRecipeInstructionsByRecipeName("Testipiirakka");
        newInstructions = newInstructions.replace("\n", "_");
        
        assertEquals(recipe1.getInstruction(), newInstructions);
    }
    /**
    * Tests that getRecipeInstructionsByRecipeName works correctly - doesn't list non-existing recipe's instructions
    *
    * @see domain.Service#getRecipeInstructionsByRecipeName(java.lang.String)   
    */
    @Test
    public void doesNotListNonExistingRecipesInstructions() {
        String newInstructions = this.service.getRecipeInstructionsByRecipeName("Suklaakakku");
        assertEquals(null, newInstructions);
    }
    /**
    * Tests that userRecipeNames works correctly - lists all user recipe's names
    *
    * @see domain.Service#userRecipeNames()    
    */
    @Test
    public void listUsersAll_Recipes() {
        List<String> recipes = this.service.userRecipeNames();
        assertTrue(recipes.size() == 2);
        assertEquals("Testipiirakka", recipes.get(0));
        assertEquals("Testileivos", recipes.get(1));
    }
    /**
    * Tests that userRecipeNames works correctly - returns an empty list if user has no recipes
    *
    * @see domain.Service#userRecipeNames()    
    */
    @Test
    public void listUsersAll_NoRecipes() {
        this.service.logOut();
        this.service.logIn("user2", "user2");
        List<String> recipes = this.service.userRecipeNames();
        assertTrue(recipes.isEmpty());
    }
    /**
    * Tests that createNewRecipe works correctly - creates a recipe with unique name
    *
    * @see domain.Service#createNewRecipe(java.lang.String, java.util.List, java.lang.String)    
    */
    @Test
    public void createANewRecipe() {
        List<String> ingredients = new ArrayList<>();
        ingredients.add("cake");
        ingredients.add("berries");
        ingredients.add("salt");
        
        String instruction = "bake_decorate_season";
        assertTrue(this.service.createNewRecipe("Koekakku", ingredients, instruction));
    }
    /**
    * Tests that createNewRecipe works correctly - doesn't create a recipe with already existing name
    *
    * @see domain.Service#createNewRecipe(java.lang.String, java.util.List, java.lang.String)    
    */
    @Test
    public void doesNotCreateRecipeWithExistingName() {
        List<String> ingredients = new ArrayList<>();
        ingredients.add("cake");
        ingredients.add("berries");
        ingredients.add("salt");
        
        String instruction = "bake_decorate_season";
        assertFalse(this.service.createNewRecipe("Testipiirakka", ingredients, instruction));
    }
    /**
    * Tests that deleteRecipe works correctly - deletes user's recipe
    *
    * @throws java.lang.Exception
    * @see domain.Service#deleteRecipe(java.lang.String)     
    */
    @Test
    public void deletingRecipeWorks() throws Exception {
        this.service.deleteRecipe(recipe1.getName());
        
        List<String> recipes = this.service.userRecipeNames();
        assertTrue(recipes.size() == 1);
        assertEquals("Testileivos", recipes.get(0));
    }
    /**
    * Tests that update works correctly - updates existing recipe with no name changes
    *
    * @throws java.lang.Exception
    * @see domain.Service#update(java.lang.String, java.lang.String, java.util.List, java.lang.String)     
    */
    @Test
    public void updateWithOldNameOK() throws Exception {
        this.service.logIn("test1", "test1");
        List<String> ingredients = new ArrayList<>();
        ingredients.add("cake");
        ingredients.add("berries");
        ingredients.add("salt");
        
        String instruction = "bake_decorate_season";
        
        assertTrue(this.service.update(recipe1.getName(), recipe1.getName(), ingredients, instruction));
        List<String> recipes = this.service.userRecipeNames();
        assertTrue(recipes.size() == 2);
        assertEquals("Testileivos", recipes.get(0));
        assertEquals(recipe1.getName(), recipes.get(1));
    }
    /**
    * Tests that update works correctly 
    * - updates existing recipe works with a name change to an unique name
    *
    * @throws java.lang.Exception
    * @see domain.Service#update(java.lang.String, java.lang.String, java.util.List, java.lang.String)     
    */
    @Test
    public void updateWithNewUniqueNameOK() throws Exception {
        this.service.logIn("test1", "test1");
        List<String> ingredients = new ArrayList<>();
        ingredients.add("cake");
        ingredients.add("berries");
        ingredients.add("salt");
        
        String instruction = "bake_decorate_season";
        
        assertTrue(this.service.update(recipe1.getName(), "Uniikkinimi", ingredients, instruction));
        List<String> recipes = this.service.userRecipeNames();
        assertTrue(recipes.size() == 2);
        assertEquals("Testileivos", recipes.get(0));
        assertEquals("Uniikkinimi", recipes.get(1));
    }
    /**
    * Tests that update works correctly 
    * - doesn't update existing recipe's name to already existing name
    *
    * @throws java.lang.Exception
    * @see domain.Service#update(java.lang.String, java.lang.String, java.util.List, java.lang.String)     
    */
    @Test
    public void updateWithNotUniqueNameNotOK() throws Exception {
        this.service.logIn("test1", "test1");
        List<String> ingredients = new ArrayList<>();
        ingredients.add("cake");
        ingredients.add("berries");
        ingredients.add("salt");
        
        String instruction = "bake_decorate_season";
        
        assertFalse(this.service.update(recipe1.getName(), "Testileivos", ingredients, instruction));
        List<String> recipes = this.service.userRecipeNames();
        assertTrue(recipes.size() == 2);
        assertEquals("Testipiirakka", recipes.get(0));
        assertEquals("Testileivos", recipes.get(1));
    }
}
