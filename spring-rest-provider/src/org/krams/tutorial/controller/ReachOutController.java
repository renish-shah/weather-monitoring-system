/**
 * 
 */
package org.krams.tutorial.controller;

import javax.annotation.Resource;

import org.krams.tutorial.domain.PersonList;
import org.krams.tutorial.service.CharityService;
import org.krams.tutorial.service.PersonService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author RENISH
 * 
 */
public class ReachOutController {

	
	@Resource(name="charityService")
	private CharityService charityService;
	

	@RequestMapping(value = "/charities", method = RequestMethod.GET, headers = "Accept=application/xml, application/json")
	public @ResponseBody
	PersonList getCharities() {

		// Call service here
		PersonList result = new PersonList();
		result.setData(charityService.getAll());

		return result;
	}

}
