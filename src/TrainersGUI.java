import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class TrainersGUI extends JFrame {
    private final List<Trainer> trainers;
    private JList<String> trainerList;
    private JTextField searchField;

    // Trainer detail components
    private JLabel[] trainerInfoLabels;
    private JTable lineupTable;
    private JTable storageTable;
    private JList<String> itemsList = new JList<>();

    private int currentTrainerIndex = 0;

    public TrainersGUI(List<Trainer> trainers) {
        this.trainers = trainers;

        setTitle("Trainer Management");
        setSize(1200, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        add(buildTrainersPanel());
        updateTrainerDetails();
        setVisible(true);
    }

    private JPanel buildTrainersPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));

        // Top Panel (Search + Back Button)
        JPanel topPanel = new JPanel(new BorderLayout(5, 5));
        searchField = new JTextField();
        JButton searchBtn = new JButton("Search");
        searchBtn.addActionListener(e -> searchTrainer());
        topPanel.add(searchBtn, BorderLayout.WEST);
        topPanel.add(searchField, BorderLayout.CENTER);

        JButton topBackBtn = new JButton("Back to Main Menu");
        topBackBtn.addActionListener(e -> {
            dispose();
            new MainMenuGUI();
        });
        topPanel.add(topBackBtn, BorderLayout.EAST);
        panel.add(topPanel, BorderLayout.NORTH);

        // Trainer List (Left Side)
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (Trainer t : trainers) {
            listModel.addElement(t.getTrainerId() + " - " + t.getName());
        }
        trainerList = new JList<>(listModel);
        trainerList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        trainerList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                currentTrainerIndex = trainerList.getSelectedIndex();
                updateTrainerDetails();
            }
        });
        JScrollPane trainerListScroll = new JScrollPane(trainerList);
        trainerListScroll.setPreferredSize(new Dimension(200, 0));
        panel.add(trainerListScroll, BorderLayout.WEST);

        // Right Side (Split Pane: Pokemon & Inventory)
        JSplitPane rightSplitPane = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                buildTrainerDetailsPanel(),
                buildInventoryPanel()
        );
        rightSplitPane.setResizeWeight(0.7);
        panel.add(rightSplitPane, BorderLayout.CENTER);

        // Bottom Action Buttons
        JPanel actionPanel = new JPanel(new FlowLayout());
        JButton addTrainerBtn = new JButton("Add Trainer");
        JButton buyItemBtn = new JButton("Buy Item");
        JButton sellItemBtn = new JButton("Sell Item");
        JButton useItemBtn = new JButton("Use Item");
        JButton addPokemonBtn = new JButton("Add Pokémon");
        JButton switchBtn = new JButton("Switch Pokémon");
        JButton releaseBtn = new JButton("Release Pokémon");
        JButton teachBtn = new JButton("Teach Move");
        JButton managePokeBtn = new JButton("Manage Pokémon");

        actionPanel.add(addTrainerBtn);
        actionPanel.add(buyItemBtn);
        actionPanel.add(sellItemBtn);
        actionPanel.add(useItemBtn);
        actionPanel.add(addPokemonBtn);
        actionPanel.add(switchBtn);
        actionPanel.add(releaseBtn);
        actionPanel.add(teachBtn);
        actionPanel.add(managePokeBtn);

        panel.add(actionPanel, BorderLayout.SOUTH);

        addTrainerBtn.addActionListener(e -> addNewTrainer());
        buyItemBtn.addActionListener(e -> buyItem());
        sellItemBtn.addActionListener(e -> sellItem());
        useItemBtn.addActionListener(e -> useItem());
        addPokemonBtn.addActionListener(e -> addPokemonToLineup());
        switchBtn.addActionListener(e -> switchPokemon());
        releaseBtn.addActionListener(e -> releasePokemon());
        teachBtn.addActionListener(e -> teachMove());
        managePokeBtn.addActionListener(e -> managePokemonDialog());

        return panel;
    }

    // Trainer Details Panel
    private JPanel buildTrainerDetailsPanel() {
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BorderLayout(10, 10));
        detailsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Top: Trainer Info
        JPanel trainerInfoPanel = new JPanel(new GridLayout(0, 2, 10, 5));
        trainerInfoPanel.setBorder(BorderFactory.createTitledBorder("Trainer Info"));

        JLabel idLabel = new JLabel();
        JLabel nameLabel = new JLabel();
        JLabel birthdateLabel = new JLabel();
        JLabel sexLabel = new JLabel();
        JLabel hometownLabel = new JLabel();
        JLabel descriptionLabel = new JLabel();
        JLabel moneyLabel = new JLabel();

        trainerInfoPanel.add(new JLabel("Trainer ID:")); trainerInfoPanel.add(idLabel);
        trainerInfoPanel.add(new JLabel("Name:")); trainerInfoPanel.add(nameLabel);
        trainerInfoPanel.add(new JLabel("Birthdate:")); trainerInfoPanel.add(birthdateLabel);
        trainerInfoPanel.add(new JLabel("Sex:")); trainerInfoPanel.add(sexLabel);
        trainerInfoPanel.add(new JLabel("Hometown:")); trainerInfoPanel.add(hometownLabel);
        trainerInfoPanel.add(new JLabel("Description:")); trainerInfoPanel.add(descriptionLabel);
        trainerInfoPanel.add(new JLabel("Money:")); trainerInfoPanel.add(moneyLabel);

        trainerInfoLabels = new JLabel[]{idLabel, nameLabel, birthdateLabel, sexLabel, hometownLabel, descriptionLabel, moneyLabel};
        detailsPanel.add(trainerInfoPanel, BorderLayout.NORTH);

        // Center: Pokemon Tables
        JSplitPane pokemonSplit = new JSplitPane(
                JSplitPane.VERTICAL_SPLIT,
                createTableWithTitle("Pokémon Lineup", true),
                createTableWithTitle("Pokémon Storage", false)
        );
        pokemonSplit.setResizeWeight(0.5);
        detailsPanel.add(pokemonSplit, BorderLayout.CENTER);

        return detailsPanel;
    }

    private JScrollPane createTableWithTitle(String title, boolean isLineup) {
        JTable table = new JTable();
        if (isLineup) lineupTable = table;
        else storageTable = table;
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder(title));
        return scrollPane;
    }

    // Inventory Panel
    private JPanel buildInventoryPanel() {
        JPanel inventoryPanel = new JPanel(new BorderLayout(5, 5));
        inventoryPanel.setBorder(BorderFactory.createTitledBorder("Trainer Inventory"));

        JScrollPane scrollPane = new JScrollPane(itemsList);
        inventoryPanel.add(scrollPane, BorderLayout.CENTER);

        return inventoryPanel;
    }

    // Update Trainer Details
    private void updateTrainerDetails() {
        if (trainers.isEmpty() || currentTrainerIndex < 0 || currentTrainerIndex >= trainers.size()) return;
        Trainer t = trainers.get(currentTrainerIndex);

        trainerInfoLabels[0].setText(t.getTrainerId());
        trainerInfoLabels[1].setText(t.getName());
        trainerInfoLabels[2].setText(t.getBirthdate());
        trainerInfoLabels[3].setText(t.getSex());
        trainerInfoLabels[4].setText(t.getHometown());
        trainerInfoLabels[5].setText(t.getDescription());
        trainerInfoLabels[6].setText("₱" + t.getMoney());

        String[] columns = {"Name", "Level", "Types", "HP", "ATK", "DEF", "SPD", "Held Item", "Moves"};

        // Lineup Table
        String[][] lineupData = new String[t.getLineup().size()][columns.length];
        for (int i = 0; i < t.getLineup().size(); i++) {
            Pokemon p = t.getLineup().get(i);
            lineupData[i] = new String[]{
                    p.getName(),
                    String.valueOf(p.getBaseLevel()),  // Add Level
                    p.getType1() + (p.getType2() != null ? "/" + p.getType2() : ""),
                    String.valueOf(p.getHp()),
                    String.valueOf(p.getAttack()),
                    String.valueOf(p.getDefense()),
                    String.valueOf(p.getSpeed()),
                    p.getHeldItem() != null ? p.getHeldItem() : "None",
                    String.join(", ", p.getMoveSet())
            };
        }
        lineupTable.setModel(new DefaultTableModel(lineupData, columns));

        // Storage Table
        String[][] storageData = new String[t.getStorage().size()][columns.length];
        for (int i = 0; i < t.getStorage().size(); i++) {
            Pokemon p = t.getStorage().get(i);
            storageData[i] = new String[]{
                    p.getName(),
                    String.valueOf(p.getBaseLevel()),  // Add Level
                    p.getType1() + (p.getType2() != null ? "/" + p.getType2() : ""),
                    String.valueOf(p.getHp()),
                    String.valueOf(p.getAttack()),
                    String.valueOf(p.getDefense()),
                    String.valueOf(p.getSpeed()),
                    p.getHeldItem() != null ? p.getHeldItem() : "None",
                    String.join(", ", p.getMoveSet())
            };
        }
        storageTable.setModel(new DefaultTableModel(storageData, columns));

        // Inventory
        DefaultListModel<String> itemsModel = new DefaultListModel<>();
        for (Map.Entry<Item, Integer> entry : t.getInventory().entrySet()) {
            itemsModel.addElement(entry.getKey().getName() + " (x" + entry.getValue() + ")");
        }
        itemsList.setModel(itemsModel);
    }

    // Search Trainer
    private void searchTrainer() {
        String query = searchField.getText().trim().toLowerCase();
        if (query.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter a keyword to search for a trainer.");
            return;
        }

        for (int i = 0; i < trainers.size(); i++) {
            Trainer t = trainers.get(i);
            if (t.getTrainerId().toLowerCase().contains(query)
                    || t.getName().toLowerCase().contains(query)
                    || t.getHometown().toLowerCase().contains(query)) {
                currentTrainerIndex = i;
                trainerList.setSelectedIndex(i);
                updateTrainerDetails();
                return;
            }
        }

        JOptionPane.showMessageDialog(this, "❌ No trainer found with keyword: " + query);
    }

    // Add Trainer
    private void addNewTrainer() {
        try {
            String id = JOptionPane.showInputDialog(this, "Enter Trainer ID:");
            if (id == null || id.trim().isEmpty()) return;

            for (Trainer t : trainers) {
                if (t.getTrainerId().equalsIgnoreCase(id.trim())) {
                    JOptionPane.showMessageDialog(this, "❌ Trainer ID already exists.");
                    return;
                }
            }

            String name = JOptionPane.showInputDialog(this, "Enter Name:");
            if (name == null || name.trim().isEmpty()) return;

            String birthdate = JOptionPane.showInputDialog(this, "Enter Birthdate (MM/DD/YYYY):");
            if (birthdate == null || birthdate.trim().isEmpty()) return;

            String[] sexes = {"M", "F"};
            String sex = (String) JOptionPane.showInputDialog(
                    this, "Select Sex:", "Sex",
                    JOptionPane.QUESTION_MESSAGE, null, sexes, sexes[0]);
            if (sex == null) return;

            String hometown = JOptionPane.showInputDialog(this, "Enter Hometown:");
            if (hometown == null || hometown.trim().isEmpty()) return;

            String description = JOptionPane.showInputDialog(this, "Enter Description:");
            if (description == null) description = "";

            int money = 1_000_000;

            trainers.add(new Trainer(id.trim(), name.trim(), birthdate, sex, hometown, description, money));
            ((DefaultListModel<String>) trainerList.getModel()).addElement(id + " - " + name);
            JOptionPane.showMessageDialog(this, "✅ Trainer " + name + " added successfully!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "❌ Error adding trainer: " + ex.getMessage());
        }
    }

    // Buy Item
    private void buyItem() {
        Trainer t = trainers.get(currentTrainerIndex);
        String[] itemNames = PokedexApp.getItems().stream().map(Item::getName).toArray(String[]::new);
        String selectedItem = (String) JOptionPane.showInputDialog(this, "Select item to buy:", "Buy Item",
                JOptionPane.QUESTION_MESSAGE, null, itemNames, itemNames[0]);

        if (selectedItem == null) return;

        String quantityStr = JOptionPane.showInputDialog(this, "Enter quantity to buy:");
        if (quantityStr == null || quantityStr.isEmpty()) return;
        int quantity = Integer.parseInt(quantityStr);

        Item item = PokedexApp.getItems().stream()
                .filter(i -> i.getName().equals(selectedItem))
                .findFirst().orElse(null);

        if (item != null) {
            try {
                t.buyItem(item, quantity);
                JOptionPane.showMessageDialog(this, "✅ Bought " + quantity + "x " + item.getName());
                updateTrainerDetails();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage());
            }
        }
    }

    // Sell Item
    private void sellItem() {
        Trainer t = trainers.get(currentTrainerIndex);
        if (t.getInventory().isEmpty()) {
            JOptionPane.showMessageDialog(this, "❌ No items to sell.");
            return;
        }

        Item[] itemArray = t.getInventory().keySet().toArray(new Item[0]);
        String[] itemOptions = new String[itemArray.length];
        for (int i = 0; i < itemArray.length; i++) {
            itemOptions[i] = itemArray[i].getName() + " (x" + t.getInventory().get(itemArray[i]) + ")";
        }

        String selectedItemStr = (String) JOptionPane.showInputDialog(
                this, "Select item to sell:", "Sell Item",
                JOptionPane.QUESTION_MESSAGE, null, itemOptions, itemOptions[0]);

        if (selectedItemStr == null) return;

        Item selectedItem = null;
        for (Item i : itemArray) {
            if (selectedItemStr.startsWith(i.getName())) {
                selectedItem = i;
                break;
            }
        }

        if (selectedItem != null) {
            String quantityStr = JOptionPane.showInputDialog(this, "Enter quantity to sell:");
            if (quantityStr == null || quantityStr.isEmpty()) return;
            int quantity = Integer.parseInt(quantityStr);

            try {
                t.sellItem(selectedItem, quantity);
                JOptionPane.showMessageDialog(this, "✅ Sold " + quantity + "x " + selectedItem.getName());
                updateTrainerDetails();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage());
            }
        }
    }

    // Use Item
    private void useItem() {
        Trainer t = trainers.get(currentTrainerIndex);
        if (t.getInventory().isEmpty()) {
            JOptionPane.showMessageDialog(this, "❌ No items to use.");
            return;
        }

        Item[] itemArray = t.getInventory().keySet().toArray(new Item[0]);
        String[] itemOptions = new String[itemArray.length];
        for (int i = 0; i < itemArray.length; i++) {
            itemOptions[i] = itemArray[i].getName() + " (x" + t.getInventory().get(itemArray[i]) + ")";
        }

        String selectedItemStr = (String) JOptionPane.showInputDialog(
                this, "Select item to use:", "Use Item",
                JOptionPane.QUESTION_MESSAGE, null, itemOptions, itemOptions[0]);

        if (selectedItemStr == null) return;

        Item selectedItem = null;
        for (Item i : itemArray) {
            if (selectedItemStr.startsWith(i.getName())) {
                selectedItem = i;
                break;
            }
        }

        if (t.getLineup().isEmpty()) {
            JOptionPane.showMessageDialog(this, "❌ No Pokémon in lineup to use the item on.");
            return;
        }

        Pokemon[] pokeArray = t.getLineup().toArray(new Pokemon[0]);
        String[] pokeOptions = new String[pokeArray.length];
        for (int i = 0; i < pokeArray.length; i++) {
            pokeOptions[i] = pokeArray[i].getName() + " (HP: " + pokeArray[i].getHp() + ")";
        }

        String selectedPokeStr = (String) JOptionPane.showInputDialog(
                this, "Select a Pokémon:", "Choose Pokémon",
                JOptionPane.QUESTION_MESSAGE, null, pokeOptions, pokeOptions[0]);

        if (selectedPokeStr == null) return;

        Pokemon selectedPoke = null;
        for (Pokemon p : pokeArray) {
            if (selectedPokeStr.startsWith(p.getName())) {
                selectedPoke = p;
                break;
            }
        }

        if (selectedItem != null && selectedPoke != null) {
            t.useItemOnPokemon(selectedItem, selectedPoke);
            JOptionPane.showMessageDialog(this, "✅ " + selectedItem.getName() + " applied to " + selectedPoke.getName());
            updateTrainerDetails();
        }
    }

    // Pokemon Management
    private void addPokemonToLineup() {
        Trainer t = trainers.get(currentTrainerIndex);
        String[] pokeNames = PokedexApp.getSeededPokemon().stream()
                .map(Pokemon::getName).toArray(String[]::new);

        String selectedPoke = (String) JOptionPane.showInputDialog(this, "Select Pokémon to add:", "Add Pokémon",
                JOptionPane.QUESTION_MESSAGE, null, pokeNames, pokeNames[0]);

        if (selectedPoke == null) return;

        Pokemon base = PokedexApp.getSeededPokemon().stream()
                .filter(p -> p.getName().equals(selectedPoke))
                .findFirst().orElse(null);

        if (base != null) {
            Pokemon newPoke = new Pokemon(base.getPokedexNumber(), base.getName(),
                    base.getType1(), base.getType2() != null ? base.getType2() : "",
                    base.getBaseLevel(), base.getEvolvesFrom(), base.getEvolvesTo(),
                    base.getEvolutionLevel(), base.getHp(), base.getAttack(),
                    base.getDefense(), base.getSpeed());

            t.addPokemonToLineup(newPoke);
            updateTrainerDetails();
        }
    }

    private void switchPokemon() {
        Trainer t = trainers.get(currentTrainerIndex);
        if (t.getLineup().isEmpty() || t.getStorage().isEmpty()) {
            JOptionPane.showMessageDialog(this, "❌ You need Pokémon in both lineup and storage to switch.");
            return;
        }

        String[] lineupNames = t.getLineup().stream().map(Pokemon::getName).toArray(String[]::new);
        String lineupChoice = (String) JOptionPane.showInputDialog(
                this, "Select Pokémon from lineup to switch:",
                "Switch Pokémon", JOptionPane.QUESTION_MESSAGE, null, lineupNames, lineupNames[0]);
        if (lineupChoice == null) return;

        String[] storageNames = t.getStorage().stream().map(Pokemon::getName).toArray(String[]::new);
        String storageChoice = (String) JOptionPane.showInputDialog(
                this, "Select Pokémon from storage to switch:",
                "Switch Pokémon", JOptionPane.QUESTION_MESSAGE, null, storageNames, storageNames[0]);
        if (storageChoice == null) return;

        int lineupIndex = -1, storageIndex = -1;
        for (int i = 0; i < t.getLineup().size(); i++) {
            if (t.getLineup().get(i).getName().equals(lineupChoice)) lineupIndex = i;
        }
        for (int i = 0; i < t.getStorage().size(); i++) {
            if (t.getStorage().get(i).getName().equals(storageChoice)) storageIndex = i;
        }

        if (lineupIndex >= 0 && storageIndex >= 0) {
            t.switchPokemonFromStorage(lineupIndex, storageIndex);
            JOptionPane.showMessageDialog(this, "✅ Switched " + lineupChoice + " with " + storageChoice);
            updateTrainerDetails();
        }
    }

    private void releasePokemon() {
        Trainer t = trainers.get(currentTrainerIndex);
        if (t.getStorage().isEmpty()) {
            JOptionPane.showMessageDialog(this, "❌ No Pokémon in storage to release.");
            return;
        }

        String[] storageNames = t.getStorage().stream().map(Pokemon::getName).toArray(String[]::new);
        String releaseChoice = (String) JOptionPane.showInputDialog(
                this, "Select Pokémon to release:",
                "Release Pokémon", JOptionPane.QUESTION_MESSAGE, null, storageNames, storageNames[0]);

        if (releaseChoice != null) {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to release " + releaseChoice + "?",
                    "Confirm Release", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                t.releasePokemon(releaseChoice);
                JOptionPane.showMessageDialog(this, "🕊️ " + releaseChoice + " has been released.");
                updateTrainerDetails();
            }
        }
    }

    private void teachMove() {
        Trainer t = trainers.get(currentTrainerIndex);
        if (t.getLineup().isEmpty()) {
            JOptionPane.showMessageDialog(this, "❌ No Pokémon in lineup.");
            return;
        }

        String[] pokeNames = t.getLineup().stream().map(Pokemon::getName).toArray(String[]::new);
        String selectedPoke = (String) JOptionPane.showInputDialog(this, "Select Pokémon to teach:", "Teach Move",
                JOptionPane.QUESTION_MESSAGE, null, pokeNames, pokeNames[0]);

        if (selectedPoke == null) return;

        Pokemon selectedPokemon = t.getLineup().stream()
                .filter(p -> p.getName().equals(selectedPoke))
                .findFirst().orElse(null);

        String[] moveNames = PokedexApp.getMoves().stream().map(Move::getName).toArray(String[]::new);
        String selectedMove = (String) JOptionPane.showInputDialog(this, "Select Move:", "Teach Move",
                JOptionPane.QUESTION_MESSAGE, null, moveNames, moveNames[0]);

        if (selectedMove == null) return;

        Move move = PokedexApp.getMoves().stream()
                .filter(m -> m.getName().equals(selectedMove))
                .findFirst().orElse(null);

        if (move == null) return;

        if (!move.getType1().equalsIgnoreCase(selectedPokemon.getType1()) &&
                (selectedPokemon.getType2() == null || !move.getType1().equalsIgnoreCase(selectedPokemon.getType2()))) {
            JOptionPane.showMessageDialog(this, "❌ Move is not compatible with Pokémon type.");
            return;
        }

        if (selectedPokemon.getMoveSet().size() >= 4 && !move.getClassification().equals("HM")) {
            String[] currentMoves = selectedPokemon.getMoveSet().toArray(new String[0]);
            String forget = (String) JOptionPane.showInputDialog(this,
                    "Pokémon knows 4 moves. Choose a move to forget:",
                    "Forget Move", JOptionPane.QUESTION_MESSAGE, null,
                    currentMoves, currentMoves[0]);
            if (forget != null) {
                selectedPokemon.forgetMove(forget);
                selectedPokemon.learnMove(selectedMove);
                JOptionPane.showMessageDialog(this, "✅ " + selectedPoke + " learned " + selectedMove);
            }
        } else {
            selectedPokemon.learnMove(selectedMove);
            JOptionPane.showMessageDialog(this, "✅ " + selectedPoke + " learned " + selectedMove);
        }

        updateTrainerDetails();
    }

    // Pokemon Detail Dialog
    private void managePokemonDialog() {
        Trainer t = trainers.get(currentTrainerIndex);
        if (t.getLineup().isEmpty() && t.getStorage().isEmpty()) {
            JOptionPane.showMessageDialog(this, "❌ No Pokémon available for this trainer.");
            return;
        }

        JPanel dialogPanel = new JPanel(new BorderLayout(10, 10));

        DefaultListModel<String> lineupModel = new DefaultListModel<>();
        for (Pokemon p : t.getLineup()) lineupModel.addElement(p.getName());
        JList<String> lineupList = new JList<>(lineupModel);

        DefaultListModel<String> storageModel = new DefaultListModel<>();
        for (Pokemon p : t.getStorage()) storageModel.addElement(p.getName());
        JList<String> storageList = new JList<>(storageModel);

        JPanel listPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        listPanel.add(new JScrollPane(lineupList));
        listPanel.add(new JScrollPane(storageList));
        dialogPanel.add(listPanel, BorderLayout.CENTER);

        JTextArea detailArea = new JTextArea(10, 30);
        detailArea.setEditable(false);
        dialogPanel.add(new JScrollPane(detailArea), BorderLayout.EAST);

        lineupList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && lineupList.getSelectedIndex() >= 0) {
                Pokemon p = t.getLineup().get(lineupList.getSelectedIndex());
                detailArea.setText(formatPokemonDetail(p));
            }
        });
        storageList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && storageList.getSelectedIndex() >= 0) {
                Pokemon p = t.getStorage().get(storageList.getSelectedIndex());
                detailArea.setText(formatPokemonDetail(p));
            }
        });

        JPanel btnPanel = new JPanel(new FlowLayout());
        JButton assignItemBtn = new JButton("Assign Held Item");
        JButton teachMoveBtn = new JButton("Teach Move");
        JButton forgetMoveBtn = new JButton("Forget Move");
        btnPanel.add(assignItemBtn);
        btnPanel.add(teachMoveBtn);
        btnPanel.add(forgetMoveBtn);
        dialogPanel.add(btnPanel, BorderLayout.SOUTH);

        assignItemBtn.addActionListener(e -> {
            Pokemon selected = getSelectedPokemon(t, lineupList, storageList);
            if (selected != null) assignHeldItem(t, selected);
            detailArea.setText(formatPokemonDetail(selected));
            updateTrainerDetails();
        });

        teachMoveBtn.addActionListener(e -> {
            Pokemon selected = getSelectedPokemon(t, lineupList, storageList);
            if (selected != null) teachMoveToPokemon(selected);
            detailArea.setText(formatPokemonDetail(selected));
            updateTrainerDetails();
        });

        forgetMoveBtn.addActionListener(e -> {
            Pokemon selected = getSelectedPokemon(t, lineupList, storageList);
            if (selected != null) forgetMoveFromPokemon(selected);
            detailArea.setText(formatPokemonDetail(selected));
            updateTrainerDetails();
        });

        JOptionPane.showMessageDialog(this, dialogPanel, "Manage Pokémon", JOptionPane.PLAIN_MESSAGE);
    }

    private String formatPokemonDetail(Pokemon p) {
        if (p == null) return "No Pokémon selected.";
        return String.format(
                "%s (#%04d)\nTypes: %s%s\nHP: %d\nATK: %d DEF: %d SPD: %d\nHeld Item: %s\nMoves: %s",
                p.getName(), p.getPokedexNumber(),
                p.getType1(),
                p.getType2() != null ? "/" + p.getType2() : "",
                p.getHp(), p.getAttack(), p.getDefense(), p.getSpeed(),
                p.getHeldItem() != null ? p.getHeldItem() : "None",
                String.join(", ", p.getMoveSet())
        );
    }

    private Pokemon getSelectedPokemon(Trainer t, JList<String> lineupList, JList<String> storageList) {
        if (lineupList.getSelectedIndex() >= 0)
            return t.getLineup().get(lineupList.getSelectedIndex());
        if (storageList.getSelectedIndex() >= 0)
            return t.getStorage().get(storageList.getSelectedIndex());
        JOptionPane.showMessageDialog(this, "❌ No Pokémon selected.");
        return null;
    }

    private void assignHeldItem(Trainer t, Pokemon p) {
        if (t.getInventory().isEmpty()) {
            JOptionPane.showMessageDialog(this, "❌ Trainer has no items.");
            return;
        }
        Item[] itemArray = t.getInventory().keySet().toArray(new Item[0]);
        String[] items = new String[itemArray.length];
        for (int i = 0; i < itemArray.length; i++) {
            items[i] = itemArray[i].getName() + " (x" + t.getInventory().get(itemArray[i]) + ")";
        }

        String selected = (String) JOptionPane.showInputDialog(this, "Select item to hold:", "Assign Held Item",
                JOptionPane.QUESTION_MESSAGE, null, items, items[0]);
        if (selected != null) {
            p.setHeldItem(selected.split(" \\(")[0]);
            JOptionPane.showMessageDialog(this, "✅ " + p.getName() + " now holds " + p.getHeldItem());
        }
    }

    private void teachMoveToPokemon(Pokemon p) {
        String[] moves = PokedexApp.getMoves().stream().map(Move::getName).toArray(String[]::new);
        String selectedMove = (String) JOptionPane.showInputDialog(this, "Select move to teach:", "Teach Move",
                JOptionPane.QUESTION_MESSAGE, null, moves, moves[0]);
        if (selectedMove == null) return;

        Move move = PokedexApp.getMoves().stream().filter(m -> m.getName().equals(selectedMove)).findFirst().orElse(null);
        if (move == null) return;

        if (!move.getType1().equalsIgnoreCase(p.getType1()) &&
                (p.getType2() == null || !move.getType1().equalsIgnoreCase(p.getType2()))) {
            JOptionPane.showMessageDialog(this, "❌ Move is not compatible with Pokémon type.");
            return;
        }

        if (p.getMoveSet().size() >= 4 && !move.getClassification().equalsIgnoreCase("HM")) {
            String forget = (String) JOptionPane.showInputDialog(this, "Choose a move to forget:",
                    "Forget Move", JOptionPane.QUESTION_MESSAGE, null,
                    p.getMoveSet().toArray(new String[0]), p.getMoveSet().get(0));
            if (forget != null) {
                p.forgetMove(forget);
                p.learnMove(selectedMove);
            }
        } else {
            p.learnMove(selectedMove);
        }
    }

    private void forgetMoveFromPokemon(Pokemon p) {
        if (p.getMoveSet().size() <= 2) {
            JOptionPane.showMessageDialog(this, "❌ Pokémon must have at least 2 moves.");
            return;
        }
        String forget = (String) JOptionPane.showInputDialog(this, "Choose a move to forget:",
                "Forget Move", JOptionPane.QUESTION_MESSAGE, null,
                p.getMoveSet().toArray(new String[0]), p.getMoveSet().get(0));
        if (forget != null && !forget.equals("Tackle") && !forget.equals("Defend")) {
            p.forgetMove(forget);
            JOptionPane.showMessageDialog(this, "✅ " + p.getName() + " forgot " + forget);
        }
    }
}
