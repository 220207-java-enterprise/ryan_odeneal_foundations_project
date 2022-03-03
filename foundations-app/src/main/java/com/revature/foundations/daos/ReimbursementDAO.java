package com.revature.foundations.daos;

import com.revature.foundations.models.*;
import com.revature.foundations.util.Bytea;
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
            "ers_reimbursement_statuses.status, " +
            "ers_reimbursement_types.type " +
            "FROM ers_reimbursements " +
            "JOIN ers_reimbursement_statuses " +
            "ON ers_reimbursements.status_id=ers_reimbursement_statuses.status_id " +
            "JOIN ers_reimbursement_types " +
            "ON ers_reimbursements.type_id=ers_reimbursement_types.type_id ";

    @Override
    public void save(Reimbursement newObject) {
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            /*conn.setAutoCommit(false);
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
*/
            conn.setAutoCommit(false);
            PreparedStatement pstmt3 = conn.prepareStatement("INSERT INTO ers_reimbursements VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            pstmt3.setString(1, newObject.getReimb_id());
            pstmt3.setDouble(2, newObject.getAmount());
            pstmt3.setTimestamp(3, newObject.getSubmitted());
            pstmt3.setTimestamp(4, newObject.getResolved());
            pstmt3.setString(5, newObject.getDescription());
            pstmt3.setBinaryStream(6, newObject.getReceipt().getBinaryStream());
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
        Reimbursement aReimbursement = null;

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            PreparedStatement pstmt = conn.prepareStatement(rootSelect + "WHERE reimb_id = ?");
            pstmt.setString(1, id);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                aReimbursement = new Reimbursement();
                aReimbursement.setReimb_id(rs.getString("reimb_id"));
                aReimbursement.setAmount(rs.getDouble("amount"));
                aReimbursement.setSubmitted(rs.getTimestamp("submitted"));
                aReimbursement.setResolved(rs.getTimestamp("resolved"));
                aReimbursement.setDescription(rs.getString("description"));
                aReimbursement.setReceipt(new Bytea(rs.getBytes("receipt")));
                aReimbursement.setPayment_id(rs.getString("payment_id"));
                aReimbursement.setAuthor_id(rs.getString("author_id"));
                aReimbursement.setResolver_id(rs.getString("resolver_id"));
                aReimbursement.setStatus(new ReimbursementStatus(rs.getString("status_id"), rs.getString("status")));
                aReimbursement.setType(new ReimbursementType(rs.getString("type_id"), rs.getString("type")));
            }

        } catch (SQLException e) {
            throw new DataSourceException(e);
        }

        return aReimbursement;
    }


    public Reimbursement getByAuthorId(String id) {
        Reimbursement aReimbursement = null;

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            PreparedStatement pstmt = conn.prepareStatement(rootSelect + "WHERE author_id = ?");
            pstmt.setString(1, id);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                aReimbursement = new Reimbursement();
                aReimbursement.setReimb_id(rs.getString("reimb_id"));
                aReimbursement.setAmount(rs.getDouble("amount"));
                aReimbursement.setSubmitted(rs.getTimestamp("submitted"));
                aReimbursement.setResolved(rs.getTimestamp("resolved"));
                aReimbursement.setDescription(rs.getString("description"));
                aReimbursement.setReceipt(new Bytea(rs.getBytes("receipt")));
                aReimbursement.setPayment_id(rs.getString("payment_id"));
                aReimbursement.setAuthor_id(rs.getString("author_id"));
                aReimbursement.setResolver_id(rs.getString("resolver_id"));
                aReimbursement.setStatus(new ReimbursementStatus(rs.getString("status_id"), rs.getString("status")));
                aReimbursement.setType(new ReimbursementType(rs.getString("type_id"), rs.getString("type")));
            }

        } catch (SQLException e) {
            throw new DataSourceException(e);
        }

        return aReimbursement;
    }

    @Override
    public List<Reimbursement> getAll() {
        List<Reimbursement> reimbursements = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            ResultSet rs = conn.createStatement().executeQuery(rootSelect);
            while (rs.next()) {
                Reimbursement aReimbursement = new Reimbursement();
                aReimbursement.setReimb_id(rs.getString("reimb_id"));
                aReimbursement.setAmount(rs.getDouble("amount"));
                aReimbursement.setSubmitted(rs.getTimestamp("submitted"));
                aReimbursement.setResolved(rs.getTimestamp("resolved"));
                aReimbursement.setDescription(rs.getString("description"));
                aReimbursement.setReceipt(new Bytea(rs.getBytes("receipt")));
                aReimbursement.setPayment_id(rs.getString("payment_id"));
                aReimbursement.setAuthor_id(rs.getString("author_id"));
                aReimbursement.setResolver_id(String.valueOf(rs.getString("resolver_id")));
                aReimbursement.setStatus(new ReimbursementStatus(rs.getString("status_id"), rs.getString("status")));
                aReimbursement.setType(new ReimbursementType(rs.getString("type_id"), rs.getString("type")));
                reimbursements.add(aReimbursement);
            }
        } catch (SQLException e) {
            throw new DataSourceException(e);
        }

        return reimbursements;
    }

    @Override
    public void update(Reimbursement updatedObject) {
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn.prepareStatement("UPDATE ers_reimbursements " +
                    "SET amount = ?, " +
                    "resolved = ?, " +
                    "description = ?, " +
                    "receipt = ?, " +
                    "payment_id = ?, " +
                    "resolver_id = ?, " +
                    "status_id = ? " +
                    "WHERE reimb_id = ?");
            pstmt.setDouble(1, updatedObject.getAmount());
            pstmt.setTimestamp(2, updatedObject.getResolved());
            pstmt.setString(3, updatedObject.getDescription());
            pstmt.setBinaryStream(4, updatedObject.getReceipt().getBinaryStream());
            pstmt.setString(5, updatedObject.getPayment_id());
            pstmt.setString(6, updatedObject.getResolver_id());
            pstmt.setString(7, updatedObject.getStatus().getStatus_id());
            pstmt.setString(8, updatedObject.getReimb_id());


            // TODO allow role to be updated as well

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted != 1) {
                throw new ResourcePersistenceException("Failed to update user data within datasource.");
            }

            conn.commit();

        } catch (SQLException e) {
            throw new DataSourceException(e);
        }
    }

    @Override
    public void deleteById(Reimbursement objectToDelete) {
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn.prepareStatement("DELETE FROM ers_reimbursement_statuses WHERE status_id = ?");
            pstmt.setString(1, objectToDelete.getStatus().getStatus_id());

            int rowsInserted = pstmt.executeUpdate();
            System.out.println(rowsInserted);
            if (rowsInserted != 1) {
                conn.rollback();
                throw new ResourcePersistenceException("Failed to delete user from data source");
            }

            conn.commit();

            conn.setAutoCommit(false);
            PreparedStatement pstmt2 = conn.prepareStatement("DELETE FROM ers_reimbursement_types WHERE type_id = ?");
            pstmt2.setString(1, objectToDelete.getType().getType_id());

            int rowsInserted2 = pstmt2.executeUpdate();
            System.out.println(rowsInserted);
            if (rowsInserted2 != 1) {
                conn.rollback();
                throw new ResourcePersistenceException("Failed to delete user from data source");
            }

            conn.commit();

        } catch (SQLException e) {
            throw new DataSourceException(e);
        }
    }
}
