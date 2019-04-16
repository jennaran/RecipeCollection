
package dao;

import domain.FakeUserDAO;
import domain.Recipe;
import domain.User;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
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
            fw.write("1;pasta;sugar_pepper;mix_eat;testName\n");
            fw.write("2;potatopie;potato_salt;cook_eat;testName\n");
            fw.write("3;salt;salt;mix;name\n");
        }
        this.dao = new DerbyRecipeDAO(userDao, userFile.getAbsolutePath());
        
    }
    
    @After
    public void tearDown() {
        this.userFile.delete();
    }
    
    @Test
    public void createWorks() {
        //pitää eka päättää recipe-luokan tallennus muoto!!
    }
    
    
}
