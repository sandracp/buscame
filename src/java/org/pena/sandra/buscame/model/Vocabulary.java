/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pena.sandra.buscame.model;

import java.util.HashMap;
import java.util.TreeSet;

/**
 *
 * @author sandra
 */
public class Vocabulary {
    private String term; 
    private int nr;
    private int maxTf;

    public Vocabulary(String term) {
        this.term = term.toLowerCase();
        this.maxTf = 0;
        this.nr = 0;
    }

    public Vocabulary(String term, int nr, int maxTf) {
        this.term = term.toLowerCase();
        this.nr = nr;
        this.maxTf = maxTf;
    }
    
    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term.toLowerCase();
    }

    public int getMaxTf() {
        return maxTf;
    }

    public void setMaxTf(int maxTf) {
        if (maxTf > this.maxTf) {
            this.maxTf = maxTf;
        }
    }
    
    public int getNr() {
        return nr;
    }

    public void setNr(int nr) {
        this.nr = nr;
    }
    
    @Override
    public boolean equals( Object other ) {
        if( this == other ) { return true; }
        if( other instanceof Vocabulary ) {
            Vocabulary v2 = ( Vocabulary ) other;
            return this.term.equals(v2.term);
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Vocabulary{term=")
                .append(term)
                .append(", nr=")
                .append(nr)
                .append(", maxTf=")
                .append(maxTf)
                .append("}");
        return sb.toString();
    }
    
    
}