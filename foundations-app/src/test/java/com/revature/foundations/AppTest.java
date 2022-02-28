package com.revature.foundations;

import static org.junit.Assert.assertTrue;

import com.revature.foundations.daos.UserDAO;
import com.revature.foundations.models.AppUser;
import com.revature.foundations.models.UserRole;
import com.revature.foundations.util.ConnectionFactory;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void grabbingAConnection()
    {
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void saveAUser() {
        UserRole aRole = new UserRole("test_role_id", "test_role");
        AppUser aUser = new AppUser("user_id", "username", "email", "password", "givenName", "surname", false, aRole);
        UserDAO dataObject = new UserDAO();
        dataObject.save(aUser);

    }

    @Test
    public void deleteAUser(){
        UserRole aRole = new UserRole("test_role_id", "test_role");
        AppUser aUser = new AppUser("user_id", "username", "email", "password", "givenName", "surname", false, aRole);
        UserDAO dataObject = new UserDAO();
        dataObject.deleteById(aUser.getRole().getId());
    }
}
