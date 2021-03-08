package com.proquest.interview.util;

import java.sql.*;

/**
 * This class is just a utility class, you should not have to change anything here
 * @author rconklin
 */
public class DatabaseUtil {
	public static void initDB() {
		try {
			Connection cn = getConnection();
			Statement stmt = cn.createStatement();
			stmt.execute("CREATE TABLE PHONEBOOK (NAME varchar(255), PHONENUMBER varchar(255), ADDRESS varchar(255))");
			stmt.execute("INSERT INTO PHONEBOOK (NAME, PHONENUMBER, ADDRESS) VALUES('Chris Johnson','(321) 231-7876', '452 Freeman Drive, Algonac, MI')");
			stmt.execute("INSERT INTO PHONEBOOK (NAME, PHONENUMBER, ADDRESS) VALUES('Dave Williams','(231) 502-1236', '285 Huron St, Port Austin, MI')");
			cn.commit();
			cn.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}
	public static Connection getConnection() throws SQLException, ClassNotFoundException {
		Class.forName("org.hsqldb.jdbcDriver");
		return DriverManager.getConnection("jdbc:hsqldb:mem", "sa", "");
	}

	public static void clear(ResultSet resultSet, Statement statement, Connection connection) {
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException throwables) {
				throwables.printStackTrace();
			}
		}
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException throwables) {
				throwables.printStackTrace();
			}
		}
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException throwables) {
				throwables.printStackTrace();
			}
		}
	}
}
