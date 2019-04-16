
package domain;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class ServiceUserTest {
    FakeUserDAO userDAO;
    FakeRecipeDAO recipeDAO;
    Service service;
    
    public ServiceUserTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        this.userDAO = new FakeUserDAO();
        this.recipeDAO = new FakeRecipeDAO();
        this.service = new Service(userDAO, recipeDAO);
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void existingUserCanLogin() throws Exception {
        assertTrue(this.service.logIn("name1", "one"));
        assertEquals("name1", this.service.getLoggenInUser().getUsername());
        
    }
    
    @Test
    public void non_existingUserCanLogin() throws Exception {
        assertFalse(this.service.logIn("test1", "test1"));
        assertEquals(null, this.service.getLoggenInUser());
    }
    
    @Test
    public void createNewUserWhoCanLogin() throws Exception {
        assertTrue(this.service.createNewUser("test1", "test1"));
        assertTrue(this.service.logIn("test1", "test1"));
        assertEquals("test1", this.service.getLoggenInUser().getUsername());
    }
    
    @Test
    public void cantCreateUserWithAlreadyUsedUsername() throws Exception {
        assertFalse(this.service.createNewUser("name1", "tikka"));
        
        assertFalse(this.service.logIn("name1", "tikka"));
        assertEquals(null, this.service.getLoggenInUser());
    }
    
    @Test 
    public void logOut() {
        this.service.logOut();
        assertEquals(null, this.service.getLoggenInUser());
    }
    
    @Test
    public void deleteExistingUser() {
        this.service.logIn("name1", "one");
        assertTrue(this.service.deleteAccount());
        
        assertEquals(null, this.service.getLoggenInUser());
    }
}
