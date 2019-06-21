package com.example.afinal.Model;

import java.util.List;

public class GroupChat {
    private String sender;
    private List<String> reciver;
    private String message;
    private String group_name;



    public GroupChat(String sender , String message, String group_name , List<String> reciver)
    {
        this.sender=sender;
        this.reciver=reciver;
        this.message=message;
        this.group_name = group_name;
    }
    public GroupChat(){}

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public List<String> getReciver() {
        return reciver;
    }

    public void setReciver(List<String> reciver) {
        this.reciver = reciver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }
}
