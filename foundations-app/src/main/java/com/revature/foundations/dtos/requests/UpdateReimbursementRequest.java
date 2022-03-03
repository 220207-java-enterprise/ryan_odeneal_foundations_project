package com.revature.foundations.dtos.requests;

import com.revature.foundations.daos.ReimbursementDAO;
import com.revature.foundations.daos.UserDAO;
import com.revature.foundations.models.AppUser;
import com.revature.foundations.models.Reimbursement;
import com.revature.foundations.models.ReimbursementStatus;
import com.revature.foundations.models.UserRole;

import java.sql.Timestamp;
import java.util.Objects;

public class UpdateReimbursementRequest {
    private String author_id;
    private String resolver_id;
    private String status;

    public UpdateReimbursementRequest(){super();}

    public UpdateReimbursementRequest(String author_id, String resolver_id, String status) {
        this.author_id = author_id;
        this.resolver_id = resolver_id;
        this.status = status;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UpdateReimbursementRequest)) return false;
        UpdateReimbursementRequest that = (UpdateReimbursementRequest) o;
        return Objects.equals(getAuthor_id(), that.getAuthor_id()) && Objects.equals(getResolver_id(), that.getResolver_id()) && Objects.equals(getStatus(), that.getStatus());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAuthor_id(), getResolver_id(), getStatus());
    }

    @Override
    public String toString() {
        return "UpdateReimbursementRequest{" +
                "author_id='" + author_id + '\'' +
                ", resolver_id='" + resolver_id + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
