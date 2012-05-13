package com.reachout.ws.domain;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="topics")
public class TopicList {

	@XmlElement(required = true)
	private List<Topic> data;

	@XmlElement(required = false)
	public List<Topic> getData() {
		return data;
	}

	public void setData(List<Topic> data) {
		this.data = data;
	}
}
