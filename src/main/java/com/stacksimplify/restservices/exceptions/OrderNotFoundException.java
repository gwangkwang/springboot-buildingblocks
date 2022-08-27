package com.stacksimplify.restservices.exceptions;

public class OrderNotFoundException extends Exception {

	private static final long serialVersionUID = 8483848611770842416L;

	public OrderNotFoundException(String message) {
		super(message);
	}
}
