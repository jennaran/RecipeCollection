
package dao;

import domain.User;
import java.util.List;

public interface UserDAO {
    void create(User object) throws Exception;
    User searchByUsername(String username);
    void delete(User user) throws Exception;
    List<User> listAll();
}
