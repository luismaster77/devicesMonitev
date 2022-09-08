package com.monitev.devices.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Date;

import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ExceptionResponse extends RuntimeException{

	private static final long serialVersionUID = 3523154871639928639L;
	
	private Date timestamp;
	private String message;
	private String details;
	
	public Date getTimestamp() {
		return timestamp;
	}

	public String getMessage() {
		return message;
	}

	public String getDetails() {
		return details;
	}

	public ExceptionResponse(Date timestamp, String message, String details) {
		super();
		this.timestamp = timestamp;
		this.message = message;
		this.details = details;
	}
}
