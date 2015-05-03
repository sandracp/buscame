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

    public Indexer() {
    }

    /**
     * Method para leer un archivo y almacenar un array.
     *
     * @param dirPath
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void parseFiles(String dirPath, final String[] filesNames) throws FileNotFoundException, IOException, ClassNotFoundException, SQLException {
        HashMap<String, Vocabulary> allVocabulary = IndexerDB.getInstance().allVocabulary;
        File[] files = getFilesToParse(dirPath, filesNames);

        for (File f : files) {
            String fileName = f.getName();
            String filePath = f.getAbsolutePath();
            if (!IndexerDB.getInstance().wasParsed(filePath)) {
                if (fileName.endsWith(".txt")) {
                    BufferedReader in = new BufferedReader(new FileReader(f));
                    StringBuilder sb = new StringBuilder();
                    String s;
                    HashMap<String, Post> posts = new HashMap<>();

                    while ((s = in.readLine()) != null) {
                        sb.append(s.toLowerCase()); //acumulo todas las lineas del archivo
                    }
                    String[] tokenizedTerms = sb.toString().split("\\W");   //obtengo una palabra
                    for (String term : tokenizedTerms) { //recorro los terminos del archivo
                        if (isValidTerm(term)) {
                            Vocabulary voc;
                            if (allVocabulary.containsKey(term)) { //allTerms es todo mi vocabulario
                                voc = allVocabulary.get(term);
                            } else { //termino no existe
                                voc = new Vocabulary(term);
                                allVocabulary.put(term, voc); //Agrego nuevo vocabulario a la lista
                            }

                            Post post;
                            if (posts.containsKey(term)) {
                                post = posts.get(term); //Obtengo posteo de la palabra
                            } else { //Creo nuevo posteo. Relaciono nuevo vocabulario con documento
                                post = new Post(term, filePath, 0);
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
                    
                    IndexerDB.getInstance().commit();
                }
            }
        }
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

    private File[] getFilesToParse(String dirPath, final String[] filesNames) {
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
        return files;
    }
}
