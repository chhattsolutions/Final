package com.example.afinal.Notification;

/**
 * Created by Sajid on 1/26/2019.
 */

public class Data {
    private String user;
    private int icon;
    private String body;
    private String title;
    private String sented;
    public Data(){}
    public Data(String user,int icon,String body,String title,String sented)
    {
        this.user=user;
        this.body=body;
        this.icon=icon;
        this.title=title;
        this.sented=sented;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getUser1() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getSented() {
        return sented;
    }

    public void setSented(String sented) {
        this.sented = sented;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
