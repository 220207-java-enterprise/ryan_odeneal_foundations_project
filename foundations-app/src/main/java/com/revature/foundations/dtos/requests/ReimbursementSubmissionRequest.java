package com.revature.foundations.dtos.requests;

import com.revature.foundations.models.*;
import com.revature.foundations.util.Bytea;

import java.sql.Timestamp;
import java.util.UUID;

public class ReimbursementSubmissionRequest {
    private double amount;
    private String description;
    private Bytea receipt;
    private String author_id;
    private String type;

    public ReimbursementSubmissionRequest(){super();}

    public ReimbursementSubmissionRequest(double amount, String description, Bytea receipt, String author_id, String type) {
        this.amount = amount;
        this.description = description;
        this.receipt = receipt;
        this.author_id = author_id;
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
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

    public String getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(String author_id) {
        this.author_id = author_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Reimbursement extractReimbursement() {
        String reimb_id = UUID.randomUUID().toString();
        ReimbursementStatus status = new ReimbursementStatus("PENDING", "PENDING");
        ReimbursementType type = new ReimbursementType(this.type, this.type);
        return new Reimbursement(reimb_id, this.amount, new Timestamp(System.currentTimeMillis()),
                                new Timestamp(0), this.description, this.receipt, null, this.author_id, null, type, status);
    }

    @Override
    public String toString() {
        return "ReimbursementSubmissionRequest{" +
                "amount=" + amount +
                ", description='" + description + '\'' +
                ", receipt=" + receipt +
                ", author_id='" + author_id + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
