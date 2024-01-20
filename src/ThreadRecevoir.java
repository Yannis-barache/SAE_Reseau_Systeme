import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;


public class ThreadRecevoir implements Runnable {
    private BufferedReader in;
    private PrintWriter out;
    private Socket socket;

    /*
     * Constructeur de la classe ThreadRecevoir
     * @param in : BufferedReader permettant de lire les messages envoyés par le serveur
     * @param out : PrintWriter permettant d'envoyer des messages au serveur
     * @param socket : socket du client
     */
    public ThreadRecevoir(BufferedReader in, PrintWriter out,Socket socket) {
        this.in = in;
        this.out = out;
        this.socket = socket;
    }

    /*
     * Cette méthode est appelée au lancement du thread
     * Elle permet de recevoir des messages du serveur
     */
    @Override
    public void run() {
        String message;
        while (true) {
            try{
                while ((message = in.readLine()) != null) {
                    // Traiter les messages reçus du serveur
                    System.out.println("Message du serveur: " + message);
                    
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            
        }
    }
}