package Admin;

import javax.swing.*;
import java.awt.*;

public class MovieFormDialog extends JDialog {

    private JTextField txtName = new JTextField(20);
    private JTextField txtGenre = new JTextField(20);
    private JTextField txtShowtime = new JTextField(20);
    private JTextField txtSeats = new JTextField(20);
    private JTextField txtRuntime = new JTextField(20);

    private boolean confirmed = false;

    public MovieFormDialog(JFrame parent, String title) {
        super(parent, title, true); // modal dialog

        setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));

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
                || txtRuntime.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(this, "All fields are required.");
            return false;
        }

        try {
            Integer.parseInt(txtSeats.getText());
            Integer.parseInt(txtRuntime.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Seats and Runtime must be numbers.");
            return false;
        }

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

    public int getRuntime() {
        return Integer.parseInt(txtRuntime.getText().trim());
    }

    // Used for update
    public void setValues(String name, String genre, String showtime, int seats, int runtime) {
        txtName.setText(name);
        txtGenre.setText(genre);
        txtShowtime.setText(showtime);
        txtSeats.setText(String.valueOf(seats));
        txtRuntime.setText(String.valueOf(runtime));
    }
}
