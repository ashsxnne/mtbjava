package KiosksPages;

import design.BaseFrame;
import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class Ticket extends BaseFrame {

    private Color darkRed = new Color(120, 0, 0);
    private Color deepBlack = new Color(15, 15, 15);
    private Color accentRed = new Color(200, 0, 0);
    private Color softWhite = new Color(230, 230, 230);

    public Ticket(int bookingId) {
        super(946, 611);   // same size as other pages
        initUI(bookingId);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initUI(int bookingId) {

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(deepBlack);
        mainPanel.setLayout(null);

        JPanel receiptPanel = new JPanel();
        receiptPanel.setBounds(300, 60, 350, 450);
        receiptPanel.setBackground(softWhite);
        receiptPanel.setLayout(null);
        receiptPanel.setBorder(BorderFactory.createLineBorder(accentRed, 2));

        JLabel title = new JLabel("MOVIE TICKET", SwingConstants.CENTER);
        title.setBounds(0, 15, 350, 30);
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(darkRed);
        receiptPanel.add(title);

        JTextArea area = new JTextArea();
        area.setBounds(20, 60, 310, 350);
        area.setEditable(false);
        area.setBackground(softWhite);
        area.setFont(new Font("Monospaced", Font.PLAIN, 14));

        loadData(area, bookingId);

        receiptPanel.add(area);

        // ------------------ PROCEED BUTTON ------------------
        JButton proceedBtn = new JButton("Proceed to the Counter");
        proceedBtn.setBounds(60, 420, 230, 30); // position under text area
        proceedBtn.setBackground(accentRed);
        proceedBtn.setForeground(softWhite);
        proceedBtn.setFocusPainted(false);
        proceedBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));

        proceedBtn.addActionListener(e -> {
            // Show thank-you message
            JOptionPane.showMessageDialog(this,
                    "Thank you for booking! Please proceed to the counter for your payment and ticket.",
                    "Thank You",
                    JOptionPane.INFORMATION_MESSAGE);

            // Close current Ticket window
            this.dispose();

            // Open HomeK page properly
            HomeK home = new HomeK();
            home.setVisible(true);      
            home.setLocationRelativeTo(null);  
        });

        receiptPanel.add(proceedBtn);
        // -----------------------------------------------------

        mainPanel.add(receiptPanel);
        add(mainPanel);
    }

    private void loadData(JTextArea area, int bookingId) {

        try {
            Connection con = DriverManager.getConnection("jdbc:sqlite:mtb.db");

            String sql
                    = "SELECT b.seat_no, b.booking_fee, "
                    + "m.movie_name, m.genre, m.run_time, m.rated, m.show_time, "
                    + "t.cash, t.change "
                    + "FROM tbl_booking b "
                    + "JOIN tbl_movies m ON b.m_id = m.m_id "
                    + "JOIN tbl_transaction t ON b.b_id = t.b_id "
                    + "WHERE b.b_id = ?";

            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, bookingId);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {

                String movieName = rs.getString("movie_name");
                String showTime = rs.getString("show_time"); // now valid
                String seatNo = rs.getString("seat_no");
                String genre = rs.getString("genre");
                String runtime = rs.getString("run_time");
                String rated = rs.getString("rated");

                double total = rs.getDouble("booking_fee");
                double cash = rs.getDouble("cash");
                double change = rs.getDouble("change");

                area.setText(
                        "Movie: " + movieName + "\n\n"
                        + "Genre: " + genre + "\n"
                        + "Runtime: " + runtime + "\n"
                        + "Rated: " + rated + "\n\n"
                        + "Show Time: " + showTime + "\n"
                        + "Seat No: " + seatNo + "\n\n"
                        + "---------------------------------\n"
                        + "Total Amount: ₱" + total + "\n"
                        + "Cash: ₱" + cash + "\n"
                        + "Change: ₱" + change + "\n"
                        + "---------------------------------\n\n"
                        + "Enjoy your movie!"
                );
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
