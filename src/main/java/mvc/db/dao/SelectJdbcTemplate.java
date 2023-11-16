package mvc.db.dao;


import mvc.db.config.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class SelectJdbcTemplate {

    public List query() throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = ConnectionManager.getConnection();
            String sql = createQuery();
            pstmt = con.prepareStatement(sql);
            setValues(pstmt);

            rs = pstmt.executeQuery();
            List objectList = new ArrayList<>();
            while (rs.next()) {
                objectList.add(mapRow(rs));
            }
            return objectList;

        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }

    public Object queryForObject() throws SQLException {
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
