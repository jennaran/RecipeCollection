
package dao;

import domain.User;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DerbyUserDAO implements UserDAO{
    private List<User> users;
    private String file;
    
    public DerbyUserDAO(String file) throws Exception{
        users = new ArrayList<>();
        this.file = file;
        try {
            Scanner reader = new Scanner(new File(file));
            while (reader.hasNextLine()) {
                String[] parts = reader.nextLine().split(";");
                //name, username, password
                User user = new User(parts[0], parts[1], parts[2]);
                users.add(user);
            }
        } catch (Exception e) {
            FileWriter writer = new FileWriter(new File(file));
            writer.close();
        }
    }
    

    @Override
    public void create(User object) throws Exception {
        try (FileWriter fw = new FileWriter(new File(this.file))) {
            for (User user : users) {
                fw.write(user.getName() + ";" + user.getUsername() + ";" + user.getPassword() + "\n");
            }
        }
    }

    @Override
    public User searchByUsername(String username) throws Exception {
        User user = users.stream().filter(u -> u.getUsername().equals(username)).findFirst().orElse(null);
        return user;
    }

    @Override
    public boolean update(User object) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Integer key) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<User> listAll() throws Exception {
        return this.users;
    }
    
}
