/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pena.sandra.buscame.rules;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import org.pena.sandra.buscame.db.IndexerDB;
import org.pena.sandra.buscame.model.Post;
import org.pena.sandra.buscame.model.Vocabulary;

/**
 * Class to read documents
 *
 * @author sandra
 */
public class Indexer {

    private HashMap<String, Vocabulary> allTerms; //to hold all terms
    private HashMap<String, Post> n;

    public Indexer() {
        allTerms = new HashMap<String, Vocabulary>(); //to hold all terms
    }

    /**
     * Method para leer un archivo y almacenar un array.
     *
     * @param filePath : source file path
     * @throws FileNotFoundException
     * @throws IOException
     */
    @SuppressWarnings("empty-statement")
    public HashMap<String, Vocabulary> parseFiles(String dirPath, final String[] filesNames) throws FileNotFoundException, IOException, ClassNotFoundException, SQLException {
        BufferedReader in = null;
        File dir = new File(dirPath);
        File[] files;
        if (filesNames.length == 0) {
            files = dir.listFiles(); //parseo todos los archivos
        } else {
            files = dir.listFiles(new FilenameFilter() {
                public boolean accept(File dir, String name) {
                    for (String fileName : filesNames) {
                        if (fileName.equals(name)) {
                            return true;
                        }
                    };
                    return false;
                }
            });
        }

        for (File f : files) {
            String fileName = f.getName();
            if (fileName.endsWith(".txt")) {
                in = new BufferedReader(new FileReader(f));
                StringBuilder sb = new StringBuilder();
                String s;
                HashMap<String, Post> posts = new HashMap<>();

                while ((s = in.readLine()) != null) {
                    sb.append(s); //acumulo todas las lineas del archivo
                }
                String[] tokenizedTerms = sb.toString().split("\\W");   //obtengo una palabra
                for (String term : tokenizedTerms) { //recorro los terminos del archivo
                    if (isValidTerm(term)) {
                        Vocabulary voc;
                        if (allTerms.containsKey(term)) { //allTerms es todo mi vocabulario
                            voc = allTerms.get(term);
                        } else { //termino no existe
                            voc = new Vocabulary(term);
                            allTerms.put(term, voc); //Agrego nuevo vocabulario a la lista
                        }

                        Post post;
                        if (posts.containsKey(term)) {
                            post = posts.get(term); //Obtengo posteo de la palabra
                        } else { //Creo nuevo posteo. Relaciono nuevo vocabulario con documento
                            post = new Post(term, fileName, 0);
                            voc.setNr(voc.getNr() + 1); //Incremento la cantidad de archivos en los que encuentro la palabra
                            posts.put(term, post);
                        }
                        int tf = post.incrementTf();
                        if (tf > voc.getMaxTf()) { //pregunto por el maximo Tf del documento
                            voc.setMaxTf(tf); //actualizo el maximo Tf
                        }
                    }
                }

                for (Post post : posts.values()) {
                    IndexerDB.getInstance().save(post);
                }
            }
        }
        IndexerDB.getInstance().commit();
        return allTerms;
    }

    /*
    Agregar reglas aca que hagan que una palabra no sea valida
    */
    private boolean isValidTerm(String term) {
        if (term.length() < 100) {
            return true;
        }
        return false;
    }
}
