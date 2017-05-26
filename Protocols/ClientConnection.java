package Protocols;

import Client.Client;
import Messages.Message;
import Chat.Chat;
import Chat.ChatMessage;

import java.math.BigInteger;

import java.io.IOException;

import static Utilities.Constants.*;

public class ClientConnection extends Connection implements Runnable {

    private Client client;

    public ClientConnection(String ip, int port, Client client) {
        super(ip, port);

        this.client = client;
    }

    /**
     * Connects to a certain ip and port
     */
    public void connect() throws IOException {
        super.connect();
    }

    /**
     * Sends a message
     * @param message message to be sent
     */
    public void sendMessage(Message message) {
        System.out.println("\nSending message - Header: " + message.getMessageType() + " Body " + message.getBody());
        super.sendMessage(message);
    }

    /**
     * Receives a message
     * @return message received
     */
    public Message receiveMessage(){
        Message message = super.receiveMessage();

        System.out.println("\nReceiving message - Header: " + message.getMessageType() +  " Sender: " + message.getSenderId() + " Body " + message.getBody());

        return message;
    }

    /**
     * Close the connection
     */
    public void closeConnection(){
        super.closeConnection();
    }

    /**
     * Handles clients message
     * @param message to be processed
     */
    public void handleMessage(Message message) {


        switch (message.getMessageType()) {
            case CLIENT_SUCCESS:
            case CLIENT_ERROR:
                client.verifyState(message);
                break;
            case NEW_CHAT_INVITATION:
                System.out.println("Received new chat invitation..");
                String body[] = message.getBody().split(" ");
                Chat chat = new Chat(new BigInteger(body[0]),body[1]);
                client.addChat(chat);
                break;
            case NEW_MESSAGE:
                System.out.println("Received a new message\n" );
                ChatMessage chatMessage = (ChatMessage) message.getObject();
                if(client.getCurrentChat()==NO_CHAT_OPPEN || client.getCurrentChat()!=chatMessage.getChatId().intValue()){
                    client.getChat(chatMessage.getChatId()).addPendingChatMessage(chatMessage);
                    System.out.println("Saved on pending chat messages");
                }
                else {
                    client.getChat(chatMessage.getChatId()).addChatMessage(chatMessage);
                    System.out.println(new String(chatMessage.getContent()));
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void run() {

        while(true){

            System.out.println("Listening...");

            Message message = receiveMessage();

            Runnable task = () -> {
                handleMessage(message);
            };

            service.execute(task);
        }

    }
}
