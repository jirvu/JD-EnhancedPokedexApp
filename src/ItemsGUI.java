import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ItemsGUI extends JFrame {
    private final List<Item> items;

    private JTextField searchField;
    private JTable itemsTable;
    private DefaultTableModel tableModel;

    public ItemsGUI(List<Item> items) {
        this.items = items;

        setTitle("Items Management");
        setSize(900, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        add(buildItemsPanel());
        setVisible(true);
    }

    private JPanel buildItemsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));

        // Top Panel with Search and View
        JPanel topPanel = new JPanel(new BorderLayout(5, 5));
        searchField = new JTextField();
        JButton searchButton = new JButton("Search");
        topPanel.add(searchField, BorderLayout.CENTER);
        topPanel.add(searchButton, BorderLayout.EAST);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton viewButton = new JButton("View All Items");
        buttonPanel.add(viewButton);
        topPanel.add(buttonPanel, BorderLayout.WEST);

        panel.add(topPanel, BorderLayout.NORTH);

        // Table for Items
        String[] columns = {"Name", "Category", "Effect", "Buy", "Sell", "Description"};
        tableModel = new DefaultTableModel(columns, 0);
        itemsTable = new JTable(tableModel);
        refreshItemsTable();
        panel.add(new JScrollPane(itemsTable), BorderLayout.CENTER);

        // Bottom Navigation
        JPanel navPanel = new JPanel(new FlowLayout());
        JButton backButton = new JButton("Back to Main Menu");
        navPanel.add(backButton);
        panel.add(navPanel, BorderLayout.SOUTH);

        // Actions
        searchButton.addActionListener(e -> searchItems());
        viewButton.addActionListener(e -> refreshItemsTable());
        backButton.addActionListener(e -> {
            dispose();
            new MainMenuGUI();
        });

        return panel;
    }

    private void refreshItemsTable() {
        tableModel.setRowCount(0);
        for (Item i : items) {
            tableModel.addRow(new Object[]{
                    i.getName(),
                    i.getCategory(),
                    i.getEffect(),
                    "₱" + i.getBuyPrice(),
                    "₱" + i.getSellPrice(),
                    i.getDescription()
            });
        }
    }

    private void searchItems() {
        String query = searchField.getText().trim().toLowerCase();
        tableModel.setRowCount(0);
        for (Item i : items) {
            if (i.getName().toLowerCase().contains(query) ||
                    i.getCategory().toLowerCase().contains(query)) {
                tableModel.addRow(new Object[]{
                        i.getName(),
                        i.getCategory(),
                        i.getEffect(),
                        "₱" + i.getBuyPrice(),
                        "₱" + i.getSellPrice(),
                        i.getDescription()
                });
            }
        }
        if (tableModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "❌ No items found for: " + query);
        }
    }
}
