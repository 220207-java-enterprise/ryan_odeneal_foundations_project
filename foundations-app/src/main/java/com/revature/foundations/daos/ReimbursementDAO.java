package com.revature.foundations.daos;

import com.revature.foundations.models.Reimbursement;
import com.revature.foundations.models.ReimbursementStatus;
import com.revature.foundations.models.ReimbursementType;
import com.revature.foundations.util.ConnectionFactory;
import com.revature.foundations.util.exceptions.DataSourceException;
import com.revature.foundations.util.exceptions.ResourcePersistenceException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReimbursementDAO implements CrudDAO<Reimbursement>{

    //TODO
    //The tables are in the public schema. We'll see if that messes with
    //anything during testing.
    private final String rootSelect = "SELECT ers_reimbursements.reimb_id, " +
            "ers_reimbursements.amount, " +
            "ers_reimbursements.submitted, " +
            "ers_reimbursements.resolved, " +
            "ers_reimbursements.description, " +
            "ers_reimbursements.receipt, " +
            "ers_reimbursements.payment_id, " +
            "ers_reimbursements.author_id, " +
            "ers_reimbursements.resolver_id, " +
            "ers_reimbursements.status_id, " +
            "ers_reimbursements.type_id, " +
            "ers_reimbursements_statuses.status, " +
            "ers_reimbursements_types.type " +
            "FROM ers_reimbursements " +
            "JOIN ers_reimbursement_statuses " +
            "ON ers_reimbursement.status_id=ers_reimbursement_statuses.status_id " +
            "JOIN ers_reimbursement_types " +
            "ON ers_reimbursement.type_id=ers_reimbursement_types.type_id ";

    @Override
    public void save(Reimbursement newObject) {
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            conn.setAutoCommit(false);
            PreparedStatement pstmt1 = conn.prepareStatement("INSERT INTO ers_reimbursement_types VALUES (?, ?)");
            pstmt1.setString(1, newObject.getType().getType_id());
            pstmt1.setString(2, newObject.getType().getType()); //THIS COULD BE A SOURCE OF ERROR. SCOPING WEIRDNESS.

            int rowsInserted1 = pstmt1.executeUpdate();
            if (rowsInserted1 != 1) {
                conn.rollback();
                throw new ResourcePersistenceException("Failed to persist user role to data source");
            }

            conn.commit();

            conn.setAutoCommit(false);
            PreparedStatement pstmt2 = conn.prepareStatement("INSERT INTO ers_reimbursement_statuses VALUES (?, ?)");
            pstmt2.setString(1, newObject.getStatus().getStatus_id());
            pstmt2.setString(2, newObject.getStatus().getStatus()); //THIS COULD BE A SOURCE OF ERROR. SCOPING WEIRDNESS.

            int rowsInserted2 = pstmt2.executeUpdate();
            if (rowsInserted2 != 1) {
                conn.rollback();
                throw new ResourcePersistenceException("Failed to persist user role to data source");
            }

            conn.commit();

            conn.setAutoCommit(false);
            PreparedStatement pstmt3 = conn.prepareStatement("INSERT INTO ers_reimbursements VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            pstmt3.setString(1, newObject.getReimb_id());
            pstmt3.setDouble(2, newObject.getAmount());
            pstmt3.setTimestamp(3, newObject.getSubmitted());
            pstmt3.setTimestamp(4, newObject.getResolved());
            pstmt3.setString(5, newObject.getDescription());
            pstmt3.setBlob(6, newObject.getReceipt());
            pstmt3.setString(7, newObject.getPayment_id());
            pstmt3.setString(8, newObject.getAuthor_id());
            pstmt3.setString(9, newObject.getResolver_id());
            pstmt3.setString(10, newObject.getStatus().getStatus_id());
            pstmt3.setString(11, newObject.getType().getType_id());

            int rowsInserted3 = pstmt3.executeUpdate();
            if (rowsInserted3 != 1) {
                conn.rollback();
                throw new ResourcePersistenceException("Failed to persist user to data source");
            }

            conn.commit();

        } catch (SQLException e) {
            throw new DataSourceException(e);
        }
    }

    @Override
    public Reimbursement getById(String id) {
        return null;
    }

    @Override
    public List<Reimbursement> getAll() {
        return null;
    }

    @Override
    public void update(Reimbursement updatedObject) {

    }

    @Override
    public void deleteById(String id) {

    }
}
