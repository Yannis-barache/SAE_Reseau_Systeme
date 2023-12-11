import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Serveur {

    final ServerSocket serveurSocket;
    Socket clientSocket;
    BufferedReader in;
    PrintWriter out;
    final Scanner sc = new Scanner(System.in);

    static ExecutorService pool = Executors.newFixedThreadPool(10);

    public Serveur() throws IOException {
        serveurSocket = new ServerSocket(5555);
        sc.useDelimiter("\n");
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

    public void ouvre() throws IOException {
        while (true){
            System.out.println("En attente d'un client");
            clientSocket = serveurSocket.accept();
            System.out.println("Nouveau client connecté");

        }
    }

    public void principale() throws IOException {
        String msg;
        while (true) {

            System.out.println("En attente d'un client");
            Socket clientSocket = this.serveurSocket.accept();
            out = new PrintWriter(clientSocket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            System.out.println("Nouveau client connecté");

            Thread t = new Thread(new ClientHandler(clientSocket));
            pool.submit(t);
            msg = this.ReceptionMessage();

            if (msg == null) {
                System.out.println("Client déconnecté");
                this.clientSocket.close();
                this.clientSocket = this.serveurSocket.accept();
            }
            if (msg.equals("/quit")) {
                System.out.println("Client déconnecté");
                this.clientSocket.close();
                this.clientSocket = this.serveurSocket.accept();
            }


            if (msg.equals("/help")) {
                this.EnvoiMessage("Liste des commandes : \n /help : affiche la liste des commandes \n /quit : vous déconnecte \n /ip : Renvoi votre ip \n ");

            }


            // Récupération de l'ip du client
            if (clientSocket != null) {
                String ip = this.clientSocket.getRemoteSocketAddress().toString();
                System.out.println(ip);
            }





        }

    }



    public static void main(String[] test) throws IOException {
        Serveur s = new Serveur();
        String msg;
        s.principale();


    }
}