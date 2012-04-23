package com.reachout.ws.service;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

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
		Topic topic1 = new Topic();
		topic1.setId(0L);
		topic1.setCreatedBy("john");
		topic1.setDescription("sample");// LastName("Smith");
		topic1.setCommunityId("BrainStormers");
		topic1.setCreatedDate("April 23,2012");
		topic1.setTitle("Sample Title 1");

		TopicComment comment1 = new TopicComment();
		comment1.setCommentBy("Renish");
		comment1.setCommentDate("24th April,2012");
		comment1.setCommentText("Sample Comment 1");

		List<TopicComment> commentList1 = new ArrayList<TopicComment>();
		commentList1.add(comment1);

		topic1.setComments(commentList1);

		Topic topic2 = new Topic();
		topic2.setId(1L);
		topic2.setCreatedBy("reeya");
		topic2.setDescription("sample");// LastName("Smith");
		topic2.setCommunityId("BrainStormers");
		topic2.setCreatedDate("April 21,2012");
		topic2.setTitle("Sample Title 2");

		TopicComment comment2 = new TopicComment();
		comment2.setCommentBy("Owez");
		comment2.setCommentDate("25th April,2012");
		comment2.setCommentText("Sample Comment 2");

		List<TopicComment> commentList2 = new ArrayList<TopicComment>();
		commentList2.add(comment2);
		topic2.setComments(commentList2);

		Topic topic3 = new Topic();
		topic3.setId(2L);
		topic3.setCreatedBy("owez");
		topic3.setDescription("sample");// LastName("Smith");
		topic3.setCommunityId("BrainStormers");
		topic3.setCreatedDate("April 24,2012");
		topic3.setTitle("Sample Title 3");

		TopicComment comment3 = new TopicComment();
		comment3.setCommentBy("Harsh");
		comment3.setCommentDate("26th April,2012");
		comment3.setCommentText("Sample Comment 3");

		List<TopicComment> commentList3 = new ArrayList<TopicComment>();
		commentList3.add(comment3);
		topic3.setComments(commentList3);

		topics.add(topic1);
		topics.add(topic2);
		topics.add(topic3);
	}

	/**
	 * Retrieves all persons
	 */
	public List<Topic> getAll() {
		logger.debug("Retrieving all persons");

		return topics;
	}

	/**
	 * Retrieves a single person
	 */
	public Topic get(String communityId, Long TopicId) {
		logger.debug("Retrieving Topic with CommunityId=" + communityId
				+ " and TopicId: " + TopicId);

		for (Topic topic : topics) {
			if (topic.getId().longValue() == TopicId.longValue()
					&& topic.getCommunityId().equalsIgnoreCase(communityId)) {
				logger.debug("Found record");
				return topic;
			}
		}

		logger.debug("No records found");
		return null;
	}

	/**
	 * Adds a new person
	 */
	public Topic create(Topic topic) {
		logger.debug("Adding new person");

		try {
			// Find suitable id
			Long newId = 0L;
			Boolean hasFoundSuitableId = false;
			while (hasFoundSuitableId == false) {
				for (int i = 0; i < topics.size(); i++) {
					if (topics.get(i).getId().longValue() == newId.longValue()) {
						newId++;
						break;
					}

					// Exit while loop
					if (i == topics.size() - 1) {
						logger.debug("Assigning id: " + newId);
						hasFoundSuitableId = true;
					}
				}
			}

			// Assign new id
			topic.setId(newId);
			// Add to list
			topics.add(topic);

			logger.debug("Added new Topic");
			return topic;
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}

	/**
	 * Deletes an existing person
	 */
	public String delete(Long id) {
		logger.debug("Deleting person with id: " + id);

		try {
			for (Topic Topic : topics) {
				if (Topic.getId().longValue() == id.longValue()) {
					logger.debug("Found record");
					topics.remove(Topic);
					return "true";
				}
			}

			logger.debug("No records found");
			return "false";

		} catch (Exception e) {
			logger.error(e);
			return "false";
		}
	}

	/**
	 * Edits an existing person
	 */
	public Boolean edit(Topic Topic) {
		logger.debug("Editing person with id: " + Topic.getId());

		try {
			for (Topic c : topics) {
				if (c.getId().longValue() == Topic.getId().longValue()) {
					logger.debug("Found record");
					topics.remove(c);
					topics.add(Topic);
					return true;
				}
			}

			logger.debug("No records found");
			return false;

		} catch (Exception e) {
			logger.error(e);
			return false;
		}
	}

	public String doComment(TopicComment comment, String communityId,
			String topicId) {
		logger.debug("Commenting on specific Topic => ");

		System.out.println("communityId: " + communityId);
		System.out.println("topicId: " + topicId);
		System.out.println("Comment By: " + comment.getCommentBy());
		System.out.println("Comment Date: " + comment.getCommentDate());
		System.out.println("Comment Text: " + comment.getCommentText());
		if (comment.getCommentText() != null
				&& !comment.getCommentText().equalsIgnoreCase(""))
			return "success";
		else
			return "failure";

	}
}
