package com.reachout.ws.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.reachout.dao.EventDao;
import com.reachout.dao.StoreTopic;
import com.reachout.ws.domain.EventCommunity;
import com.reachout.ws.domain.Topic;

@Service("eventService")
public class EventService {

	protected static Logger logger = Logger.getLogger("service");

	// In-memory list
	private List<EventCommunity> events = new ArrayList<EventCommunity>();

	public EventService() {
		logger.debug("Init database");

		// Create in-memory list
		// Event person1 = new Event();
		// person1.setId(0L);
		// person1.setFirstName("John");
		// person1.setLastName("Smith");
		// person1.setMoney(1000.0);
		//
		// Event person2 = new Event();
		// person2.setId(1L);
		// person2.setFirstName("Jane");
		// person2.setLastName("Adams");
		// person2.setMoney(2000.0);
		//
		// Event person3 = new Event();
		// person3.setId(2L);
		// person3.setFirstName("Jeff");
		// person3.setLastName("Mayer");
		// person3.setMoney(3000.0);
		//
		// persons.add(person1);
		// persons.add(person2);
		// persons.add(person3);
	}

	/**
	 * Retrieves all persons
	 */
	public List<EventCommunity> getAll() {
		logger.debug("Retrieving all events");
		try {
			return new EventDao().listEvents();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Retrieves a single person
	 */
	public EventCommunity get(String eventId) {
		logger.debug("Retrieving event with" + " and EventId: " + eventId);

		return new EventDao().retrieveEventById(eventId);

	}

	/**
	 * Adds a new person
	 */
	public EventCommunity create(EventCommunity event) {
		logger.debug("Adding new Topic");

		try {

			return new EventDao().populateEvent(event);

		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}

/*	*//**
	 * Deletes an existing person
	 *//*
	public String delete(Long id) {
		logger.debug("Deleting person with id: " + id);

		try {
			for (EventCommunity person : persons) {
				if (person.getId().longValue() == id.longValue()) {
					logger.debug("Found record");
					persons.remove(person);
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

	*//**
	 * Edits an existing person
	 *//*
	public Boolean edit(EventCommunity person) {
		logger.debug("Editing person with id: " + person.getId());

		try {
			for (EventCommunity p : persons) {
				if (p.getId().longValue() == person.getId().longValue()) {
					logger.debug("Found record");
					persons.remove(p);
					persons.add(person);
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
*/}
