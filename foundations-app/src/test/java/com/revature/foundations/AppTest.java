package com.revature.foundations;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import com.revature.foundations.daos.UserDAO;
import com.revature.foundations.models.AppUser;
import com.revature.foundations.models.UserRole;
import com.revature.foundations.util.ConnectionFactory;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    //this will later be split into proper unit tests with mocks. For now I just want to see it work, lol.
    //REQUIRES DATABASE TO BE RUNNING.
    @Test
    public void testingUserDao() {
        UserRole aRole = new UserRole("test_role_id", "test_role");
        AppUser aUser = new AppUser("user_id", "username", "email", "password", "givenName", "surname", false, aRole);
        UserDAO dataObject = new UserDAO();
        dataObject.save(aUser);
        assertEquals(dataObject.findUserByUsername("username"), aUser);
        assertEquals(dataObject.findUserByEmail("email"), aUser);
        assertEquals(dataObject.findUserByUsernameAndPassword("username", "password"), aUser);
        assertEquals(dataObject.getById("user_id"), aUser);

        UserRole aRole2 = new UserRole("test_role_id2", "test_role2");
        AppUser aUser2 = new AppUser("user_id2", "username2", "email2", "password2", "givenName2", "surname2", false, aRole2);
        dataObject.save(aUser2);
        assertEquals(dataObject.findUserByUsername("username2"), aUser2);
        assertEquals(dataObject.findUserByEmail("email2"), aUser2);
        assertEquals(dataObject.findUserByUsernameAndPassword("username2", "password2"), aUser2);
        assertEquals(dataObject.getById("user_id2"), aUser2);


        List<AppUser> users = new ArrayList<>();
        users.add(aUser);
        users.add(aUser2);
        assertEquals(dataObject.getAll(), users);

        UserRole aRole3 = new UserRole("test_role_id3", "test_role3");
        AppUser aUser3 = new AppUser("user_id3", "username3", "email3", "password3", "givenName3", "surname3", false, aRole3);
        dataObject.save(aUser3);

        aUser3.setGiven_name("givenName4");
        dataObject.update(aUser3);
        aUser3.setGiven_name("givenName3");
        assertNotEquals(dataObject.findUserByUsername("username3"), aUser3);

        dataObject.deleteById(aUser3.getRole().getId());
        dataObject.deleteById(aUser2.getRole().getId());
        dataObject.deleteById(aUser.getRole().getId());

    }

  /*  @Test
    public void deleteAUser(){
        UserRole aRole = new UserRole("test_role_id", "test_role");
        AppUser aUser = new AppUser("user_id", "username", "email", "password", "givenName", "surname", false, aRole);
        UserDAO dataObject = new UserDAO();
        dataObject.deleteById(aUser.getRole().getId());
    }

   */
}
