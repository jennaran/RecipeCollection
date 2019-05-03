
package dao;

import domain.User;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

public class DerbyUserDAOTest {
    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    File userFile;  
    UserDAO dao;
    
    public DerbyUserDAOTest() {
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
        
        try (FileWriter fw = new FileWriter(userFile.getAbsolutePath())) {
            fw.write("testUsername;testPassword\n");
        } 
        this.dao = new DerbyUserDAO(userFile.getAbsolutePath());
    }
    
    @After
    public void tearDown() {
        this.userFile.delete();
    }
    /**
    * Tests that users are saved correctly to the file
    * @see dao.UserDAO#listAll() 
    */
    @Test 
    public void usersAreSavedCorrectly() {
        List<User> users = dao.listAll();
        assertEquals(1, users.size());
        User testUser = users.get(0);
        assertEquals("testUsername", testUser.getUsername());
        assertEquals("testPassword", testUser.getPassword());
    }
    /**
    * Tests searchByUsername - existing user is found
    * @see dao.UserDAO#searchByUsername(java.lang.String) 
    */
    @Test
    public void existingUserIsFound() {
        User testUser = dao.searchByUsername("testUsername");
        assertEquals("testUsername", testUser.getUsername());
        assertEquals("testPassword", testUser.getPassword());
    }
    /**
    * Tests searchByUsername - non-existing user is not found
    * @see dao.UserDAO#searchByUsername(java.lang.String) 
    */
    @Test
    public void nonexistingUserIsNotFound() {
        User testUser = dao.searchByUsername("matti");
        assertEquals(null, testUser);
    }
    /**
    * Tests create - user can be created and it's saved to the file correctly
    * @throws java.lang.Exception
    * @see dao.UserDAO#searchByUsername(java.lang.String) 
    * @see dao.UserDAO#create(domain.User) 
    */
    @Test
    public void createdUserIsFound() throws Exception {
        User newUser = new User("TU", "SS");
        dao.create(newUser);
        
        User newUserDao = dao.searchByUsername("TU");
        assertEquals("TU", newUserDao.getUsername());
        assertEquals("SS", newUserDao.getPassword());
    }
    /**
    * Tests delete - 
    * user can be deleted and it can't be found from the file afterwards
    * @throws java.lang.Exception
    * @see dao.UserDAO#searchByUsername(java.lang.String) 
    * @see dao.UserDAO#delete(domain.User) 
    * @see dao.UserDAO#listAll() 
    */
    @Test
    public void deletedUserIsNotFound() throws Exception {
        User testUser = dao.searchByUsername("testUsername");
        dao.delete(testUser);
        List<User> users = dao.listAll();
        assertEquals(0, users.size());
        User deletedUser = dao.searchByUsername("testUsername");
        assertEquals(null, deletedUser);
    }
    /**
    * Tests that FileNotFoundException is thrown if the file does not exist
    * @throws java.lang.Exception
    */
    @Test(expected = FileNotFoundException.class)
    public void throwsExceptionWhenFileNotFound() throws Exception {
        this.dao = new DerbyUserDAO("/var/folders/7v/yz3z403j3fz6_0jts_fqqyd80000gn/T/junit3674697855278835194/fakeFile.txt");
    } 
}
