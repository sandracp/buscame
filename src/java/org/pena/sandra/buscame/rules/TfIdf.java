/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pena.sandra.buscame.rules;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase para calcular Tf e Idf de una palabra.
 * @author sandra
 */
public class TfIdf {
    
   /**
     * Calculo del tf del termino termToCheck
     * @param totalterms : Array de todas las palabras en el documento procesado.
     * @param termToCheck : termino al cual le calculo tf.
     * @return tf(term frequency) del termino termToCheck
     */
    public double tfCalculator(String[] totalterms, String termToCheck) {
        double count = 0;  //cuento el total de ocurrencias de termToCheck
        for (String s : totalterms) {
            if (s.equalsIgnoreCase(termToCheck)) {
                count++;
            }
        }
        return count / totalterms.length; //Retorno el valor de tf
    }
    
    /**
     * Calculo idf del termino termToCheck
     * @param allTerms : todos los terminos de todos los documentos
     * @param termToCheck
     * @return idf(inverse document frequency)
     */
    public double idfCalculator(List<String[]> allTerms, String termToCheck) {
        double count = 0; //Almacena la cantidad de documentos en los que aparece termToCheck
        for (String[] ss : allTerms) {
            for (String s : ss) {
                if (s.equalsIgnoreCase(termToCheck)) {
                    count++;
                    break;
                }
            }
        }
        return Math.log(allTerms.size() / count); //Retorno el valor de idf
    }
}
