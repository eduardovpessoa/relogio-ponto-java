package br.com.orasystems.util;

import java.sql.*;

/**
 * @author Eduardo
 */
public class ConnectionFactory {

    private final String url = "";
    private final String user = "";
    private final String password = "";

    private Connection connection;

    public ConnectionFactory() throws SQLException {

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
            connection = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeConnection(ResultSet rs, PreparedStatement ps, Connection conn) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
