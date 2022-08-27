package com.stacksimplify.restservices.exceptions;

import java.util.Date;

//Simple custom error details
public class CustomErrorDetails {

	private Date timestamp;
	private String message;
	private String errordetails;
	
	//Fields Constructor
	public CustomErrorDetails(Date timestamp, String message, String errordetails) {
		super();
		this.timestamp = timestamp;
		this.message = message;
		this.errordetails = errordetails;
	}

	//GETTERS
	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getErrordetails() {
		return errordetails;
	}

	public void setErrordetails(String errordetails) {
		this.errordetails = errordetails;
	}
	
	
	
	
}
