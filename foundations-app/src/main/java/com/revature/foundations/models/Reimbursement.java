package com.revature.foundations.models;

import com.revature.foundations.util.Bytea;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Objects;
import java.sql.Blob;
import java.sql.Timestamp;

// POJO = Plain Ol' Java Object
// Contains NO BUSINESS LOGIC
// Simple encapsulation of some domain object's states
public class Reimbursement {

	private String reimb_id;
	private double amount;
	private Timestamp submitted;
	private Timestamp resolved;
	private String description;
	private Bytea receipt;
	private String payment_id;
	private String author_id;
	private String resolver_id;
	private ReimbursementStatus status;
	private ReimbursementType type;

	public Reimbursement(){super();}

	public Reimbursement(String reimb_id, double amount,
						 Timestamp submitted,
						 Timestamp resolved,
						 String description,
						 Bytea receipt,
						 String payment_id,
						 String author_id,
						 String resolver_id,
						 ReimbursementType type,
						 ReimbursementStatus status) {
		this.reimb_id = reimb_id;
		this.amount = amount;
		this.submitted = submitted;
		this.resolved = resolved;
		this.description = description;
		this.receipt = receipt;
		this.payment_id = payment_id;
		this.author_id = author_id;
		this.resolver_id = resolver_id;
		this.type = type;
		this.status = status;
	}

	public String getReimb_id() {
		return reimb_id;
	}

	public void setReimb_id(String reimb_id) {
		this.reimb_id = reimb_id;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Timestamp getSubmitted() {
		return submitted;
	}

	public void setSubmitted(Timestamp submitted) {
		this.submitted = submitted;
	}

	public Timestamp getResolved() {
		return resolved;
	}

	public void setResolved(Timestamp resolved) {
		this.resolved = resolved;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Bytea getReceipt() {
		return receipt;
	}

	public void setReceipt(Bytea receipt) {
		this.receipt = receipt;
	}

	public String getPayment_id() {
		return payment_id;
	}

	public void setPayment_id(String payment_id) {
		this.payment_id = payment_id;
	}

	public String getAuthor_id() {
		return author_id;
	}

	public void setAuthor_id(String author_id) {
		this.author_id = author_id;
	}

	public String getResolver_id() {
		return resolver_id;
	}

	public void setResolver_id(String resolver_id) {
		this.resolver_id = resolver_id;
	}

	public ReimbursementStatus getStatus() {
		return status;
	}

	public void setStatus(ReimbursementStatus status) {
		this.status = status;
	}

	public ReimbursementType getType() {
		return type;
	}

	public void setType(ReimbursementType type) {
		this.type = type;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Reimbursement)) return false;
		Reimbursement that = (Reimbursement) o;
		return Double.compare(that.getAmount(), getAmount()) == 0 && Objects.equals(getReimb_id(), that.getReimb_id()) && Objects.equals(getSubmitted(), that.getSubmitted()) && Objects.equals(getResolved(), that.getResolved()) && Objects.equals(getDescription(), that.getDescription()) && Objects.equals(getReceipt(), that.getReceipt()) && Objects.equals(getPayment_id(), that.getPayment_id()) && Objects.equals(getAuthor_id(), that.getAuthor_id()) && Objects.equals(getResolver_id(), that.getResolver_id()) && Objects.equals(getStatus(), that.getStatus()) && Objects.equals(getType(), that.getType());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getReimb_id(), getAmount(), getSubmitted(), getResolved(), getDescription(), getReceipt(), getPayment_id(), getAuthor_id(), getResolver_id(), getStatus(), getType());
	}

	@Override
	public String toString() {
		return "Reimbursement{" +
				"reimb_id='" + reimb_id + '\'' +
				", amount=" + amount +
				", submitted=" + submitted +
				", Resolved=" + resolved +
				", description='" + description + '\'' +
				", receipt=" + receipt +
				", payment_id='" + payment_id + '\'' +
				", author_id='" + author_id + '\'' +
				", resolver_id='" + resolver_id + '\'' +
				", status=" + status +
				", type=" + type +
				'}';
	}
}

