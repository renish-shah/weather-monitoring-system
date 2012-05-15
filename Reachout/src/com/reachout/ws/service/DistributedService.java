/*package com.reachout.ws.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.reachout.dao.CharityDao;
import com.reachout.ws.domain.Charity;
import com.reachout.ws.domain.DistributeServiceParam;
import com.reachout.ws.domain.Person;

@Service("personService")
public class DistributedService {

	protected static Logger logger = Logger.getLogger("service");

	// In-memory list
	private List<DistributeServiceParam> persons = new ArrayList<Person>();

	public DistributedService() {
		logger.debug("Init database");

		// Create in-memory list
		DistributeServiceParam person1 = new DistributeServiceParam();
		person1.setId(0L);

		persons.add(person1);
		persons.add(person2);
		persons.add(person3);
	}

	public List<Charity> getAll(int noOfHopes) {
		logger.debug("Retrieving all Charities");

		if (noOfHopes != -1) {

			if (noOfHopes == 0) {
				CharityDao charityDao = new CharityDao();
				List<Charity> charities = new ArrayList<Charity>();

				try {
					charities.addAll(charityDao.listLocalCharities());
					// charities.addAll(charityDao.listRemoteCharities());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return charities;

			} else if (noOfHopes > 0) {
				CharityDao charityDao = new CharityDao();
				List<Charity> charities = new ArrayList<Charity>();

				try {
					 charities.addAll(charityDao.listLocalCharities());
					 charities.addAll(charityDao.listRemoteCharities(noOfHopes--));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return charities;

			}

		} else if (noOfHopes == -1) {
			CharityDao charityDao = new CharityDao();
			List<Charity> charities = new ArrayList<Charity>();

			try {
				// charities.addAll(charityDao.listLocalCharities());
				charities.addAll(charityDao.listRemoteCharities());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return charities;

		}

	}

	*//**
	 * Retrieves a single person
	 *//*
	public Person get(Long id) {
		logger.debug("Retrieving person with id: " + id);

		for (Person person : persons) {
			if (person.getId().longValue() == id.longValue()) {
				logger.debug("Found record");
				return person;
			}
		}

		logger.debug("No records found");
		return null;
	}

	*//**
	 * Adds a new person
	 *//*
	public Person add(Person person) {
		logger.debug("Adding new person");

		try {
			// Find suitable id
			Long newId = 0L;
			Boolean hasFoundSuitableId = false;
			while (hasFoundSuitableId == false) {
				for (int i = 0; i < persons.size(); i++) {
					if (persons.get(i).getId().longValue() == newId.longValue()) {
						newId++;
						break;
					}

					// Exit while loop
					if (i == persons.size() - 1) {
						logger.debug("Assigning id: " + newId);
						hasFoundSuitableId = true;
					}
				}
			}

			// Assign new id
			person.setId(newId);
			// Add to list
			persons.add(person);

			logger.debug("Added new person");
			return person;
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}

	*//**
	 * Deletes an existing person
	 *//*
	public String delete(Long id) {
		logger.debug("Deleting person with id: " + id);

		try {
			for (Person person : persons) {
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
	public Boolean edit(Person person) {
		logger.debug("Editing person with id: " + person.getId());

		try {
			for (Person p : persons) {
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
}
*/