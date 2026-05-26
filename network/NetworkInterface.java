package network;

import java.net.Socket;

public interface NetworkInterface {
    public void send(int data);
    public int receive();

    public void connect(String IP);
    public void disconnect();
}
