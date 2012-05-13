package com.reachout.ws.domain;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/*@XmlRootElement(name = "persons")*/
public class PersonList {

/*	@XmlElement(required = true)*/
	public List<Person> data;

/*	@XmlElement(required = false)*/
	public List<Person> getData() {
		return data;
	}

	public void setData(List<Person> data) {
		this.data = data;
	}
}