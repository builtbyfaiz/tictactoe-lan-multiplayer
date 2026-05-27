package network;

import java.io.IOException;
import java.net.Socket;

public abstract class GamePeer {
    protected Socket socket;

    public boolean send(int data) {
        try {
            socket.getOutputStream().write(data);  // Write to stream.
            socket.getOutputStream().flush();      // Send the data then clear stream.
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
            return -1;
        }
    }

    public abstract boolean connect(String IP);

    public void disconnect() {
        try {
            if (socket != null)
                socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
