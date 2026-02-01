package CRUD;

import Admin.usermanagement;
import javax.swing.*;
import java.awt.*;
import static java.awt.event.PaintEvent.UPDATE;
import java.sql.*;
import static jdk.nashorn.internal.runtime.PropertyDescriptor.SET;

public class EditUserDialog extends JDialog {

    private JTextField txtName, txtEmail, txtType, txtStatus;
    private int userId;
    private usermanagement parent;

    public EditUserDialog(usermanagement parent,
            int id, String name, String email,
            String type, int status) {

        this.parent = parent;
        this.userId = id;

        setTitle("Edit User");
        setModal(true);
        setSize(350, 300);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(6, 2, 10, 10));

        add(new JLabel("Full Name:"));
        txtName = new JTextField(name);
        add(txtName);

        add(new JLabel("Email:"));
        txtEmail = new JTextField(email);
        add(txtEmail);

        add(new JLabel("Type:"));
        txtType = new JTextField(type);
        add(txtType);

        add(new JLabel("Status:"));
        txtStatus = new JTextField(String.valueOf(status));
        add(txtStatus);

        JButton btnSave = new JButton("Save");
        JButton btnCancel = new JButton("Cancel");

        btnSave.addActionListener(e -> updateUser());
        btnCancel.addActionListener(e -> dispose());

        add(btnSave);
        add(btnCancel);
    }

    private void updateUser() {

        String sql = "UPDATE tbl_user SET u_name=?, u_email=?, u_type=?, u_status=? WHERE u_id=?";

        try (Connection con = DriverManager.getConnection("jdbc:sqlite:mtb.db");
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, txtName.getText());
            ps.setString(2, txtEmail.getText());
            ps.setString(3, txtType.getText());
            ps.setInt(4, Integer.parseInt(txtStatus.getText()));
            ps.setInt(5, userId); // VERY IMPORTANT

            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "User updated successfully!");
            parent.loadUsers(); // refresh JTable
            dispose();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Update failed!");
        }
    }

}
