package Messages;

import Client.Client;
import Server.Server;
import Server.Node;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;

import static Utilities.Constants.PREDECESSOR;

public class MessageHandler implements Runnable {

    String ip;
    int port;
    Server server = null;
    Client client = null;
    private SSLSocket sslSocket;
    private SSLSocketFactory sslSocketFactory;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private Message message;

    public MessageHandler(Message message, String ip, String port, Server server) {

        this.ip = ip;
        this.port = Integer.parseInt(port);
        this.server = server;
        this.message = message;
        run();
    }

    public MessageHandler(Message message, String ip, String port, Client client) {

        this.ip = ip;
        this.port =  Integer.parseInt(port);
        this.client = client;
        this.message = message;
        run();
    }

    public void run() {
        connectToServer();
        sendMessage(message);

        while(true){
            receiveResponse();
        }
    }

    /**
     * Connects to server
     */
    public void connectToServer() {

        try {
            sslSocketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
            sslSocket = (SSLSocket) sslSocketFactory.createSocket(ip, port);
            sslSocket.setEnabledCipherSuites(sslSocket.getSupportedCipherSuites());
            outputStream = new ObjectOutputStream(sslSocket.getOutputStream());
            inputStream = new ObjectInputStream(sslSocket.getInputStream());
        } catch (IOException e) {
            System.out.println("Error creating ssl socket...");
            e.printStackTrace();
        }

    }

    /**
     * Sends a message through a ssl socket
     *
     */
    public void sendMessage(Message message) {
        try {
            outputStream.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Reads a message response from the socket and calls the handler function
     */
    public void receiveResponse(){
        Message response = null;
        try {
            response = (Message) inputStream.readObject();
            handleResponse(response);
        } catch (IOException e) {
            System.out.println("Error reading message...");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Error reading message...");
            e.printStackTrace();
        }
    }


    public void handleResponse(Message response){

        if(response.getMessageType().equals(PREDECESSOR)){
            String[] nodeInfo = response.getBody().split(" ");
            Node node = new Node(nodeInfo[0],nodeInfo[1]);
            this.server.setPredecessor(node);
        }
    }


    /**
     * Closes socket
     */
    public void closeSocket() {
        try {
            sslSocket.close();
        } catch (IOException e) {
            System.out.println("Error closing ssl socket...");
            e.printStackTrace();
        }
    }

}
