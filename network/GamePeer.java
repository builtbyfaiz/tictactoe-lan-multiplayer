package network;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/// Parent network class for client/server, Handles data communication
public abstract class GamePeer {
    protected Socket socket;

    public boolean send(int data) {
        try {
            socket.getOutputStream().write(data); // Write to stream.
            socket.getOutputStream().flush();     // Force send any remaining data then clear stream.
            System.out.println("Sent: " + data);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error sending your move to opponent.");
            return false;
        }
    }

    public int receive() {
        try {
            int data = socket.getInputStream().read(); 
            System.out.println("Received: " + data);
            return data;
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error receiving opponent's move.");
            return -1; // Return -1 to indicate failure
        }
    }

    /// Client/Server will handle there own connection implementation.
    public abstract boolean connect(String IP);

    public void disconnect() {
        if (this != null) {
            try {
                if (socket != null)
                    socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /// Returns local IP which the client can connect to.
    public String getIP() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            return "";
        }
    }

}
