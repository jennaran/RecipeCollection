
package domain;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class RecipeTest {
    Recipe recipe;
    Recipe noId;
    User user;
    public RecipeTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        user = new User("username", "password");
        noId = new Recipe("pie", user);
        recipe = new Recipe(1, "cheesecake", user);
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void constructorsWork() {
        assertTrue(1 == recipe.getUniqueID());
        Assert.assertTrue(recipe.getUser().equals(user) && user.equals(recipe.getUser()));
        Assert.assertTrue(noId.getUser().equals(user) && user.equals(noId.getUser()));
        assertEquals("cheesecake", recipe.getName());
        assertEquals("pie", noId.getName());
    }
    
    @Test
    public void setIdWorks() {
        noId.setUniqueID(2);
        assertTrue(2 == noId.getUniqueID());
    }
    
    @Test
    public void settingAndGettingIngredientsWorks() {
        String ingredients = "cheese_sugar_cake";
        recipe.setIngredients(ingredients);
        assertEquals(ingredients, recipe.getIngredientsString());
    }
    
    @Test
    public void settingAndGettingInstructionsWorks() {
        String instructions = "mix_bake_enjoy";
        recipe.setInstruction(instructions);
        assertEquals(instructions, recipe.getInstruction());
    }
    
    
}
