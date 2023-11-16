package mvc.db.dao;

import mvc.db.config.ConnectionManager;
import mvc.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    public void insert(User user) throws SQLException {
        JdbcTemplate insertJdbcTemplate = new JdbcTemplate() {
            @Override
            protected String createQuery() {
                return "INSERT INTO USERS VALUES (?, ?, ?, ?)";
            }

            @Override
            protected void setValues(PreparedStatement pstmt) throws SQLException {
                pstmt.setString(1, user.getUserId());
                pstmt.setString(2, user.getPassword());
                pstmt.setString(3, user.getName());
                pstmt.setString(4, user.getEmail());
            }
        };
        insertJdbcTemplate.update();
    }

    public void update(User user) throws Exception {
        JdbcTemplate insertJdbcTemplate = new JdbcTemplate() {
            @Override
            protected String createQuery() {
                return "UPDATE USERS SET PASSWORD = ?, NAME = ?, EMAIL = ? WHERE USERID = ?";
            }

            @Override
            protected void setValues(PreparedStatement pstmt) throws SQLException {
                pstmt.setString(1, user.getPassword());
                pstmt.setString(2, user.getName());
                pstmt.setString(3, user.getEmail());
                pstmt.setString(4, user.getUserId());
            }
        };
        insertJdbcTemplate.update();
    }

    public User findByUserId(String userId) throws SQLException {
        SelectJdbcTemplate selectJdbcTemplate = new SelectJdbcTemplate() {
            @Override
            protected String createQuery() {
                return "SELECT USERID, PASSWORD, NAME, EMAIL FROM USERS WHERE USERID = ?";
            }

            @Override
            protected void setValues(PreparedStatement pstmt) throws SQLException {
                pstmt.setString(1, userId);
            }

            @Override
            protected Object mapRow(ResultSet rs) throws SQLException {
                return new User(
                        rs.getString("userId"),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getString("email")
                );
            }
        };

        return (User) selectJdbcTemplate.queryForObject();
    }

    public List<User> findAll() throws SQLException{
        SelectJdbcTemplate selectJdbcTemplate = new SelectJdbcTemplate() {
            @Override
            protected String createQuery() {
                return "SELECT USERID, PASSWORD, NAME, EMAIL FROM USERS WHERE USERID = ?";
            }

            @Override
            protected void setValues(PreparedStatement pstmt) throws SQLException {
            }

            @Override
            protected Object mapRow(ResultSet rs) throws SQLException {
                return new User(
                        rs.getString("userId"),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getString("email")
                );
            }
        };

        return (List<User>) selectJdbcTemplate.query();
    }

}
