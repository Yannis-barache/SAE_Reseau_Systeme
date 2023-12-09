import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;


public class ClientHandler implements Runnable{
    private Socket client;

public ClientHandler(Socket socket){ 
    this.client = socket;
}

@Override   
public void run(){
    try {
        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            System.out.println("Received from " + client.getLocalAddress().toString() + " : " + inputLine);
        }
    } catch (IOException e) {
        System.out.println("Error in ClientHandler: " + e.getMessage());
    }
}
}