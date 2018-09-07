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
public class VictimProfile {
    private String Lage;
    private String Rage;
    private String Gender;
    private String Descent;
    private String perc;

    public VictimProfile(String Lage, String Rage, String Gender, String Descent, String perc) {
        this.Lage = Lage;
        this.Rage = Rage;
        this.Gender = Gender;
        this.Descent = Descent;
        this.perc = perc;
    }

    public String getLage() {
        return Lage;
    }

    public void setLage(String Lage) {
        this.Lage = Lage;
    }

    public String getRage() {
        return Rage;
    }

    public void setRage(String Rage) {
        this.Rage = Rage;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String Gender) {
        this.Gender = Gender;
    }

    public String getDescent() {
        return Descent;
    }

    public void setDescent(String Descent) {
        this.Descent = Descent;
    }

    public String getPerc() {
        return perc;
    }

    public void setPerc(String perc) {
        this.perc = perc;
    }
    
    
}
