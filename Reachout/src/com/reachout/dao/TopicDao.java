package com.reachout.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.reachout.ws.domain.Topic;
import com.reachout.ws.domain.TopicComment;

public class TopicDao {

	private static java.sql.Connection con = null;

	public static void main(String[] args) throws SQLException,
			ClassNotFoundException {

		TopicDao sample = new TopicDao();
		// sample.createColumnFamily();

		Topic topic = new Topic();
		topic.setCommunityID("communityID");
		topic.setCreatedBy("createdBy");
		topic.setCreatedDate("createdDate");
		// topic.setId("key");
		topic.setTopicDescription("topicDescription");
		topic.setTopicTitle("topicTitle");

		TopicComment comment = new TopicComment();
		comment.setCommentBy("renish");
		comment.setCommentDate("1234");
		comment.setCommentId("");
		comment.setCommentText("ABC");

		// topic = sample.populateData(topic);
		sample.populateComments(comment, "1");

		sample.retrieveTopicById("1");
		try {
			// createColumnFamily("topic");
			// pouplateData();
			// deleteData();
			// updateData();
			listTopics();
			// dropColumnFamily("news");

		} catch (SQLException e) {
			// log and throw exceptionfox
			throw e;
		}

	}

	public static void createColumnFamily() throws SQLException {

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

	public static Topic populateData(Topic topic) throws SQLException,
			ClassNotFoundException {

		Connection con = getCassandraConnection();

		int topicId = new TopicDao().getColumnCount("topic") + 1;

		// (KEY text primary key, topic_title varchar, topic_desc varchar,
		// created_date varchar,community_id varchar, created_by varchar)

		String data = ""
				+ "insert into topic (key, topic_title, topic_desc, created_date, community_id, created_by) "
				+ "values ('" + topicId + "','" + topic.getTopicTitle() + "','"
				+ topic.getTopicDescription() + "','" + topic.getCreatedDate()
				+ "','" + topic.getCommunityID() + "','" + topic.getCreatedBy()
				+ "')";

		System.out.println("SQL :" + data);

		topic.setId("" + topicId);

		Statement st = con.createStatement();
		st.executeUpdate(data);

		con.close();

		return topic;
	}

	public static String populateComments(TopicComment topic, String topicId)
			throws SQLException, ClassNotFoundException {

		Connection con = getCassandraConnection();

		int commentId = new TopicDao().getColumnCount("topicComment") + 1;

		// topicComment"
		// +
		// " (KEY text primary key, topic_id varchar, comment_by varchar, comment_date varchar, comment_text varchar)";

		String data = ""
				+ "insert into topicComment (key, topic_id, comment_by, comment_date, comment_text) "
				+ "values ('" + commentId + "','" + topicId + "','"
				+ topic.getCommentBy() + "','" + topic.getCommentDate() + "','"
				+ topic.getCommentText() + "')";

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

	public static List<Topic> listTopics() throws SQLException {

		try {
			con = getCassandraConnection();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String t = "SELECT * FROM topic";
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(t);
		System.out.println("Column Count:" + rs.getMetaData().getColumnCount());

		List<Topic> topics = new ArrayList<Topic>();
		while (rs.next()) {

			Topic topic = new Topic();
			topic.setCommunityID("" + rs.getString("community_id"));
			topic.setCreatedBy(rs.getString("created_by"));
			topic.setCreatedDate(rs.getString("created_date"));
			topic.setId(rs.getString("KEY"));
			topic.setTopicDescription(rs.getString("topic_desc"));
			topic.setTopicTitle(rs.getString("topic_title"));

			topics.add(topic);
		}
		con.close();
		return topics;
	}

	public Topic retrieveTopicById(String topicId) {

		// (KEY text primary key, topic_title varchar, topic_desc varchar,
		// created_date varchar,community_id varchar, created_by varchar)
		try {
			Connection con = getCassandraConnection();
			Statement st = con.createStatement();
			String sql = "select * from topic where key = '" + topicId + "'";
			ResultSet rs = st.executeQuery(sql);
			Topic topic = new Topic();
			while (rs.next()) {

				topic.setCommunityID("" + rs.getString("community_id"));
				topic.setCreatedBy(rs.getString("created_by"));
				topic.setCreatedDate(rs.getString("created_date"));
				topic.setId(rs.getString("KEY"));
				topic.setTopicDescription(rs.getString("topic_desc"));
				topic.setTopicTitle(rs.getString("topic_title"));
			}

			// data = "create columnfamily topicComment"
			// +
			// " (KEY text primary key, topic_id varchar, comment_by varchar, comment_date varchar, comment_text varchar)";
			sql = "select * from topicComment";
			rs = st.executeQuery(sql);

			List<TopicComment> commentlist = new ArrayList<TopicComment>();

			while (rs.next()) {
				// System.out.println(rs.getString("KEY"));
				if (rs.getString("topic_id").equalsIgnoreCase("" + topicId)) {

					TopicComment comment = new TopicComment();
					comment.setCommentBy(rs.getString("comment_by"));
					comment.setCommentDate(rs.getString("comment_date"));
					comment.setCommentText(rs.getString("comment_text"));
					commentlist.add(comment);
				}
				// for (int j = 1; j < rs.getMetaData().getColumnCount() + 1;
				// j++) {
				// System.out.println(rs.getMetaData().getColumnName(j)
				// + " : "
				// + rs.getString(rs.getMetaData().getColumnName(j)));
				// }

				topic.setComments(commentlist);

			}
			con.close();
			return topic;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	public List<Topic> retrieveTopicByName(String topicName) {

		// (KEY text primary key, topic_title varchar, topic_desc varchar,
		// created_date varchar,community_id varchar, created_by varchar)
		try {
			Connection con = getCassandraConnection();
			Statement st = con.createStatement();
			String sql = "select * from topic";
			ResultSet rs = st.executeQuery(sql);
			List<Topic> topics=new ArrayList<Topic>();
			while (rs.next()) {

				if (rs.getString("topic_title").contains("" + topicName)) {
					Topic topic = new Topic();
					topic.setCommunityID("" + rs.getString("community_id"));
					topic.setCreatedBy(rs.getString("created_by"));
					topic.setCreatedDate(rs.getString("created_date"));
					topic.setId(rs.getString("KEY"));
					topic.setTopicDescription(rs.getString("topic_desc"));
					topic.setTopicTitle(rs.getString("topic_title"));
					topics.add(topic);
				}
			}
			con.close();
			return topics;

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
