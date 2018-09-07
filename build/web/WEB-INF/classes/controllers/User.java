/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

/**
 *
 * @author Vigneet Sompura
 */
public class User{
    private String Username;
    private String Pass;
    private String Sex;
    private String DOB;
    private String Ethnicity;
    private String Address;
    private float longitude;
    private float latitude;

    
    public User(String Username, String Pass, String Sex, String DOB, String Ethnicity, String Address, float longitude, float latitude) {
        this.Username = Username;
        this.Pass = Pass;
        this.Sex = Sex;
        this.DOB = DOB;
        this.Ethnicity = Ethnicity;
        this.Address = Address;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    User() {
       
    }
    
    public String getUsername() {
        return Username;
    }

    public void setUsername(String Username) {
        this.Username = Username;
    }

    public String getPass() {
        return Pass;
    }

    public void setPass(String Pass) {
        this.Pass = Pass;
    }

    public String getSex() {
        return Sex;
    }

    public void setSex(String Sex) {
        this.Sex = Sex;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getEthnicity() {
        return Ethnicity;
    }

    public void setEthnicity(String Ethnicity) {
        this.Ethnicity = Ethnicity;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }
    
    
    
}
