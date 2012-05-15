package com.reachout.ws.domain;

import javax.xml.bind.annotation.XmlRootElement;

/*@XmlRootElement(name="person")*/
public class DistributeServiceParam {

	private String noOfHopes;

	public String getNoOfHopes() {
		return noOfHopes;
	}

	public void setNoOfHopes(String noOfHopes) {
		this.noOfHopes = noOfHopes;
	}

}
