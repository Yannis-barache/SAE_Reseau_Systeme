import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public Client(String serverAddress, int serverPort) {
        try {
            socket = new Socket(serverAddress, serverPort);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    
            // Initialiser la communication avec le serveur
            Thread inputListener = new Thread(() -> {
                try {
                    String message;
                    while ((message = in.readLine()) != null) {
                        // Traiter les messages reçus du serveur
                        System.out.println("Message du serveur: " + message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            inputListener.start();
    
            // Envoyer le nom d'utilisateur au serveur
            Scanner scanner = new Scanner(System.in);
            System.out.print("Entrez votre nom d'utilisateur : ");
            String username = scanner.nextLine();
            out.println(username);
    
            // Gérer l'interaction avec le serveur (envoi de messages, commandes, etc.)
            handleUserInput();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

    public void sendMessage(String message) {
        out.println(message);
    }

    public void followUser(String username) {
        sendMessage("/follow " + username);
    }

    public void unfollowUser(String username) {
        sendMessage("/unfollow " + username);
    }

    public void likeMessage(int messageId) {
        sendMessage("/like " + messageId);
    }

    public void deleteMessage(int messageId) {
        sendMessage("/delete " + messageId);
    }

    


    // Gérer les commandes utilisateur dans la console
    private void handleUserInput() throws IOException {
        BufferedReader userInputReader = new BufferedReader(new InputStreamReader(System.in));
        String userInput;
        while ((userInput = userInputReader.readLine()) != null) {
            // Traiter les différentes commandes de l'utilisateur ici
            if (userInput.startsWith("/follow")) {
                // Logique pour gérer la commande de suivi d'un utilisateur
                String[] parts = userInput.split(" ");
                if (parts.length == 2) {
                    followUser(parts[1]);
                } else {
                    System.out.println("Command format: /follow <username>");
                }
            } else if (userInput.startsWith("/unfollow")) {
                // Logique pour gérer la commande de désabonnement
                // ...
            } else if (userInput.startsWith("/like")) {
                // Logique pour gérer la commande de like
                // ...
            } else if (userInput.startsWith("/delete")) {
                // Logique pour gérer la commande de suppression de message
                // ...
            } else {
                // Envoyer le message au serveur s'il n'y a pas de commande spécifique
                sendMessage(userInput);
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
    
        System.out.print("Entrez l'adresse IP du serveur : ");
        String serverAddress = scanner.nextLine();
    
        System.out.print("Entrez le numéro de port : ");
        int port = scanner.nextInt();
        scanner.nextLine(); // Pour consommer la fin de ligne
    
        new Client(serverAddress, port); // Lance le client avec le constructeur qui demande le nom d'utilisateur
    }
    
}
