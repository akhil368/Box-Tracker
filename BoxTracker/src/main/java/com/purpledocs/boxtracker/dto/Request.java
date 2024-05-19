package com.purpledocs.boxtracker.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import jakarta.validation.Valid;

@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class Request<T> {

	@Valid
	private T query;
	private String requestId;

	private String timestamp;

	public T getQuery() {
		return query;
	}

	public void setQuery(T query) {
		this.query = query;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public Request() {
	}

	/**
	 * @param query
	 * @param requestId
	 * @param loggedInUser
	 * @param timestamp
	 */

	@Override
	public String toString() {
		return "Request [query=" + query + ", requestId=" + requestId + ", timestamp=" + timestamp + "]";
	}

}
