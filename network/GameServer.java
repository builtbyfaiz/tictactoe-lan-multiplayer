package network;

import java.io.IOException;
import java.net.*;

public class GameServer implements NetworkInterface {

    ServerSocket server;
    Socket socket;

    public GameServer() {
        try {
            server = new ServerSocket(8080); // New Connection Listener on the port
            
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error opening server.");
        } 

        try {
            String myIP = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            System.err.println("Error getting local IP");
        } 

    }
    
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
            socket = server.accept();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error connecting to client.");
        }
    }

    public void disconnect() {
        try {
            server.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error in closing server.");
        }
    }
}
