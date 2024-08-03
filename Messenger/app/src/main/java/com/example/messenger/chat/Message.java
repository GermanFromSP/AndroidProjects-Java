package com.example.messenger.chat;

public class Message {

    private String tex;
    private String senderId;
    private String receiverId;

    public Message(String tex, String senderId, String receiverId) {
        this.tex = tex;
        this.senderId = senderId;
        this.receiverId = receiverId;
    }

    public Message() {
    }

    public String getTex() {
        return tex;
    }

    public String getSenderId() {
        return senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }
}
