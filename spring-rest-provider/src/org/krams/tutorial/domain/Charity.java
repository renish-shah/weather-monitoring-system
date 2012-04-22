package org.krams.tutorial.domain;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "charity")
public class Charity {

	String name;
	String description;
	String createdBy;
	String fromCommunityId;
	String fromUserName;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getFromCommunityId() {
		return fromCommunityId;
	}

	public void setFromCommunityId(String fromCommunityId) {
		this.fromCommunityId = fromCommunityId;
	}

	public String getFromUserName() {
		return fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

}
