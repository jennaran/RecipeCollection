
package domain;
    
import dao.RecipeDAO;
import dao.UserDAO;
import java.sql.*;
import java.util.List;
import java.util.stream.Collectors;
public class Service {
    //tarvitaanko @Autowired DerbyRecipeDAO;...;
    //voiko DB browsetilla tehdä tietokannan
    //kutsutaanko   Connection connection = DriverManager.getConnection("jdbc:sqlite:testi.db");   täällä vai ui:ssa?
    /*Kun NetBeans-projektista valitsee oikealla hiirennapilla 
    Dependencies ja klikkaa Download Declared Dependencies, 
    latautuu JDBC-ajuri projektin käyttöön. ????*/
    
    //tarvitaanko listat jotka sisältää kaikki käyttäjät ja reseptit?
    
    /*public static void main(String[] args) throws Exception {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:RecipeUser.db");

        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("SELECT 1");

        if (resultSet.next()) {
            System.out.println("Hei tietokantamaailma!");
        } else {
            System.out.println("Yhteyden muodostaminen epäonnistui.");
        }
    }*/
    
    private UserDAO userDao;
    private RecipeDAO recipeDAO;
    private User loggenInUser;

    public Service(UserDAO userDao, RecipeDAO recipeDAO) {
        this.userDao = userDao;
        this.recipeDAO = recipeDAO;
    }
    
    public boolean createNewRecipe(String name, String ingredients, String instruction) {
        Recipe recipe = new Recipe(name, loggenInUser);
        recipe.setIngredients(ingredients);
        recipe.setInstruction(instruction);
        try {
            recipeDAO.create(recipe);
        } catch (Exception e) {
            return false;
        }
        return true;
    } 
    
    public boolean createNewUser(String username, String password) throws Exception {
        if (userDao.searchByUsername(username) != null) {
            return false;
        }
        User user = new User(username, password);
        try {
            userDao.create(user);
        } catch (Exception e) {
            return false;
        }
        return true;
    } 
    
    public boolean logIn(String username, String password) throws Exception {
        User user = userDao.searchByUsername(username);
        if (user != null) {
            if (user.getPassword().equals(password)) {
                this.loggenInUser = user;
                return true;
            }
        } 
        return false;
    }
    
    public User getLoggenInUser() {
        return loggenInUser;
    }
     
    public void logOut() {
        this.loggenInUser = null;
    }
    
    public void deleteAccount() throws Exception {
        userDao.delete(this.loggenInUser);
        this.recipeDAO.delete(loggenInUser);
        logOut();
    }
    
    public List<String> userRecipeNames() throws Exception {
        List<Recipe> recipes = this.recipeDAO.listUsersAll(loggenInUser);
        List<String> recipeNames = recipes.stream().map(r -> r.getName()).collect(Collectors.toList());
        return recipeNames;
    }
    
}
