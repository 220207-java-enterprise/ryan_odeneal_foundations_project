package com.revature.foundations.models;

import java.util.Objects;

// POJO = Plain Ol' Java Object
// Contains NO BUSINESS LOGIC
// Simple encapsulation of some domain object's states
public class ReimbursementStatus {

	private String status_id;
	private String status;

	public ReimbursementStatus(String status_id, String status) {
		this.status_id = status_id;
		this.status = status;
	}

	public String getStatus_id() {
		return status_id;
	}

	public void setStatus_id(String status_id) {
		this.status_id = status_id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof ReimbursementStatus)) return false;
		ReimbursementStatus that = (ReimbursementStatus) o;
		return Objects.equals(getStatus_id(), that.getStatus_id()) && Objects.equals(getStatus(), that.getStatus());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getStatus_id(), getStatus());
	}

	@Override
	public String toString() {
		return "ReimbursementStatus{" +
				"status_id='" + status_id + '\'' +
				", status='" + status + '\'' +
				'}';
	}
}
