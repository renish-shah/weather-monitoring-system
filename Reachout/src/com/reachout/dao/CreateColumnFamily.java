/**
 * 
 */
package com.reachout.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author RENISH
 * 
 */
public class CreateColumnFamily {

	private static java.sql.Connection con = null;

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		try {
			
			con = getCassandraConnection();

			createColumnFamilies();

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static Connection getCassandraConnection()
			throws ClassNotFoundException {
		Class.forName("org.apache.cassandra.cql.jdbc.CassandraDriver");
		try {
			return con = DriverManager
					.getConnection("jdbc:cassandra://localhost:9160/test1");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	public static void createColumnFamilies() throws SQLException {

		try {
			con = getCassandraConnection();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String data = "create columnfamily topic"
				+ " (KEY text primary key, topic_title varchar, topic_desc varchar, created_date varchar,community_id varchar, created_by varchar)";
		Statement st = con.createStatement();
		st.execute(data);

		data = "create columnfamily topicComment"
				+ " (KEY text primary key, topic_id varchar, comment_by varchar, comment_date varchar, comment_text varchar)";

		st = con.createStatement();
		st.execute(data);

		data = "create columnfamily charity"
				+ " (KEY text primary key, charity_name varchar, charity_desc varchar, "
				+ "created_by varchar, community_id varchar, username varchar, amount varchar)";
		st = con.createStatement();
		st.execute(data);

		data = "create columnfamily donation"
				+ " (KEY text primary key, charity_id varchar, community_id varchar, username varchar, amount varchar)";

		st = con.createStatement();
		st.execute(data);

		st.close();

	}

}
