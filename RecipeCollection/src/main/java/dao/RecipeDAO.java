
package dao;

import domain.Recipe;
import domain.User;
import java.util.List;

public interface RecipeDAO {
    void create(Recipe object) throws Exception;
    boolean update(Recipe object) throws Exception;
    void delete(String name, User user) throws Exception;
    void delete(User user) throws Exception;
    List<Recipe> listUsersAll(User user);
}
