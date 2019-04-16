
package domain;

import dao.UserDAO;
import java.util.ArrayList;
import java.util.List;

public class FakeUserDAO implements UserDAO {
    List<User> users = new ArrayList<>();

    public FakeUserDAO() {
        users.add(new User("name1", "one"));
    }
    
    @Override
    public void create(User newUser) throws Exception {
        users.add(newUser);
    }

    @Override
    public User searchByUsername(String username) {
        User user = users.stream().filter(u -> u.getUsername().equals(username)).findFirst().orElse(null);
        return user;    
    }

    @Override
    public void delete(User user) {
        this.users.remove(user);
    }

    @Override
    public List<User> listAll() {
        return this.users;
    }
    
}
