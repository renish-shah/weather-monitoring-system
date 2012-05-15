/**
 * 
 */
package com.reachout.ws.controller;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.reachout.ws.domain.Charity;
import com.reachout.ws.domain.CharityList;
import com.reachout.ws.domain.Donation;
import com.reachout.ws.domain.EventCommunity;

import com.reachout.ws.service.CharityService;

/**
 * @author RENISH
 * 
 */
@Controller
/*@RequestMapping("/charities")*/
public class CharityController {

	protected static Logger logger = Logger.getLogger("controller");

	@Resource(name = "charityService")
	private CharityService charityService;

	@RequestMapping(value = "/charity", method = RequestMethod.GET, headers = "Accept=application/xml,application/json")
	public @ResponseBody
	CharityList getCharities() {

		// Call service here
		CharityList result = new CharityList();
		result.setData(charityService.getAll());

		return result;
	}

	@RequestMapping(value = "/charity/{communityId}/{charityId}", method = RequestMethod.GET, headers = "Accept=application/xml, application/json")
	public @ResponseBody
	Charity getCharity(@PathVariable("communityId") String fromCommunityId,
			@PathVariable("charityId") String charityId) {

		// Call service here
		// charityService = new CharityService();
		return charityService.get(fromCommunityId, charityId);
	}

	@RequestMapping(value = "/charity/{communityId}/{charityId}", method = RequestMethod.POST, headers = "Accept=application/xml, application/json")
	public @ResponseBody
	String donateToCharity(@RequestBody Donation donation,
			@PathVariable("communityId") String fromCommunityId,
			@PathVariable("charityId") String charityId) {

		// Call service here
		// charityService = new CharityService();
		return charityService.donateAmount(donation, charityId);// (fromCommunityId,
														// charityId);
	}

	@RequestMapping(value = "/charity", method = RequestMethod.POST, headers = "Accept=application/xml, application/json")
	public @ResponseBody
	Charity createCharity(@RequestBody Charity charity) {
		logger.debug("Provider has received request to add new Charity");

		// Call service to here
		// charityService = new CharityService();
		return charityService.create(charity);
	}
	
	@RequestMapping(value = "/charity/name", method = RequestMethod.POST, headers = "Accept=application/xml, application/json")
	public @ResponseBody
	CharityList searchCharity(@RequestBody Charity charity) {
		logger.debug("Provider has received request to search Charity");

		CharityList result = new CharityList();
		result.setData(charityService.searchCharity(charity));

		return result;
	}

	

}
