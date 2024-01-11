import java.time.LocalDate;

public class Message {
    private int id;
    private String contenu;
    private String nomAuteur;
    private int nbLikes;
    private LocalDate date;

    public Message(String contenu, String nomAuteur, int nbLikes, LocalDate date) {
        this.contenu = contenu;
        this.nomAuteur = nomAuteur;
        this.nbLikes = nbLikes;
        this.date = date;
        int maxId = RequetesJson.ajoutMessageJSON(contenu, nomAuteur, nbLikes, date);
        this.id = maxId + 1;
    }

    public Message(int id) {
        this.id = id;
    
        String[] tableauAttributs = RequetesJson.rechercheMessageParId(this.id);

        this.contenu = tableauAttributs[0];
        this.nomAuteur = tableauAttributs[1];
        this.nbLikes = Integer.parseInt(tableauAttributs[2]);
        this.date = LocalDate.parse(tableauAttributs[3]);
    }

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

    public void like() {
        this.nbLikes++;

        RequetesJson.likeMessage(this.id);
    }
    
    public void delete(String username) {
        if (this.nomAuteur.equals(username)) {
            RequetesJson.deleteMessage(this.id);
        }
        else {
            System.out.println("Vous ne pouvez pas supprimer ce message."+this.nomAuteur+" "+username);
        }
    }
}
