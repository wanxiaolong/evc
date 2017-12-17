package com.my.evc.common;

import java.io.Serializable;

public class JsonResponse<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	private int status;
	private T response;

	public JsonResponse() {
		super();
	}

	public JsonResponse(int status) {
		super();
		this.status = status;
	}

	public JsonResponse(int status, T response) {
		super();
		this.status = status;
		this.response = response;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public T getResponse() {
		return response;
	}

	public void setResponse(T response) {
		this.response = response;
	}
}
