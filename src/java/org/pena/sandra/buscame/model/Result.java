/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pena.sandra.buscame.model;

/**
 *
 * @author sandra
 */
public class Result {
    private String name;
    private double weight;

    public Result(String name) {
        this.name = name;
        this.weight = 0;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
    
    public void addWeight(double w) {
        this.weight += w;
    }
}
