package com.revature.foundations.daos;

import com.revature.foundations.models.AppUser;
import com.revature.foundations.models.UserRole;
import com.revature.foundations.util.ConnectionFactory;
import com.revature.foundations.util.exceptions.DataSourceException;
import com.revature.foundations.util.exceptions.ResourcePersistenceException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// TODO attempt to centralize exception handling in service layer
public class UserDAO implements CrudDAO<AppUser> {

	//TODO
	//The tables are in the public schema. We'll see if that messes with
	//anything during testing.
    private final String rootSelect = "SELECT ers_users.user_id, " + 
											 "ers_users.username, " + 
											 "ers_users.email, " +
											 "ers_users.password, " +
											 "ers_users.given_name, " + 
											 "ers_users.surname, " + 
											 "ers_users.is_active, " + 
											 "ers_users.role_id, " +
											 "ers_user_roles.role " +
									  "FROM ers_users " + 
									  "JOIN ers_user_roles " +
									  "ON ers_users.role_id=ers_user_roles.role_id ";

	public AppUser findUserByUsername(String username) {

        AppUser user = null;

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            PreparedStatement pstmt = conn.prepareStatement(rootSelect + "WHERE username = ?");
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                user = new AppUser();
                user.setUser_id(rs.getString("user_id"));
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setGiven_name(rs.getString("given_name"));
                user.setSurname(rs.getString("surname"));
				user.setIs_active(rs.getBoolean("is_active"));
                user.setRole(new UserRole(rs.getString("role_id"), rs.getString("role")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    public AppUser findUserByEmail(String email) {

        AppUser user = null;

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            PreparedStatement pstmt = conn.prepareStatement(rootSelect + "WHERE email = ?");
            pstmt.setString(1, email);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                user = new AppUser();
                user.setUser_id(rs.getString("user_id"));
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setGiven_name(rs.getString("given_name"));
                user.setSurname(rs.getString("surname"));
                user.setIs_active(rs.getBoolean("is_active"));
                user.setRole(new UserRole(rs.getString("role_id"), rs.getString("role")));

            }

        } catch (SQLException e) {
            throw new DataSourceException(e);
        }

        return user;

    }

    public AppUser findUserByUsernameAndPassword(String username, String password) {

        AppUser authUser = null;

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            PreparedStatement pstmt = conn.prepareStatement(rootSelect + "WHERE username = ? AND password = ?");
            pstmt.setString(1, username);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                authUser = new AppUser();
                authUser.setUser_id(rs.getString("user_id"));
                authUser.setUsername(rs.getString("username"));
                authUser.setEmail(rs.getString("email"));
                authUser.setPassword(rs.getString("password"));
                authUser.setGiven_name(rs.getString("given_name"));
                authUser.setSurname(rs.getString("surname"));
                authUser.setIs_active(rs.getBoolean("is_active"));
                authUser.setRole(new UserRole(rs.getString("role_id"), rs.getString("role")));
            }

        } catch (SQLException e) {
            throw new DataSourceException(e);
        }

        return authUser;
    }

    @Override
    public void save(AppUser newUser) {

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

          /*  conn.setAutoCommit(false);
            PreparedStatement pstmt1 = conn.prepareStatement("INSERT INTO ers_user_roles VALUES (?, ?)");
            pstmt1.setString(1, newUser.getRole().getId());
            pstmt1.setString(2, newUser.getRole().getRoleName());

            int rowsInserted1 = pstmt1.executeUpdate();
            if (rowsInserted1 != 1) {
                conn.rollback();
                throw new ResourcePersistenceException("Failed to persist user role to data source");
            }

            conn.commit(); */

            conn.setAutoCommit(false);
            PreparedStatement pstmt2 = conn.prepareStatement("INSERT INTO ers_users VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            pstmt2.setString(1, newUser.getUser_id());
            pstmt2.setString(2, newUser.getUsername());
            pstmt2.setString(3, newUser.getEmail());
            pstmt2.setString(4, newUser.getPassword());
            pstmt2.setString(5, newUser.getGiven_name());
            pstmt2.setString(6, newUser.getSurname());
			pstmt2.setBoolean(7, newUser.isIs_active());
            pstmt2.setString(8, newUser.getRole().getId());

            int rowsInserted2 = pstmt2.executeUpdate();
            if (rowsInserted2 != 1) {
                conn.rollback();
                throw new ResourcePersistenceException("Failed to persist user to data source");
            }

            conn.commit();

        } catch (SQLException e) {
            throw new DataSourceException(e);
        }
    }

    @Override
    public AppUser getById(String id) {

        AppUser user = null;

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            PreparedStatement pstmt = conn.prepareStatement(rootSelect + "WHERE user_id = ?");
            pstmt.setString(1, id);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                user = new AppUser();
                user.setUser_id(rs.getString("user_id"));
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setGiven_name(rs.getString("given_name"));
                user.setSurname(rs.getString("surname"));
				user.setIs_active(rs.getBoolean("is_active"));
                user.setRole(new UserRole(rs.getString("role_id"), rs.getString("role")));
            }

        } catch (SQLException e) {
            throw new DataSourceException(e);
        }

        return user;

    }

    @Override
    public List<AppUser> getAll() {

        List<AppUser> users = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            ResultSet rs = conn.createStatement().executeQuery(rootSelect);
            while (rs.next()) {
                AppUser user = new AppUser();
				user.setUser_id(rs.getString("user_id"));
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setGiven_name(rs.getString("given_name"));
                user.setSurname(rs.getString("surname"));
				user.setIs_active(rs.getBoolean("is_active"));
                user.setRole(new UserRole(rs.getString("role_id"), rs.getString("role")));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new DataSourceException(e);
        }

        return users;
    }

    @Override
    public void update(AppUser updatedUser) {
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn.prepareStatement("UPDATE ers_users " +
                                                            "SET username = ?, " +
                                                                "email = ?, " +
                                                                "password = ?, " +
                                                                "given_name = ?, " +
																"surname = ?, " +
																"is_active = ?, " +
                                                                "role_id = ? " +
                                                            "WHERE user_id = ?");
            pstmt.setString(1, updatedUser.getUsername());
            pstmt.setString(2, updatedUser.getEmail());
            pstmt.setString(3, updatedUser.getPassword());
            pstmt.setString(4, updatedUser.getGiven_name());
            pstmt.setString(5, updatedUser.getSurname());
            pstmt.setBoolean(6, updatedUser.isIs_active());
            pstmt.setString(7, updatedUser.getRole().getRoleName());
			pstmt.setString(8, updatedUser.getUser_id());

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
    public void deleteById(AppUser objectToDelete) {
   /*     try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn.prepareStatement("DELETE FROM ers_user_roles WHERE role_id = ?");
            pstmt.setString(1, objectToDelete.getRole().getId());

            int rowsInserted = pstmt.executeUpdate();
            System.out.println(rowsInserted);
            if (rowsInserted != 1) {
                conn.rollback();
                throw new ResourcePersistenceException("Failed to delete user from data source");
            }

            conn.commit();

        } catch (SQLException e) {
            throw new DataSourceException(e);
        } */
    }
}

