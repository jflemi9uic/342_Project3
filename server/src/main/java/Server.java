import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.function.Consumer;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import javafx.application.Platform;
import javafx.scene.control.ListView;

public class Server {

    int count = 1;
    ArrayList<ClientThread> clients = new ArrayList<ClientThread> ();
    TheServer server;
    private Consumer<Serializable> callback, callback2;
    private int port;
    public int countClients = 0;
    Label numClients;
    MorraInfo masterMorraInfo = new MorraInfo();

    int player1 = -1;
    int player2 = -1;

    Server(int port, Consumer<Serializable> call, Consumer<Serializable> call2) {
        this.numClients = numClients;
        this.port = port;
        callback = call;
        callback2 = call2;
        server = new TheServer();
        server.start();
    }

    void printMasterObject() {
        System.out.println("* MASTER OBJECT *");
        System.out.println("p1plays: " + masterMorraInfo.p1Plays);
        System.out.println("p1guess: " + masterMorraInfo.p1Guess);
        System.out.println("p2plays: " + masterMorraInfo.p2Plays);
        System.out.println("p2guess: " + masterMorraInfo.p2Guess);
        System.out.println("player1played: " + masterMorraInfo.player1played);
        System.out.println("player2played: " + masterMorraInfo.player2played);
    }

    public class TheServer extends Thread {

        public void run() {
            try (ServerSocket mysocket = new ServerSocket(port);) {
                callback.accept("Server is waiting for a client!");

                while (true) {
                    if (countClients < 2) {
                        
                        ClientThread c = new ClientThread(mysocket.accept(), count);
                        callback.accept("client has connected to server: client #" + count);
                        callback2.accept("1");
                        clients.add(c);

                        if (player1 == -1) {
                            player1 = count;
                        } else if (player2 == -1) {
                            player2 = count;
                        }

                        c.start();

                        count++;
                        countClients++;
                    }
                }
            } //end of try
            catch (Exception e) {
                callback.accept("Server socket did not launch");
            }
        } //end of while
    }

    class ClientThread extends Thread {

        Socket connection;
        int count;
        ObjectInputStream in ;
        ObjectOutputStream out;

        ClientThread(Socket s, int count) {
            this.connection = s;
            this.count = count;
        }

        public void updateClients(String message) {
            for (int i = 0; i < clients.size(); i++) {
                ClientThread t = clients.get(i);
                try {
                    t.out.writeObject(message);
                } catch (Exception e) {}
            }
        }

        public void run() {
            try {
                in = new ObjectInputStream(connection.getInputStream());
                out = new ObjectOutputStream(connection.getOutputStream());
                connection.setTcpNoDelay(true);
            } catch (Exception e) {
                System.out.println("Streams not open");
            }

            updateClients("new client on server: client #" + count);

            MorraInfo mi = new MorraInfo();
            if (player1 == count) {
                mi.playernumber = 1;
            } else if (player2 == count) {
                mi.playernumber = 2;
            }

            try {
                out.writeObject(mi);
            } catch (Exception e) {}

            while (true) {
                try {
                    // if this runs, we have recieved an object...
                    MorraInfo data = (MorraInfo) in.readObject();

                    // player 1 sent us something
                    if (data.playernumberREAL == 1) {
                        // update the master object
                        masterMorraInfo.p1Plays = data.getp1play();
                        masterMorraInfo.p1Guess = data.getp1guess();
                        masterMorraInfo.player1played = true;
                        // send the messages to the serverlog
                        callback.accept("Client #" + count + " played: " + data.getp1play());
                        callback.accept("Client #" + count + " guessed: " + data.getp1guess());
                    // player 2 sent us soething
                    } else if (data.playernumberREAL == 2) {
                        // update the master object
                        masterMorraInfo.p2Plays = data.getp2play();
                        masterMorraInfo.p2Guess = data.getp2guess();
                        masterMorraInfo.player2played = true;
                        // send the mseeages to the serverlog
                        callback.accept("Client #" + count + " played: " + data.getp2play());
                        callback.accept("Client #" + count + " guessed: " + data.getp2guess());
                    }

                    if (masterMorraInfo.player1played && masterMorraInfo.player2played) {
                        masterMorraInfo.have2players = true;
                        System.out.println("finish round logic");

                        // EVALUATE WHO WON
                        int p1play = masterMorraInfo.getp1play();
                        int p2play = masterMorraInfo.getp2play();
                        int p1guess = masterMorraInfo.getp1guess();
                        int p2guess = masterMorraInfo.getp2guess();
                        int total = p1play + p2play;

                        if (p1guess == total && p2guess != total) {
                            System.out.println("p1 wins round");
                        }
                        else if (p1guess != total && p2guess == total) {
                            System.out.println("p2 wins round");
                        }
                        else if (p1guess == total && p2guess == total) {
                            System.out.println("both right, no one wins");
                        }
                        else {
                            System.out.println("both wrong, no one wins");
                        }
                    }

                    // printMasterObject();

                    try {
                        out.writeObject(masterMorraInfo);
                    } catch (Exception e) {}

                } catch (Exception e) {
                    callback.accept("Client #" + count + " left");
                    updateClients("Client #" + count + " has left the server!");
                    clients.remove(this);
                    countClients--;
                    callback2.accept("-1");
                    break;
                }
            }
        }
    }
}