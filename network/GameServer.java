package network;

import java.io.IOException;
import java.net.*;

public class GameServer extends GamePeer {

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
    
    public void connect(String IP) {
        try {
            socket = server.accept();
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error connecting to client.");
        }
    }
}
