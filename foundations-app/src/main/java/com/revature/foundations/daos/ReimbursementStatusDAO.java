package com.revature.foundations.daos;

import com.revature.foundations.models.ReimbursementStatus;
import com.revature.foundations.util.ConnectionFactory;
import com.revature.foundations.util.exceptions.DataSourceException;
import com.revature.foundations.util.exceptions.ResourcePersistenceException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ReimbursementStatusDAO implements CrudDAO<ReimbursementStatus> {

    @Override
    public void save(ReimbursementStatus newObject) {

    }

    @Override
    public ReimbursementStatus getById(String id) {
        return null;
    }

    @Override
    public List<ReimbursementStatus> getAll() {
        return null;
    }

    @Override
    public void update(ReimbursementStatus updatedObject) {
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn.prepareStatement("UPDATE ers_reimbursement_statuses " +
                    "SET status = ? " +
                    "WHERE status_id = ?");
            pstmt.setString(1, updatedObject.getStatus());
            pstmt.setString(2, updatedObject.getStatus_id());


            // TODO allow role to be updated as well

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted != 1) {
                throw new ResourcePersistenceException("Failed to update user data within datasource.");
            }

            conn.commit();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataSourceException(e);
        }
    }

    @Override
    public void deleteById(ReimbursementStatus newObject) {

    }
}
