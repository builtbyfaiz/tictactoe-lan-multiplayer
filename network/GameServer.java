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
    }
    
    public boolean connect(String IP) {
        try {
            socket = server.accept();
            server.close();
            System.out.println("Player 2 has connected.");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error connecting to client.");
            return false;
        }
    }
}
