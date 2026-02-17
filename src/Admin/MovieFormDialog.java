package Admin;

import javax.swing.*;
import java.awt.*;

public class MovieFormDialog extends JDialog {

    private JTextField txtName = new JTextField(20);
    private JTextField txtGenre = new JTextField(20);
    private JTextField txtShowtime = new JTextField(20);
    private JTextField txtSeats = new JTextField(20);
    private JTextField txtRuntime = new JTextField(20);
    private JTextField txtPoster = new JTextField(20);

    private JComboBox<String> cmbRated = new JComboBox<>(
            new String[]{"G", "PG", "PG-13", "R"}
    );

    private JComboBox<String> cmbStatus = new JComboBox<>(
            new String[]{"NOW SHOWING", "UPCOMING", "Ended"}
    );

    private JTextArea txtDescription = new JTextArea(3, 20);

    private boolean confirmed = false;

    public MovieFormDialog(JFrame parent, String title) {
        super(parent, title, true);

        setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(new GridLayout(9, 2, 10, 10));

        formPanel.add(new JLabel("Movie Name:"));
        formPanel.add(txtName);

        formPanel.add(new JLabel("Genre:"));
        formPanel.add(txtGenre);

        formPanel.add(new JLabel("Showtime:"));
        formPanel.add(txtShowtime);

        formPanel.add(new JLabel("Available Seats:"));
        formPanel.add(txtSeats);

        formPanel.add(new JLabel("Run Time (minutes):"));
        formPanel.add(txtRuntime);

        formPanel.add(new JLabel("Rated:"));
        formPanel.add(cmbRated);

        formPanel.add(new JLabel("Status:"));
        formPanel.add(cmbStatus);

        formPanel.add(new JLabel("Poster Name (ex: 1.jpg):"));
        formPanel.add(txtPoster);

        formPanel.add(new JLabel("Description:"));
        formPanel.add(new JScrollPane(txtDescription));

        JPanel buttonPanel = new JPanel();

        JButton btnSave = new JButton("Save");
        JButton btnCancel = new JButton("Cancel");

        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        btnSave.addActionListener(e -> {
            if (validateFields()) {
                confirmed = true;
                dispose();
            }
        });

        btnCancel.addActionListener(e -> dispose());

        pack();
        setLocationRelativeTo(parent);
    }

    private boolean validateFields() {
        if (txtName.getText().trim().isEmpty()
                || txtGenre.getText().trim().isEmpty()
                || txtShowtime.getText().trim().isEmpty()
                || txtSeats.getText().trim().isEmpty()
                || txtRuntime.getText().trim().isEmpty()
                || txtPoster.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(this, "All fields are required.");
            return false;
        }

        try {
            Integer.parseInt(txtSeats.getText());  // Only check seats as number
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Seats must be a number.");
            return false;
        }

        // No integer check for runtime because it's a free-form string
        return true;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public String getNameValue() {
        return txtName.getText().trim();
    }

    public String getGenre() {
        return txtGenre.getText().trim();
    }

    public String getShowtime() {
        return txtShowtime.getText().trim();
    }

    public int getSeats() {
        return Integer.parseInt(txtSeats.getText().trim());
    }

    public String getRuntime() {
        return txtRuntime.getText().trim();
    }

    public String getRated() {
        return cmbRated.getSelectedItem().toString();
    }

    public String getStatus() {
        return cmbStatus.getSelectedItem().toString();
    }

    public String getPosterName() {
        return txtPoster.getText().trim();
    }

    public String getDescription() {
        return txtDescription.getText().trim();
    }

    public void setRated(String rated) {
        cmbRated.setSelectedItem(rated);
    }

    public void setStatus(String status) {
        cmbStatus.setSelectedItem(status);
    }

    public void setPosterName(String posterName) {
        txtPoster.setText(posterName);
    }

    public void setDescription(String description) {
        txtDescription.setText(description);
    }

    // Used for update
    public void setValues(String name, String genre, String showtime,
            int seats, String run_time) {

        txtName.setText(name);
        txtGenre.setText(genre);
        txtShowtime.setText(showtime);
        txtSeats.setText(String.valueOf(seats));
        txtRuntime.setText(run_time);
    }
}
