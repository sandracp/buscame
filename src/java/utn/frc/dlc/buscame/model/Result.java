/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utn.frc.dlc.buscame.model;

/**
 *
 * @author Peña - Ligorria
 */
public class Result {
    private String document;
    private double weight;

    public Result(String name) {
        this.document = name;
        this.weight = 0;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
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
