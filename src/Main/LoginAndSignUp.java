package Main;

import config.config;

public class LoginAndSignUp {

    private final config conf;

    public LoginAndSignUp() {
        conf = new config();
    }

    // ================= LOGIN =================
    public boolean login(String email, String password) {

        if (email == null || password == null ||
            email.isEmpty() || password.isEmpty()) {
            return false;
        }

        return conf.loginUser(email, password);
    }

    // ================= SIGN UP =================
    public boolean signUp(String fullName, String email, String password) {

        if (fullName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            return false;
        }

       String sql = "INSERT INTO user_table (u_name, u_email, u_pass) VALUES (?, ?, ?)";
         
        try {
            conf.addRecord(sql, fullName, email, password);
            return true;
        } catch (Exception e) {
            System.out.println("Signup error: " + e.getMessage());
            return false;
        }
    }

}
