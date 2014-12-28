package com.queueing.dao;

import org.apache.http.client.methods.HttpRequestBase;

public class RequestObject {

	private int id;
	private HttpRequestBase httpRequest;
	private Class httpRequestClass;
	private Class callbackClass;
	private long initiatedTime;
	
	
	
	public RequestObject(int id, HttpRequestBase httpRequest,
			Class httpRequestClass ,Class callbackClass, long initiatedTime) {
		super();
		this.id = id;
		this.httpRequest = httpRequest;
		this.callbackClass = callbackClass;
		this.initiatedTime = initiatedTime;
		this.httpRequestClass = httpRequestClass;
	}
	public HttpRequestBase getHttpRequest() {
		return httpRequest;
	}
	public void setHttpRequest(HttpRequestBase httpRequest) {
		this.httpRequest = httpRequest;
	}
	public Class getCallbackClass() {
		return callbackClass;
	}
	public void setCallbackClass(Class callbackClass) {
		this.callbackClass = callbackClass;
	}
	public long getInitiatedTime() {
		return initiatedTime;
	}
	public void setInitiatedTime(long initiatedTime) {
		this.initiatedTime = initiatedTime;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Class getHttpRequestClass() {
		return httpRequestClass;
	}
	public void setHttpRequestClass(Class httpRequestClass) {
		this.httpRequestClass = httpRequestClass;
	}
	
	
}
