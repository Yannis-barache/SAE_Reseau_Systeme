import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Tuito {
    public static void main(String[] args) {
        try {
            Socket socketClient = new Socket("127.0.0.1", 100);
            System.out.println("Client connecté");
            socketClient.close();
            System.out.println("Client déconnecté");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Erreur lors de la connexion au serveur");
            e.printStackTrace();
        }
    }   
}
