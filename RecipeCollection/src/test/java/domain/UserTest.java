
package domain;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class UserTest {
    User user;
    
    public UserTest() {
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
    }
    
    @After
    public void tearDown() {
    }
    /**
    * Tests that constructors are set correctly 
    * - username is set correctly
    */
    @Test
    public void usernameIsSetCorrectly() {
        assertEquals("username", user.getUsername());
    }
    /**
    * Tests that constructors are set correctly 
    * - password is set correctly
    */
    @Test
    public void passwordIsSetCorrectly() {
        assertEquals("password", user.getPassword());
    }
    /**
    * Tests that equal works correctly 
    * - notices user equals to similar user
    *
    * @see domain.User#equals(java.lang.Object)    
    */
    @Test
    public void testEquals_Symmetric() {
        User sameUser = new User("username", "password");
        assertTrue(user.equals(sameUser) && sameUser.equals(user));
        assertTrue(user.hashCode() == sameUser.hashCode());
    }
    /**
    * Tests that equal works correctly 
    * - notices user equals to same user
    *
    * @see domain.User#equals(java.lang.Object)    
    */
    @Test
    public void testEquals_SameAndSame() {
        assertTrue(user.equals(user) && user.equals(user));
        assertTrue(user.hashCode() == user.hashCode());
    }
   /**
    * Tests that equal works correctly 
    * - notices user doesn't equal to null
    *
    * @see domain.User#equals(java.lang.Object)    
    */
    @Test
    public void testEquals_WithNull() {
        User nullUser = null;
        assertNotSame(user, nullUser);
        assertFalse(user.equals(nullUser) && nullUser.equals(user));
    }
    /**
    * Tests that equal works correctly 
    * - notices difference in object
    *
    * @see domain.User#equals(java.lang.Object)    
    */
    @Test
    public void testEquals_WithWrongObject() {
        Recipe recipe = new Recipe("name", user);
        assertNotSame(user, recipe); 
        assertFalse(user.equals(recipe) && recipe.equals(user));
    }
    /**
    * Tests that equal works correctly 
    * - notices difference in username
    *
    * @see domain.User#equals(java.lang.Object)    
    */
    @Test
    public void testEquals_DifferendUsernames() {
        User user2 = new User("user", "password");
        assertNotSame(user, user2); 
        assertNotEquals(user.hashCode(), user2.hashCode());
        assertFalse(user.equals(user2) && user2.equals(user));
    }
    /**
    * Tests that equal works correctly 
    * - notices difference in password
    *
    * @see domain.User#equals(java.lang.Object)    
    */
    @Test
    public void testEquals_DifferendPasswords() {
        User user2 = new User("username", "wordpassu");
        assertNotSame(user, user2); 
        assertNotEquals(user.hashCode(), user2.hashCode());
        assertFalse(user.equals(user2) && user2.equals(user));

    }
}
