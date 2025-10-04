import javax.swing.*;
import java.awt.*;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class PokemonGUI extends JFrame {
    private final List<Pokemon> pokedex;

    // Pokemon components
    private JTextField searchField;
    private JLabel nameLabel, numberLabel, type1Label, type2Label;
    private JLabel hpValue, atkValue, defValue, spdValue;
    private JProgressBar hpBar, attackBar, defenseBar, speedBar;
    private JLabel imageLabel;
    private JList<String> pokemonList;
    private int currentIndex = 0;

    public PokemonGUI(List<Pokemon> pokedex) {
        this.pokedex = pokedex;

        setTitle("Enhanced Pokedex");
        setSize(950, 550);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        add(buildPokemonPanel());
        setVisible(true);
    }

    // POKEMON PANEL
    private JPanel buildPokemonPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));

        // Search & Management Bar
        JPanel topPanel = new JPanel(new BorderLayout(5, 5));
        searchField = new JTextField();
        JButton searchButton = new JButton("Search");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton addButton = new JButton("Add New Pokémon");
        JButton viewAllButton = new JButton("View All Pokémon");
        buttonPanel.add(addButton);
        buttonPanel.add(viewAllButton);

        topPanel.add(searchField, BorderLayout.CENTER);
        topPanel.add(searchButton, BorderLayout.EAST);
        topPanel.add(buttonPanel, BorderLayout.WEST);

        panel.add(topPanel, BorderLayout.NORTH);

        // Pokemon List
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (Pokemon p : pokedex) {
            listModel.addElement(String.format("%03d - %s", p.getPokedexNumber(), p.getName()));
        }
        pokemonList = new JList<>(listModel);
        pokemonList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        pokemonList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                currentIndex = pokemonList.getSelectedIndex();
                updatePokemonDetails();
            }
        });
        panel.add(new JScrollPane(pokemonList), BorderLayout.WEST);

        // Pokemon Details
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));

        JPanel namePanel = new JPanel(new FlowLayout());
        nameLabel = new JLabel("", SwingConstants.CENTER);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 24));
        JButton cryButton = new JButton("Cry");
        cryButton.addActionListener(e -> playPokemonCry());
        namePanel.add(nameLabel);
        namePanel.add(cryButton);

        numberLabel = new JLabel("", SwingConstants.CENTER);

        imageLabel = new JLabel("Image", SwingConstants.CENTER);
        imageLabel.setPreferredSize(new Dimension(200, 200));

        JPanel typePanel = new JPanel(new FlowLayout());
        type1Label = createTypeLabel("");
        type2Label = createTypeLabel("");
        typePanel.add(type1Label);
        typePanel.add(type2Label);

        detailsPanel.add(namePanel);
        detailsPanel.add(numberLabel);
        detailsPanel.add(imageLabel);
        detailsPanel.add(typePanel);

        // Stats
        hpBar = new JProgressBar(0, 255);
        attackBar = new JProgressBar(0, 255);
        defenseBar = new JProgressBar(0, 255);
        speedBar = new JProgressBar(0, 255);

        hpValue = new JLabel();
        atkValue = new JLabel();
        defValue = new JLabel();
        spdValue = new JLabel();

        detailsPanel.add(createStatPanel("HP", hpBar, hpValue));
        detailsPanel.add(createStatPanel("Attack", attackBar, atkValue));
        detailsPanel.add(createStatPanel("Defense", defenseBar, defValue));
        detailsPanel.add(createStatPanel("Speed", speedBar, spdValue));

        panel.add(detailsPanel, BorderLayout.CENTER);

        // Bottom Navigation
        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton prevButton = new JButton("Previous");
        JButton backButton = new JButton("Back to Main Menu");
        JButton nextButton = new JButton("Next");

        navPanel.add(prevButton);
        navPanel.add(backButton);
        navPanel.add(nextButton);
        panel.add(navPanel, BorderLayout.SOUTH);

        // Actions
        addButton.addActionListener(e -> addNewPokemon());
        viewAllButton.addActionListener(e -> openViewAllPokemonWindow());
        prevButton.addActionListener(e -> showPreviousPokemon());
        nextButton.addActionListener(e -> showNextPokemon());
        searchButton.addActionListener(e -> searchPokemon());
        backButton.addActionListener(e -> {
            dispose();
            new MainMenuGUI();
        });

        updatePokemonDetails();
        return panel;
    }

    private JLabel createTypeLabel(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setOpaque(true);
        label.setBackground(Color.GRAY);
        label.setForeground(Color.WHITE);
        label.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        return label;
    }

    private JPanel createStatPanel(String statName, JProgressBar bar, JLabel valueLabel) {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 5, 2, 5);

        // Stat name label (left)
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(new JLabel(statName + ":"), gbc);

        // Progress bar (center)
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        bar.setPreferredSize(new Dimension(150, 15));
        panel.add(bar, gbc);

        // Value label (right)
        gbc.gridx = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        panel.add(valueLabel, gbc);

        return panel;
    }

    private void updatePokemonDetails() {
        if (pokedex.isEmpty() || currentIndex < 0 || currentIndex >= pokedex.size()) return;

        Pokemon p = pokedex.get(currentIndex);
        nameLabel.setText(p.getName().toUpperCase());
        numberLabel.setText(String.format("#%03d", p.getPokedexNumber()));
        type1Label.setText(p.getType1());
        type2Label.setText(p.getType2() != null ? p.getType2() : "");

        hpBar.setValue(p.getHp());
        attackBar.setValue(p.getAttack());
        defenseBar.setValue(p.getDefense());
        speedBar.setValue(p.getSpeed());

        hpValue.setText(String.valueOf(p.getHp()));
        atkValue.setText(String.valueOf(p.getAttack()));
        defValue.setText(String.valueOf(p.getDefense()));
        spdValue.setText(String.valueOf(p.getSpeed()));

        pokemonList.setSelectedIndex(currentIndex);
    }

    private void showPreviousPokemon() {
        currentIndex = (currentIndex - 1 + pokedex.size()) % pokedex.size();
        updatePokemonDetails();
    }

    private void showNextPokemon() {
        currentIndex = (currentIndex + 1) % pokedex.size();
        updatePokemonDetails();
    }

    private void searchPokemon() {
        String query = searchField.getText().trim().toLowerCase();
        for (int i = 0; i < pokedex.size(); i++) {
            Pokemon p = pokedex.get(i);
            if (p.getName().toLowerCase().contains(query) ||
                    p.getType1().toLowerCase().contains(query) ||
                    (p.getType2() != null && p.getType2().toLowerCase().contains(query))) {
                currentIndex = i;
                updatePokemonDetails();
                return;
            }
        }
        JOptionPane.showMessageDialog(this, "Pokémon not found: " + query);
    }

    private void playPokemonCry() {
        if (pokedex.isEmpty()) return;
        Pokemon p = pokedex.get(currentIndex);
        JOptionPane.showMessageDialog(this, p.getName() + "!!!");
    }

    private void openViewAllPokemonWindow() {
        String[] columns = {
                "Dex#", "Name", "Type 1", "Type 2", "Base Lv",
                "Evolves From", "Evolves To", "Evo Lv",
                "HP", "ATK", "DEF", "SPD", "Move Set", "Held Item"
        };

        DefaultTableModel model = new DefaultTableModel(columns, 0);
        for (Pokemon p : pokedex) {
            model.addRow(new Object[]{
                    p.getPokedexNumber(),
                    p.getName(),
                    p.getType1(),
                    (p.getType2() != null ? p.getType2() : "None"),
                    p.getBaseLevel(),
                    (p.getEvolvesFrom() > 0 ? p.getEvolvesFrom() : "None"),
                    (p.getEvolvesTo() > 0 ? p.getEvolvesTo() : "None"),
                    p.getEvolutionLevel(),
                    p.getHp(), p.getAttack(), p.getDefense(), p.getSpeed(),
                    String.join(", ", p.getMoveSet()),
                    (p.getHeldItem() != null ? p.getHeldItem() : "None")
            });
        }

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setPreferredScrollableViewportSize(new Dimension(800, 300));

        JOptionPane.showMessageDialog(this, scrollPane, "All Pokémon", JOptionPane.PLAIN_MESSAGE);
    }

    private void addNewPokemon() {
        try {
            String numberStr = JOptionPane.showInputDialog(this, "Enter Pokédex Number (1-9999):");
            if (numberStr == null) return;
            if (!numberStr.matches("\\d{1,4}")) {
                JOptionPane.showMessageDialog(this, "❌ Invalid number format. Enter 1-4 digits.");
                return;
            }
            int number = Integer.parseInt(numberStr);
            for (Pokemon p : pokedex) {
                if (p.getPokedexNumber() == number) {
                    JOptionPane.showMessageDialog(this, "❌ A Pokémon with this Pokédex number already exists.");
                    return;
                }
            }

            String name = JOptionPane.showInputDialog(this, "Enter Pokémon Name:");
            if (name == null || name.trim().isEmpty()) return;
            for (Pokemon p : pokedex) {
                if (p.getName().equalsIgnoreCase(name.trim())) {
                    JOptionPane.showMessageDialog(this, "❌ A Pokémon with this name already exists.");
                    return;
                }
            }

            String type1 = JOptionPane.showInputDialog(this, "Enter Type 1:");
            if (type1 == null || type1.trim().isEmpty()) return;

            String type2 = JOptionPane.showInputDialog(this, "Enter Type 2 (optional):");
            if (type2 == null) type2 = "";

            int baseLevel = getIntInput("Base Level");
            int evolvesFrom = getIntInput("Evolves From (Pokédex #, 0 if none)");
            int evolvesTo = getIntInput("Evolves To (Pokédex #, 0 if none)");
            int evolutionLevel = getIntInput("Evolution Level (0 if none)");

            int hp = getStatInput("HP");
            int atk = getStatInput("Attack");
            int def = getStatInput("Defense");
            int spd = getStatInput("Speed");

            String moveSet = JOptionPane.showInputDialog(this, "Enter Move Set (comma-separated, max 4):");
            if (moveSet == null) moveSet = "Tackle, Defend";
            String[] moves = moveSet.split(",");

            String heldItem = JOptionPane.showInputDialog(this, "Enter Held Item (optional):");
            if (heldItem == null || heldItem.isEmpty()) heldItem = "None";

            Pokemon newPoke = new Pokemon(number, name.trim(), type1, type2, baseLevel,
                    evolvesFrom, evolvesTo, evolutionLevel, hp, atk, def, spd);

            newPoke.getMoveSet().clear();
            for (int i = 0; i < moves.length && i < 4; i++) {
                newPoke.getMoveSet().add(moves[i].trim());
            }
            newPoke.setHeldItem(heldItem);

            pokedex.add(newPoke);
            ((DefaultListModel<String>) pokemonList.getModel())
                    .addElement(String.format("%03d - %s", newPoke.getPokedexNumber(), newPoke.getName()));

            JOptionPane.showMessageDialog(this, "✅ Pokémon " + name + " added successfully!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "❌ Error adding Pokémon: " + ex.getMessage());
        }
    }

    private int getIntInput(String fieldName) {
        while (true) {
            String input = JOptionPane.showInputDialog(this, "Enter " + fieldName + ":");
            if (input == null) return 0;
            if (input.matches("\\d+")) return Integer.parseInt(input);
            JOptionPane.showMessageDialog(this, "❌ Invalid value for " + fieldName + ". Please enter a number.");
        }
    }

    private int getStatInput(String statName) {
        while (true) {
            String input = JOptionPane.showInputDialog(this, "Enter " + statName + " (0-255):");
            if (input == null) return 0;
            if (input.matches("\\d+")) {
                int val = Integer.parseInt(input);
                if (val >= 0 && val <= 255) return val;
            }
            JOptionPane.showMessageDialog(this, "❌ Invalid value for " + statName + ". Please enter 0-255.");
        }
    }
}
