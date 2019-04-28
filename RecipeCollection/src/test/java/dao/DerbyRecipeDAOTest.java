
package dao;

import domain.FakeUserDAO;
import domain.Recipe;
import domain.User;
import java.io.File;
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
    
    @Test
    public void deleteRecipe_Recipe() throws Exception {
        dao.delete("pasta", user);
        
        List<Recipe> recipes = this.dao.listUsersAll(user);
        assertTrue(recipes.size() == 1);
        assertEquals("potatopie", recipes.get(0).getName());
    }
    
    @Test
    public void deleteRecipe_NoRecipe() throws Exception {
        dao.delete("Testipiirakka", user);
        
        List<Recipe> recipes = this.dao.listUsersAll(user);
        assertTrue(recipes.size() == 2);
        assertEquals("pasta", recipes.get(0).getName());
        assertEquals("potatopie", recipes.get(1).getName());
    }
    
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
}
