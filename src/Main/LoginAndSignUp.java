package Main;

import config.config;

public class LoginAndSignUp {

    private final config conf;

    public LoginAndSignUp() {
        conf = new config();
    }

    // ================= SIGN UP =================
    public boolean signUp(String fullName, String email, String password) {

        if (fullName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            return false;
        }

        String sql = "INSERT INTO tbl_user (u_name, u_email, u_pass, u_type, u_status) " +
                     "VALUES (?, ?, ?, 'customer', 0)";

        try {
            conf.addRecord(sql, fullName, email, password);
            return true;
        } catch (Exception e) {
            System.out.println("Signup error: " + e.getMessage());
            return false;
        }
    }
}
