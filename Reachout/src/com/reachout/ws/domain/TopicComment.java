package com.reachout.ws.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/*@XmlRootElement(name="topicComment")*/

@Embeddable
@Table(name = "topicComments", schema = "test1@cassandra_pu")
public class TopicComment implements Serializable {

	private static final long serialVersionUID = 1L;

	
	@Column(name = "commentId")
	private String commentId;
	@Column(name = "commentBy")
	private String commentBy;
	@Column(name = "commentDate")
	private String commentDate;
	@Column(name = "commentText")
	String commentText;

	public String getCommentId() {
		return commentId;
	}

	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}

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
