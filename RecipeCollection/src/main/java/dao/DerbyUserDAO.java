
package dao;

import domain.User;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DerbyUserDAO implements UserDAO {
    private List<User> users;
    private String userFile;
    
    public DerbyUserDAO(String file) throws Exception {
        users = new ArrayList<>();
        this.userFile = file;
        try {
            Scanner reader = new Scanner(new File(file));
            while (reader.hasNextLine()) {
                String[] parts = reader.nextLine().split(";");
                //name, username, password
                User user = new User(parts[0], parts[1]);
                users.add(user);
            }
        } catch (Exception e) {
            FileWriter writer = new FileWriter(new File(file));
            writer.close();
        }
    }
    

    @Override
    public void create(User newUser) throws Exception {
            users.add(newUser);
            saveToFile();
    }

    @Override
    public User searchByUsername(String username) {
        User user = users.stream().filter(u -> u.getUsername().equals(username)).findFirst().orElse(null);
        return user;
    }

    @Override
    public void delete(User user) throws Exception {
        this.users.remove(user);
        saveToFile();
    }

    @Override
    public List<User> listAll(){
        return this.users;
    }
    
    public void saveToFile() throws Exception {
        try (FileWriter writer = new FileWriter(new File(userFile))) {
            for (User u : users) {
                writer.write(u.getUsername() + ";" + u.getPassword() + "\n");
            }
        } catch (Exception e) {
            
        }
    }
    
}
