package com.reachout.ws.domain;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/*@XmlRootElement(name = "topic")*/
public class Topic {

	Long id;
	String title;
	String description;
	String createdDate;
	String communityId;
	String createdBy;
	List<TopicComment> comments;

	public List<TopicComment> getComments() {
		return comments;
	}

	public void setComments(List<TopicComment> comments) {
		this.comments = comments;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getCommunityId() {
		return communityId;
	}

	public void setCommunityId(String communityId) {
		this.communityId = communityId;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

}
