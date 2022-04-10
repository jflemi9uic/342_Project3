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

    private Consumer<Serializable> callback, callback2;

    boolean firstmessage = true;
    int playernum = -1;

    Client(String ip, int port, Consumer<Serializable> call, Consumer<Serializable> call2) {
        this.ip = ip;
        this.port = port;
        callback = call;
        callback2 = call2;
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
                // `message` tells us what player we are from `mi`
                MorraInfo message = (MorraInfo) in.readObject();

                if (message.have2players) {
                    callback2.accept(message);
                }

                if (firstmessage) {
                    playernum = message.playernumber;
                    firstmessage = false;
                    callback.accept(message);

                } else {
                    callback.accept(message);
                }
            } catch (Exception e) {}
        }
    }

    public void send(MorraInfo data) {
        System.out.println("sending data to out");
        try {
            out.writeObject(data);
            out.reset();
        } catch (IOException e) {
            e.printStackTrace(); 
        }
    }
}
