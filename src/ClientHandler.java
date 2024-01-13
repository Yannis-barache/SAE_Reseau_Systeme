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
    private User user;


    public ClientHandler(Socket socket, Serveur server, int id) throws IOException {
        this.clientSocket = socket;
        this.server = server;
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        try{
            if (!RequetesJson.isUserExists(in.readLine())) this.user = new User(in.readLine(), in.readLine(), clientSocket.getInetAddress().getHostAddress());
       
        }catch(IOException e){
            e.printStackTrace();
        }
    }    

    public void broadcast(String msg, Socket clienSocket) {
        for (ClientHandler client : server.getClients()) {
            if (client.clientSocket != clienSocket) {
                try {
                    client.out.println(msg);
                    client.out.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void run() {
        String inputLine;
        try {
            while ((inputLine = in.readLine()) != null) {
                if (inputLine.contains("/")) {
                    String[] requestParams = inputLine.split(" ");
                    if (requestParams.length == 2) {
                        String command = requestParams[0];
                        String param = requestParams[1];
        
                        switch (command) {
                            case "/follow" -> this.user.follow(param);
                            case "/unfollow" -> this.user.unfollow(param);
                            case "/like" -> likeMessage(Integer.parseInt(param));
                            case "/delete" -> deleteMessage(Integer.parseInt(param));
                            default -> System.out.println("Commande invalide");
                        }
                    }
                }
            }
        } catch (IOException e) {
            // Gérer les exceptions d'entrée/sortie
            e.printStackTrace();
        } finally{
            deconnecter();
        }
    }

    public void likeMessage(int id) {
        Message message = new Message(id);
        message.like();
        broadcast("Le message " + id + " a été liké par " + this.user.getUsername(), this.clientSocket);
    }

    public void deleteMessage(int id) {
        Message message = new Message(id);
        message.delete(this.user.getUsername());
        broadcast("Le message " + id + " a été supprimé par " + this.user.getUsername(), this.clientSocket);
    }


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
    
  
    
}


    

    

