package com.example.afinal.Model;

public class User {
    private String id;
    private String user;
    private String photo;
    private String status;
    private String email;
    public User() {
    }
public  User(String id,String user, String photo,String status,String email)
{
    this.id=id;
    this.user=user;
    this.photo=photo;
    this.status=status;
    this.email=email;
}
 public  String getId(){return id;}
 public void setId(String id){this.id=id;}

    public  String getUser(){return user;}
    public void setUser(String user){this.user=user;}

    public  String getPhoto(){return photo;}
    public void setPhoto(String photo){this.photo=photo;}

    public  String getStatus(){return status;}
    public void setStatus(String status){this.status=status;}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
