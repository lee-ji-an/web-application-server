package mvc.db.dao;


import mvc.db.config.ConnectionManager;
import mvc.db.exception.DataAccessException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class SelectJdbcTemplate {

    public List query() {
        ResultSet rs = null;
        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement pstmt = con.prepareStatement(createQuery())){

            setValues(pstmt);
            rs = pstmt.executeQuery();
            List objectList = new ArrayList<>();
            while (rs.next()) {
                objectList.add(mapRow(rs));
            }
            return objectList;

        } catch (SQLException e) {
            throw new DataAccessException(e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new DataAccessException(e);
                }
            }
        }
    }

    public Object queryForObject() {
        List result = query();
        if (result.isEmpty()) {
            return null;
        }
        return result.get(0);
    }

    abstract protected String createQuery();

    abstract protected void setValues(PreparedStatement pstmt) throws SQLException;

    abstract protected Object mapRow(ResultSet rs) throws SQLException;
}
