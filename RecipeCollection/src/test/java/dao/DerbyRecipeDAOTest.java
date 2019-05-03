
package dao;

import domain.FakeUserDAO;
import domain.Recipe;
import domain.User;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

public class DerbyRecipeDAOTest {
    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    File userFile;  
    RecipeDAO dao;
    User user = new User("testName", "testPassword");
    
    public DerbyRecipeDAOTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws Exception {
        userFile = testFolder.newFile("userTestFile.txt");
        UserDAO userDao = new FakeUserDAO();
        userDao.create(user);
        
        try (FileWriter fw = new FileWriter(this.userFile.getAbsolutePath())) {
            fw.write("pasta;sugar_pepper;mix_eat;testName\n");
            fw.write("potatopie;potato_salt;cook_eat;testName\n");
        }
        this.dao = new DerbyRecipeDAO(userDao, userFile.getAbsolutePath());
        
    }
    
    @After
    public void tearDown() {
        this.userFile.delete();
    }
    /**
    * Tests listUsersAll - 
    * lists only one user's recipes
    * @throws java.lang.Exception
    * @see dao.RecipeDAO#create(domain.Recipe) 
    * @see dao.RecipeDAO#listUsersAll(domain.User) 
    */
    @Test
    public void listsOnlyOneUsersRecipes() throws Exception {
        User user2 = new User("UU", "o");
        Recipe recipe1 = new Recipe("Testipiirakka", user2);
        recipe1.setIngredients("test_pie");
        recipe1.setInstruction("mix_eat");
        this.dao.create(recipe1);
        
        List<Recipe> recipes = this.dao.listUsersAll(user);
        assertTrue(recipes.size() == 2);
        assertEquals("pasta", recipes.get(0).getName());
        assertEquals("sugar_pepper", recipes.get(0).getIngredientsString());
        assertEquals("mix_eat", recipes.get(0).getInstruction());
        assertEquals("potatopie", recipes.get(1).getName());
        assertEquals("potato_salt", recipes.get(1).getIngredientsString());
        assertEquals("cook_eat", recipes.get(1).getInstruction());
    }
    /**
    * Tests create - 
    * recipe can be created and it's saved to the file correctly
    * @throws java.lang.Exception
    * @see dao.RecipeDAO#create(domain.Recipe) 
    * @see dao.RecipeDAO#listUsersAll(domain.User) 
    */
    @Test
    public void createRecipeWorks() throws Exception {
        Recipe recipe1 = new Recipe("Testipiirakka", user);
        recipe1.setIngredients("test_pie");
        recipe1.setInstruction("mix_eat");
        
        this.dao.create(recipe1);
        
        List<Recipe> recipes = this.dao.listUsersAll(user);
        assertTrue(recipes.size() == 3);
        assertEquals("Testipiirakka", recipes.get(2).getName());
        assertEquals("test_pie", recipes.get(2).getIngredientsString());
        assertEquals("mix_eat", recipes.get(2).getInstruction());
    }
    /**
    * Tests delete(User) - 
    * when user has recipes, 
    * all user's recipes are deleted
    * @throws java.lang.Exception
    * @see dao.RecipeDAO#create(domain.Recipe) 
    * @see dao.RecipeDAO#delete(domain.User) 
    * @see dao.RecipeDAO#listUsersAll(domain.User) 
    */
    @Test
    public void deleteUsersAll_Recipes() throws Exception {
        User user2 = new User("UU", "o");
        Recipe recipe1 = new Recipe("Testipiirakka", user2);
        recipe1.setIngredients("test_pie");
        recipe1.setInstruction("mix_eat");
        this.dao.create(recipe1);
        
        this.dao.delete(user);
        List<Recipe> recipes = this.dao.listUsersAll(user);
        assertTrue(recipes.isEmpty());
        List<Recipe> recipes2 = this.dao.listUsersAll(user2);
        assertTrue(recipes2.size() == 1);
        assertEquals("Testipiirakka", recipes2.get(0).getName());
    }
    /**
    * Tests delete(User) - 
    * when user has no recipes,
    * nothing is deleted
    * @throws java.lang.Exception
    * @see dao.RecipeDAO#delete(domain.User) 
    * @see dao.RecipeDAO#listUsersAll(domain.User) 
    */
    @Test
    public void deleteUsersAll_NoRecipes() throws Exception {
        User user2 = new User("UU", "o");
        this.dao.delete(user2);
        List<Recipe> recipes = this.dao.listUsersAll(user2);
        assertTrue(recipes.isEmpty());
        List<Recipe> recipes2 = this.dao.listUsersAll(user);
        assertEquals("pasta", recipes2.get(0).getName());
        assertEquals("potatopie", recipes2.get(1).getName());
    }
    /**
    * Tests delete(Recipe, User) - 
    * existing recipe is deleted and changes are saved to the file
    * @throws java.lang.Exception
    * @see dao.RecipeDAO#delete(java.lang.String, domain.User) 
    * @see dao.RecipeDAO#listUsersAll(domain.User) 
    */
    @Test
    public void deleteRecipe_Recipe() throws Exception {
        dao.delete("pasta", user);
        
        List<Recipe> recipes = this.dao.listUsersAll(user);
        assertTrue(recipes.size() == 1);
        assertEquals("potatopie", recipes.get(0).getName());
    }
    /**
    * Tests delete(Recipe, User) - 
    * deleting non-existing recipe does nothing
    * @throws java.lang.Exception
    * @see dao.RecipeDAO#delete(java.lang.String, domain.User) 
    * @see dao.RecipeDAO#listUsersAll(domain.User) 
    */
    @Test
    public void deleteRecipe_NoRecipe() throws Exception {
        dao.delete("Testipiirakka", user);
        
        List<Recipe> recipes = this.dao.listUsersAll(user);
        assertTrue(recipes.size() == 2);
        assertEquals("pasta", recipes.get(0).getName());
        assertEquals("potatopie", recipes.get(1).getName());
    }
    /**
    * Tests delete(Recipe, User) - 
    * method deletes only given user's recipes
    * @throws java.lang.Exception
    * @see dao.RecipeDAO#create(domain.Recipe) 
    * @see dao.RecipeDAO#delete(java.lang.String, domain.User) 
    * @see dao.RecipeDAO#listUsersAll(domain.User) 
    */
    @Test
    public void doesNotDeleteOtherUsersRecipes() throws Exception {
        User user2 = new User("UU", "o");
        Recipe recipe1 = new Recipe("Testipiirakka", user2);
        recipe1.setIngredients("test_pie");
        recipe1.setInstruction("mix_eat");
        this.dao.create(recipe1);
        
        dao.delete("Testipiirakka", user);
        
        List<Recipe> recipes = this.dao.listUsersAll(user2);
        assertTrue(recipes.size() == 1);
        assertEquals("Testipiirakka", recipes.get(0).getName());
    }
    /**
    * Tests that FileNotFoundException is thrown if the file does not exist
    * @throws java.lang.Exception
    */
    @Test(expected = FileNotFoundException.class)
    public void throwsExceptionWhenFileNotFound() throws Exception {
        UserDAO userDao = new FakeUserDAO();
        this.dao = new DerbyRecipeDAO(userDao, "/var/folders/7v/yz3z403j3fz6_0jts_fqqyd80000gn/T/junit3674697855278835194/fakeFile.txt");
    } 
}
