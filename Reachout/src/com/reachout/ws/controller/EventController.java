package com.reachout.ws.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.reachout.ws.domain.EventCommunity;
import com.reachout.ws.domain.EventList;
import com.reachout.ws.service.EventService;

import javax.annotation.Resource;

/**
 * REST service provider
 * 
 * Only GET and POST will return values PUT and DELETE will not.
 */
@Controller
/* @RequestMapping("/provider") */
public class EventController {

	protected static Logger logger = Logger.getLogger("controller");

	@Resource(name = "eventService")
	private EventService eventService;

	/*
	 * @RequestMapping(value = "/person/{id}", method = RequestMethod.GET,
	 * headers = "Accept=image/jpeg, image/jpg, image/png, image/gif") public
	 * @ResponseBody byte[] getPhoto(@PathVariable("id") Long id) {
	 * 
	 * // Call service here try { // Retrieve image from the classpath
	 * InputStream is = this.getClass().getResourceAsStream("/bella.jpg");
	 * 
	 * // Prepare buffered image BufferedImage img = ImageIO.read(is);
	 * 
	 * // Create a byte array output stream ByteArrayOutputStream bao = new
	 * ByteArrayOutputStream();
	 * 
	 * // Write to output stream ImageIO.write(img, "jpg", bao);
	 * 
	 * logger.debug("Retrieving photo as byte array image"); return
	 * bao.toByteArray();
	 * 
	 * } catch (IOException e) { logger.error(e); throw new RuntimeException(e);
	 * } }
	 */
	// @RequestMapping(value = "/person/{id}", method = RequestMethod.GET,
	// headers = "Accept=application/html, application/xhtml+xml")
	// public String getPersonHTML(@PathVariable("id") Long id, Model model) {
	// logger.debug("Provider has received request to get person with id: "
	// + id);
	//
	// // Call service to here
	// model.addAttribute("person", eventService.get(id));
	//
	// return "getpage";
	// }

	@RequestMapping(value = "/events", method = RequestMethod.GET, headers = "Accept=application/xml, application/json")
	public @ResponseBody
	EventList getEvent() {
		logger.debug("Provider has received request to get all persons");

		// Call service here
		EventList result = new EventList();
		result.setData(eventService.getAll());

		return result;
	}

	@RequestMapping(value = "/event/{id}", method = RequestMethod.GET, headers = "Accept=application/xml, application/json")
	public @ResponseBody
	EventCommunity getEvent(@PathVariable("id") String id) {
		logger.debug("Provider has received request to get event with id: "
				+ id);

		// Call service here
		return eventService.get(id);
	}

	@RequestMapping(value = "/event", method = RequestMethod.POST, headers = "Accept=application/xml, application/json")
	public @ResponseBody
	EventCommunity addEvent(@RequestBody EventCommunity event) {
		logger.debug("Provider has received request to add new person");

		// Call service to here
		return eventService.create(event);
	}

//	@RequestMapping(value = "/person/{id}", method = RequestMethod.PUT, headers = "Accept=application/xml, application/json")
//	public @ResponseBody
//	String updatePerson(@RequestBody EventCommunity person,
//			@PathVariable("id") Long id) {
//		logger.debug("Provider has received request to edit person with id: "
//				+ id);
//
//		// Call service here
//		person.setId(id);
//		return eventService.edit(person).toString();
//	}
//
//	@RequestMapping(value = "/person/{id}", method = RequestMethod.DELETE, headers = "Accept=application/xml, application/json")
//	public @ResponseBody
//	String deleteEmployee(@PathVariable("id") Long id) {
//		logger.debug("Provider has received request to delete person with id: "
//				+ id);
//
//		// Call service here
//		return eventService.delete(id).toString();
//	}
}