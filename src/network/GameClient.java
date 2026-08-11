package src.network;

import java.io.IOException;
import java.net.*;

/// Game class responsible for creating, and establishing the client socket
public class GameClient extends GamePeer {

    /// Establish a socket connection with the entered IP on port 8080
    public boolean connect(String IP) {
        try {
            socket = new Socket(IP, 8080);
            System.out.println("Successfully connected to server with IP: " + IP);
            return true;
        } catch (IOException e) {
            System.err.println("Error connecting to server with IP: " + IP);
            return false;
        }
    }

    /// Disconnect connection and close sockets
    public void disconnect() {
        if (this != null) {
            try {
                if (super.socket != null)
                    super.socket.close();
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error Disconnecting our client");
            }
        }
    }
}
