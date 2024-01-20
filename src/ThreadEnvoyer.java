
import java.io.PrintWriter;
import java.util.Scanner;
import java.time.LocalDate;

public class ThreadEnvoyer implements Runnable {
    private Scanner scanner;
    private PrintWriter out;
    private Client client;
    private LocalDate date;


    /*
     * Constructeur de la classe ThreadEnvoyer
     * @param scanner : scanner permettant de lire les messages entrés par l'utilisateur
     * @param out : PrintWriter permettant d'envoyer des messages au serveur
     * @param client : client qui envoie les messages
     */
    public ThreadEnvoyer(Scanner scanner, PrintWriter out, Client client) {
        this.scanner = scanner;
        this.out = out;
        this.client = client;
        this.date = LocalDate.now();
    }


    /*
     * Cette méthode est appelée au lancement du thread
     * Elle permet d'envoyer des messages au serveur
     
     */
    @Override
    public void run(){
        while (true) {
            String messageToSend = scanner.nextLine();
            out.println(client.getUsername() + ":" + messageToSend);
            out.flush();


            

        }
    }




}