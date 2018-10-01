package Singleton;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class SingletonConnection {

	private static String url; 
	private static String user;

	private static String passwd;

	
	private static Connection connection;
	
	
	public static Connection getConnection() {
		if(connection == null) {
			try {
				Properties prop = new Properties();
				InputStream input = null;
				
				input = SingletonConnection.class.getResourceAsStream("/config.properties");
				
				// load a properties file
				prop.load(input);

				// get the property value
				url = prop.getProperty("url");
				user = prop.getProperty("user");
				passwd = prop.getProperty("password");
				
//				String antislash = System.getProperty("file.separator");
//				url = "jdbc"+antislash+":postgresql"+antislash+"://postgresql-lmad.alwaysdata.net:5432/lmad_database";
//				user = "lmad";
//				passwd = "JosephGrellier";
//				
				Class.forName("org.postgresql.Driver");
				connection = DriverManager.getConnection(url, user, passwd);
			} catch (SQLException | ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
		}
		return connection;
	}
	
}