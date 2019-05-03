
package domain;

import java.util.Objects;
/**
 * This is a class for users
 */
public class User {
    private final String username;
    private final String password;
    /**
     * Sets constructors
     * 
     * @param username user's username
     * @param password user's password
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
    /**
    * Returns user's password
    * 
    * @return User's password
    */
    public String getPassword() {
        return password;
    }
    /**
    * Returns user's username
    * 
    * @return User's username
    */
    public String getUsername() {
        return username;
    }    
    /**
    * Generates hashCode for users
    * 
    * @return Generated hashCode
    */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.username);
        hash = 53 * hash + Objects.hashCode(this.password);
        return hash;
    }
    /**
    * Method for comparing user's
    * 
    * @return True - if user equals given object. False - if not.
    */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        if (!Objects.equals(this.username, other.username)) {
            return false;
        }
        if (!Objects.equals(this.password, other.password)) {
            return false;
        }
        return true;
    }

}
