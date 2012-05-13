/**
 * 
 */
package com.reachout.ws.domain;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author RENISH
 * 
 */
@XmlRootElement(name="donation")
public class Donation {

	String userCommunityId;
	String userName;
	String donateAmt;

	public String getUserCommunityId() {
		return userCommunityId;
	}

	public void setUserCommunityId(String userCommunityId) {
		this.userCommunityId = userCommunityId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDonateAmt() {
		return donateAmt;
	}

	public void setDonateAmt(String donateAmt) {
		this.donateAmt = donateAmt;
	}

}
