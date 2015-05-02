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
public class Post implements Comparable {
    private String document;
    private int tf;

    public Post(String document, int tf) {
        this.document = document;
        this.tf = tf;
    }

    public Post(String document) {
        this(document, 0);
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }
    
    public int getTf() {
        return tf;
    }

    public void setTf(int tf) {
        this.tf = tf;
    }
    
    @Override
    public int compareTo(Object obj) {
        Post post = (Post) obj;
        return tf - post.tf;
    }

    @Override
    public String toString() {
        return String.format("%s (tf=%d)", document, tf);
    }

    public int incrementTf() {
        tf += 1;
        return tf;
    }
}
