package com.example.afinal.Model;

import java.util.ArrayList;
import java.util.List;



public class group {

    public List<Object> Members ;
    public String Group_name;
    private String admin;
    public group(List<Object> members, String Group_name, String admin ) {
        this.Members = members;
        this.Group_name=Group_name;
        this.admin = admin;

    }

    public List<Object> getMembers() {
        return Members;
    }

    public void setMembers(List<Object> members) {
        Members = members;
    }

    public String getGroup_name() {
        return Group_name;
    }

    public void setGroup_name(String group_name) {
        Group_name = group_name;
    }
    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }
}
