
package dao;

import domain.User;
import java.sql.SQLException;
import java.util.List;

public interface UserDAO {
    void create(User object) throws Exception;
    User searchByUsername(String username) throws Exception;
    boolean update(User object) throws Exception;
    void delete(User user) throws Exception;
    List<User> listAll() throws Exception;
    public void saveToFile() throws Exception;

}