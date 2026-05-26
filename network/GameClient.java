package network;

import java.io.IOException;
import java.net.*;

public class GameClient implements NetworkInterface {

    Socket socket;

    public void send(int data) {
        try {
            socket.getOutputStream().write(data);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error sending your move to opponent.");
        }
    }

    public int receive() {
        try {
            return socket.getInputStream().read();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error receiving opponent's move.");
            return -1;
        }
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
