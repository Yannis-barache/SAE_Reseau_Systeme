import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    final Socket clientSocket;
    final BufferedReader in;

    final PrintWriter out;
    static final Scanner sc = new Scanner(System.in);// pour lire à partir du clavier

    public Client(Socket clientSocket) {
        this.clientSocket = clientSocket;
        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
    }


    public static void main(String[] args) {
        try {
            // On permet au client de choisir son port
            System.out.println("Choisissez votre port : ");
            int port = sc.nextInt();
            Socket socket = new Socket("localhost", port);
            Client client = new Client(socket);
            Thread envoyer = new Thread(new Runnable() {
                String msg;
                @Override
                public void run() {
                    while (true) {
                        msg = sc.nextLine();
                        client.EnvoiMessage(msg);
                    }
                }
            });

            Thread recevoir = new Thread(new Runnable() {
                String msg;
                @Override
                public void run() {
                    try {
                        msg = client.ReceptionMessage();
                        while (msg != null) {
                            System.out.println(msg);
                            msg = client.ReceptionMessage();
                        }
                        System.out.println("Serveur déconecté");
                        // A la déconnexion du serveur, on ferme le client
                        System.exit(0);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            envoyer.start();
            recevoir.start();

            

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}