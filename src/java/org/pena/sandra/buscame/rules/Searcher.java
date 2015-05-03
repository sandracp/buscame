/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pena.sandra.buscame.rules;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import static java.util.Collections.list;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.pena.sandra.buscame.db.IndexerDB;
import org.pena.sandra.buscame.model.Post;
import org.pena.sandra.buscame.model.Vocabulary;

/**
 *
 * @author sandra
 */
public class Searcher {
    private final int MAX_POSTS = 10;

    public List<Post> search(String sentence) throws ClassNotFoundException, SQLException {
        ArrayList<Vocabulary> searchVocabulary = convertSentenceToVocabulary(sentence);
        List<Post> posts = new LinkedList<>();
        for(Vocabulary voc: searchVocabulary) {
            try {
                List<Post> tmpPosts = IndexerDB.getInstance()
                        .getPostsByWord(voc.getTerm(), MAX_POSTS);
                posts.addAll(tmpPosts);
            } catch (SQLException ex) {
                Logger.getLogger(Searcher.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return posts;
    }

    private ArrayList<Vocabulary> convertSentenceToVocabulary(String sentence) throws SQLException, ClassNotFoundException {
        String[] words = sentence.split("\\W");
        ArrayList<Vocabulary> searchVocabulary = new ArrayList<>();
        for (String word: words) {
            Vocabulary voc = IndexerDB.getInstance().allVocabulary.get(word.toLowerCase());
            if (voc != null) {
                searchVocabulary.add(voc);
            }
        }
        
        Collections.sort(searchVocabulary, new Comparator() {
            @Override
            public int compare(Object a, Object b) {
                Vocabulary first = ((Vocabulary)a);
                Vocabulary second =((Vocabulary)b);
                return first.getNr() - second.getNr();
            }
        }); 
        return searchVocabulary;
    }
}
