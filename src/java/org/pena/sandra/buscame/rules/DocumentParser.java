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
import org.pena.sandra.buscame.model.Term;

/**
 * Class to read documents
 *
 * @author sandra
 */
public class DocumentParser {

    private HashMap<String, Term> allTerms; //to hold all terms

    public DocumentParser() {
        allTerms = new HashMap<String, Term>(); //to hold all terms
    }

    /**
     * Method to read files and store in array.
     *
     * @param filePath : source file path
     * @throws FileNotFoundException
     * @throws IOException
     */
    @SuppressWarnings("empty-statement")
    public HashMap<String, Term> parseFiles(String dirPath, final String[] filesNames) throws FileNotFoundException, IOException {
        BufferedReader in = null;
        File dir = new File(dirPath);
        File[] files;
        if (filesNames.length == 0) {
            files = dir.listFiles();
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
                    sb.append(s);
                }
                String[] tokenizedTerms = sb.toString().replaceAll("[\\W&&[^\\s]]", " ").split("\\W+");   //to get individual terms
                for (String term : tokenizedTerms) {
                    Term voc;
                    if (allTerms.containsKey(term)) {
                        voc = allTerms.get(term);
                    } else {
                        voc = new Term(term);
                        allTerms.put(term, voc);
                    }

                    HashMap<String, Post> posts = voc.getPosts();

                    Post post;
                    if (posts.containsKey(fileName)) {
                        post = posts.get(fileName);
                    } else {
                        post = new Post(fileName);
                        voc.setNr(voc.getNr() + 1);
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
