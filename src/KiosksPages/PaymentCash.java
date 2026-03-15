package KiosksPages;

import design.BaseFrame;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class PaymentCash extends BaseFrame {

    private int movieId;
    private String seats;
    private int totalAmount;
    private byte[] posterBytes;
    private String movieTitle;
    private String movieRating;

    private Color darkRed = new Color(120, 0, 0);
    private Color deepBlack = new Color(15, 15, 15);
    private Color accentRed = new Color(200, 0, 0);
    private Color softWhite = new Color(230, 230, 230);

    private JTextField totalField, cashField, changeField;

    public PaymentCash(int movieId, String movieTitle,
            String movieRating,
            String seats,
            int totalAmount,
            byte[] posterBytes) {

        super(946, 611); // ✅ SAME AS OTHER PAGES

        this.movieId = movieId;
        this.movieTitle = movieTitle;
        this.movieRating = movieRating;
        this.seats = seats;
        this.totalAmount = totalAmount;
        this.posterBytes = posterBytes;

        initUI();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initUI() {

        setLayout(null);
        getContentPane().setBackground(deepBlack);

        JLabel title = new JLabel("CASH PAYMENT", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 36));
        title.setForeground(accentRed);
        title.setBounds(300, 60, 350, 50);
        add(title);

        // TOTAL
        JLabel totalLbl = new JLabel("Total Amount:");
        totalLbl.setForeground(softWhite);
        totalLbl.setFont(new Font("Arial", Font.BOLD, 18));
        totalLbl.setBounds(300, 180, 150, 30);
        add(totalLbl);

        totalField = new JTextField("₱ " + totalAmount);
        totalField.setBounds(460, 180, 180, 35);
        totalField.setEditable(false);
        totalField.setFont(new Font("Arial", Font.BOLD, 16));
        add(totalField);

        // CASH
        JLabel cashLbl = new JLabel("Cash Received:");
        cashLbl.setForeground(softWhite);
        cashLbl.setFont(new Font("Arial", Font.BOLD, 18));
        cashLbl.setBounds(300, 240, 150, 30);
        add(cashLbl);

        cashField = new JTextField();
        cashField.setBounds(460, 240, 180, 35);
        cashField.setFont(new Font("Arial", Font.BOLD, 16));
        add(cashField);

        // CHANGE
        JLabel changeLbl = new JLabel("Change:");
        changeLbl.setForeground(softWhite);
        changeLbl.setFont(new Font("Arial", Font.BOLD, 18));
        changeLbl.setBounds(300, 300, 150, 30);
        add(changeLbl);

        changeField = new JTextField();
        changeField.setBounds(460, 300, 180, 35);
        changeField.setEditable(false);
        changeField.setFont(new Font("Arial", Font.BOLD, 16));
        changeField.setBackground(Color.LIGHT_GRAY);
        add(changeField);

        // BUTTON
        JButton payBtn = new JButton("PROCESS PAYMENT");
        payBtn.setBounds(370, 380, 250, 50);
        payBtn.setBackground(accentRed);
        payBtn.setForeground(Color.WHITE);
        payBtn.setFont(new Font("Arial", Font.BOLD, 16));
        payBtn.setFocusPainted(false);

        payBtn.addActionListener(e -> {
            payBtn.setEnabled(false);
            processPayment(payBtn);
        });

        add(payBtn);
    }

    private void processPayment(JButton payBtn) {

        try {

            int cash = Integer.parseInt(cashField.getText().replaceAll("[^0-9]", ""));

            if (cash < totalAmount) {
                JOptionPane.showMessageDialog(this, "Insufficient Cash!");
                payBtn.setEnabled(true); // enable again
                return;
            }

            int change = cash - totalAmount;

            // ✅ SHOW CHANGE FIRST
            changeField.setText("₱ " + change);

            Connection con = DriverManager.getConnection("jdbc:sqlite:mtb.db");

            con.setAutoCommit(false);  // 🔥 important for safety

// ================= INSERT BOOKING FIRST =================
            String bookingSql
                    = "INSERT INTO tbl_booking "
                    + "(m_id, total_price, booking_status, booking_date) "
                    + "VALUES (?, ?, ?, datetime('now'))";

            PreparedStatement bookingPst
                    = con.prepareStatement(bookingSql, Statement.RETURN_GENERATED_KEYS);

            bookingPst.setInt(1, movieId);
            bookingPst.setInt(2, totalAmount);
            bookingPst.setString(3, "ONGOING");

            bookingPst.executeUpdate();

            ResultSet rs = bookingPst.getGeneratedKeys();
            int bookingId = 0;

            if (rs.next()) {
                bookingId = rs.getInt(1);
            }

            // ================= INSERT SEATS =================
            String[] seatArray = seats.split(",");

            String seatSql = "INSERT INTO booking_seats (b_id, m_id, seat_no, price) VALUES (?, ?, ?, ?)";

            PreparedStatement seatPst = con.prepareStatement(seatSql);

            for (String seat : seatArray) {
                seatPst.setInt(1, bookingId);
                seatPst.setInt(2, movieId);
                seatPst.setString(3, seat.trim());
                seatPst.setInt(4, totalAmount / seatArray.length);
                seatPst.executeUpdate();
            }

            // ================= UPDATE AVAILABLE SEATS =================
            String updateSeatsSql = "UPDATE tbl_movies SET available_seats = available_seats - ? WHERE m_id = ?";
            PreparedStatement updatePst = con.prepareStatement(updateSeatsSql);

            updatePst.setInt(1, seatArray.length); // number of seats booked
            updatePst.setInt(2, movieId);

            updatePst.executeUpdate();

            // ================= INSERT TRANSACTION ================= \\
            String transSql = "INSERT INTO tbl_transactions "
                    + "(b_id, payment_method, amount, payment_status, transaction_date) "
                    + "VALUES (?, ?, ?, 'PENDING', datetime('now'))";

            PreparedStatement transPst = con.prepareStatement(transSql);

            transPst.setInt(1, bookingId);
            transPst.setString(2, "CASH");
            transPst.setInt(3, totalAmount);

            transPst.executeUpdate();

            con.commit();   // 🔥 finalize both inserts
            con.close();

            JOptionPane.showMessageDialog(this,
                    "Payment Successful!\nYour change is ₱ " + change);

            // ✅ GO TO TICKET PAGE
            dispose();
            new Ticket(bookingId);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid cash amount.");
            payBtn.setEnabled(true);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database Error!");
        }
    }
}
