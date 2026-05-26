package network;

import java.io.IOException;
import java.net.Socket;

public abstract class GamePeer {
    protected Socket socket;

    public void send(int data) {
        try {
            socket.getOutputStream().write(data);
            System.out.println("Sent: " + data);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error sending your move to opponent.");
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

    public abstract void connect(String IP);

    public void disconnect() {
        try {
            if (socket != null)
                socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
