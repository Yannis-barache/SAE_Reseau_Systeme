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

    public Serveur(int port) {
        try {
            serverSocket = new ServerSocket(port);
            pool = Executors.newFixedThreadPool(4);
            System.out.println("Serveur démarré sur le port " + port);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                clientCounter++;
                ClientHandler clientHandler = new ClientHandler(clientSocket, this,clientCounter);
                clients.add(clientHandler);
                pool.submit(clientHandler);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<ClientHandler> getClients() {
        return clients;
    }

    public static void main(String[] args) {
        int port = 5555; // Remplacez par votre numéro de port souhaité
        new Serveur(port); // Crée une instance de Serveur et démarre le serveur dans le constructeur
    }    
}
