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
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.pena.sandra.buscame.db.IndexerDB;
import org.pena.sandra.buscame.model.Post;
import org.pena.sandra.buscame.model.Vocabulary;

/**
 *
 * @author sandra
 */
public class IndexerTest {
    
    public IndexerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        IndexerDB.fileName = "buscame-test"; 
    }
    
    @After
    public void tearDown() {
        IndexerDB.deleteDatabase();
    }

    /**
     * Test of parseFiles method, of class Indexer.
     */
    @Test
    public void testParseFiles() throws Exception {
        String dir = "/home/sandra/buscame/DocumentosTP1";
        String[] files = new String[] {};
        Indexer instance = new Indexer();
        instance.parseFiles(dir, files);
        assertEquals(2008319, IndexerDB.allVocabulary.size());
    }
    
    @Test
    public void testParseFiles2() throws Exception {
        String dir = "/home/javier/src/sandra/buscame/DocumentosTP2";
        String[] files = new String[] {};
        Indexer instance = new Indexer();
        instance.parseFiles(dir, files);
        assertEquals(370545, IndexerDB.allVocabulary.size());
    }
    
    private void writeText(String dir, String fileName, String text) throws IOException {
        File output = new File(dir, fileName);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(output))) {
            writer.write(text);
        }
    }
    
    @Test
    public void testParseSmallFiles() throws Exception {
        String dir = "/tmp";
        writeText(dir, "buscame-test1.txt", "Mi texto es asi\nMi resultado es 6");
        String[] files = new String[] {"buscame-test1.txt"};
        
        Indexer instance = new Indexer();
        instance.parseFiles(dir, files);
        assertEquals(6, IndexerDB.allVocabulary.size());
    }
    
    @Test
    public void testParseSmallFiles2() throws Exception {
        String dir = "/tmp";
        writeText(dir, "buscame-test2.txt", "Mi texto es asi \nMi resultado es 6");
        writeText(dir, "buscame-test3.txt", "Otro texto con 5 nuevas palabras");
        
        String[] files = new String[] {"buscame-test2.txt", "buscame-test3.txt"};
        
        Indexer instance = new Indexer();
        instance.parseFiles(dir, files);
        assertEquals(11, IndexerDB.allVocabulary.size());
        
        List<Post> posteos = IndexerDB.getInstance().getPostsByWord("Mi");
        assertEquals("Mi", posteos.get(0).getWord());
        assertEquals("buscame-test2.txt", posteos.get(0).getDocument());
        assertEquals(2, posteos.get(0).getTf());
    }
    
}
