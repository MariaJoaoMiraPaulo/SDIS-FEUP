package Messages;

import Chat.Chat;

import java.io.*;
import java.math.BigInteger;

import static Utilities.Constants.CREATE_CHAT;
import static Utilities.Constants.CRLF;

/**
 * Message class.
 */
public class Message implements Serializable {

    private String messageType;
    private String senderId;
    private String body;

    private Object object;

    /**
     * Message Constructor
     * @param messageType
     * @param senderId
     * @param body
     */
    public Message(String messageType, BigInteger senderId, String ... body){
        this.messageType = messageType;
        this.senderId = new String(senderId.toByteArray());
        this.body = String.join(" ", body);
        //createMessage(this.messageType,this.senderId,this.body);
    }


    public Message(String messageType, BigInteger senderId, Object obj){
        this.messageType = messageType;
        this.senderId =  new String(senderId.toByteArray());
        this.object = obj;
       // createMessage(this.messageType,this.senderId,this.object);
    }

    /**
     * Creates Message with format[MessageType][SenderId][CRLF][Body][CRLF][CRLF]
     * @param messageType
     * @param senderId
     * @param body
     * @return
     */
    public byte[] createMessage(String messageType, String senderId, String body){

        return (String.join(" ", messageType) + " "
                + String.join(" ", senderId) + " "
                + CRLF
                + String.join(" ", body) + " "
                + CRLF + CRLF).getBytes();
    }
    /**
     * Returns message type
     * @return messageType
     */
    public String getMessageType() {
        return messageType;
    }

    /**
     * Returns message senderId
     * @return senderId
     */
    public String getSenderId() {
        return senderId;
    }

    /**
     * Returns message body
     * @return body
     */
    public String getBody() {
        return body;
    }

    /**
     * Returns Chat
     * @return
     */
    public Object getObject() {
        return object;
    }

    /**
     * Prints message content
     */
    public void printMessage(Message message){

        System.out.println("Message Type: " + message.getMessageType() + "\n");
        System.out.println("Body: " + message.getBody() + "\n");
    }
}