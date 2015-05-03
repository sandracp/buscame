/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pena.sandra.buscame.rules;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import static java.util.Collections.list;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.pena.sandra.buscame.db.IndexerDB;
import org.pena.sandra.buscame.model.Post;
import org.pena.sandra.buscame.model.Result;
import org.pena.sandra.buscame.model.Vocabulary;

/**
 *
 * @author sandra
 */
public class Searcher {
    private final int MAX_POSTS = 10;

    public ArrayList<Result> search(String sentence) throws ClassNotFoundException, SQLException {
        ArrayList<Vocabulary> searchVocabulary = convertSentenceToVocabulary(sentence);     
        
        List<Post> candidates = getCandidates(searchVocabulary);
        ArrayList<Result> promoted = promoteCandidates(candidates);

        Collections.sort(promoted, new Comparator<Result>() {
            @Override
            public int compare(Result r1, Result r2) {
                return Double.compare(r2.getWeight(), r1.getWeight());
            }
        });
                
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
        Collections.sort(searchVocabulary, new Comparator<Vocabulary>() {
            @Override
            public int compare(Vocabulary first, Vocabulary second) {
                // menor a mayor Nr
                return first.getNr() - second.getNr(); 
            }
        }); 
        return searchVocabulary;
    }

    private List<Post> getCandidates(ArrayList<Vocabulary> searchVocabulary) throws ClassNotFoundException, SQLException {
        List<Post> posts = new LinkedList<>();
        for(Vocabulary voc: searchVocabulary) {
            // 2- y traemos de su lista de posteo los R(MAX_POSTS) primeros documentos

            // 3- Por otra parte, como el máximo tf está almacenado en cada
            // término del vocabulario, es posible eliminar términos completos sin siquiera ir al
            //disco una vez para mirar su lista de posteo.

            // en que caso el maximo TF hace que ni siquiera vayamos a disco?
            if (voc.getMaxTf() > 0) {
                List<Post> tmpPosts = IndexerDB.getInstance()
                       .getPostsByWord(voc.getTerm(), MAX_POSTS);
                posts.addAll(tmpPosts);
            }
        }
        return posts;
    }

    private ArrayList<Result> promoteCandidates(List<Post> candidates) throws ClassNotFoundException, SQLException {
        
        //4- Para armar el ranking, los documentos se van manteniendo en el orden en que
        //ingresan, pero si al chequear la lista de otro término el mismo documento aparece otra
        //vez, se puede hacer que suba en el ranking general.
        HashMap<String, Result> results = new HashMap<>();
        int N = IndexerDB.getInstance().getTotalDocuments();
        HashMap<String, Vocabulary> allVocabulary = IndexerDB.getInstance().allVocabulary;
        System.out.println(candidates);
        for (Post post: candidates) {
            Vocabulary voc = allVocabulary.get(post.getWord());
            double div = (N * 1.0) / (voc.getNr() * 1.0);
            double weight = post.getTf() * Math.log10(div);
            Result result;
            if (results.containsKey(post.getDocument())) {
                result = results.get(post.getDocument());
            } else {
                result = new Result(post.getDocument());
                results.put(post.getDocument(), result);
            }
            result.addWeight(weight);
        }
        return new ArrayList<Result>(results.values());
    }
}
