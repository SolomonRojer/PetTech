package com.pettech.data.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MessageResponse {
	
	private String status;
	private int response;
	private Object data;
	public MessageResponse(String status, int response, Object data) {
		super();
		this.status = status;
		this.response = response;
		this.data = data;
	}
	public MessageResponse(String status, int response) {
		super();
		this.status = status;
		this.response = response;
	}

}
