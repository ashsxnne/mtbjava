package config;

import java.sql.*;
import net.proteanit.sql.DbUtils;

public class config {
    //Connection Method to SQLITE

    public static Connection connectDB() {
        Connection con = null;
        try {
            Class.forName("org.sqlite.JDBC"); // Load the SQLite JDBC driver
            con = DriverManager.getConnection("jdbc:sqlite:mtb.db"); // Establish connection
            System.out.println("Connection Successful");
        } catch (Exception e) {
            System.out.println("Connection Failed: " + e);
        }
        return con;
    }

    public void addRecord(String sql, Object... values) {
        try (Connection conn = this.connectDB(); // Use the connectDB method
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Loop through the values and set them in the prepared statement dynamically
            for (int i = 0; i < values.length; i++) {
                if (values[i] instanceof Integer) {
                    pstmt.setInt(i + 1, (Integer) values[i]); // If the value is Integer
                } else if (values[i] instanceof Double) {
                    pstmt.setDouble(i + 1, (Double) values[i]); // If the value is Double
                } else if (values[i] instanceof Float) {
                    pstmt.setFloat(i + 1, (Float) values[i]); // If the value is Float
                } else if (values[i] instanceof Long) {
                    pstmt.setLong(i + 1, (Long) values[i]); // If the value is Long
                } else if (values[i] instanceof Boolean) {
                    pstmt.setBoolean(i + 1, (Boolean) values[i]); // If the value is Boolean
                } else if (values[i] instanceof java.util.Date) {
                    pstmt.setDate(i + 1, new java.sql.Date(((java.util.Date) values[i]).getTime())); // If the value is Date
                } else if (values[i] instanceof java.sql.Date) {
                    pstmt.setDate(i + 1, (java.sql.Date) values[i]); // If it's already a SQL Date
                } else if (values[i] instanceof java.sql.Timestamp) {
                    pstmt.setTimestamp(i + 1, (java.sql.Timestamp) values[i]); // If the value is Timestamp
                } else {
                    pstmt.setString(i + 1, values[i].toString()); // Default to String for other types
                }
            }

            pstmt.executeUpdate();
            System.out.println("Record added successfully!");
        } catch (SQLException e) {
            System.out.println("Error adding record: " + e.getMessage());
        }
    }

    public boolean authenticate(String sql, Object... values) {
        try (Connection conn = connectDB();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            for (int i = 0; i < values.length; i++) {
                pstmt.setObject(i + 1, values[i]);
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return true;
                }
            }
        } catch (SQLException e) {
            System.out.println("Login Error: " + e.getMessage());
        }
        return false;
    }

    public void displayData(String sql, javax.swing.JTable table) {
        try (Connection conn = connectDB();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {

            // This line automatically maps the Resultset to your JTable
            table.setModel(DbUtils.resultSetToTableModel(rs));

        } catch (SQLException e) {
            System.out.println("Error displaying data: " + e.getMessage());
        }
    }

    public LoginResult loginUserDetailed(String email, String password) {
        String sql = "SELECT u_email, u_type, u_status FROM tbl_user WHERE u_email = ? AND u_pass = ?";

        try (Connection conn = connectDB();
                PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, email);
            pst.setString(2, password);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    String dbEmail = rs.getString("u_email"); // ✅ renamed
                    String userType = rs.getString("u_type");
                    int status = rs.getInt("u_status");

                    return new LoginResult(dbEmail, userType, status);
                }
            }

        } catch (SQLException e) {
            System.out.println("Login error: " + e.getMessage());
        }
        return null;
    }

    public static class LoginResult {

        public String email;
        public String userType;
        public int status;

        public LoginResult(String email, String userType, int status) {
            this.email = email;
            this.userType = userType;
            this.status = status;
        }
    }

    public boolean updateEmail(String oldEmail, String password, String newEmail) {
        String sql = "UPDATE tbl_user SET u_email = ? WHERE u_email = ? AND u_pass = ?";

        try (Connection conn = connectDB();
                PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, newEmail);
            pst.setString(2, oldEmail);
            pst.setString(3, password);

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Update email error: " + e.getMessage());
        }
        return false;
    }

    public boolean updatePassword(String email, String oldPass, String newPass) {
        String sql = "UPDATE tbl_user SET u_pass = ? WHERE u_email = ? AND u_pass = ?";

        try (Connection conn = connectDB();
                PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, newPass);
            pst.setString(2, email);
            pst.setString(3, oldPass);

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Update password error: " + e.getMessage());
        }
        return false;
    }

    public boolean deleteAccount(String email, String password) {
        String sql = "DELETE FROM tbl_user WHERE u_email = ? AND u_pass = ?";

        try (Connection conn = connectDB(); // <-- use connectDB()
                PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, email);
            pst.setString(2, password);
            int affected = pst.executeUpdate();
            return affected > 0; // true if deleted

        } catch (SQLException e) {
            System.out.println("Delete account error: " + e.getMessage());
            return false;
        }
    }

    public boolean isEmailExists(String email) {
        String sql = "SELECT u_email FROM tbl_user WHERE u_email = ?";

        try (Connection conn = connectDB();
                PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, email);

            try (ResultSet rs = pst.executeQuery()) {
                return rs.next(); // true if email already exists
            }

        } catch (SQLException e) {
            System.out.println("Email check error: " + e.getMessage());
        }

        return false;
    }

    public void displayData(String sql, javax.swing.JTable table, Object... values) {
        try (Connection conn = connectDB();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Set the parameters for the search
            for (int i = 0; i < values.length; i++) {
                pstmt.setObject(i + 1, values[i]);
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                // Automatically maps the filtered ResultSet to your JTable
                table.setModel(DbUtils.resultSetToTableModel(rs));
            }

        } catch (SQLException e) {
            System.out.println("Error filtering data: " + e.getMessage());
        }
    }

      // Add new movie
    public boolean addMovie(String name, String genre, String showtime, int seats, int runtime) {
        String sql = "INSERT INTO tbl_movies(movie_name, genre, showtime, available_seats, run_time) VALUES(?,?,?,?,?)";
        try (Connection con = connectDB();
                PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, name);
            pst.setString(2, genre);
            pst.setString(3, showtime);
            pst.setInt(4, seats);
            pst.setInt(5, runtime);

            return pst.executeUpdate() > 0; // true if insert success
        } catch (SQLException e) {
            System.out.println("Add movie error: " + e.getMessage());
            return false;
        }
    }

       // Update existing movie
    public boolean updateMovie(int id, String name, String genre, String showtime, int seats, int runtime) {
        String sql = "UPDATE tbl_movies SET movie_name=?, genre=?, showtime=?, available_seats=?, run_time=? WHERE m_id=?";
        try (Connection con = connectDB();
                PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, name);
            pst.setString(2, genre);
            pst.setString(3, showtime);
            pst.setInt(4, seats);
            pst.setInt(5, runtime);
            pst.setInt(6, id);

            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Update movie error: " + e.getMessage());
            return false;
        }
    }

       // Delete movie
    public boolean deleteMovie(int id) {
        String sql = "DELETE FROM tbl_movies WHERE m_id=?";
        try (Connection con = connectDB();
                PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, id);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Delete movie error: " + e.getMessage());
            return false;
        }
    }

}
