
package dao;

import domain.Recipe;
import java.sql.SQLException;
import java.util.List;

public interface RecipeDAO {
    void create(Recipe object) throws Exception;
    Recipe searchByKey(Integer key) throws Exception;
    boolean update(Recipe object) throws Exception;
    void delete(Integer key) throws Exception;
    List<Recipe> listAll() throws Exception;
}
