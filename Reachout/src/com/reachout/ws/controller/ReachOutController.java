/**
 * 
 */
package com.reachout.ws.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.reachout.ws.domain.Charity;
import com.reachout.ws.domain.CharityList;
import com.reachout.ws.domain.PersonList;
import com.reachout.ws.service.CharityService;
import com.reachout.ws.service.PersonService;

/**
 * @author RENISH
 * 
 */
@Controller
public class ReachOutController {

	@Resource(name = "charityService")
	private CharityService charityService;

	@RequestMapping(value = "/charities", method = RequestMethod.GET, headers = "Accept=application/xml, application/json")
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
			@PathVariable("charityId") Long charityId) {

		// Call service here
		CharityService charityService = new CharityService();
		return charityService.get(fromCommunityId, charityId);
	}
	
	@RequestMapping(value = "/charity/{communityId}/{charityId}", method = RequestMethod.POST, headers = "Accept=application/xml, application/json")
	public @ResponseBody
	Charity donateToCharity(@PathVariable("communityId") String fromCommunityId,
			@PathVariable("charityId") Long charityId) {

		// Call service here
		CharityService charityService = new CharityService();
		return charityService.get(fromCommunityId, charityId);
	}


}
