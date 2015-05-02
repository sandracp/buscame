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
    private HashMap<String, Post> posts;
    private String term; 
    private int nr;
    private int maxTf;

    public Vocabulary(String term) {
        this.term = term;
        this.maxTf = 0;
        this.posts = new HashMap<>();
    }
    
    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
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

    public HashMap<String, Post> getPosts() {
        return posts;
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
                .append(" [posts=");
        for(String key: posts.keySet()) {
             sb.append(posts.get(key)).append(", ");
        }
        sb.append("]}");
        return sb.toString();
    }
    
    
}