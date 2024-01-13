import java.util.Set;

public class User {
    private String username;
    private String password;
    private String ip;

    public User(String username, String password, String ip) {
        this.username = username;
        this.password = password;
        this.ip = ip;

        RequetesJson.ajoutUserJSON(username, password, ip);
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public String getIp() {
        return this.ip;
    }

    public Set<String> getAbonnes() {
        return RequetesJson.getUserFollowers(this.username);
    }

    public Set<String> getAbonnements() {
        return RequetesJson.getUserFollowing(this.username);
    }

    public void follow(String username) {
        RequetesJson.ajoutFollower(this.username, username);
    }

    public void unfollow(String username) {
        RequetesJson.deleteFollower(this.username, username);
    }

    
    
}
