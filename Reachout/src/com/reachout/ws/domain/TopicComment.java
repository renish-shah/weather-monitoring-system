package com.reachout.ws.domain;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="topicComment")
public class TopicComment {

	String commentBy;
	String commentDate;
	String commentText;
	

	public String getCommentBy() {
		return commentBy;
	}

	public void setCommentBy(String commentBy) {
		this.commentBy = commentBy;
	}

	public String getCommentDate() {
		return commentDate;
	}

	public void setCommentDate(String commentDate) {
		this.commentDate = commentDate;
	}

	public String getCommentText() {
		return commentText;
	}

	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}

}
