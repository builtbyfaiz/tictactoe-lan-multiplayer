package network;

import java.io.IOException;
import java.net.*;

public class GameClient implements NetworkInterface {

    Socket socket;

    public void send(int data) {
    }

    public int receive() {
        return 0;
    }

    public void connect(String IP) {
        try {
            socket = new Socket(IP, 8080);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error connecting to server with IP: " + IP);
        }
    }

    public void disconnect() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error in closing client socket.");
        }
    }

}
