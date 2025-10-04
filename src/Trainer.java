import java.util.*;

public class Trainer {
    private final String trainerId;
    private String name;
    private String birthdate;
    private String sex;
    private String hometown;
    private String description;
    private int money;

    private Map<Item, Integer> inventory = new HashMap<>();
    private List<Pokemon> lineup = new ArrayList<>();
    private List<Pokemon> storage = new ArrayList<>();

    public Trainer(String trainerId, String name, String birthdate, String sex, String hometown, String description, int money) {
        this.trainerId = trainerId;
        this.name = name;
        this.birthdate = birthdate;
        this.sex = sex;
        this.hometown = hometown;
        this.description = description;
        this.money = money;
    }

    // Item Management
    public void buyItem(Item item, int quantity) {
        if (quantity <= 0) {
            System.out.println("❌ Quantity must be positive.");
            return;
        }

        int totalItems = inventory.values().stream().mapToInt(Integer::intValue).sum();
        if (totalItems + quantity > 50) {
            throw new IllegalStateException("❌ Cannot hold more than 50 total items.");
        }
        if (!inventory.containsKey(item) && inventory.size() >= 10) {
            throw new IllegalStateException("❌ Cannot hold more than 10 unique items.");
        }

        int totalCost = item.getBuyPrice() * quantity;
        if (money >= totalCost) {
            money -= totalCost;
            inventory.put(item, inventory.getOrDefault(item, 0) + quantity);
            System.out.println("🛒 " + name + " bought " + quantity + "x " + item.getName() + " for ₱" + totalCost);
        } else {
            throw new IllegalStateException("❌ Not enough money to buy " + quantity + "x " + item.getName());
        }
    }

    public void sellItem(Item item, int quantity) {
        if (quantity <= 0) {
            System.out.println("❌ Quantity must be positive.");
            return;
        }

        if (!inventory.containsKey(item)) {
            throw new IllegalStateException("❌ Item not found in inventory.");
        }

        int currentCount = inventory.get(item);
        if (quantity > currentCount) {
            throw new IllegalStateException("❌ You only have " + currentCount + "x " + item.getName() + " to sell.");
        }

        int refund = item.getBuyPrice() / 2 * quantity;
        money += refund;

        if (currentCount == quantity) {
            inventory.remove(item);
        } else {
            inventory.put(item, currentCount - quantity);
        }

        System.out.println("💰 Sold " + quantity + "x " + item.getName() + " for ₱" + refund);
    }

    public void useItem(String itemName) {
        for (Item item : inventory.keySet()) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                removeItem(item);
                System.out.println("🧪 Used item: " + item.getName() + " (Effect: " + item.getEffect() + ")");
                return;
            }
        }
        System.out.println("❌ Item not found in inventory.");
    }

    // Add item with stacking
    public void addItem(Item item) {
        int totalItems = inventory.values().stream().mapToInt(Integer::intValue).sum();
        boolean hasItem = inventory.containsKey(item);

        if (!hasItem && inventory.size() >= 10) {
            throw new IllegalStateException("❌ Cannot hold more than 10 unique items.");
        }
        if (totalItems >= 50) {
            throw new IllegalStateException("❌ Cannot hold more than 50 total items.");
        }

        inventory.put(item, inventory.getOrDefault(item, 0) + 1);
    }

    // Remove item with stack reduction
    public void removeItem(Item item) {
        if (inventory.containsKey(item)) {
            int count = inventory.get(item);
            if (count > 1) {
                inventory.put(item, count - 1);
            } else {
                inventory.remove(item);
            }
        }
    }

    // Apply item effect on Pokemon
    public void useItemOnPokemon(Item item, Pokemon target) {
        if (item.getCategory().equalsIgnoreCase("Evolution Stone")) {
            target.evolveWithStone();
        } else {
            switch (item.getEffect()) {
                case "+10 HP EVs" -> target.setHp(target.getHp() + 10);
                case "+10 Attack EVs" -> target.setAttack(target.getAttack() + 10);
                case "+10 Defense EVs" -> target.setDefense(target.getDefense() + 10);
                case "+10 Speed EVs" -> target.setSpeed(target.getSpeed() + 10);
                case "Increases level by 1" -> {
                    target.setBaseLevel(target.getBaseLevel() + 1);
                    System.out.println(target.getName() + " leveled up to " + target.getBaseLevel() + "!");
                }
                default -> System.out.println("Effect not recognized for " + item.getName());
            }
        }
        removeItem(item);
    }

    // Pokémon Management
    public void addPokemonToLineup(Pokemon pokemon) {
        if (lineup.size() >= 6) {
            System.out.println("⚠️ Lineup full. Pokémon sent to storage.");
            storage.add(pokemon);
        } else {
            lineup.add(pokemon);
            System.out.println("✅ " + pokemon.getName() + " added to lineup.");
        }
    }

    public void switchPokemonFromStorage(int lineupIndex, int storageIndex) {
        if (lineupIndex < lineup.size() && storageIndex < storage.size()) {
            Pokemon fromLineup = lineup.get(lineupIndex);
            Pokemon fromStorage = storage.get(storageIndex);
            lineup.set(lineupIndex, fromStorage);
            storage.set(storageIndex, fromLineup);
            System.out.println("🔁 Switched " + fromLineup.getName() + " with " + fromStorage.getName());
        } else {
            System.out.println("❌ Invalid lineup or storage index.");
        }
    }

    public void releasePokemon(String name) {
        Iterator<Pokemon> it = storage.iterator();
        while (it.hasNext()) {
            Pokemon p = it.next();
            if (p.getName().equalsIgnoreCase(name)) {
                it.remove();
                System.out.println("🕊️ " + p.getName() + " has been released.");
                return;
            }
        }
        System.out.println("❌ Pokémon not found in storage.");
    }

    public void teachMove(String pokemonName, String moveName) {
        for (Pokemon p : lineup) {
            if (p.getName().equalsIgnoreCase(pokemonName)) {
                p.getMoveSet().add(moveName);
                System.out.println("📘 " + moveName + " taught to " + p.getName());
                return;
            }
        }
        System.out.println("❌ Pokémon not found in lineup.");
    }

    // Money Management
    public void addMoney(int amount) {
        this.money += amount;
    }

    public void deductMoney(int amount) {
        if (this.money >= amount) {
            this.money -= amount;
        }
    }

    // Getters
    public String getTrainerId() { return trainerId; }
    public String getName() { return name; }
    public String getBirthdate() { return birthdate; }
    public String getSex() { return sex; }
    public String getHometown() { return hometown; }
    public String getDescription() { return description; }
    public int getMoney() { return money; }
    public Map<Item, Integer> getInventory() { return inventory; }
    public List<Pokemon> getLineup() { return lineup; }
    public List<Pokemon> getStorage() { return storage; }
}
