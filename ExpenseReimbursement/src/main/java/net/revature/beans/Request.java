package net.revature.beans;

import java.util.Objects;

public class Request {
	private int requestId, submitterId, cost; 
	private String description, eventDate, submittedDate, status;
	
	public Request() {
		requestId = 0;
		submitterId = 0;
		cost = 0;
		description = "";
		eventDate = "";
		submittedDate = "";
		setStatus("Approved");
		
	}

	public int getRequestId() {
		return requestId;
	}

	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}

	public int getSubmitterId() {
		return submitterId;
	}

	public void setSubmitterId(int submitterId) {
		this.submitterId = submitterId;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
	public String getEventDate() {
		return eventDate;
	}

	public void setEventDate(String eventDate) {
		this.eventDate = eventDate;
	}

	public String getSubmittedDate() {
		return submittedDate;
	}

	public void setSubmittedDate(String submittedDate) {
		this.submittedDate = submittedDate;
	}
	

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Request [requestId=" + requestId + ", submitterId=" + submitterId + ", cost=" + cost + ", description="
				+ description + ", eventDate=" + eventDate + ", submittedDate="
				+ submittedDate + ", status=" + status + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(cost, description, eventDate, requestId, status, submittedDate, submitterId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Request other = (Request) obj;
		return cost == other.cost && Objects.equals(description, other.description)
				&& Objects.equals(eventDate, other.eventDate) && requestId == other.requestId
				&& Objects.equals(status, other.status) && Objects.equals(submittedDate, other.submittedDate)
				&& submitterId == other.submitterId;
	}

	

	
	
}
