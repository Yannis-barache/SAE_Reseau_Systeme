import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Serveur {
    private ServerSocket serverSocket;
    private List<ClientHandler> clients = new ArrayList<>();
    private int clientCounter = 0;
    private ExecutorService pool;

    public Serveur(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
        this.clients = new ArrayList<>();
        this.pool = Executors.newFixedThreadPool(4);
        this.clientCounter = 0;
    }

    public void start() {
        System.out.println("Serveur à l'écoute sur le port " + serverSocket.getLocalPort());
        while (true) {
            try {
                // Attente de connexion d'un client
                Socket client = serverSocket.accept();
                System.out.println("Connexion cliente reçue.");
                clientCounter++;
                ClientHandler t = new ClientHandler(client, this, clientCounter);
                clients.add(t);
                pool.submit(t);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /*
     * Cette méthode permet de récupérer la liste des clients connectés au serveur
     */
    public List<ClientHandler> getClients() {
        return clients;
    }

    /*
     * Cette méthode permet de supprimer un message de la base de données
     */
    public void deleteMessage(int id) {
        RequetesJson.deleteMessage(id);
    }

    /*
     * Cette méthode permet de supprimer un utilisateur de la base de données
     */
    public void removeUser(String username) {
        RequetesJson.deleteUser(username);
        RequetesJson.deleteAllMessagesUser(username);
        for (ClientHandler client : clients) {
            if (client.getUtilisateur().getUsername().equals(username)) {
                client.deconnecter();
                break;
            }
        }
    }


    

    public static void main(String[] args) {
        int port = 5555; // Remplacez par votre numéro de port souhaité
        try{
        Serveur serv = new Serveur(port);// Crée une instance de Serveur et démarre le serveur dans le constructeur

        new Thread(serv::start).start();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String command = scanner.nextLine();
    
                // Vérifier si la commande est /delete ou /remove
            if (command.startsWith("/delete ")) {
                int id = Integer.parseInt(command.split(" ")[1]);
                serv.deleteMessage(id);
            } else if (command.startsWith("/remove ")) {
                String username = command.split(" ")[1];
                serv.removeUser(username);
            }


            }
    } catch (IOException error){
        error.printStackTrace();
        
    }
    }
}                

