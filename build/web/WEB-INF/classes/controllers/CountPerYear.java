/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

public class CountPerYear {
    String name;
    String data;

    public CountPerYear(String label, String data) {
        this.name = label;
        this.data = data;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String label) {
        this.name = label;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
 
    
}
