import java.io.*;
import java.net.*;
import java.util.*;


public class Serveur {
    private ServerSocket serverSocket;
    private List<ClientHandler> clients = new ArrayList<>();
    private Map<Integer, String> messages = new HashMap<>(); // Stockage des messages par ID
    private Map<String, List<Integer>> userMessages = new HashMap<>(); // Messages associés à chaque utilisateur
    private int clientCounter = 0;

    public Serveur(int port) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Serveur démarré sur le port " + port);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                String username = "User" + clientCounter++;
                ClientHandler clientHandler = new ClientHandler(clientSocket, this,username);
                clients.add(clientHandler);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void broadcastMessage(String message) {
        for (ClientHandler client : clients) {
            client.sendMessage(message);
        }
    }

    


    // Méthodes pour gérer les messages, les commandes, etc.
    public void deleteMessage(int messageId) {
        // Logique pour supprimer un message spécifique
        String message = messages.get(messageId);
        if (message != null) {
            messages.remove(messageId);
            for (List<Integer> userMessageIds : userMessages.values()) {
                userMessageIds.remove(Integer.valueOf(messageId));
            }
            broadcastMessage("Message avec ID " + messageId + " a été supprimé");
        } else {
            broadcastMessage("Message avec ID " + messageId + " n'existe pas");
        }
    }

    public void removeUser(String username) {
        // Logique pour supprimer un utilisateur et tous ses messages
        List<Integer> messageIds = userMessages.get(username);
        if (messageIds != null) {
            for (int messageId : messageIds) {
                messages.remove(messageId);
            }
            userMessages.remove(username);
            clients.removeIf(client -> client.getUsername().equals(username));
            broadcastMessage("Utilisateur " + username + " et le contenu associé ont été supprimés");
        } else {
            broadcastMessage("Utilisateur " + username + " n'existe pas");
        }
    }

    public synchronized void followUser(String followerUsername, String followeeUsername) {
        if (!userMessages.containsKey(followerUsername)) {
            userMessages.put(followerUsername, new ArrayList<>());
        }
        List<Integer> userFolloweeMessages = userMessages.get(followeeUsername);
        if (userFolloweeMessages != null) {
            List<Integer> userFollowerMessages = userMessages.get(followerUsername);
            userFollowerMessages.addAll(userFolloweeMessages);
        }
    }

    public synchronized void unfollowUser(String followerUsername, String followeeUsername) {
        List<Integer> userFollowerMessages = userMessages.get(followerUsername);
        if (userFollowerMessages != null) {
            List<Integer> userFolloweeMessages = userMessages.get(followeeUsername);
            userFollowerMessages.removeAll(userFolloweeMessages);
        }
    }

    public synchronized void likeMessage(String username, int messageId) {
        String message = messages.get(messageId);
        if (message != null) {
            // Augmenter le nombre de likes du message
            // Ici, vous pouvez stocker le message dans un objet Message et mettre à jour les likes
            // Par exemple, si vous avez une classe Message :
            // Message msg = messageObjects.get(messageId);
            // msg.incrementLikes();
            // Puis mettez à jour messages.put(messageId, msg);
        } else {
            System.out.println("Message with ID " + messageId + " does not exist");
        }
    }

    public synchronized void deleteMessage(String username, int messageId) {
        List<Integer> userMessageIds = userMessages.get(username);
        if (userMessageIds != null) {
            if (userMessageIds.contains(messageId)) {
                userMessageIds.remove(Integer.valueOf(messageId));
                messages.remove(messageId);
                System.out.println("Message with ID " + messageId + " deleted by " + username);
            } else {
                System.out.println("User " + username + " does not have a message with ID " + messageId);
            }
        } else {
            System.out.println("User " + username + " does not exist or has no messages");
        }
    }


    public synchronized void handleMessage(String message, ClientHandler sender) {
        
        if (message.startsWith("/delete")) {
            // Logique pour supprimer un message spécifique
            String[] parts = message.split(" ");
            if (parts.length == 2) {
                int messageId = Integer.parseInt(parts[1]);
                deleteMessage(messageId);
            } else {
                // Gérer le format incorrect de la commande /delete
                sender.sendMessage("Command format: /delete <messageId>");
            }
        } else if (message.startsWith("/remove")) {
            // Logique pour supprimer un utilisateur et tous ses messages
            String[] parts = message.split(" ");
            if (parts.length == 2) {
                String usernameToRemove = parts[1];
                removeUser(usernameToRemove);
            } else {
                // Gérer le format incorrect de la commande /remove
                sender.sendMessage("Command format: /remove <username>");
            }
        } else {
            broadcastMessage(message);
        }
    }

    public static void main(String[] args) {
        int port = 5555; // Remplacez par votre numéro de port souhaité
        new Serveur(port); // Crée une instance de Serveur et démarre le serveur dans le constructeur
    }    
}
