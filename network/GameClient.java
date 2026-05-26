package network;

import java.io.IOException;
import java.net.*;

public class GameClient extends GamePeer {

    public boolean connect(String IP) {
        try {
            socket = new Socket(IP, 8080);
            System.out.println("Successfully connected to server with IP: " + IP);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error connecting to server with IP: " + IP);
            return false;
        }
    }
}
