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
import java.util.LinkedList;
import java.util.List;
import org.pena.sandra.buscame.model.Result;

/**
 *
 * @author sandra
 */
public class IndexerDB {
    String path = String.format("jdbc:h2:%s", "~/buscame");
    public IndexerDB() throws ClassNotFoundException {
        Class.forName("org.h2.Driver");
    }

    public void configureDB() throws ClassNotFoundException, SQLException {
        Connection conn = DriverManager.getConnection(path, "sa", "");
        Statement statement = conn.createStatement();
        try {
            statement.execute("create table if not exists words(id int auto_increment primary key, name varchar(255));");
        } catch (SQLException sqle) {
            System.out.println("Table couldn't be created");
        }
        statement.close();
        conn.close();
    }

    public void save(String word) throws SQLException {
        Connection conn = DriverManager.getConnection(path, "sa", "");
        Statement statement = conn.createStatement();
        String query = String.format("insert into words(name) values ('%s');",
                word);
        statement.execute(query);
        conn.close();
    }

    public List<Result> find(String word) throws Exception {
        Connection conn = DriverManager.getConnection(path, "sa", "");
        Statement statement = conn.createStatement();
        String query = String.format("select * from words where name='%s'", word);
        ResultSet r = statement.executeQuery(query);
        List<Result> results = new LinkedList<Result>();
        while (r.next()) {
            results.add(new Result(r.getString("name")));
        }
        
        r.close();
        conn.close();
        return results;
    }
}
