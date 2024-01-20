
import java.io.PrintWriter;
import java.util.Scanner;
import java.time.LocalDate;

public class ThreadEnvoyer implements Runnable {
    private Scanner scanner;
    private PrintWriter out;
    private Client client;
    private LocalDate date;



    public ThreadEnvoyer(Scanner scanner, PrintWriter out, Client client) {
        this.scanner = scanner;
        this.out = out;
        this.client = client;
        this.date = LocalDate.now();
    }


    @Override
    public void run(){
        while (true){
            String message = scanner.nextLine();
            out.println(message);
            out.flush();

            new Message(message,client.getUsername(),0,date);
            
        }
    }




}