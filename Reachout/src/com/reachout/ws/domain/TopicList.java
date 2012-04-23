package com.reachout.ws.domain;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="topics")
public class TopicList {

	private List<Topic> data;

	public List<Topic> getData() {
		return data;
	}

	public void setData(List<Topic> data) {
		this.data = data;
	}
}
