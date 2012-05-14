package com.reachout.dao;

import java.nio.ByteBuffer;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import javax.persistence.Query;

import org.apache.cassandra.thrift.Cassandra;
import org.apache.cassandra.thrift.Compression;
import org.apache.cassandra.thrift.ConsistencyLevel;

import org.apache.cassandra.thrift.KsDef;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import com.impetus.kundera.persistence.EntityManagerFactoryImpl;
import com.reachout.ws.domain.Topic;
import com.reachout.ws.domain.TopicComment;

public class StoreTopic {

	// set up some constants
	private static final String UTF8 = "UTF8";
	private static final String HOST = "localhost";
	private static final int PORT = 9160;
	private static final ConsistencyLevel CL = ConsistencyLevel.ONE;

	public static void main(String[] args) {

		Map map = new HashMap();
		map.put("kundera.nodes", "localhost");
		map.put("kundera.port", "9160");
		map.put("kundera.keyspace", "Blog");
		map.put("sessionless", "false");
		map.put("kundera.client",
				"com.impetus.kundera.client.cassandra.pelops.PelopsClient");

		EntityManagerFactory factory = new EntityManagerFactoryImpl("test", map);
		EntityManager manager = factory.createEntityManager();

		// Map map = new HashMap();
		// map.put("kundera.nodes", "localhost");
		// map.put("kundera.port", "9160");
		// map.put("kundera.keyspace", "test1");
		// map.put("sessionless", "false");
		// map.put("kundera.client", "com.impetus.kundera.client.PelopsClient");
		// map.put("kundera.client.lookup.class",
		// "com.impetus.client.cassandra.pelops.PelopsClientFactory");
		// map.put("kundera.cache.provider.class",
		// "com.impetus.kundera.cache.ehcache.EhCacheProvider");
		// map.put("kundera.cache.config.resource", "/ehcache-test.xml");

		// PersistenceUnitInfo info = "gdfgdf";
		// info.getPersistenceUnitName();
		// info.

		// EntityManagerFactory emf = new
		// EntityManagerFactoryImpl("cassandra_pu",map);
		// EntityManager em = emf.createEntityManager();

		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("cassandra_pu");
		EntityManager em = emf.createEntityManager();

		TTransport tr = new TSocket(HOST, PORT); // new default in 0.7 is
		try {

			// framed
			// transport
			TFramedTransport tf = new TFramedTransport(tr);
			TProtocol proto = new TBinaryProtocol(tf);
			Cassandra.Client client = new Cassandra.Client(proto);

			tf.open();

			List<KsDef> ksdef = client.describe_keyspaces();
			boolean isKeyspaceSet = false;

			for (KsDef k1 : ksdef) {
				if (k1.getName().equalsIgnoreCase("test1")) {
					isKeyspaceSet = true;
				}
			}
			if (isKeyspaceSet == false) {

				System.out.println("create new keyspace");
				String cql = "CREATE keyspace test1 WITH strategy_options:DC1 = '1' AND replication_factor = '1' AND strategy_class = 'NetworkTopologyStrategy'";
				// create our test keyspace to use
				client.execute_cql_query(ByteBuffer.wrap(cql.getBytes()),
						Compression.NONE);

				client.set_keyspace("test1");

				// cql = "create column family renish";
				// client.execute_cql_query(ByteBuffer.wrap(cql.getBytes()),
				// Compression.NONE);

			} else {
				System.out.println("keyspace exists");
				client.set_keyspace("test1");
			}
		} catch (Exception e) {
			System.out.println("Exception : " + e);
		} finally {

			tr.close();

		}
		em.close();
		emf.close();

		Topic topic = new Topic();
		topic.setTopicTitle("sample title");
		topic.setTopicDescription("desription");
		topic.setCreatedDate("date1234");
		topic.setCreatedBy("renish");
		topic.setCommunityID("ABC");

		StoreTopic storeTopic = new StoreTopic();
//		storeTopic.setupCassandra();
		storeTopic.addTopicIntoDB(topic);
		storeTopic.retrieveTopic("1");

	}

	public List<Topic> getAllTopics() {

		try {
			return new TopicDao().listTopics();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

/*	public String removeTopic(String topicId) {
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("cassandra_pu");
		EntityManager em = emf.createEntityManager();

		try {
			Topic topic = em.find(Topic.class, topicId);
			em.remove(topic);
			return "success";
		} catch (Exception e) {
			System.out.println("Exception in remove topic :" + e);
			return "failure";
		}
	}
*/
	public String doComment(TopicComment comment, String topicId) {


		try {
			return new TopicDao().populateComments(comment, topicId);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "failure";
		
		
	}

	public List<Topic> searchTopic(String topicTitle) {

		return new TopicDao().retrieveTopicByName(topicTitle);
	}

	
	public Topic retrieveTopic(String topicId) {

		return new TopicDao().retrieveTopicById(topicId);
	}

	public Topic addTopicIntoDB(Topic topic) {
		long startTime = System.currentTimeMillis();

		try {
			return new TopicDao().populateData(topic);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}


}
