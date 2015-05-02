/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pena.sandra.buscame.rules;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import org.pena.sandra.buscame.model.Post;
import org.pena.sandra.buscame.model.Vocabulary;

/**
 * Class to read documents
 *
 * @author sandra
 */
public class DocumentParser {

    private HashMap<String, Vocabulary> allTerms; //to hold all terms

    public DocumentParser() {
        allTerms = new HashMap<String, Vocabulary>(); //to hold all terms
    }

    /**
     * Method to read files and store in array.
     *
     * @param filePath : source file path
     * @throws FileNotFoundException
     * @throws IOException
     */
    @SuppressWarnings("empty-statement")
    public HashMap<String, Vocabulary> parseFiles(String dirPath, final String[] filesNames) throws FileNotFoundException, IOException {
        BufferedReader in = null;
        File dir = new File(dirPath);
        File[] files;
        if (filesNames.length == 0) {
            files = dir.listFiles(); //parseo todos los archivos
        } else {
            files = dir.listFiles(new FilenameFilter() {
                public boolean accept(File dir, String name) {
                    for (String fileName: filesNames) {
                        if (fileName.equals(name))
                            return true;
                    };
                    return false;
                }
            });
        }
        
        for (File f : files) {
            String fileName = f.getName();
            if (f.getName().endsWith(".txt")) {
                in = new BufferedReader(new FileReader(f));
                StringBuilder sb = new StringBuilder();
                String s = null;
                while ((s = in.readLine()) != null) {
                    sb.append(s); //acumulo todas las lineas del archivo
                }
                String[] tokenizedTerms = sb.toString().replaceAll("[\\W&&[^\\s]]", " ").split("\\W+");   //obtengo una palabra
                for (String term : tokenizedTerms) { //recorro los terminos del archivo
                    Vocabulary voc;
                    if (allTerms.containsKey(term)) { //allTerms es todo mi vocabulario
                        voc = allTerms.get(term);
                    } else {
                        voc = new Vocabulary(term);
                        allTerms.put(term, voc);
                    }

                    HashMap<String, Post> posts = voc.getPosts(); //Obtengo posteos del vocabulario. Primera vez es vacio

                    Post post;
                    if (posts.containsKey(fileName)) {
                        post = posts.get(fileName);
                    } else {
                        post = new Post(fileName);
                        voc.setNr(voc.getNr() + 1); //
                        posts.put(fileName, post);
                    }
                    int tf = post.incrementTf();
                    if (tf > voc.getMaxTf()) {
                        voc.setMaxTf(tf);
                    }
                }
            }
        }

        return allTerms;
    }
}
