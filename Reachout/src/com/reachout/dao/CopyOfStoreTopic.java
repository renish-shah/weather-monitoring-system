/*package com.reachout.dao;

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

public class CopyOfStoreTopic {

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
		map.put("kundera.client", "com.impetus.kundera.client.cassandra.pelops.PelopsClient");

		EntityManagerFactory factory = new EntityManagerFactoryImpl("test", map);
		EntityManager manager = factory.createEntityManager();
		
		
		
		//		Map map = new HashMap();
//		map.put("kundera.nodes", "localhost");
//		map.put("kundera.port", "9160");
//		map.put("kundera.keyspace", "test1");
//		map.put("sessionless", "false");
//		map.put("kundera.client", "com.impetus.kundera.client.PelopsClient");
//		map.put("kundera.client.lookup.class",
//				"com.impetus.client.cassandra.pelops.PelopsClientFactory");
//		map.put("kundera.cache.provider.class",
//				"com.impetus.kundera.cache.ehcache.EhCacheProvider");
//		map.put("kundera.cache.config.resource", "/ehcache-test.xml");

//		 PersistenceUnitInfo info = "gdfgdf";
//		 info.getPersistenceUnitName();
//		 info.
		
//		EntityManagerFactory emf = new EntityManagerFactoryImpl("cassandra_pu",map);
//		EntityManager em = emf.createEntityManager();

		
		
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

		CopyOfStoreTopic storeTopic = new CopyOfStoreTopic();
		storeTopic.setupCassandra();
		storeTopic.addTopicIntoDB(topic);
		storeTopic.retrieveTopic("1");

	}

	public List<Topic> getAllTopics() {

		// setupCassandra();
//		Map map = new HashMap();
//		map.put("kundera.nodes", "localhost");
//		map.put("kundera.port", "9160");
//		map.put("kundera.keyspace", "test1");
//		map.put("sessionless", "false");
//		// map.put("kundera.client", "com.impetus.kundera.client.PelopsClient");
//		map.put("kundera.client.lookup.class",
//				"com.impetus.client.cassandra.pelops.PelopsClientFactory");
//		map.put("kundera.cache.provider.class",
//				"com.impetus.kundera.cache.ehcache.EhCacheProvider");
//		map.put("kundera.cache.config.resource", "/ehcache-test.xml");
//
//		EntityManagerFactory emf = new EntityManagerFactoryImpl("cassandra_pu",
//				map);
//		EntityManager em = emf.createEntityManager();

		 EntityManagerFactory emf = Persistence
		 .createEntityManagerFactory("cassandra_pu");
		 EntityManager em = emf.createEntityManager();

		Query query = em.createQuery("select t from Topic t");
		List<Topic> listOfTopic = query.getResultList();

		em.close();
		emf.close();

		return listOfTopic;
		// return "success";

	}

	public String removeTopic(String topicId) {
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

	public String doComment(TopicComment comment, String topicId) {

		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("cassandra_pu");
		EntityManager em = emf.createEntityManager();

		try {
			Topic topic = em.find(Topic.class, topicId);
			List<TopicComment> comments = topic.getComments();
			comments.add(comment);
			topic.setComments(comments);
			return "success";
		} catch (Exception e) {
			System.out.println("Exception in doComment:" + e);
			return "failure";
		}

	}

	public Topic retrieveTopic(String topicId) {

		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("cassandra_pu");
		EntityManager em = emf.createEntityManager();

		Topic topic = em.find(Topic.class, "1");
		return topic;

		// em.remove(topicUpdate);

		// try{
		//
		//
		//
		// String cql =
		// "select * from topics where communityId='XYZ' and id in (1,10)";
		// // create our test keyspace to use
		// int
		// cql1=client.execute_cql_query(ByteBuffer.wrap(cql.getBytes()),Compression.NONE).getRowsSize();
		// //cqlResult.getRowsSize();
		//
		// client.set_keyspace("test1");
		//
		// }catch (Exception e) {
		// System.out.println("Exception:"+e);
		// }
		//
		// String communityId="XYZ";
		// Query query1 = em
		// .createQuery("select t from Topic t where t.communityId like :arg1");
		// query1.setParameter("arg1", communityId);
		//
		// List list = query1.getResultList();
		// query1.setParameter("communityId", "ABC");
		// query1.executeUpdate();
		// query1.getParameter("communityId");

		// System.out.println("Size:" + query1.getResultList().size());
		// List<Topic> res = query1.getResultList();
		// Iterator iter = res.iterator();

		// CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		// CriteriaQuery<Object> criteriaQuery = criteriaBuilder.createQuery();
		// Root<Topic> from = criteriaQuery.from(Topic.class);
		// Expression ex = Expression.eq("communityId", "XYZ");
		// CriteriaQuery<Object> select = criteriaQuery.select(from).where(
		// communityId = "ABC");
		// TypedQuery<Object> typedQuery = em.createQuery(select);
		// List<Object> resultList = typedQuery.getResultList();
		// assertEqualsList(list, resultList);

		// Topic topicUpdate = em.find(Topic.class, "1");
		// em.remove(topicUpdate);

		// Query queryNew = em
		// .createQuery("select p from Topic p where p.createdBy = 'pray desai'");
		// List list = queryNew.getResultList();

		// System.out.println("" +
		// topicUpdate.getCommunityId()+topicUpdate.getCreatedBy());
		//
		// topicUpdate.setCommunityId("XYZ");
		// topicUpdate.setCreatedBy("Pray Desai");
		// em.persist(topicUpdate);
		//
		// while (iter.hasNext()) {
		// Topic t = (Topic) iter.next();
		// System.out.println("\nTopic:");
		// System.out.println(t.getId());
		// System.out.println(t.getCommunityId());
		// System.out.println(t.getCreatedBy());
		// System.out.println(t.getCreatedDate());
		// System.out.println(t.getTitle());
		// System.out.println(t.getDescription());
		//
		//
		//
		// List<TopicComment> tc = t.getComments();
		// for (TopicComment tcc : tc) {
		// System.out.println("\t" + tcc.getCommentId());
		// System.out.println("\t" + tcc.getCommentBy());
		// System.out.println("\t" + tcc.getCommentDate());
		// System.out.println("\t" + tcc.getCommentText());
		// System.out.println();
		// }
		//
		// }
		//
		//

		// em.close();
		// emf.close();

	}

	public Topic addTopicIntoDB(Topic topic) {
		long startTime = System.currentTimeMillis();

		try {
			return new CopyOfSamples().populateData(topic);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("cassandra_pu");
		EntityManager em = emf.createEntityManager();

		Query query = em.createQuery("select t from Topic t");
		int records = 0;
		// System.out.println(""+query.getMaxResults());
		// List list=query.getResultList();

		if (null != query.getResultList()) {
			records = query.getResultList().size();
		}

		System.out.println("Topic records:" + records);

		Topic topicToInsert = new Topic();

		records++;
		topicToInsert.setId(String.valueOf("" + records));
		topicToInsert.setTopicTitle("" + topic.getTopicTitle());
		topicToInsert.setTopicDescription("" + topic.getTopicDescription());
		topicToInsert.setCreatedDate("" + topic.getCreatedDate());
		topicToInsert.setCreatedBy("" + topic.getCreatedBy());
		topicToInsert.setCommunityID("" + topic.getCommunityID());

		int noOfComments = topicToInsert.getComments().size();
		noOfComments++;

		System.out.println("Topic no of comments:" + noOfComments);

		// TopicComment topicCommentToInsert = new TopicComment();
		// topicCommentToInsert.setCommentId(String.valueOf(""+noOfComments));
		// topicCommentToInsert.setCommentBy(""+topic.getComments().get);
		// topicCommentToInsert.setCommentText("Lovel Community");
		// topicCommentToInsert.setCommentDate((new Date()).toString());
		//
		// topic.getComments().add(topicCommentToInsert);

		em.persist(topicToInsert);

		long stopTime = System.currentTimeMillis();
		System.out.println("Avg Ping = " + ((stopTime - startTime) / 1000f)
				+ " secs");
		return topicToInsert;

	}

	public void setupCassandra() {

		TTransport tr = new TSocket(HOST, PORT); // new default in 0.7 is
													// framed
		// transport
		TFramedTransport tf = new TFramedTransport(tr);
		TProtocol proto = new TBinaryProtocol(tf);
		Cassandra.Client client = new Cassandra.Client(proto);

		try {
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

	}

}
*/