package com.pettech.data.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MessageResponse {
	
	private int status;
	private int response;
	private Object data;
	private String message;
	
	public MessageResponse(int status, int response, Object data) {
		super();
		this.status = status;
		this.response = response;
		this.data = data;
	}
	public MessageResponse(int status, int response) {
		super();
		this.status = status;
		this.response = response;
	}
	public MessageResponse() {
		super();
	}
	
	public MessageResponse(int status, int response, Object data,String message) {
		super();
		this.status = status;
		this.response = response;
		this.data = data;
		this.message = message;
	}

}
