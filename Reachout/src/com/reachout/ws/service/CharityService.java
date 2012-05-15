package com.reachout.ws.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.reachout.dao.CharityDao;
import com.reachout.dao.TopicDao;
import com.reachout.ws.domain.Charity;
import com.reachout.ws.domain.Donation;
import com.reachout.ws.domain.EventCommunity;
import com.reachout.ws.domain.Topic;

@Service("charityService")
public class CharityService {

	protected static Logger logger = Logger.getLogger("service");

	// In-memory list
	// private List<Charity> charities = new ArrayList<Charity>();

	public CharityService() {
		logger.debug("Init database");

		// Create in-memory list
		// Charity charity1 = new Charity();
		// charity1.setId(0L);
		// charity1.setCreatedBy("john");
		// charity1.setDescription("sample");// LastName("Smith");
		// charity1.setFromCommunityId("BrainStormers");
		// charity1.setFromUserName("john.smith");
		// charity1.setName("donate");
		// charity1.setAmount("1000");
		//
		// Charity charity2 = new Charity();
		// charity2.setId(1L);
		// charity2.setCreatedBy("reeya");
		// charity2.setDescription("sample");// LastName("Smith");
		// charity2.setFromCommunityId("BrainStormers");
		// charity2.setFromUserName("john.smith");
		// charity2.setName("donate");
		// charity2.setAmount("2200");
		//
		// Charity charity3 = new Charity();
		// charity3.setId(2L);
		// charity3.setCreatedBy("owez");
		// charity3.setDescription("sample");// LastName("Smith");
		// charity3.setFromCommunityId("BrainStormers");
		// charity3.setFromUserName("john.smith");
		// charity3.setName("donate");
		// charity3.setAmount("300");
		//
		// charities.add(charity1);
		// charities.add(charity2);
		// charities.add(charity3);
	}

	/**
	 * Retrieves all persons
	 */
	public List<Charity> getAll() {
		logger.debug("Retrieving all Charities");

		CharityDao charityDao = new CharityDao();
		List<Charity> charities = new ArrayList<Charity>();

		try {
			charities.addAll(charityDao.listLocalCharities());
			charities.addAll(charityDao.listRemoteCharities());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return charities;

	}

	/**
	 * Retrieves a single person
	 */
	public Charity get(String fromCommunityId, String charityId) {
		logger.debug("Retrieving Charity with CommunityId=" + fromCommunityId
				+ " and CharityId: " + charityId);

		return new CharityDao().retrieveCharityById(charityId);

	}

	/**
	 * Adds a new person
	 */
	public Charity create(Charity charity) {
		logger.debug("Adding new charity");

		try {
			return new CharityDao().populateCharity(charity);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	public String donateAmount(Donation donation, String charityId) {
		logger.debug("Donating charity with CommunityId: "
				+ donation.getUserCommunityId());

		try {
			return new CharityDao().populateDonation(donation, charityId);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	public List<Charity> searchCharity(Charity charity) {
		return new CharityDao().retrieveCharityByName(charity.getName());
	}

}
