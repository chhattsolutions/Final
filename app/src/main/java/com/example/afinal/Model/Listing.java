package com.example.afinal.Model;

/**
 * Created by Sajid on 1/12/2019.
 */

public class Listing {
    private String bed;
    private String bath;
    private String prie;
    private String squ;
    private String plt;
    private String cites;
    private String selectedCategory;
    private String propertytype;
    private String list1;
    private String propertyfor;
    private String ap;
    private String uid;
    private String Us;
    private String ph;
    private String Propertyphoto;

    public Listing(){}
    public Listing(String bed, String bath , String prie, String squ, String plt, String cites, String selectedCategory, String list1, String propertyfor, String ap,
                   String uid, String Us,String ph, String Propertyphoto) {

        this.ap=ap;
        this.bath=bath;
        this.bed=bed;
        this.cites=cites;
        this.list1=list1;
        this.prie=prie;
        this.squ=squ;
        this.plt=plt;
        this.selectedCategory=selectedCategory;
        this.propertyfor=propertyfor;
        this.uid=uid;
        this.ph=ph;
        this.Propertyphoto=Propertyphoto;
        this.Us=Us;

    }

    public String getUs() {
        return Us;
    }

    public String getPh() {
        return ph;
    }


    public void setUs(String us) {
        Us = us;
    }

    public void setPh(String ph) {
        this.ph = ph;
    }

    public String getPropertyphoto() {
        return Propertyphoto;
    }
    public void setPropertyphoto(String Propertyphoto) {
        this.Propertyphoto = Propertyphoto;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getBed() {
        return bed;
    }

    public void setBed(String bed) {
        this.bed = bed;
    }

    public String getBath() {
        return bath;
    }

    public void setBath(String bath) {
        this.bath = bath;
    }

    public String getPrie() {
        return prie;
    }

    public void setPrie(String prie) {
        this.prie = prie;
    }

    public String getSqu() {
        return squ;
    }

    public void setSqu(String squ) {
        this.squ = squ;
    }

    public String getPlt() {
        return plt;
    }

    public void setPlt(String plt) {
        this.plt = plt;
    }

    public String getCites() {
        return cites;
    }

    public void setCites(String cites) {
        this.cites = cites;
    }

    public String getSelectedCategory() {
        return selectedCategory;
    }

    public void setSelectedCategory(String selectedCategory) {
        this.selectedCategory = selectedCategory;
    }

    public String getPropertytype() {
        return propertytype;
    }

    public void setPropertytype(String propertytype) {
        this.propertytype = propertytype;
    }

    public String getList1() {
        return list1;
    }

    public void setList1(String list1) {
        this.list1 = list1;
    }

    public String getPropertyfor() {
        return propertyfor;
    }

    public void setPropertyfor(String propertyfor) {
        this.propertyfor = propertyfor;
    }

    public String getAp() {
        return ap;
    }

    public void setAp(String ap) {
        this.ap = ap;
    }
}
