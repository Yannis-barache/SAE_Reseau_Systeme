import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Serveur {

    final ServerSocket serveurSocket;
    Socket clientSocket;
    final BufferedReader in;
    final PrintWriter out;
    final Scanner sc = new Scanner(System.in);

    public Serveur() throws IOException {
        serveurSocket = new ServerSocket(5000);
        clientSocket = serveurSocket.accept();
        out = new PrintWriter(clientSocket.getOutputStream());
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    public void EnvoiMessage(String msg) {
        out.println(msg);
        out.flush();
    }

    public String ReceptionMessage() throws IOException {
        String msg = in.readLine();
        return msg;
    }

    public void Fermeture() throws IOException {
        out.close();
        clientSocket.close();
        serveurSocket.close();
    }



    public static void main(String[] test) throws IOException {
        Serveur s = new Serveur();
        String msg;
        while (true) {
            msg = s.ReceptionMessage();
            if (msg == null) {
                System.out.println("Client déconecté");
                s.clientSocket.close();
                s.clientSocket = s.serveurSocket.accept();
            }
            if (msg.equals("/quit")) {
                System.out.println("Client déconecté");
                s.clientSocket.close();
                s.clientSocket = s.serveurSocket.accept();
            }

            if (msg.equals("/help")) {
                s.EnvoiMessage("Liste des commandes : \n /help : affiche la liste des commandes \n /quit : vous déconnecte \n /ip : Renvoi votre ip \n ");

            }
            // Récupération de l'ip du client
            String ip = s.clientSocket.getRemoteSocketAddress().toString();
            System.out.println(ip +" : " + msg);
        }

    }
}