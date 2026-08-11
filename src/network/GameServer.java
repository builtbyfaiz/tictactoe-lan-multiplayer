package src.network;

import java.io.IOException;
import java.net.*;

/// Game class responsible for creating, and establishing the server socket
public class GameServer extends GamePeer {

    ServerSocket server;

    private void openServer() {
        if(server != null)
            disconnect();
        try {
            this.server = new ServerSocket(8080); // New Connection Listener on the port
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error opening server.");
        }
    }

    /// Try connecting to any incoming client, IP arg is redundant
    public boolean connect(String IP) {
        try {
            openServer();
            socket = server.accept();
            System.out.println("Player 2 has connected.");
            return true;
        } catch (IOException e) {
            if (!server.isClosed()) {
                e.printStackTrace(); // Unexpected error
                System.err.println("Error connecting to client.");
            }
            return false;
        }
    }

    /// Disconnect connection and close sockets
    public void disconnect() {
        if (this != null) {
            try {
                if (super.socket != null)
                    super.socket.close();
                if (this.server != null)
                    this.server.close();
                System.out.println("Disconnected Successfully"); // Debug
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error Disconnecting our server");
            }
        }
    }
}
