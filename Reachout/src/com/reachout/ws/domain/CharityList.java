package com.reachout.ws.domain;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="charities")
public class CharityList {

	private List<Charity> data;

	public List<Charity> getData() {
		return data;
	}

	public void setData(List<Charity> data) {
		this.data = data;
	}
}
