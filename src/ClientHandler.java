import java.io.*;
import java.net.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.Date;

public class ClientHandler extends Thread {
    private Socket clientSocket;
    private Serveur server;
    private PrintWriter out;
    private BufferedReader in;
    private String username;


    public ClientHandler(Socket socket, Serveur server, String username) {
        this.clientSocket = socket;
        this.server = server;
        this.username = username;
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            String message;
            while ((message = in.readLine()) != null) {
                System.out.println("Message du client: " + message);
                sendMessage(message);

                // Autres traitements ou réponses au client ici
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        out.println(message);
    }

    public String getUsername() {
        return username;
    }
  
    // public void handleCommand(String command) {
    //     String[] parts = command.split(" ");
    //     switch (parts[0]) {
    //         case "/follow":
    //             if (parts.length == 2) {
    //                 server.followUser(username, parts[1]);
    //             } else {
    //                 sendMessage("Command format: /follow <username>");
    //             }
    //             break;
    //         case "/unfollow":
    //             if (parts.length == 2) {
    //                 server.unfollowUser(username, parts[1]);
    //             } else {
    //                 sendMessage("Command format: /unfollow <username>");
    //             }
    //             break;
    //         case "/like":
    //             if (parts.length == 2) {
    //                 try {
    //                     int messageId = Integer.parseInt(parts[1]);
    //                     server.likeMessage(username, messageId);
    //                 } catch (NumberFormatException e) {
    //                     sendMessage("Invalid message ID");
    //                 }
    //             } else {
    //                 sendMessage("Command format: /like <messageId>");
    //             }
    //             break;
    //         case "/delete":
    //             if (parts.length == 2) {
    //                 try {
    //                     int messageId = Integer.parseInt(parts[1]);
    //                     server.deleteMessage(username, messageId);
    //                 } catch (NumberFormatException e) {
    //                     sendMessage("Invalid message ID");
    //                 }
    //             } else {
    //                 sendMessage("Command format: /delete <messageId>");
    //             }
    //             break;
    //         default:
    //             sendMessage("Command not recognized");
    //             break;
    //     }
    // }

    // public void handleMessage(String message) {
    //     // Logique pour traiter les messages spécifiques du client
    //     // Par exemple, vérifier les commandes spéciales
    //     if (!message.startsWith("/")) {
    //         // Créer un nouveau message avec les détails fournis par le client
    //         SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    //         String date = dateFormat.format(new Date()); // Date actuelle

    //         // Créer le nouveau message
    //         Message newMessage = new Message(message, getUsername(), 0, LocalDate.parse(date));

    //         // Stocker le message dans le fichier JSON
    //         newMessage.store(); // Méthode pour stocker le message dans le fichier JSON

    //         // Envoyer un message de confirmation au client
    //         sendMessage("Message saved successfully.");
    //     }
    //     else if (message.startsWith("/")) {
    //         handleCommand(message);
            
    //     }
    //     else{
    //         server.handleMessage(message, this);
    //     }
    // }
}


    

    

