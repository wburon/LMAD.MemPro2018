package Singleton;

import java.sql.Connection;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Connection SC = SingletonConnection.getConnection();
	}

}
