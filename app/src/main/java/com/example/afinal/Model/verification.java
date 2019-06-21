package com.example.afinal.Model;

public class verification {
  private   String Agent;
  private   String Company;
  private   String infoid;
  private   String Phone;
  public verification(){}
    public verification(String Agent,String Company,String Phone,String infoid)
    {
        this.Agent=Agent;
        this.Company=Company;
        this.infoid=infoid;
        this.Phone=Phone;
    }

    public String getAgent() {
        return Agent;
    }

    public String getCompany() {
        return Company;
    }

    public String getInfoid() {
        return infoid;
    }

    public String getPhone() {
        return Phone;
    }

    public void setAgent(String agent) {
        Agent = agent;
    }

    public void setCompany(String company) {
        Company = company;
    }

    public void setInfoid(String infoid) {
        this.infoid = infoid;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }
}
