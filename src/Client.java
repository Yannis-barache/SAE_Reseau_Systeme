import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private Scanner sc;
    private String clientAdress;
    private String username;

    /*
     * Constructeur de la classe Client
     * @param clientAdress : adresse IP du serveur
     * @param serverPort : numéro de port du serveur
     * @param username : nom d'utilisateur du client
     */
    public Client(String clientAdress, int serverPort, String username) {
        this.clientAdress = clientAdress;
        this.username = username;
        this.sc = new Scanner(System.in);
        try {
            socket = new Socket(clientAdress, serverPort);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            // Initialiser la communication avec le serveur
            out.println(username);
            new Thread(new ThreadEnvoyer(sc, out, this)).start();
            new Thread(new ThreadRecevoir(in, out, socket)).start();

            System.out.println();
    
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * @getter
     */
    public String getUsername() {
        return this.username;
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Entrez votre nom d'utilisateur : ");
        String userName = scanner.nextLine();
    
        System.out.print("Entrez l'adresse IP du serveur : ");
        String clientAdd = scanner.nextLine();
    
        System.out.print("Entrez le numéro de port : ");
        int port = scanner.nextInt();
    
        new Client(clientAdd, port,userName); // Lance le client avec le constructeur qui demande le nom d'utilisateur
    }
    
}
