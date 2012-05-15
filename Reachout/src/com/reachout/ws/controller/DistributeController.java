/*package com.reachout.ws.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.reachout.ws.domain.CharityList;
import com.reachout.ws.domain.DistributeServiceParam;
import com.reachout.ws.domain.EventCommunity;
import com.reachout.ws.domain.EventList;
import com.reachout.ws.service.DistributedService;
import com.reachout.ws.service.EventService;

import javax.annotation.Resource;

*//**
 * REST service provider
 * 
 * Only GET and POST will return values PUT and DELETE will not.
 *//*
@Controller
 @RequestMapping("/provider") 
public class DistributeController {

	protected static Logger logger = Logger.getLogger("controller");

	@Resource(name = "distributedService")
	private DistributedService distributeService;

	
	 * @RequestMapping(value = "/remoteCharity", method = RequestMethod.GET,
	 * headers = "Accept=application/xml, application/json") public
	 * 
	 * @ResponseBody EventList getEvent() {
	 * logger.debug("Provider has received request to get all persons");
	 * 
	 * // Call service here EventList result = new EventList();
	 * result.setData(distributeService.getAll());
	 * 
	 * return result; }
	 
	
	 * @RequestMapping(value = "/remoteCharity/{noOfHopes}", method =
	 * RequestMethod.GET, headers = "Accept=application/xml, application/json")
	 * public @ResponseBody EventCommunity getEvent(@PathVariable("id") String
	 * id) { logger.debug("Provider has received request to get event with id: "
	 * + id);
	 * 
	 * // Call service here return distributeService.get(id); }
	 
	@RequestMapping(value = "/remoteCharity", method = RequestMethod.POST, headers = "Accept=application/xml, application/json")
	public @ResponseBody
	CharityList addEvent(@RequestBody DistributeServiceParam param) {
		logger.debug("Provider has received request to add new person");

		// Call service to here
		if (null != param.getNoOfHopes()) {

			CharityList result = new CharityList();
			result.setData(distributeService.getAll(Integer.parseInt(param
					.getNoOfHopes())));

			return result;

		}
		return null;

	}
	// @RequestMapping(value = "/person/{id}", method = RequestMethod.PUT,
	// headers = "Accept=application/xml, application/json")
	// public @ResponseBody
	// String updatePerson(@RequestBody EventCommunity person,
	// @PathVariable("id") Long id) {
	// logger.debug("Provider has received request to edit person with id: "
	// + id);
	//
	// // Call service here
	// person.setId(id);
	// return eventService.edit(person).toString();
	// }
	//
	// @RequestMapping(value = "/person/{id}", method = RequestMethod.DELETE,
	// headers = "Accept=application/xml, application/json")
	// public @ResponseBody
	// String deleteEmployee(@PathVariable("id") Long id) {
	// logger.debug("Provider has received request to delete person with id: "
	// + id);
	//
	// // Call service here
	// return eventService.delete(id).toString();
	// }
}*/