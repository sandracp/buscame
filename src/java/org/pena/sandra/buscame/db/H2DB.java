/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pena.sandra.buscame.db;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.h2.tools.DeleteDbFiles;
/**
 * Para crear la base de datos si no existe
 * @author sandra
 */
public class H2DB {
   
	public static void contextInitialized(String databaseName) {

		// Comprobamos si no existe la base de datos
		String home = System.getProperty("user.home");
		String pathToFile = String.format("%s/%s.h2.db", home, databaseName);
		boolean exists = new File(pathToFile).exists();

		try {
			Class.forName("org.h2.Driver");
			// Si no existe, llamamos al metodo initDb()
			if (!exists) {
				initDb(databaseName);
			}
		} catch (Exception e) {
		}
	}

	//Ejecuta el archivo init.sql para crear la base de datos
	private static void initDb(String databaseName) throws SQLException {
		Connection conn = DriverManager.getConnection("jdbc:h2:~/"
				+ databaseName);
		Statement stat = conn.createStatement();
		stat.execute("runscript from 'init.sql'");
		stat.close();
		conn.close();
	}

	public static void deleteDatabase(String databaseName) {
		DeleteDbFiles.execute("~", databaseName, true);
	}
}


