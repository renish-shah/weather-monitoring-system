package com.reachout.ws.domain;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/*@XmlRootElement(name = "persons")*/
public class EventList {

/*	@XmlElement(required = true)*/
	public List<EventCommunity> data;

/*	@XmlElement(required = false)*/
	public List<EventCommunity> getData() {
		return data;
	}

	public void setData(List<EventCommunity> data) {
		this.data = data;
	}
}