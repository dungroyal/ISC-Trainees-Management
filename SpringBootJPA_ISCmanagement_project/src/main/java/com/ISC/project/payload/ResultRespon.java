package com.ISC.project.payload;

import java.util.List;

public class ResultRespon {
	private int status;
	private String message; 
	private List<?> data;
	
	public ResultRespon(int status, String message, List<?> data) {
		super();
		this.status = status;
		this.message = message;
		this.data = data;
	}

	public ResultRespon() {
		super();
	}


	public ResultRespon(int status, String message) {
		super();
		this.status = status;
		this.message = message;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<?> getData() {
		return data;
	}

	public void setData(List<?> data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "ResultRespon [status=" + status + ", message=" + message + ", data=" + data + "]";
	}
}
