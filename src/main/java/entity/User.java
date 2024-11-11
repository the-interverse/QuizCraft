package entity;

/**
 * The representation of a user in our program.
 */
public class User {
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
    /**
     * Returns the username of the user.
     * @return the username of the user.
     */
    public String getUsername(){
        return username;
    }

    /**
     * Returns the password of the user.
     * @return the password of the user.
     */
    public String getPassword(){
        return password;
    }
}