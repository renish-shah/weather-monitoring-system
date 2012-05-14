package com.reachout.ws.service;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.reachout.dao.TopicDao;
import com.reachout.dao.StoreTopic;
import com.reachout.ws.domain.Topic;
import com.reachout.ws.domain.TopicComment;

@Service("discussionService")
public class DiscussionService {

	protected static Logger logger = Logger.getLogger("service");

	// In-memory list
	private List<Topic> topics = new ArrayList<Topic>();

	public DiscussionService() {
		logger.debug("Init database");

		// Create in-memory list
		// Topic topic1 = new Topic();
		// topic1.setId("0");
		// topic1.setCreatedBy("john");
		// topic1.setDescription("sample");// LastName("Smith");
		// topic1.setCommunityId("BrainStormers");
		// topic1.setCreatedDate("April 23,2012");
		// topic1.setTitle("Sample Title 1");
		//
		// TopicComment comment1 = new TopicComment();
		// comment1.setCommentBy("Renish");
		// comment1.setCommentDate("24th April,2012");
		// comment1.setCommentText("Sample Comment 1");
		//
		// List<TopicComment> commentList1 = new ArrayList<TopicComment>();
		// commentList1.add(comment1);
		//
		// topic1.setComments(commentList1);
		//
		// Topic topic2 = new Topic();
		// topic2.setId("1");
		// topic2.setCreatedBy("reeya");
		// topic2.setDescription("sample");// LastName("Smith");
		// topic2.setCommunityId("BrainStormers");
		// topic2.setCreatedDate("April 21,2012");
		// topic2.setTitle("Sample Title 2");
		//
		// TopicComment comment2 = new TopicComment();
		// comment2.setCommentBy("Owez");
		// comment2.setCommentDate("25th April,2012");
		// comment2.setCommentText("Sample Comment 2");
		//
		// List<TopicComment> commentList2 = new ArrayList<TopicComment>();
		// commentList2.add(comment2);
		// topic2.setComments(commentList2);
		//
		// Topic topic3 = new Topic();
		// topic3.setId("2");
		// topic3.setCreatedBy("owez");
		// topic3.setDescription("sample");// LastName("Smith");
		// topic3.setCommunityId("BrainStormers");
		// topic3.setCreatedDate("April 24,2012");
		// topic3.setTitle("Sample Title 3");
		//
		// TopicComment comment3 = new TopicComment();
		// comment3.setCommentBy("Harsh");
		// comment3.setCommentDate("26th April,2012");
		// comment3.setCommentText("Sample Comment 3");
		//
		// List<TopicComment> commentList3 = new ArrayList<TopicComment>();
		// commentList3.add(comment3);
		// topic3.setComments(commentList3);
		//
		// topics.add(topic1);
		// topics.add(topic2);
		// topics.add(topic3);
	}

	/**
	 * Retrieves all persons
	 */
	public List<Topic> getAll() {
		logger.debug("Retrieving all topics");
		StoreTopic storeTopic = new StoreTopic();
		return storeTopic.getAllTopics();
	}

	/**
	 * Retrieves a single person
	 */
	public Topic get(String communityId, String topicId) {
		logger.debug("Retrieving Topic with CommunityId=" + communityId
				+ " and TopicId: " + topicId);

		StoreTopic storeTopic = new StoreTopic();
		return storeTopic.retrieveTopic(topicId);

	}

	/**
	 * Adds a new person
	 */
	public Topic create(Topic topic) {
		logger.debug("Adding new Topic");

		try {

			StoreTopic storeTopic = new StoreTopic();
			return storeTopic.addTopicIntoDB(topic);

		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}

	public String doComment(TopicComment comment, String communityId,
			String topicId) {
		logger.debug("Commenting on specific Topic => ");

		StoreTopic storeTopic = new StoreTopic();

		return storeTopic.doComment(comment, topicId);

	}
	
	public List<Topic> searchTopic(Topic topic)
	{
		return new TopicDao().retrieveTopicByName(topic.getTopicTitle());
	}
	
}
