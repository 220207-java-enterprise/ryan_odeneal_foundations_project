package com.revature.foundations.models;

import java.util.Objects;

// POJO = Plain Ol' Java Object
// Contains NO BUSINESS LOGIC
// Simple encapsulation of some domain object's states
public class ReimbursementType {

	private String type_id;
	private String type;

	public ReimbursementType(String type_id, String type) {
		this.type_id = type_id;
		this.type = type;
	}

	public String getType_id() {
		return type_id;
	}

	public void setType_id(String type_id) {
		this.type_id = type_id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof ReimbursementType)) return false;
		ReimbursementType that = (ReimbursementType) o;
		return Objects.equals(getType_id(), that.getType_id()) && Objects.equals(getType(), that.getType());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getType_id(), getType());
	}

	@Override
	public String toString() {
		return "ReimbursementType{" +
				"type_id='" + type_id + '\'' +
				", type='" + type + '\'' +
				'}';
	}
}
