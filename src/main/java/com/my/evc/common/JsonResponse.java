package com.my.evc.common;

import java.io.Serializable;

public class JsonResponse<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	private int status;
	private T data;

	public JsonResponse() {
		super();
	}

	public JsonResponse(int status) {
		super();
		this.status = status;
	}

	public JsonResponse(int status, T data) {
		super();
		this.status = status;
		this.data = data;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}
