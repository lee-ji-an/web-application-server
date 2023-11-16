package mvc.db.dao;

import static org.junit.Assert.*;

import mvc.model.User;
import org.junit.Test;

import java.sql.SQLException;

public class UserInsertTest {

    @Test
    public void create_user() throws SQLException {
        // given
        String userId = "testUserId";
        String testPassword = "testPwd";
        String testName = "testName";
        String testEmail = "test@example.com";

        // when
        UserDao userDao = new UserDao();
        userDao.insert(new User(
                userId,
                testPassword,
                testName,
                testEmail
        ));

        //then
        User user = userDao.findByUserId(userId);
        assertEquals(userId, user.getUserId());
        assertEquals(testPassword, user.getPassword());
        assertEquals(testName, user.getName());
        assertEquals(testEmail, user.getEmail());
    }

}
