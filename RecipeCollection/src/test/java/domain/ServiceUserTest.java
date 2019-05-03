
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
    /**
    * Tests loging in with existing user
    *
    * @throws java.lang.Exception
    * 
    * @see domain.Service#logIn(java.lang.String, java.lang.String) 
    * @see domain.Service#getLoggenInUser() 
    */
    @Test
    public void existingUserCanLogin() throws Exception {
        assertTrue(this.service.logIn("name1", "one"));
        assertEquals("name1", this.service.getLoggenInUser().getUsername());
        
    }
    /**
    * Tests loging in with non-existing user
    *
    * @throws java.lang.Exception
    * 
    * @see domain.Service#logIn(java.lang.String, java.lang.String) 
    * @see domain.Service#getLoggenInUser() 
    */
    @Test
    public void non_existingUserCanNotLogin() throws Exception {
        assertFalse(this.service.logIn("test1", "test1"));
        assertEquals(null, this.service.getLoggenInUser());
    }
    /**
    * Tests creating a new user with unique name works
    *
    * @throws java.lang.Exception
    * 
    * @see domain.Service#createNewUser(java.lang.String, java.lang.String)  
    * @see domain.Service#logIn(java.lang.String, java.lang.String) 
    * @see domain.Service#getLoggenInUser() 
    */
    @Test
    public void createNewUserWhoCanLogin() throws Exception {
        assertTrue(this.service.createNewUser("test1", "test1"));
        assertTrue(this.service.logIn("test1", "test1"));
        assertEquals("test1", this.service.getLoggenInUser().getUsername());
    }
    /**
    * Tests creating a new user with already existing name doesn't work
    *
    * @throws java.lang.Exception
    * 
    * @see domain.Service#createNewUser(java.lang.String, java.lang.String)  
    * @see domain.Service#logIn(java.lang.String, java.lang.String) 
    * @see domain.Service#getLoggenInUser() 
    */
    @Test
    public void canNotCreateUserWithAlreadyUsedUsername() throws Exception {
        assertFalse(this.service.createNewUser("name1", "tikka"));
        
        assertFalse(this.service.logIn("name1", "tikka"));
        assertEquals(null, this.service.getLoggenInUser());
    }
    /**
    * Tests creating a new user with no username doesn't work
    *
    * @throws java.lang.Exception
    * 
    * @see domain.Service#createNewUser(java.lang.String, java.lang.String)  
    * @see domain.Service#logIn(java.lang.String, java.lang.String) 
    * @see domain.Service#getLoggenInUser() 
    */
    @Test
    public void canNotCreateUserWithNoUsername() throws Exception {
        assertFalse(this.service.createNewUser("", "test"));
        assertFalse(this.service.logIn("", "test"));
        assertEquals(null, this.service.getLoggenInUser());
    }
    /**
    * Tests creating a new user with no password doesn't work
    *
    * @throws java.lang.Exception
    * 
    * @see domain.Service#createNewUser(java.lang.String, java.lang.String)  
    * @see domain.Service#logIn(java.lang.String, java.lang.String) 
    * @see domain.Service#getLoggenInUser() 
    */
    @Test
    public void canNotCreateUserWithNoPassword() throws Exception {
        assertFalse(this.service.createNewUser("test", ""));
        assertFalse(this.service.logIn("test", ""));
        assertEquals(null, this.service.getLoggenInUser());
    }
    /**
    * Tests creating a new user with illegal mark ";" in username doesn't work
    *
    * @throws java.lang.Exception
    * 
    * @see domain.Service#createNewUser(java.lang.String, java.lang.String)  
    * @see domain.Service#logIn(java.lang.String, java.lang.String) 
    * @see domain.Service#getLoggenInUser() 
    */
    @Test
    public void canNotCreateUserWithIllegalMarksInUsername() throws Exception {
        assertFalse(this.service.createNewUser("test;", "test"));
        assertFalse(this.service.logIn("test;", "test"));
        assertEquals(null, this.service.getLoggenInUser());
    }
    /**
    * Tests creating a new user with illegal mark ";" in password doesn't work
    *
    * @throws java.lang.Exception
    * 
    * @see domain.Service#createNewUser(java.lang.String, java.lang.String)  
    * @see domain.Service#logIn(java.lang.String, java.lang.String) 
    * @see domain.Service#getLoggenInUser() 
    */
    @Test
    public void canNotCreateUserWithIllegalMarksInPassword() throws Exception {
        assertFalse(this.service.createNewUser("test", "te;st"));
        assertFalse(this.service.logIn("test", "te;st"));
        assertEquals(null, this.service.getLoggenInUser());
    }
    /**
    * Tests logging out
    *
    * @see domain.Service#logOut() 
    * @see domain.Service#getLoggenInUser() 
    */
    @Test 
    public void logOut() {
        this.service.logOut();
        assertEquals(null, this.service.getLoggenInUser());
    }
    /**
    * Tests deleting an existing user
    * 
    * @see domain.Service#createNewUser(java.lang.String, java.lang.String)  
    * @see domain.Service#logIn(java.lang.String, java.lang.String) 
    * @see domain.Service#getLoggenInUser() 
    */
    @Test
    public void deleteExistingUser() {
        this.service.logIn("name1", "one");
        assertTrue(this.service.deleteAccount());
        
        assertEquals(null, this.service.getLoggenInUser());
    }
}
