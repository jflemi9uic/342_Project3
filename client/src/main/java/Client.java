import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.function.Consumer;

public class Client extends Thread {

    Socket socketClient;

    ObjectOutputStream out;
    ObjectInputStream in ;

    private String ip;
    private int port;

    private Consumer<Serializable> callback;

    boolean firstmessage = true;
    int playernum = -1;

    Client(String ip, int port, Consumer<Serializable> call) {
        this.ip = ip;
        this.port = port;
        callback = call;
    }

    public void run() {
        try {
            socketClient = new Socket(ip, port);
            out = new ObjectOutputStream(socketClient.getOutputStream());
            in = new ObjectInputStream(socketClient.getInputStream());
            socketClient.setTcpNoDelay(true);
        } catch (Exception e) {}

        while (true) {
            try {
                MorraInfo message = (MorraInfo) in.readObject();

                if (firstmessage) {
                    System.out.println("Assigning player number here");
                    playernum = message.playernumber;
                    System.out.println("Player number = " + playernum);
                    firstmessage = false;

                } else {
                    System.out.println("callback.accept()");
                    callback.accept(message);
                }

                System.out.println("passed the if else");

            } catch (Exception e) {}
        }

    }

    public void send(MorraInfo data) {
        try {
            out.writeObject(data);
            System.out.println("Sending data inside of send()");
        } catch (IOException e) {
            e.printStackTrace(); 
        }
    }
}
