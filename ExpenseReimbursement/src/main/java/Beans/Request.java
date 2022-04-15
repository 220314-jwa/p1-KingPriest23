package Beans;

import java.util.Objects;

public class Request {
	private int requestId, submitterId, eventTypeId, statusId, cost; 
	private String description, location, eventDate, submittedDate;
	
	public Request() {
		
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

	public int getEventTypeId() {
		return eventTypeId;
	}

	public void setEventTypeId(int eventTypeId) {
		this.eventTypeId = eventTypeId;
	}

	public int getStatusId() {
		return statusId;
	}

	public void setStatusId(int statusId) {
		this.statusId = statusId;
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

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
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

	@Override
	public String toString() {
		return "Request [requestId=" + requestId + ", submitterId=" + submitterId + ", eventTypeId=" + eventTypeId
				+ ", statusId=" + statusId + ", cost=" + cost + ", description=" + description + ", location="
				+ location + ", eventDate=" + eventDate + ", submittedDate=" + submittedDate + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(cost, description, eventDate, eventTypeId, location, requestId, statusId, submittedDate,
				submitterId);
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
				&& Objects.equals(eventDate, other.eventDate) && eventTypeId == other.eventTypeId
				&& Objects.equals(location, other.location) && requestId == other.requestId
				&& statusId == other.statusId && Objects.equals(submittedDate, other.submittedDate)
				&& submitterId == other.submitterId;
	}
	
	
}
