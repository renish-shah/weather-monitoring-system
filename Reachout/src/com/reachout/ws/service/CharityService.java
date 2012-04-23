package com.reachout.ws.service;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.reachout.ws.domain.Charity;
import com.reachout.ws.domain.Donation;
import com.reachout.ws.domain.Person;

@Service("charityService")
public class CharityService {

	protected static Logger logger = Logger.getLogger("service");

	// In-memory list
	private List<Charity> charities = new ArrayList<Charity>();

	public CharityService() {
		logger.debug("Init database");

		// Create in-memory list
		Charity charity1 = new Charity();
		charity1.setId(0L);
		charity1.setCreatedBy("john");
		charity1.setDescription("sample");// LastName("Smith");
		charity1.setFromCommunityId("BrainStormers");
		charity1.setFromUserName("john.smith");
		charity1.setName("donate");
		charity1.setAmount("1000");

		Charity charity2 = new Charity();
		charity2.setId(1L);
		charity2.setCreatedBy("reeya");
		charity2.setDescription("sample");// LastName("Smith");
		charity2.setFromCommunityId("BrainStormers");
		charity2.setFromUserName("john.smith");
		charity2.setName("donate");
		charity2.setAmount("2200");

		Charity charity3 = new Charity();
		charity3.setId(2L);
		charity3.setCreatedBy("owez");
		charity3.setDescription("sample");// LastName("Smith");
		charity3.setFromCommunityId("BrainStormers");
		charity3.setFromUserName("john.smith");
		charity3.setName("donate");
		charity3.setAmount("300");

		charities.add(charity1);
		charities.add(charity2);
		charities.add(charity3);
	}

	/**
	 * Retrieves all persons
	 */
	public List<Charity> getAll() {
		logger.debug("Retrieving all persons");

		return charities;
	}

	/**
	 * Retrieves a single person
	 */
	public Charity get(String fromCommunityId, Long charityId) {
		logger.debug("Retrieving Charity with CommunityId=" + fromCommunityId
				+ " and CharityId: " + charityId);

		for (Charity charity : charities) {
			if (charity.getId().longValue() == charityId.longValue()
					&& charity.getFromCommunityId().equalsIgnoreCase(
							fromCommunityId)) {
				logger.debug("Found record");
				return charity;
			}
		}

		logger.debug("No records found");
		return null;
	}

	/**
	 * Adds a new person
	 */
	public Charity create(Charity charity) {
		logger.debug("Adding new charity");

		try {
			// Find suitable id
			Long newId = 0L;
			Boolean hasFoundSuitableId = false;
			while (hasFoundSuitableId == false) {
				for (int i = 0; i < charities.size(); i++) {
					if (charities.get(i).getId().longValue() == newId
							.longValue()) {
						newId++;
						break;
					}

					// Exit while loop
					if (i == charities.size() - 1) {
						logger.debug("Assigning id: " + newId);
						hasFoundSuitableId = true;
					}
				}
			}

			// Assign new id
			charity.setId(newId);
			// Add to list
			charities.add(charity);

			logger.debug("Added new person");
			return charity;
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
			for (Charity charity : charities) {
				if (charity.getId().longValue() == id.longValue()) {
					logger.debug("Found record");
					charities.remove(charity);
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
	public Boolean edit(Charity charity) {
		logger.debug("Editing person with id: " + charity.getId());

		try {
			for (Charity c : charities) {
				if (c.getId().longValue() == charity.getId().longValue()) {
					logger.debug("Found record");
					charities.remove(c);
					charities.add(charity);
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

	public String donateAmount(Donation donation) {
		logger.debug("Donating charity with CommunityId: "
				+ donation.getUserCommunityId());

		System.out.println("Donation Amount: " + donation.getDonateAmt());
		System.out
				.println("User CommunityId: " + donation.getUserCommunityId());
		System.out.println("User Name: " + donation.getUserName());
		if (donation.getDonateAmt() != null
				&& !donation.getDonateAmt().equalsIgnoreCase(""))
			return "success";
		else
			return "failure";

	}
}
