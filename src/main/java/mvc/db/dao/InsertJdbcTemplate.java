package mvc.db.dao;

import mvc.db.config.ConnectionManager;
import mvc.db.dao.UserDao;
import mvc.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class InsertJdbcTemplate {

    public void insert(User user) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = ConnectionManager.getConnection();
            String sql = createQueryForInsert();
            pstmt = con.prepareStatement(sql);
            setValuesForInsert(user, pstmt);

            pstmt.executeUpdate();
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }

            if (con != null) {
                con.close();
            }
        }
    }

    protected abstract String createQueryForInsert();

    protected abstract void setValuesForInsert(User user, PreparedStatement pstmt);
}
