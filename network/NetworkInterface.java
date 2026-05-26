package network;

public interface NetworkInterface {
    public void send(int data);
    public int receive();

    public void connect();
    public void disconnect();
}
