import java.io.*;
import java.net.*;
import java.util.Set;

public class ClientHandler extends Thread {
    private Socket clientSocket;
    private Serveur server;
    private PrintWriter out;
    private BufferedReader in;
    private User user;


    /*
     * Constructeur de la classe ClientHandler
     * @param socket : socket du client
     * @param server : serveur
     * @param id : identifiant du client
     */
    public ClientHandler(Socket socket, Serveur server, int id) throws IOException {
        this.clientSocket = socket;
        this.server = server;
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        try{
            String username = in.readLine();
            if (!RequetesJson.isUserExists(username)){
                this.user = new User(username, clientSocket.getInetAddress().getHostAddress());
            }    
            else{this.user = RequetesJson.getUser(username);}
       
        }catch(IOException e){
            e.printStackTrace();
        }
    }    

    /*
     * Cette méthode permet d'envoyer un message à tous les clients connectés au serveur
     * @param msg : message à envoyer
     * @param socketClient : socket du client qui envoie le message
     */
    public void broadcast(String msg, Socket socketClient) {
        Set<String> abonnees = this.user.getAbonnes();
        for (ClientHandler client : server.getClients()) {
            if (client.clientSocket != socketClient && abonnees.contains(user.getUsername())) {
                try {
                    client.out.println(msg);
                    client.out.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /*
     * @getter
     */
    public User getUtilisateur() {
        return this.user;
    }

    public Socket getSocketClient() {
        return this.clientSocket;
    }

    /*
     * Cette méthode permet de déconnecter un client
     */
    public void deconnecter() {
        server.getClients().remove(this);
        try {
            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * Cette méthode est appelée au lancement du thread
     * Elle permet de recevoir des messages du client
     */
    @Override
    public void run() {
    String inputLine;
    try {
        while ((inputLine = in.readLine()) != null) {
            if (clientSocket.isClosed()) {
                return;
               
            }
            String[] pseudoEtMessage = inputLine.split(":");
            String pseudo = pseudoEtMessage[0];
            String message = pseudoEtMessage[1];


            Message messageSousFormatJSON = new Message(message, pseudo, 0, java.time.LocalDate.now());


            if (message.startsWith("/")) {
                String[] requestParams = message.split(" ");
                if (requestParams.length == 2) {
                    String command = requestParams[0];
                    String param = requestParams[1];
                    System.out.println("Commande reçue : " + command + " " + param);


                    if ("/follow".equals(command)) {
                        this.user.follow(param);
                    } else if ("/unfollow".equals(command)) {
                        this.user.unfollow(param);
                    } else if ("/like".equals(command)) {
                        likeMessage(Integer.parseInt(param));
                    } else if ("/delete".equals(command)) {
                        deleteMessage(Integer.parseInt(param));
                       
                    }
                    else if ("/quit".equals(command)) {
                        deconnecter();
                        break;
                    }    
                   
                    else {
                        System.out.println("Commande invalide");
                    }
                }
            }
            else {
                String[] messageTab = messageSousFormatJSON.toString().split("~");
                String messageFinal = "Id : " + messageTab[0] + " Auteur : " + messageTab[1] + " Contenu : "+ messageTab[2] + " Likes : " + messageTab[3] + " Date : " + messageTab[4];
                broadcast(messageFinal, clientSocket);
            }
        }
    } catch (IOException e) {
        // Gérer les exceptions d'entrée/sortie
        e.printStackTrace();
    }    
    }


    /*
     * Cette méthode permet de liker un message
     * @param id : id du message à liker
     */
    public void likeMessage(int id) {
        Message message = new Message(id);
        message.like();
        System.out.println("Le message " + id + " a été liké par " + this.user.getUsername());
        broadcast("Le message " + id + " a été liké par " + this.user.getUsername(), this.clientSocket);
    }

    /*
     * Cette méthode permet de supprimer un message
     * @param id : id du message à supprimer
     */
    public void deleteMessage(int id) {
        Message message = new Message(id);
        message.delete(this.user.getUsername());
        broadcast("Le message " + id + " a été supprimé par " + this.user.getUsername(), this.clientSocket);
    }
}


    

    

