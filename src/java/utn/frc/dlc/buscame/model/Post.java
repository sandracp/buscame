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
public class Post implements Comparable {
    private String word;
    private String document;
    private int tf;

    public Post(String word, String document, int tf) {
        this.word = word;
        this.document = document;
        this.tf = tf;
    }

    public Post(String word, String document) {
        this(word, document, 0);
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
        return String.format("%s, %s (tf=%d)", word, document, tf);
    }

    public int incrementTf() {
        tf += 1;
        return tf;
    }
      public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}
