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
import com.reachout.ws.domain.Topic;
import com.reachout.ws.domain.TopicComment;

public class CharityDao {

	private static java.sql.Connection con = null;

	public static void main(String[] args) throws SQLException,
			ClassNotFoundException {

		CharityDao sample = new CharityDao();
		// sample.createCharityColumnFamily();

		Charity charity = new Charity();
		charity.setAmount("22222");
		charity.setCreatedBy("createdBy");
		charity.setDescription("Description");
		// topic.setId("key");
		charity.setFromCommunityId("BrainStormers");
		charity.setFromUserName("renish");
		charity.setName("FOOD");

		Donation donation = new Donation();
		donation.setDonateAmt("500");
		donation.setUserCommunityId("BrainStormers");
		donation.setUserName("nancy");

		charity = sample.populateCharity(charity);
		sample.populateDonation(donation, "2");

		sample.retrieveCharityById("1");
		try {
			// createColumnFamily("topic");
			// pouplateData();
			// deleteData();
			// updateData();
			listLocalCharities();
			// dropColumnFamily("news");

		} catch (SQLException e) {
			// log and throw exceptionfox
			throw e;
		}

	}

	public static void createCharityColumnFamily() throws SQLException {

		try {
			con = getCassandraConnection();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String data = "create columnfamily charity"
				+ " (KEY text primary key, charity_name varchar, charity_desc varchar, "
				+ "created_by varchar, community_id varchar, username varchar, amount varchar)";
		Statement st = con.createStatement();
		st.execute(data);

		data = "create columnfamily donation"
				+ " (KEY text primary key, charity_id varchar, community_id varchar, username varchar, amount varchar)";

		st = con.createStatement();
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

	public static Charity populateCharity(Charity charity) throws SQLException,
			ClassNotFoundException {

		Connection con = getCassandraConnection();

		int charityId = new CharityDao().getColumnCount("charity") + 1;

		// String data = "create columnfamily charity"
		// +
		// " (KEY text primary key, charity_name varchar, charity_desc varchar, "
		// +
		// "created_by varchar, community_id varchar, username varchar, amount varchar)";

		String data = ""
				+ "insert into charity (key, charity_name, charity_desc, created_by, community_id, username, amount) "
				+ "values ('" + charityId + "','" + charity.getName() + "','"
				+ charity.getDescription() + "','" + charity.getCreatedBy()
				+ "','" + charity.getFromCommunityId() + "','"
				+ charity.getFromUserName() + "','" + charity.getAmount()
				+ "')";

		System.out.println("SQL :" + data);

		charity.setCharityID("" + charityId);

		Statement st = con.createStatement();
		st.executeUpdate(data);

		con.close();

		return charity;
	}

	public static String populateDonation(Donation doantion, String charityId)
			throws SQLException, ClassNotFoundException {

		Connection con = getCassandraConnection();

		int donationId = new CharityDao().getColumnCount("donation") + 1;

		// data = "create columnfamily donation"
		// +
		// " (KEY text primary key, charity_id varchar, community_id varchar, username varchar, amount varchar)";

		String data = ""
				+ "insert into donation (key, charity_id, community_id, username, amount) "
				+ "values ('" + donationId + "','" + charityId + "','"
				+ doantion.getUserCommunityId() + "','"
				+ doantion.getUserName() + "','" + doantion.getDonateAmt()
				+ "')";

		System.out.println("SQL :" + data);

		// topic.setId("" + topicId);

		Statement st = con.createStatement();
		st.executeUpdate(data);

		con.close();

		return "success";
	}

	public static void deleteData() throws SQLException {
		String data = "BEGIN BATCH \n"
				+ "delete from  news where key='user1' \n"
				+ "delete  category from  news where key='user2' \n"
				+ "APPLY BATCH;";
		Statement st = con.createStatement();
		st.executeUpdate(data);
	}

	public static void updateData() throws SQLException {
		String t = "update news set category='sports', linkcounts=1 where key='user3'";
		Statement st = con.createStatement();
		st.executeUpdate(t);
	}

	public static List<Charity> listLocalCharities() throws SQLException {

		try {
			con = getCassandraConnection();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String t = "SELECT * FROM charity";
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(t);
		System.out.println("Column Count:" + rs.getMetaData().getColumnCount());

		List<Charity> charities = new ArrayList<Charity>();
		while (rs.next()) {

			Charity charity = new Charity();
			charity.setAmount(rs.getString("amount"));
			charity.setCharityID(rs.getString("KEY"));
			charity.setCreatedBy(rs.getString("created_by"));
			charity.setDescription(rs.getString("charity_desc"));
			charity.setFromCommunityId(rs.getString("community_id"));
			charity.setFromUserName(rs.getString("username"));
			charity.setName(rs.getString("charity_name"));

			charities.add(charity);
		}
		con.close();
		return charities;
	}

	public List<Charity> listRemoteCharities() {

		List<String> listOfRemoteCharities = new DistributedDiscovery()
				.ReadConfigFile();

		JavaRestClient restClient = new JavaRestClient();
		
		List<Charity> remoteCharities=new ArrayList<Charity>();
		
		for (String remoteChariy : listOfRemoteCharities) {

			remoteCharities.addAll(restClient.callService(remoteChariy));
			
		}

		return remoteCharities;

	}

	public Charity retrieveCharityById(String charityId) {

		// String data = "create columnfamily charity"
		// +
		// " (KEY text primary key, charity_name varchar, charity_desc varchar, "
		// +
		// "created_by varchar, community_id varchar, username varchar, amount varchar)";
		try {
			Connection con = getCassandraConnection();
			Statement st = con.createStatement();
			String sql = "select * from charity where key = '" + charityId
					+ "'";
			ResultSet rs = st.executeQuery(sql);
			Charity charity = new Charity();
			while (rs.next()) {

				charity.setAmount(rs.getString("amount"));
				charity.setCharityID(rs.getString("KEY"));
				charity.setCreatedBy(rs.getString("created_by"));
				charity.setDescription(rs.getString("charity_desc"));
				charity.setFromCommunityId(rs.getString("community_id"));
				charity.setFromUserName(rs.getString("username"));
				charity.setName(rs.getString("charity_name"));
			}

			con.close();
			return charity;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	public List<Charity> retrieveCharityByName(String charityName) {

		try {
			con = getCassandraConnection();
			Statement st = con.createStatement();
			String sql = "select * from charity";
			ResultSet rs = st.executeQuery(sql);
			List<Charity> charities = new ArrayList<Charity>();
			while (rs.next()) {

				if (rs.getString("charity_name").contains("" + charityName)) {

					Charity charity = new Charity();
					charity.setAmount(rs.getString("amount"));
					charity.setCharityID(rs.getString("KEY"));
					charity.setCreatedBy(rs.getString("created_by"));
					charity.setDescription(rs.getString("charity_desc"));
					charity.setFromCommunityId(rs.getString("community_id"));
					charity.setFromUserName(rs.getString("username"));
					charity.setName(rs.getString("charity_name"));

					charities.add(charity);
				}
			}
			con.close();
			return charities;

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
