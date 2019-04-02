
package dao;

import domain.Recipe;
import java.sql.SQLException;
import java.util.List;

public interface RecipeDAO {
    void create(Recipe object) throws SQLException;
    Recipe searchByKey(Integer key) throws SQLException;
    boolean update(Recipe object) throws SQLException;
    void delete(Integer key) throws SQLException;
    List<Recipe> listAll() throws SQLException;
}
