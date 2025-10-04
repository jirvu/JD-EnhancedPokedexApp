import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MovesGUI extends JFrame {
    private final List<Move> moves;

    private JTextField searchField;
    private JTable movesTable;
    private DefaultTableModel tableModel;

    public MovesGUI(List<Move> moves) {
        this.moves = moves;

        setTitle("Moves Management");
        setSize(900, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        add(buildMovesPanel());
        setVisible(true);
    }

    private JPanel buildMovesPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));

        // Top Panel with Search, Add, and View
        JPanel topPanel = new JPanel(new BorderLayout(5, 5));
        searchField = new JTextField();
        JButton searchButton = new JButton("Search");
        topPanel.add(searchField, BorderLayout.CENTER);
        topPanel.add(searchButton, BorderLayout.EAST);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton addButton = new JButton("Add Move");
        buttonPanel.add(addButton);
        topPanel.add(buttonPanel, BorderLayout.WEST);

        panel.add(topPanel, BorderLayout.NORTH);

        // Table for Moves
        String[] columns = {"Name", "Class", "Type 1", "Type 2", "Description"};
        tableModel = new DefaultTableModel(columns, 0);
        movesTable = new JTable(tableModel);
        refreshMovesTable();
        panel.add(new JScrollPane(movesTable), BorderLayout.CENTER);

        // Bottom Navigation
        JPanel navPanel = new JPanel(new FlowLayout());
        JButton backButton = new JButton("Back to Main Menu");
        navPanel.add(backButton);
        panel.add(navPanel, BorderLayout.SOUTH);

        // Actions
        searchButton.addActionListener(e -> searchMoves());
        addButton.addActionListener(e -> addNewMove());
        backButton.addActionListener(e -> {
            dispose();
            new MainMenuGUI();
        });

        return panel;
    }

    private void refreshMovesTable() {
        tableModel.setRowCount(0);
        for (Move m : moves) {
            tableModel.addRow(new Object[]{
                    m.getName(),
                    m.getClassification(),
                    m.getType1(),
                    (m.getType2() != null ? m.getType2() : "None"),
                    m.getDescription()
            });
        }
    }

    private void searchMoves() {
        String query = searchField.getText().trim().toLowerCase();
        tableModel.setRowCount(0);
        for (Move m : moves) {
            if (m.getName().toLowerCase().contains(query) ||
                    m.getType1().toLowerCase().contains(query) ||
                    (m.getType2() != null && m.getType2().toLowerCase().contains(query))) {
                tableModel.addRow(new Object[]{
                        m.getName(),
                        m.getClassification(),
                        m.getType1(),
                        (m.getType2() != null ? m.getType2() : "None"),
                        m.getDescription()
                });
            }
        }
        if (tableModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "❌ No moves found for: " + query);
        }
    }

    private void addNewMove() {
        try {
            String name = JOptionPane.showInputDialog(this, "Enter Move Name:");
            if (name == null || name.trim().isEmpty()) return;

            for (Move m : moves) {
                if (m.getName().equalsIgnoreCase(name.trim())) {
                    JOptionPane.showMessageDialog(this, "❌ Move already exists.");
                    return;
                }
            }

            String description = JOptionPane.showInputDialog(this, "Enter Description:");
            if (description == null || description.trim().isEmpty()) return;

            String[] classifications = {"HM", "TM"};
            String classification = (String) JOptionPane.showInputDialog(
                    this, "Select Classification:", "Classification",
                    JOptionPane.QUESTION_MESSAGE, null, classifications, classifications[0]);

            if (classification == null) return;

            String type1 = JOptionPane.showInputDialog(this, "Enter Type 1:");
            if (type1 == null || type1.trim().isEmpty()) return;

            String type2 = JOptionPane.showInputDialog(this, "Enter Type 2 (optional):");
            if (type2 == null) type2 = "";

            Move newMove = new Move(name.trim(), description.trim(), classification, type1, type2);
            moves.add(newMove);
            refreshMovesTable();

            JOptionPane.showMessageDialog(this, "✅ Move " + name + " added successfully!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "❌ Error adding move: " + ex.getMessage());
        }
    }
}
