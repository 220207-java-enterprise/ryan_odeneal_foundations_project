package com.revature.foundations;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import com.revature.foundations.daos.ReimbursementDAO;
import com.revature.foundations.daos.ReimbursementStatusDAO;
import com.revature.foundations.daos.UserDAO;
import com.revature.foundations.models.*;
import com.revature.foundations.util.Bytea;
import com.revature.foundations.util.ConnectionFactory;
import org.junit.Test;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
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
        //assertEquals(dataObject.getAll(), users);

        UserRole aRole3 = new UserRole("test_role_id3", "test_role3");
        AppUser aUser3 = new AppUser("user_id3", "username3", "email3", "password3", "givenName3", "surname3", false, aRole3);
        dataObject.save(aUser3);

        aUser3.setGiven_name("givenName4");
        dataObject.update(aUser3);
        aUser3.setGiven_name("givenName3");
        assertNotEquals(dataObject.findUserByUsername("username3"), aUser3);

        dataObject.deleteById(aUser3);
        dataObject.deleteById(aUser2);
        dataObject.deleteById(aUser);

    }

 /*   @Test
    public void testingReimbursementDAO() {
        UserRole aRole = new UserRole("test_role_id", "test_role");
        AppUser aUser = new AppUser("author_id", "username", "email", "password", "givenName", "surname", false, aRole);
        UserDAO dataUserObject = new UserDAO();
        dataUserObject.save(aUser);

        UserRole aRole2 = new UserRole("test_role_id2", "test_role2");
        AppUser aUser2 = new AppUser("resolver_id", "username2", "email2", "password2", "givenName2", "surname2", false, aRole2);
        dataUserObject.save(aUser2);

        ReimbursementType aType = new ReimbursementType("reimbursement_type_id", "reimbursement_type");
        ReimbursementStatus aStatus = new ReimbursementStatus("status_id", "status_type");
        Timestamp aTimeStamp = new Timestamp(System.currentTimeMillis());
        Timestamp aTimeStamp2 = new Timestamp(System.currentTimeMillis());
        byte[] aBlobData = {(byte) 8, (byte) 7, (byte) 6, (byte) 5, (byte) 4, (byte) 3, (byte) 2, (byte) 1};
        Bytea aBlob = new Bytea(aBlobData);
        Reimbursement aReimbursement = new Reimbursement("reimb_id", 45.53,
                                                         aTimeStamp, aTimeStamp2,
                                                         "description", aBlob,
                                                         "payment_id", "author_id",
                                                         "resolver_id", aType, aStatus);
        ReimbursementDAO dataReimbursementObject = new ReimbursementDAO();
        dataReimbursementObject.save(aReimbursement);

        ReimbursementType aType2 = new ReimbursementType("reimbursement_type_id2", "reimbursement_type2");
        ReimbursementStatus aStatus2 = new ReimbursementStatus("status_id2", "status_type2");
        Timestamp aTimeStamp3 = new Timestamp(System.currentTimeMillis());
        Timestamp aTimeStamp4 = new Timestamp(System.currentTimeMillis());
        byte[] aBlobData2 = {(byte) 14, (byte) 7, (byte) 6, (byte) 5, (byte) 4, (byte) 3, (byte) 2, (byte) 1};
        Bytea aBlob2 = new Bytea(aBlobData2);
        Reimbursement aReimbursement2 = new Reimbursement("reimb_id2", 47.53,
                aTimeStamp3, aTimeStamp4,
                "description2", aBlob2,
                "payment_id2", "author_id",
                "resolver_id", aType2, aStatus2);
        dataReimbursementObject.save(aReimbursement2);

        ArrayList<Reimbursement> reimbursementList = new ArrayList<>();
        reimbursementList.add(aReimbursement);
        reimbursementList.add(aReimbursement2);

        assertEquals(dataReimbursementObject.getAll(), reimbursementList);
        assertEquals(dataReimbursementObject.getById("reimb_id"), aReimbursement);

        Timestamp aTimeStamp5 = new Timestamp(System.currentTimeMillis());
        aReimbursement.setResolved(aTimeStamp5);
        dataReimbursementObject.update(aReimbursement);
        assertEquals(dataReimbursementObject.getById(aReimbursement.getReimb_id()), aReimbursement);

        aReimbursement.setResolved(aTimeStamp2);
        assertNotEquals(dataReimbursementObject.getById(aReimbursement.getReimb_id()), aReimbursement);

        dataReimbursementObject.deleteById(aReimbursement);
        dataReimbursementObject.deleteById(aReimbursement2);
        dataUserObject.deleteById(aUser);
        dataUserObject.deleteById(aUser2);
    }

    /*
    @Test
    public void deleteAUser(){
        UserRole aRole = new UserRole("test_role_id", "test_role");
        AppUser aUser = new AppUser("user_id", "username", "email", "password", "givenName", "surname", false, aRole);
        UserDAO dataObject = new UserDAO();
        dataObject.deleteById(aUser.getRole().getId());
    }

*/

    @Test
    public void updateReimbursementStatus() {
        ReimbursementStatusDAO aReimbursementStatusDAO = new ReimbursementStatusDAO();
        ReimbursementStatus aReimbursementStatus = new ReimbursementStatus("376a99f8-7047-4a7a-8c0b-95d3fabae3be", "Pending");
        aReimbursementStatusDAO.update(aReimbursementStatus);
    }
}
