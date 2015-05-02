/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pena.sandra.buscame.rules;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.pena.sandra.buscame.model.Term;

/**
 *
 * @author javier
 */
public class DocumentParserTest {
    
    public DocumentParserTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of parseFiles method, of class DocumentParser.
     */
    @Test
    public void testParseFiles() throws Exception {
        String dir = "/home/javier/src/sandra/buscame/DocumentosTP1";
        String[] files = new String[] {};
        DocumentParser instance = new DocumentParser();
        HashMap<String, Term> allTerms = instance.parseFiles(dir, files);
        assertEquals(2008319, allTerms.size());
    }
    
    private void writeText(String dir, String fileName, String text) throws IOException {
        File output = new File(dir, fileName);
        BufferedWriter writer = new BufferedWriter(new FileWriter(output));
        writer.write(text);
        writer.close();
    }
    
    @Test
    public void testParseSmallFiles() throws Exception {
        String dir = "/tmp";
        writeText(dir, "buscame-test1.txt", "Mi texto es asi\nMi resultado es 6");
        String[] files = new String[] {"buscame-test1.txt"};
        
        DocumentParser instance = new DocumentParser();
        HashMap<String, Term> vocabulary = instance.parseFiles(dir, files);
        assertEquals(6, vocabulary.size());
    }
    
    @Test
    public void testParseSmallFiles2() throws Exception {
        String dir = "/tmp";
        writeText(dir, "buscame-test2.txt", "Mi texto es asi\nMi resultado es 6");
        writeText(dir, "buscame-test3.txt", "Otro texto con 5 nuevas palabras");
        
        String[] files = new String[] {"buscame-test2.txt", "buscame-test3.txt"};
        
        DocumentParser instance = new DocumentParser();
        HashMap<String, Term> vocabulary = instance.parseFiles(dir, files);
        assertEquals(11, vocabulary.size());
        assertEquals(11, vocabulary.size());
    }
    
}