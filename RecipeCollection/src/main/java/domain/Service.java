
package domain;
    
import java.sql.*;
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
    
    

}
