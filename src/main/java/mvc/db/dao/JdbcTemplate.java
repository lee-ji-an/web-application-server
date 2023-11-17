package mvc.db.dao;

import mvc.db.config.ConnectionManager;
import mvc.db.exception.DataAccessException;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class JdbcTemplate {
    public void update() throws DataAccessException {

        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement pstmt = con.prepareStatement(createQuery())){

            setValues(pstmt);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    abstract protected String createQuery();

    abstract protected void setValues(PreparedStatement pstmt) throws SQLException;
}
