/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utn.frc.dlc.buscame.db;

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
import utn.frc.dlc.buscame.model.Post;
import utn.frc.dlc.buscame.model.Vocabulary;

/**
 *
 * @author Peña - Ligorria
 */
public class IndexerDB {

    public HashMap<String, Vocabulary> allVocabulary;
    public static String pathFormat = "jdbc:h2:~/%s";
    public static String fileName;
    private static IndexerDB instance;
    private Connection conn;
    private int totalDocuments;

    public void setTotalDocuments(int totalDocuments) {
        this.totalDocuments = totalDocuments;
    }

    public int getTotalDocuments() {
        return totalDocuments;
    }

    public static IndexerDB getInstance() throws ClassNotFoundException, SQLException {
        if (instance == null) {
            if (fileName == null) {
                fileName = "buscame";
            }
            instance = new IndexerDB();
            instance.configureDB();
            instance.loadVocabulary();
            instance.countDocuments();
        }
        return instance;
    }

    private Connection getConnection() throws SQLException {
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

    public void save(Post post) throws SQLException {
        Statement statement = getConnection().createStatement();
        String query = String.format("insert into posteo(word, document, tf) values ('%s', '%s', %d);",
                post.getWord(), post.getDocument(), post.getTf());
        int result = statement.executeUpdate(query);
        statement.close();
    }

    public List<Post> getPostsByWord(String word, int max) throws SQLException {
        Statement statement = getConnection().createStatement();
        String query = String.format("select top %d word, document, tf from posteo where word='%s' order by tf desc", max, word);
        List<Post> results = new LinkedList<>();
        ResultSet r = statement.executeQuery(query);
        while (r.next()) {
            Post post = new Post(r.getString("word"),
                    r.getString("document"),
                    r.getInt("tf"));
            results.add(post);
        }

        close();
        return results;
    }

    public HashMap<String, Vocabulary> loadVocabulary() throws SQLException {
        Statement statement = getConnection().createStatement();
        String query = "select word, count(*) as nr, max(tf) as maxtf from posteo group by word";
        HashMap<String, Vocabulary> results = new HashMap<String, Vocabulary>();
        try (ResultSet r = statement.executeQuery(query)) {
            while (r.next()) {
                Vocabulary voc = new Vocabulary(r.getString("word"),
                        r.getInt("nr"),
                        r.getInt("maxtf"));
                results.put(r.getString("word"), voc);
            }
        }
        close();
        allVocabulary = results;
        return results;
    }

    public boolean wasParsed(String document) throws SQLException {
        Statement statement = getConnection().createStatement();
        String query = String.format("select count(*) as c from posteo where document='%s'", document);
        boolean result = false;
        try (ResultSet r = statement.executeQuery(query)) {
            if (r.next()) {
                result = r.getInt("c") > 0;
            }
        }
        close();
        return result;
    }

    public void countDocuments() throws SQLException {
        Statement statement = getConnection().createStatement();
        String query = "select count(distinct document) as c from posteo";
        int result = 0;
        try (ResultSet r = statement.executeQuery(query)) {
            if (r.next()) {
                result = r.getInt("c");
            }
        }
        close();
        totalDocuments = result;
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

    public void incrementTotalDocuments() {
        this.totalDocuments += 1;
    }
}
