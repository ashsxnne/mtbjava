package config;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
    public boolean addMovie(String name,
            String genre,
            String showtime,
            int seats,
            String runtime,
            String rated,
            String status,
            String posterName,
            String description) {

        String sql = "INSERT INTO tbl_movies "
                + "(movie_name, genre, show_time, available_seats, run_time, rated, status, poster, description) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = connectDB();
                PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, name);
            pst.setString(2, genre);
            pst.setString(3, showtime);
            pst.setInt(4, seats);
            pst.setString(5, runtime);
            pst.setString(6, rated);
            pst.setString(7, status);
            pst.setString(9, description);

            InputStream input = config.class.getResourceAsStream("/movies/" + posterName);

            if (input != null) {
                byte[] imgBytes = toByteArray(input);

                pst.setBytes(8, imgBytes);
            } else {
                pst.setNull(8, java.sql.Types.BLOB);
            }

            pst.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

// Helper method - keep this one ONLY once in the class
    private byte[] toByteArray(InputStream is) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[16384]; // 16 KB buffer

        while ((nRead = is.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }

        buffer.flush();
        return buffer.toByteArray();
    }

    // Update existing movie
    public boolean updateMovie(
            int id,
            String name,
            String genre,
            String showtime,
            int seats,
            String runtime, // change here to String
            String rated,
            String status,
            String posterName,
            String description) {

        String sql = "UPDATE tbl_movies SET "
                + "movie_name = ?, "
                + "genre = ?, "
                + "show_time = ?, "
                + "available_seats = ?, "
                + "run_time = ?, "
                + "rated = ?, "
                + "status = ?, "
                + "poster = ?, "
                + "description = ? "
                + "WHERE m_id = ?";

        try (Connection con = connectDB();
                PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, name);
            pst.setString(2, genre);
            pst.setString(3, showtime);
            pst.setInt(4, seats);
            pst.setString(5, runtime);  // <-- set as string here
            pst.setString(6, rated);
            pst.setString(7, status);

            java.io.InputStream is = config.class.getResourceAsStream("/movies/" + posterName);

            if (is != null) {
                byte[] imgBytes = toByteArray(is);
                pst.setBytes(8, imgBytes);
            } else {
                pst.setNull(8, java.sql.Types.BLOB);
            }

            pst.setString(9, description);
            pst.setInt(10, id);

            return pst.executeUpdate() > 0;

        } catch (SQLException | IOException e) {
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

    public boolean cancelBooking(int b_id, String reason) {

        String insertSQL = "INSERT INTO tbl_canceled_booking "
                + "(b_id, m_id, seat_no, booking_fee, poster, status, cancellation_reason, cancel_date) "
                + "SELECT b_id, m_id, seat_no, booking_fee, poster, 'CANCELED', ?, datetime('now') "
                + "FROM tbl_booking WHERE b_id = ?";

        String getBookingSQL = "SELECT m_id, seat_no FROM tbl_booking WHERE b_id = ?";

        String updateMovieSQL = "UPDATE tbl_movies SET available_seats = available_seats + ? WHERE m_id = ?";

        String deleteSQL = "DELETE FROM tbl_booking WHERE b_id = ?";

        try (Connection conn = connectDB()) {

            conn.setAutoCommit(false);

            int movieId = 0;
            int seatCount = 0;

            // 1️⃣ Get movie id and seat count
            try (PreparedStatement pst = conn.prepareStatement(getBookingSQL)) {
                pst.setInt(1, b_id);
                ResultSet rs = pst.executeQuery();

                if (rs.next()) {

                    movieId = rs.getInt("m_id");

                    String seats = rs.getString("seat_no");
                    if (seats != null && !seats.isEmpty()) {
                        seatCount = seats.split(",").length;
                    }
                }
            }

            // 2️⃣ Insert into canceled table
            try (PreparedStatement pstInsert = conn.prepareStatement(insertSQL)) {
                pstInsert.setString(1, reason);
                pstInsert.setInt(2, b_id);
                pstInsert.executeUpdate();
            }

            // 3️⃣ Restore available_seats
            try (PreparedStatement pstUpdate = conn.prepareStatement(updateMovieSQL)) {
                pstUpdate.setInt(1, seatCount);
                pstUpdate.setInt(2, movieId);
                pstUpdate.executeUpdate();
            }

            // 4️⃣ Delete from booking
            try (PreparedStatement pstDelete = conn.prepareStatement(deleteSQL)) {
                pstDelete.setInt(1, b_id);
                pstDelete.executeUpdate();
            }

            conn.commit();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
