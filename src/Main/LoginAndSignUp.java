package Main;

import config.config;

public class LoginAndSignUp {

    private final config conf;

    public LoginAndSignUp() {
        conf = new config();
    }

    // ================= SIGN UP =================
    public String signUp(String fullName, String email, String password) {

        // Trim inputs
        fullName = fullName.trim();
        email = email.trim();
        password = password.trim();

        // 1️⃣ Empty check
        if (fullName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            return "Please fill in all fields.";
        }

        // 2️⃣ Full name validation
        if (!fullName.matches("^[A-Za-z ]{3,}$")) {
            return "Full name must contain only letters and be at least 3 characters.";
        }

        // 3️⃣ Email validation
        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            return "Invalid email. Example: example@gmail.com";
        }

        // 4️⃣ Password validation ✅ <--- PUT HERE
        if (password.length() < 6) {
            return "Password must be at least 6 characters.";
        }

        if (!password.matches(".*[A-Za-z].*")) {
            return "Password must contain at least one letter.";
        }

        if (!password.matches(".*\\d.*")) {
            return "Password must contain at least one number.";
        }

        // 5️⃣ Check if email already exists
        if (conf.isEmailExists(email)) {
            return "Email already used. Use another one.";
        }

        // 6️⃣ Insert new user (default status 0 - not active)
        String sql = "INSERT INTO tbl_user (u_name, u_email, u_pass, u_type, u_status) "
                + "VALUES (?, ?, ?, 'Customer', 0)";

        try {
            conf.addRecord(sql, fullName, email, password);
            return "SUCCESS";
        } catch (Exception e) {
            System.out.println("Signup error: " + e.getMessage());
            return "Signup failed.";
        }
    }

}
