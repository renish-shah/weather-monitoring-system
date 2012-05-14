package com.reachout.ws.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/*@XmlRootElement(name = "topic")*/
@Entity
@Table(name = "topics", schema = "test1@cassandra_pu")
public class Topic {

	@Id
	private String id;
	@Column(name = "topicTitle")
	private String topicTitle;
	@Column(name = "topicDescription")
	private String topicDescription;
	@Column(name = "createdDate")
	private String createdDate;
	@Column(name = "communityID")
	private String communityID;
	@Column(name = "createdBy")
	private String createdBy;
	@Column(name = "comments")
	private List<TopicComment> comments = new ArrayList<TopicComment>();
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTopicTitle() {
		return topicTitle;
	}
	public void setTopicTitle(String topicTitle) {
		this.topicTitle = topicTitle;
	}
	public String getTopicDescription() {
		return topicDescription;
	}
	public void setTopicDescription(String topicDescription) {
		this.topicDescription = topicDescription;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getCommunityID() {
		return communityID;
	}
	public void setCommunityID(String communityID) {
		this.communityID = communityID;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public List<TopicComment> getComments() {
		return comments;
	}
	public void setComments(List<TopicComment> comments) {
		this.comments = comments;
	}


}
