package CRUD;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class ActionButtonRenderer extends JPanel implements TableCellRenderer {

    private final JButton edit = new JButton("Edit");
    private final JButton delete = new JButton("Delete");

    public ActionButtonRenderer() {
        setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));

        edit.setBackground(new Color(52, 152, 219));
        edit.setForeground(Color.WHITE);
        edit.setFocusPainted(false);

        delete.setBackground(new Color(231, 76, 60));
        delete.setForeground(Color.WHITE);
        delete.setFocusPainted(false);

        add(edit);
        add(delete);
    }

    @Override
    public Component getTableCellRendererComponent(
            JTable table, Object value, boolean isSelected,
            boolean hasFocus, int row, int column) {

        return this;
    }
}
