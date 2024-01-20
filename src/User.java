import java.util.Set;

public class User {
    private String username;
    private String ip;

    /*
     * Constructeur de la classe User
     * @param username : nom d'utilisateur
     * @param ip : adresse IP de l'utilisateur
     */
    public User(String username, String ip) {
        this.username = username;
        this.ip = ip;

        RequetesJson.ajoutUserJSON(username, ip);
    }

    /*
     * @getter
     */
    public String getUsername() {
        return this.username;
    }

    public String getIp() {
        return this.ip;
    }

    /*
     * Récupère les abonnés de l'utilisateur
     */
    public Set<String> getAbonnes() {
        return RequetesJson.getUserFollowers(this.username);
    }

    /*
     * Récupère les abonnements de l'utilisateur
     */
    public Set<String> getAbonnements() {
        return RequetesJson.getUserFollowing(this.username);
    }

    /*
     * S'abonne à un utilisateur
     */
    public void follow(String username) {
        RequetesJson.ajoutFollower(this.username, username);
    }

    /*
     * Se désabonne d'un utilisateur
     */
    public void unfollow(String username) {
        RequetesJson.deleteFollower(this.username, username);
    }

    
    
}
