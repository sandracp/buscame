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
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;


/**
 * Class to read documents
 *
 * @author sandra
 */
public class DocumentParser {
    //This variable will hold all terms of each document in an array.
    private List<String[]> termsDocsArray = new ArrayList<String[]>();
    private HashSet<String> allTerms = new HashSet<String>(); //to hold all terms
    private List<double[]> tfidfDocsVector = new ArrayList<double[]>();

    /**
     * Method to read files and store in array.
     * @param filePath : source file path
     * @throws FileNotFoundException
     * @throws IOException
     */
    @SuppressWarnings("empty-statement")
    public void parseFiles(String dir, String[] files) throws FileNotFoundException, IOException {
        BufferedReader in = null;
        int n = 0;
        for (String fileName : files) {
            File f = new File(dir, fileName);
            System.out.println(String.format("File %d: %s", n, f.getName()));
            n += 1;
            if (f.getName().endsWith(".txt")) {
            	in = new BufferedReader(new FileReader(f));
                StringBuilder sb = new StringBuilder();
                String s = null;
                while ((s = in.readLine()) != null) {
                    sb.append(s);
                }
                String[] tokenizedTerms = sb.toString().replaceAll("[\\W&&[^\\s]]", "").split("\\W+");   //to get individual terms
                for (String term : tokenizedTerms) {
                    allTerms.add(term);       
                }
                termsDocsArray.add(tokenizedTerms);
            }
        }
    }

    /**
     * Method to create termVector according to its tfidf score.
     * @return 
     */
    public List<double[]> tfIdfCalculator() {
        double tf; //term frequency
        double idf; //inverse document frequency
        double tfidf; //term requency inverse document frequency        
        for (String[] docTermsArray : termsDocsArray) {
            double[] tfidfvectors = new double[allTerms.size()];
            int count = 0;
            for (String terms : allTerms) {
                tf = new TfIdf().tfCalculator(docTermsArray, terms);
                idf = new TfIdf().idfCalculator(termsDocsArray, terms);
                tfidf = tf * idf;
                tfidfvectors[count] = tfidf;
                count++;
            }
            tfidfDocsVector.add(tfidfvectors);  //storing document vectors;            
        }
        return tfidfDocsVector;
    }
}
