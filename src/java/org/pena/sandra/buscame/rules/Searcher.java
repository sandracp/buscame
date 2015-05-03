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
        for(Vocabulary voc: searchVocabulary) {
            System.out.println(voc);
        }
        List<Post> candidates = getCandidates(searchVocabulary);
        List<Post> promoted = promoteCandidates(candidates);
        return promoted;
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
        
        /*1- Se comienza con el término de la consulta que tenga el mayor idf (o sea, la lista de posteo 
        más corta. En nuestro caso, comenzamos por el término con MENOR n r ) */
        Collections.sort(searchVocabulary, new Comparator() {
            @Override
            public int compare(Object a, Object b) {
                Vocabulary first = ((Vocabulary)a);
                Vocabulary second =((Vocabulary)b);
                // menor a mayor Nr
                return first.getNr() - second.getNr(); 
            }
        }); 
        return searchVocabulary;
    }

    private List<Post> getCandidates(ArrayList<Vocabulary> searchVocabulary) throws ClassNotFoundException {
        List<Post> posts = new LinkedList<>();
        for(Vocabulary voc: searchVocabulary) {
            try {
                // 2- y traemos de su lista de posteo los R(MAX_POSTS) primeros documentos
                
                // 3- Por otra parte, como el máximo tf está almacenado en cada
                // término del vocabulario, es posible eliminar términos completos sin siquiera ir al
                //disco una vez para mirar su lista de posteo.
                
                // en que caso el maximo TF hace que ni siquiera vayamos a disco?
                if (voc.getMaxTf() < 0) {
                    List<Post> tmpPosts = IndexerDB.getInstance()
                           .getPostsByWord(voc.getTerm(), MAX_POSTS);
                    posts.addAll(tmpPosts);
                }
            } catch (SQLException ex) {
                Logger.getLogger(Searcher.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return posts;
    }

    private List<Post> promoteCandidates(List<Post> candidates) {
        
        //4- Para armar el ranking, los documentos se van manteniendo en el orden en que
        //ingresan, pero si al chequear la lista de otro término el mismo documento aparece otra
        //vez, se puede hacer que suba en el ranking general.
        return candidates;
    }
}
