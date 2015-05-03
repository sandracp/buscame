/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pena.sandra.buscame.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.lang.String;
import org.h2.tools.DeleteDbFiles;
import org.pena.sandra.buscame.model.Post;
import org.pena.sandra.buscame.model.Vocabulary;
/**
 *
 * @author sandra
 */
public class IndexerDB {
    public static String pathFormat = "jdbc:h2:~/%s";
    public static String fileName;
    private static IndexerDB instance;
    private Connection conn;
    
    public static IndexerDB getInstance () throws ClassNotFoundException, SQLException {
        if (instance == null) {
            if (fileName == null) {
                fileName = "buscame";
            }
            instance = new IndexerDB();
            instance.configureDB();
        }
        return instance;
    }
    
    private Connection getConnection () throws SQLException {
        if (conn == null || conn.isClosed()) {
            conn = DriverManager.getConnection(
            String.format(pathFormat, fileName), "sa", "");
            conn.setAutoCommit(false);
        }
        return conn;
    }
    
    public IndexerDB() throws ClassNotFoundException, SQLException {
        Class.forName("org.h2.Driver");
    }

    public void configureDB() throws ClassNotFoundException, SQLException {
        try (Statement statement = getConnection().createStatement()) {
            try {
                statement.execute("create table if not exists posteo(id int auto_increment primary key, word varchar(255), document varchar(255), tf int);");
                this.commit();
            } catch (SQLException sqle) {
                System.out.println("Table couldn't be created");
            }
        }    
    }

    public void save(Post post)  throws SQLException {
        Statement statement = getConnection().createStatement();
        String query = String.format("insert into posteo(word, document, tf) values ('%s', '%s', %d);",
                post.getWord(), post.getDocument(), post.getTf());
        int result = statement.executeUpdate(query);
        statement.close();
    }

    public List<Post> get(String word) throws Exception {
        Statement statement = getConnection().createStatement();
        String query = String.format("select * from posteo where word='%s'", word);
        List<Post> results;
        try (ResultSet r = statement.executeQuery(query)) {
            results = new LinkedList<>();
            while (r.next()) {
                Post post= new Post(r.getString("word"), 
                        r.getString("document"),
                        r.getInt("tf"));
                results.add(post);
            }
        }
        close();
        return results;
    }
    
    public HashMap<String, Vocabulary> loadVocabulary() throws Exception {
        Statement statement = getConnection().createStatement();
        String query = "select word, count(*) as nr, max(tf) as maxtf from posteo group by word";
        HashMap<String, Vocabulary> results;
        try (ResultSet r = statement.executeQuery(query)) {
           results = new HashMap<String, Vocabulary>();
            while (r.next()) {
                Vocabulary voc= new Vocabulary(r.getString("word"), 
                        r.getInt("nr"),
                        r.getInt("maxtf"));
                results.put(r.getString("word"), voc);
            }
        }
        close();
        return results;
    }
    
    public static void deleteDatabase() {
        DeleteDbFiles.execute("~", fileName, true);
    }
    
    public synchronized void commit() {
        try {
            this.conn.commit();
            close();
        } catch (SQLException ex) {
        }
    }

    public synchronized void close() {
        try {
            this.conn.close();
        } catch (SQLException ex) {
        }
    }
}