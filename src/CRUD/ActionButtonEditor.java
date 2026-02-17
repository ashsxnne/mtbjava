package CRUD;

import Admin.moviemanagement;
import Admin.usermanagement;
import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import CRUD.EditUserDialog;
import java.sql.*;

public class ActionButtonEditor extends AbstractCellEditor implements TableCellEditor {

    private final JPanel panel = new JPanel();
    private final JButton edit = new JButton("Edit");
    private final JButton delete = new JButton("Delete");
    private final JTable table;

    public ActionButtonEditor(JTable table) {
        this.table = table;
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));

        edit.setBackground(new Color(52, 152, 219));
        edit.setForeground(Color.WHITE);

        delete.setBackground(new Color(231, 76, 60));
        delete.setForeground(Color.WHITE);

        edit.addActionListener(e -> editUser());
        delete.addActionListener(e -> deleteUser());

        panel.add(edit);
        panel.add(delete);
    }

    public ActionButtonEditor(JTable movietable, moviemanagement aThis) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void editUser() {

        if (table.isEditing()) {
            table.getCellEditor().stopCellEditing();
        }

        int viewRow = table.getSelectedRow();
        if (viewRow < 0) {
            return;
        }

        int row = table.convertRowIndexToModel(viewRow);

        int id = (int) table.getModel().getValueAt(row, 0);
        String name = table.getModel().getValueAt(row, 1).toString();
        String email = table.getModel().getValueAt(row, 2).toString();
        String type = table.getModel().getValueAt(row, 4).toString();
        int status = Integer.parseInt(
                table.getModel().getValueAt(row, 5).toString()
        );

        usermanagement parent
                = (usermanagement) SwingUtilities.getWindowAncestor(table);

        new EditUserDialog(parent, id, name, email, type, status)
                .setVisible(true);
    }

    private void deleteUser() {

        // ✅ STOP EDITING FIRST (VERY IMPORTANT)
        if (table.isEditing()) {
            table.getCellEditor().stopCellEditing();
        }

        int viewRow = table.getSelectedRow();
        if (viewRow < 0) {
            return;
        }

        int row = table.convertRowIndexToModel(viewRow);

        int userId = (int) table.getModel().getValueAt(row, 0);

        int confirm = JOptionPane.showConfirmDialog(
                table,
                "Delete this user?",
                "Confirm",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {

            deleteFromDB(userId);

            ((usermanagement) SwingUtilities
                    .getWindowAncestor(table))
                    .loadUsers();
        }
    }

    private void deleteFromDB(int id) {
        String sql = "DELETE FROM tbl_user WHERE u_id=?";

        try (Connection con = DriverManager.getConnection("jdbc:sqlite:mtb.db");
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Component getTableCellEditorComponent(
            JTable table, Object value, boolean isSelected,
            int row, int column) {

        return panel;
    }

    @Override
    public Object getCellEditorValue() {
        return null;
    }
}
