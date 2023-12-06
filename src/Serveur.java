import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Serveur {

    public static void main(String[] args) {
        SocketServeur socketServeur = new SocketServeur();
        Socket socketClient = socketServeur.accept();

    }

}