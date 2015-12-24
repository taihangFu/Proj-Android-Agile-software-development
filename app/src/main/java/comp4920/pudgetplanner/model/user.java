package comp4920.pudgetplanner.model;

/**
 * Created by Zaw on 11/10/2015.
 */
public class user {
    private String id;
    private String username;
    private String email;
    private String password;

    public user(){
        id = "";
        username = "";
        email = "";
        password = "";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
