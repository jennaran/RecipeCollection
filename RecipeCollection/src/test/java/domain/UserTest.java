
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
    
    @Test
    public void usernameIsSetCorrectly() {
        assertEquals("username", user.getUsername());
    }
    
    @Test
    public void passwordIsSetCorrectly() {
        assertEquals("password", user.getPassword());
    }
    
    @Test
    public void testEquals_Symmetric() {
        User sameUser = new User("username", "password");
        assertTrue(user.equals(sameUser) && sameUser.equals(user));
        assertTrue(user.hashCode() == sameUser.hashCode());
    }
    
    @Test
    public void testEquals_DifferendUsernames() {
        User user2 = new User("user", "password");
        assertNotSame(user, user2); 
        assertNotEquals(user.hashCode(), user2.hashCode());
    }
    @Test
    public void testEquals_SameAndSame() {
        assertTrue(user.equals(user) && user.equals(user));
        assertTrue(user.hashCode() == user.hashCode());
    }
    @Test
    public void testEquals_WithNull() {
        User nullUser = null;
        assertNotSame(user, nullUser);
        assertFalse(user.equals(nullUser) && nullUser.equals(user));
    }
    
    @Test
    public void testEquals_WithWrongObject() {
        Recipe recipe = new Recipe("name", user);
        assertNotSame(user, recipe); 
        assertFalse(user.equals(recipe) && recipe.equals(user));

    }
    @Test
    public void testEquals_DifferendPasswords() {
        User user2 = new User("username", "wordpassu");
        assertNotSame(user, user2); 
        assertNotEquals(user.hashCode(), user2.hashCode());
        assertFalse(user.equals(user2) && user2.equals(user));

    }
}
