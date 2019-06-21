package com.example.afinal.Model;

public class Chat {
    private String sender;
    private String reciver;
    private String message;
    private String image;
    private String video;
    private boolean isseen;
    private String voice;
    private String timestamp;

    public Chat(String sender,String reciver,String message,boolean isseen,String image,String video,String voice,String timestamp)
    {
        this.video=video;
        this.sender=sender;
        this.isseen=isseen;
        this.reciver=reciver;
        this.message=message;
        this.image=image;
        this.voice=voice;
        this.timestamp=timestamp;
    }
    public Chat(){}

    public String getVideo() {
        return video;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getVoice() {
        return voice;
    }

    public void setVoice(String voice) {
        this.voice = voice;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public  String getSender(){return sender;}
    public void setSender(String sender){this.sender=sender;}

    public  String getReciver(){return reciver;}
    public void setReciver(String reciver){this.reciver=reciver;}

    public  String getMessage(){return message;}
    public void setMessage(String message){this.message=message;}

    public  boolean isIsseen(){return isseen;}
    public void setIsseen(boolean isseen){this.isseen=isseen;}
}
