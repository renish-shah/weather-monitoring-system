package com.reachout.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.reachout.ws.domain.Charity;
import com.reachout.ws.domain.Donation;
import com.reachout.ws.domain.EventCommunity;
import com.reachout.ws.domain.Topic;
import com.reachout.ws.domain.TopicComment;

public class EventDao {

	private static java.sql.Connection con = null;

	public static void main(String[] args) throws SQLException,
			ClassNotFoundException {

		EventDao sample = new EventDao();
		sample.createEventColumnFamily();

		EventCommunity event = new EventCommunity();
		event.setDate("22222");
		event.setName("name");
		event.setPlace("place");
		event.setCreated_by("me");
		// topic.setId("key");

		event = sample.populateEvent(event);

		sample.retrieveEventById("1");
		try {
			// createColumnFamily("topic");
			// pouplateData();
			// deleteData();
			// updateData();
			listEvents();
			// dropColumnFamily("news");

		} catch (SQLException e) {
			// log and throw exceptionfox
			throw e;
		}

	}

	public static void createEventColumnFamily() throws SQLException {

		try {
			con = getCassandraConnection();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String data = "create columnfamily eventCommunity"
				+ " (KEY text primary key, name varchar, date varchar, "
				+ "place varchar, created_by varchar)";
		Statement st = con.createStatement();
		st.execute(data);

		st.close();

	}

	public static void dropColumnFamily(String name) throws SQLException {

		String data = "drop columnfamily " + name + ";";
		Statement st = con.createStatement();
		st.execute(data);
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

	public static EventCommunity populateEvent(EventCommunity event)
			throws SQLException, ClassNotFoundException {

		Connection con = getCassandraConnection();

		int eventId = new EventDao().getColumnCount("eventCommunity") + 1;

		// String data = "create columnfamily charity"
		// +
		// " (KEY text primary key, charity_name varchar, charity_desc varchar, "
		// +
		// "created_by varchar, community_id varchar, username varchar, amount varchar)";

		/*
		 * private String id; private String name; private String place; private
		 * String date;
		 */
		String data = ""
				+ "insert into eventCommunity (key, name, place, date, created_by) "
				+ "values ('" + eventId + "','" + event.getName() + "','"
				+ event.getPlace() + "','" + event.getDate() + "','"
				+ event.getCreated_by() + "')";

		System.out.println("SQL :" + data);

		event.setId("" + eventId);

		Statement st = con.createStatement();
		st.executeUpdate(data);

		con.close();

		return event;
	}

//	public static void deleteData() throws SQLException {
//		String data = "BEGIN BATCH \n"
//				+ "delete from  news where key='user1' \n"
//				+ "delete  category from  news where key='user2' \n"
//				+ "APPLY BATCH;";
//		Statement st = con.createStatement();
//		st.executeUpdate(data);
//	}
//
//	public static void updateData() throws SQLException {
//		String t = "update news set category='sports', linkcounts=1 where key='user3'";
//		Statement st = con.createStatement();
//		st.executeUpdate(t);
//	}

	public static List<EventCommunity> listEvents() throws SQLException {

		try {
			con = getCassandraConnection();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String t = "SELECT * FROM eventCommunity";
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(t);
		System.out.println("Column Count:" + rs.getMetaData().getColumnCount());

		List<EventCommunity> events = new ArrayList<EventCommunity>();
		while (rs.next()) {

			EventCommunity event = new EventCommunity();
			event.setDate(rs.getString("date"));
			event.setName(rs.getString("name"));
			event.setPlace(rs.getString("place"));
			event.setId(rs.getString("KEY"));
			event.setCreated_by(rs.getString("created_by"));

			events.add(event);
		}
		con.close();
		return events;
	}

	public EventCommunity retrieveEventById(String eventId) {

		// String data = "create columnfamily charity"
		// +
		// " (KEY text primary key, charity_name varchar, charity_desc varchar, "
		// +
		// "created_by varchar, community_id varchar, username varchar, amount varchar)";
		try {
			Connection con = getCassandraConnection();
			Statement st = con.createStatement();
			String sql = "select * from eventCommunity where key = '" + eventId
					+ "'";
			ResultSet rs = st.executeQuery(sql);
			EventCommunity event = new EventCommunity();
			while (rs.next()) {

				event.setDate(rs.getString("date"));
				event.setName(rs.getString("name"));
				event.setPlace(rs.getString("place"));
				event.setId(rs.getString("KEY"));
				event.setCreated_by(rs.getString("created_by"));
			}

			con.close();
			return event;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	public List<EventCommunity> retrieveEventByName(String eventName) {

		try {
			con = getCassandraConnection();
			Statement st = con.createStatement();
			String sql = "select * from eventCommunity";
			ResultSet rs = st.executeQuery(sql);
			List<EventCommunity> events = new ArrayList<EventCommunity>();
			while (rs.next()) {

				if (rs.getString("name").contains("" + eventName)) {

					EventCommunity event = new EventCommunity();
					event.setDate(rs.getString("date"));
					event.setName(rs.getString("name"));
					event.setPlace(rs.getString("place"));
					event.setId(rs.getString("KEY"));
					event.setCreated_by(rs.getString("created_by"));

					events.add(event);
				}
			}
			con.close();
			return events;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	public int getColumnCount(String columnFamily) throws SQLException {
		String t = "SELECT count(*) FROM '" + columnFamily + "'";
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(t);
		String columnCount = "";
		while (rs.next()) {
			columnCount = rs.getString(1);

		}

		return Integer.parseInt(columnCount);
		// System.out.println("Column Count:"+rs.getMetaData().getColumnCount());
		// return rs.getMetaData().getColumnCount();
	}

}
