
package dao;

import domain.User;
import java.sql.SQLException;
import java.util.List;

public interface UserDAO {
    void create(User object) throws SQLException;
    User searchByKey(Integer key) throws SQLException;
    boolean update(User object) throws SQLException;
    void delete(Integer key) throws SQLException;
    List<User> listAll() throws SQLException;
}
