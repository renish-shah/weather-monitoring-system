/**
 * 
 */
package com.reachout.ws.controller;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.reachout.ws.domain.CharityList;
import com.reachout.ws.domain.PersonList;
import com.reachout.ws.domain.Topic;
import com.reachout.ws.domain.TopicComment;
import com.reachout.ws.domain.TopicList;
import com.reachout.ws.service.CharityService;
import com.reachout.ws.service.DiscussionService;
import com.reachout.ws.service.PersonService;

/**
 * @author RENISH
 * 
 */
@Controller
@RequestMapping("/topics")
public class DiscussionController {

	protected static Logger logger = Logger.getLogger("controller");

	@Resource(name = "discussionService")
	private DiscussionService discussionService;

	@RequestMapping(value = "/topic", method = RequestMethod.GET, headers = "Accept=application/xml, application/json")
	public @ResponseBody
	TopicList getTopics() {

		// Call service here
		TopicList result = new TopicList();
		result.setData(discussionService.getAll());

		return result;
	}

	@RequestMapping(value = "/topic/{communityId}/{topicId}", method = RequestMethod.GET, headers = "Accept=application/xml, application/json")
	public @ResponseBody
	Topic getTopic(@PathVariable("communityId") String communityId,
			@PathVariable("topicId") String topicId) {

		// Call service here
		// DiscussionService discussionService = new DiscussionService();
		return discussionService.get(communityId, topicId);
	}

	@RequestMapping(value = "/topic/{communityId}/{topicId}", method = RequestMethod.POST, headers = "Accept=application/xml, application/json")
	public @ResponseBody
	String doComment(@PathVariable("communityId") String communityId,
			@PathVariable("topicId") String topicId, @RequestBody TopicComment comment) {

		// Call service here
//		DiscussionService discussionService = new DiscussionService();
		return discussionService.doComment(comment, communityId, topicId);
	}

	@RequestMapping(value = "/topic", method = RequestMethod.POST, headers = "Accept=application/xml, application/json")
	public @ResponseBody
	Topic addTopic(@RequestBody Topic topic) {
		logger.debug("Provider has received request to add new Topic");

		// Call service to here
		return discussionService.create(topic);
	}

}
