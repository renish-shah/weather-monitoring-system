package com.reachout.ws.domain;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="charities")
public class CharityList {

	@XmlElement(required = true)
	private List<Charity> data;

	@XmlElement(required = false)
	public List<Charity> getData() {
		return data;
	}

	public void setData(List<Charity> data) {
		this.data = data;
	}
}
