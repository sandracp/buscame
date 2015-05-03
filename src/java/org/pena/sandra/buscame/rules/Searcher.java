/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pena.sandra.buscame.rules;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.pena.sandra.buscame.db.IndexerDB;
import org.pena.sandra.buscame.model.Post;

/**
 *
 * @author sandra
 */
public class Searcher {

    public List<Post> search(String sentence) {
        try {
           String[] words = sentence.split("\\W");
           List<Post> posts = new LinkedList<>();
            for (String word: words) {
                posts = IndexerDB.getInstance().getPostsByWord(word);
            }
            return posts;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Searcher.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Searcher.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Searcher.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new LinkedList<>();
    }
}
