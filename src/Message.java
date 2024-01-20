import java.time.LocalDate;

public class Message {
    private int id;
    private String contenu;
    private String nomAuteur;
    private int nbLikes;
    private LocalDate date;

    /*
     * Constructeur de la classe Message
     * @param contenu : contenu du message
     * @param nomAuteur : nom de l'auteur du message
     * @param nbLikes : nombre de likes du message
     * @param date : date de publication du message
     */
    public Message(String contenu, String nomAuteur, int nbLikes, LocalDate date) {
        this.contenu = contenu;
        this.nomAuteur = nomAuteur;
        this.nbLikes = nbLikes;
        this.date = date;
        int maxId = RequetesJson.ajoutMessageJSON(contenu, nomAuteur, nbLikes, date);
        this.id = maxId + 1;
    }

    /*
     * Constructeur de la classe Message
     * @param id : id du message
     */
    public Message(int id) {
        this.id = id;
    
        String[] tableauAttributs = RequetesJson.rechercheMessageParId(this.id);

        this.contenu = tableauAttributs[0];
        this.nomAuteur = tableauAttributs[1];
        this.nbLikes = Integer.parseInt(tableauAttributs[2]);
        this.date = LocalDate.parse(tableauAttributs[3]);
    }

    /*
     * @getter
     */
    public int getId() {
        return this.id;
    }

    public String getContenu() {
        return this.contenu;
    }

    public String getNomAuteur() {
        return this.nomAuteur;
    }

    public int getNbLikes() {
        return this.nbLikes;
    }

    public LocalDate getDate() {
        return this.date;
    }

    /*
     * Permet d'incrémenter le nombre de likes d'un message grâce à la bibliothèque RequetesJson
     */
    public void like() {
        this.nbLikes++;

        RequetesJson.likeMessage(this.id);
    }
    
    /*
     * Permet de supprimer un message grâce à la bibliothèque RequetesJson
     * @param username : nom de l'utilisateur qui souhaite supprimer le message
     */
    public void delete(String username) {
        if (this.nomAuteur.equals(username)) {
            RequetesJson.deleteMessage(this.id);
            System.out.println("Le message "+this.contenu+" de "+username+" a été supprimé");
        }
        else {
            System.out.println("Il est impossible de supprimer ce message "+this.nomAuteur+" "+username);
        }
    }

    @Override
    public String toString() {
        return this.id + "~" + this.nomAuteur + "~" + this.contenu + "~" + this.nbLikes + "~" + this.date;
    }
}
