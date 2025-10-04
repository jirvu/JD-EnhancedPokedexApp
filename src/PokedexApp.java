import java.util.*;

public class PokedexApp {
    private static final Scanner scanner = new Scanner(System.in);
    private static final List<Pokemon> pokedex = new ArrayList<>();
    private static final List<Item> items = new ArrayList<>();
    private static final List<Move> moves = new ArrayList<>();
    private static final List<Trainer> trainers = new ArrayList<>();

    public static void main(String[] args) {
        seedAll();

        int option;
        do {
            System.out.println("\n--- Enhanced Pokedex ---");
            System.out.println("[1] Add New Pokémon");
            System.out.println("[2] View All Pokémon");
            System.out.println("[3] Search Pokémon");
            System.out.println("[4] Add New Move");
            System.out.println("[5] View All Moves");
            System.out.println("[6] Search Moves");
            System.out.println("[7] View All Items");
            System.out.println("[8] Search Items");
            System.out.println("[9] Add Trainer");
            System.out.println("[10] View All Trainers");
            System.out.println("[11] Search Trainer");

            System.out.println("[0] Exit");
            System.out.print("Choose an option: ");
            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1 -> addPokemon();
                case 2 -> viewAllPokemon();
                case 3 -> searchPokemon();
                case 4 -> addMove();
                case 5 -> viewAllMoves();
                case 6 -> searchMoves();
                case 7 -> viewAllItems();
                case 8 -> searchItems();
                case 9 -> addTrainer();
                case 10 -> viewAllTrainers();
                case 11 -> searchTrainer();
                case 0 -> System.out.println("Exiting Enhanced Pokedex, thank you!");
                default -> System.out.println("❌ Invalid option, please try again!");
            }
        } while (option != 0);
    }

    // -------------------- SEED ALL --------------------
    public static void seedAll() {
        if (pokedex.isEmpty()) seedPokemon();
        if (moves.isEmpty()) seedMoves();
        if (items.isEmpty()) seedItems();
        if (trainers.isEmpty()) seedTrainers();
    }

    // -------------------- SEEDING DATA --------------------
    private static void seedPokemon() {
        pokedex.add(new Pokemon(1, "Bulbasaur", "Grass", "Poison", 5, 0, 2, 16, 45, 49, 49, 45));
        pokedex.add(new Pokemon(2, "Ivysaur", "Grass", "Poison", 16, 1, 3, 32, 60, 62, 63, 60));
        pokedex.add(new Pokemon(3, "Venusaur", "Grass", "Poison", 32, 2, 0, 0, 80, 82, 83, 80));

        pokedex.add(new Pokemon(4, "Charmander", "Fire", "", 5, 0, 5, 16, 39, 52, 43, 65));
        pokedex.add(new Pokemon(5, "Charmeleon", "Fire", "", 16, 4, 6, 36, 58, 64, 58, 80));
        pokedex.add(new Pokemon(6, "Charizard", "Fire", "Flying", 36, 5, 0, 0, 78, 84, 78, 100));

        pokedex.add(new Pokemon(7, "Squirtle", "Water", "", 5, 0, 8, 16, 44, 48, 65, 43));
        pokedex.add(new Pokemon(8, "Wartortle", "Water", "", 16, 7, 9, 36, 59, 63, 80, 58));
        pokedex.add(new Pokemon(9, "Blastoise", "Water", "", 36, 8, 0, 0, 79, 83, 100, 78));

        pokedex.add(new Pokemon(10, "Caterpie", "Bug", "", 5, 0, 11, 16, 45, 30, 35, 45));
        pokedex.add(new Pokemon(11, "Metapod", "Bug", "", 16, 10, 12, 36, 50, 20, 55, 30));
        pokedex.add(new Pokemon(12, "Butterfree", "Bug", "Flying", 36, 11, 0, 0, 60, 45, 50, 70));

        pokedex.add(new Pokemon(13, "Weedle", "Bug", "Poison", 5, 0, 14, 16, 40, 35, 30, 50));
        pokedex.add(new Pokemon(14, "Kakuna", "Bug", "Poison", 16, 13, 15, 36, 45, 25, 50, 35));
        pokedex.add(new Pokemon(15, "Beedrill", "Bug", "Poison", 14, 14, 0, 0, 65, 90, 40, 75));

        pokedex.add(new Pokemon(16, "Pidgey", "Normal", "Flying", 5, 0, 17, 16, 40, 45, 40, 56));
        pokedex.add(new Pokemon(17, "Pidgeotto", "Normal", "Flying", 16, 16, 18, 36, 63, 60, 55, 71));
        pokedex.add(new Pokemon(18, "Pidgeot", "Normal", "Flying", 14, 17, 0, 0, 83, 80, 75, 101));

        pokedex.add(new Pokemon(19, "Rattata", "Normal", "", 5, 0, 20, 16, 30, 56, 35, 72));
        pokedex.add(new Pokemon(20, "Raticate", "Dark", "Normal", 16, 19, 0, 0, 55, 81, 60, 97));

        pokedex.add(new Pokemon(21, "Spearow", "Normal", "Flying", 5, 0, 22, 16, 40, 60, 30, 70));
        pokedex.add(new Pokemon(22, "Fearow", "Normal", "Flying", 16, 21, 0, 0, 65, 90, 65, 100));

        pokedex.add(new Pokemon(23, "Ekans", "Poison", "", 5, 0, 24, 16, 35, 60, 44, 55));
        pokedex.add(new Pokemon(24, "Arbok", "Poison", "", 16, 23, 0, 0, 60, 95, 69, 80));

        pokedex.add(new Pokemon(25, "Pikachu", "Electric", "", 5, 0, 26, 16, 60, 35, 40, 90));
        pokedex.add(new Pokemon(26, "Raichu", "Electric", "", 16, 25, 0, 0, 60, 60, 55, 110));

        pokedex.add(new Pokemon(27, "Sandshrew", "Ground", "", 5, 0, 28, 16, 50, 75, 85, 40));
        pokedex.add(new Pokemon(28, "Sandslash", "Ground", "", 16, 27, 0, 0, 75, 100, 110, 65));

        pokedex.add(new Pokemon(29, "Nidoran", "Poison", "", 5, 0, 30, 16, 55, 47, 52, 41));
        pokedex.add(new Pokemon(30, "Nidorina", "Poison", "", 16, 29, 0, 0, 70, 62, 67, 56));
    }

    public static List<Pokemon> getSeededPokemon() {
        if (pokedex.isEmpty()) {
            seedPokemon();
        }
        return pokedex;
    }

    private static void seedMoves() {
        // Core defaults
        moves.add(new Move("Tackle", "A basic physical attack.", "TM", "Normal", ""));
        moves.add(new Move("Defend", "Raises user's defense.", "TM", "Normal", ""));
        // Bulbasaur line moves
        moves.add(new Move("Growl", "Lowers target's attack.", "TM", "Normal", ""));
        moves.add(new Move("Leech Seed", "Seeds drain HP each turn.", "TM", "Grass", ""));
        moves.add(new Move("Vine Whip", "Strikes with vines.", "TM", "Grass", ""));
        moves.add(new Move("Poison Powder", "May poison the target.", "TM", "Poison", ""));
        moves.add(new Move("Razor Leaf", "Sharp leaves thrown.", "TM", "Grass", ""));
        moves.add(new Move("Sleep Powder", "Puts target to sleep.", "TM", "Grass", ""));
        moves.add(new Move("Growth", "Raises user's stats gradually.", "TM", "Normal", ""));
        moves.add(new Move("Solar Beam", "Absorbs sun then attacks.", "TM", "Grass", ""));
        // Charmander line moves (Generation I defaults)
        moves.add(new Move("Ember", "Small flame attack.", "TM", "Fire", ""));
        moves.add(new Move("Dragon Rage", "Always deals 40 damage.", "TM", "Dragon", ""));
        moves.add(new Move("Seismic Toss", "Inflicts damage equal to user level.", "TM", "Fighting", ""));
        moves.add(new Move("Flamethrower", "Large flame attack.", "TM", "Fire", ""));
        // Squirtle line moves
        moves.add(new Move("Tail Whip", "Lowers target's defense.", "TM", "Normal", ""));
        moves.add(new Move("Bubble", "Shoots bubble to damage.", "TM", "Water", ""));
        moves.add(new Move("Water Gun", "Shoots water jet.", "TM", "Water", ""));
        moves.add(new Move("Bite", "Bites with fangs; may flinch.", "TM", "Normal", ""));
        moves.add(new Move("Withdraw", "Raises user defense.", "TM", "Water", ""));
        moves.add(new Move("Skull Bash", "Charges then strikes.", "TM", "Normal", ""));
        moves.add(new Move("Hydro Pump", "Powerful water blast.", "TM", "Water", ""));
        // HM moves
        moves.add(new Move("Cut", "Cuts small trees and obstacles.", "HM", "Normal", ""));
        moves.add(new Move("Strength", "Push large objects.", "HM", "Normal", ""));
        moves.add(new Move("Surf", "Powerful wave attack.", "HM", "Water", ""));
        moves.add(new Move("Fly", "Flies high and hits next turn.", "HM", "Flying", ""));
        moves.add(new Move("Flash", "Lights up dark areas.", "HM", "Normal", ""));
    }

    public static List<Move> getMoves() {
        if (moves.isEmpty()) seedMoves();
        return moves;
    }

    private static void seedItems() {
        // Vitamins
        items.add(new Item("HP Up", "Vitamin", "A nutritious drink for Pokémon.", "+10 HP EVs", 10000, 5000));
        items.add(new Item("Protein", "Vitamin", "A nutritious drink for Pokémon.", "+10 Attack EVs", 10000, 5000));
        items.add(new Item("Iron", "Vitamin", "A nutritious drink for Pokémon.", "+10 Defense EVs", 10000, 5000));
        items.add(new Item("Carbos", "Vitamin", "A nutritious drink for Pokémon.", "+10 Speed EVs", 10000, 5000));
        items.add(new Item("Zinc", "Vitamin", "A nutritious drink for Pokémon.", "+10 Special Defense EVs", 10000, 5000));
        // Feathers
        items.add(new Item("Health Feather", "Feather", "A feather that slightly increases HP.", "+1 HP EV", 300, 150));
        items.add(new Item("Muscle Feather", "Feather", "A feather that slightly increases Attack.", "+1 Attack EV", 300, 150));
        items.add(new Item("Resist Feather", "Feather", "A feather that slightly increases Defense.", "+1 Defense EV", 300, 150));
        items.add(new Item("Swift Feather", "Feather", "A feather that slightly increases Speed.", "+1 Speed EV", 300, 150));
        // Leveling Item
        items.add(new Item("Rare Candy", "Leveling Item", "A candy that is packed with energy.", "Increases level by 1", 4800, 2400));
        // Evolution Stones
        items.add(new Item("Fire Stone", "Evolution Stone", "A stone that radiates heat.", "Evolves Pokémon", 5000, 1500));
        items.add(new Item("Water Stone", "Evolution Stone", "A stone with a blue, watery appearance.", "Evolves Pokémon", 5000, 1500));
        items.add(new Item("Thunder Stone", "Evolution Stone", "A stone that sparkles with electricity.", "Evolves Pokémon", 5000, 1500));
        items.add(new Item("Leaf Stone", "Evolution Stone", "A stone with a leaf pattern.", "Evolves Pokémon", 5000, 1500));
        items.add(new Item("Moon Stone", "Evolution Stone", "A stone that glows faintly in the moonlight.", "Evolves Pokémon", 3000, 1500));
        items.add(new Item("Sun Stone", "Evolution Stone", "A stone that glows like the sun.", "Evolves Pokémon", 5000, 1500));
        items.add(new Item("Shiny Stone", "Evolution Stone", "A stone that sparkles brightly.", "Evolves Pokémon", 5000, 1500));
        items.add(new Item("Dusk Stone", "Evolution Stone", "A dark stone that is ominous in appearance.", "Evolves Pokémon", 5000, 1500));
        items.add(new Item("Dawn Stone", "Evolution Stone", "A stone that sparkles like the morning sky.", "Evolves Pokémon", 5000, 1500));
        items.add(new Item("Ice Stone", "Evolution Stone", "A stone that is cold to the touch.", "Evolves Pokémon", 5000, 1500));
    }

    public static List<Item> getItems() {
        if (items.isEmpty()) seedItems();
        return items;
    }

    private static void seedTrainers() {
        Trainer trainer1 = new Trainer("001", "Ash", "05/22/1990", "M", "Pallet Town",
                "The legendary Pokémon trainer.", 1_000_000);

        // Active Pokémon (5)
        Pokemon bulbasaur = new Pokemon(1, "Bulbasaur", "Grass", "Poison", 5, 0, 2, 16, 45, 49, 49, 45);
        bulbasaur.learnMove("Surf");         // HM
        bulbasaur.learnMove("Strength");     // HM
        bulbasaur.learnMove("Razor Leaf");   // TM
        bulbasaur.learnMove("Solar Beam");   // TM
        trainer1.addPokemonToLineup(bulbasaur);
        Pokemon charmeleon = new Pokemon(5, "Charmeleon", "Fire", "", 16, 4, 6, 36, 58, 64, 58, 80);
        trainer1.addPokemonToLineup(charmeleon);
        Pokemon wartortle = new Pokemon(8, "Wartortle", "Water", "", 16, 7, 9, 36, 59, 63, 80, 58);
        trainer1.addPokemonToLineup(wartortle);
        Pokemon ivysaur = new Pokemon(2, "Ivysaur", "Grass", "Poison", 16, 1, 3, 32, 60, 62, 63, 60);
        trainer1.addPokemonToLineup(ivysaur);
        Pokemon pikachu = new Pokemon(25, "Pikachu", "Electric", "", 10, 0, 26, 0, 35, 55, 40, 90);
        trainer1.addPokemonToLineup(pikachu);

        // Storage Pokémon (5)
        trainer1.getStorage().add(new Pokemon(7, "Squirtle", "Water", "", 5, 0, 8, 16, 44, 48, 65, 43));
        trainer1.getStorage().add(new Pokemon(10, "Caterpie", "Bug", "", 3, 0, 11, 7, 45, 30, 35, 45));
        trainer1.getStorage().add(new Pokemon(16, "Pidgey", "Normal", "Flying", 3, 0, 17, 18, 40, 45, 40, 56));
        trainer1.getStorage().add(new Pokemon(19, "Rattata", "Normal", "", 3, 0, 20, 20, 30, 56, 35, 72));
        trainer1.getStorage().add(new Pokemon(23, "Ekans", "Poison", "", 5, 0, 24, 22, 35, 60, 44, 55));

        // Unique Items (9)
        trainer1.addItem(new Item("HP Up", "Vitamin", "Increases HP", "+10 HP EVs", 10000, 5000));
        trainer1.addItem(new Item("Protein", "Vitamin", "Increases Attack", "+10 Attack EVs", 10000, 5000));
        trainer1.addItem(new Item("Iron", "Vitamin", "Increases Defense", "+10 Defense EVs", 10000, 5000));
        trainer1.addItem(new Item("Carbos", "Vitamin", "Increases Speed", "+10 Speed EVs", 10000, 5000));
        trainer1.addItem(new Item("Resist Feather", "Feather", "Slightly increases Defense", "+1 Defense EV", 4800, 2400));
        trainer1.addItem(new Item("Fire Stone", "Evolution Stone", "Evolves Fire Pokémon", "Evolves Pokémon", 5000, 1500));
        trainer1.addItem(new Item("Water Stone", "Evolution Stone", "Evolves Water Pokémon", "Evolves Pokémon", 5000, 1500));
        trainer1.addItem(new Item("Health Feather", "Feather", "Slightly increases HP", "+1 HP EV", 300, 150));
        trainer1.addItem(new Item("Muscle Feather", "Feather", "Slightly increases Attack", "+1 Attack EV", 300, 150));

        trainers.add(trainer1);

        // ------------------------
        // Trainer 2 to 5
        // ------------------------
        trainers.add(new Trainer("002", "Misty", "07/01/1992", "F", "Cerulean City", "Water-type Gym Leader", 800000));
        trainers.add(new Trainer("003", "Brock", "01/15/1989", "M", "Pewter City", "Rock-type Gym Leader", 900000));
        trainers.add(new Trainer("004", "Gary", "09/25/1991", "M", "Pallet Town", "Rival Trainer", 950000));
        trainers.add(new Trainer("005", "Erika", "05/10/1993", "F", "Celadon City", "Grass-type Gym Leader", 850000));

        for (int i = 1; i <= 4; i++) {
            Item item = (Item) trainer1.getInventory().keySet().toArray()[i - 1];
            trainers.get(1).addItem(item);
            trainers.get(2).addItem(item);
            trainers.get(3).addItem(item);
            trainers.get(4).addItem(item);
        }
    }

    public static List<Trainer> getTrainers() {
        if (trainers.isEmpty()) seedTrainers();
        return trainers;
    }

    // -------------------- POKÉMON --------------------
    private static void addPokemon() {
        int number;
        while (true) {
            System.out.print("Enter Pokédex Number (must be 4 digits): ");
            String input = scanner.nextLine();

            if (!input.matches("\\d{4}")) {
                System.out.println("❌ Invalid format. Please enter exactly 4 digits (e.g., 0123, 1234).");
                continue;
            }

            number = Integer.parseInt(input);

            // Check for duplicate Pokedex number
            boolean exists = false;
            for (Pokemon p : pokedex) {
                if (p.getPokedexNumber() == number) {
                    System.out.println("❌ A Pokémon with this Pokédex number already exists.");
                    exists = true;
                    break;
                }
            }
            if (!exists) break;
            System.out.println("🔁 Please enter a different Pokédex number.");
        }

        String name;
        while (true) {
            System.out.print("Enter Pokémon Name: ");
            name = scanner.nextLine();

            boolean exists = false;
            for (Pokemon p : pokedex) {
                if (p.getName().equalsIgnoreCase(name)) {
                    System.out.println("❌ A Pokémon with this name already exists.");
                    exists = true;
                    break;
                }
            }

            if (!exists) break;
            System.out.println("🔁 Please enter a different name.");
        }

        System.out.print("Enter Type 1: ");
        String type1 = scanner.nextLine();

        System.out.print("Enter Type 2 (or press enter if none): ");
        String type2 = scanner.nextLine();

        System.out.print("Enter Base Level: ");
        int baseLevel = scanner.nextInt();

        System.out.print("Enter Evolves From (Pokédex #, 0 if none): ");
        int evolvesFrom = scanner.nextInt();

        System.out.print("Enter Evolves To (Pokédex #, 0 if none): ");
        int evolvesTo = scanner.nextInt();

        System.out.print("Enter Evolution Level: ");
        int evolutionLevel = scanner.nextInt();

        System.out.print("Enter Base HP: ");
        int hp = scanner.nextInt();

        System.out.print("Enter Base Attack: ");
        int attack = scanner.nextInt();

        System.out.print("Enter Base Defense: ");
        int defense = scanner.nextInt();

        System.out.print("Enter Base Speed: ");
        int speed = scanner.nextInt();
        scanner.nextLine();

        // Create and add the Pokémon
        Pokemon p = new Pokemon(number, name, type1, type2, baseLevel,
                evolvesFrom, evolvesTo, evolutionLevel,
                hp, attack, defense, speed);

        pokedex.add(p);
        System.out.println("✅ Pokémon added!");
    }

    private static void viewAllPokemon() {
        if (pokedex.isEmpty()) {
            System.out.println("No Pokémon in database.");
            return;
        }

        System.out.println("\nSort Pokémon by:");
        System.out.println("[1] Name");
        System.out.println("[2] Pokédex Number");
        System.out.println("[3] Type 1");
        System.out.println("[4] Type 2");
        System.out.println("[5] HP");
        System.out.println("[6] Attack");
        System.out.println("[7] Defense");
        System.out.println("[8] Speed");
        System.out.println("[9] Base Level");
        System.out.println("[10] Evolution Level");
        System.out.print("Enter sorting option: ");
        String choice = scanner.nextLine();

        switch (choice) {
            case "1" -> pokedex.sort(Comparator.comparing(Pokemon::getName, String.CASE_INSENSITIVE_ORDER));
            case "2" -> pokedex.sort(Comparator.comparingInt(Pokemon::getPokedexNumber));
            case "3" -> pokedex.sort(Comparator.comparing(Pokemon::getType1, String.CASE_INSENSITIVE_ORDER));
            case "4" -> pokedex.sort(Comparator.comparing(
                    p -> p.getType2() != null ? p.getType2().toLowerCase() : "zzz"));
            case "5" -> pokedex.sort(Comparator.comparingInt(Pokemon::getHp));
            case "6" -> pokedex.sort(Comparator.comparingInt(Pokemon::getAttack));
            case "7" -> pokedex.sort(Comparator.comparingInt(Pokemon::getDefense));
            case "8" -> pokedex.sort(Comparator.comparingInt(Pokemon::getSpeed));
            case "9" -> pokedex.sort(Comparator.comparingInt(Pokemon::getBaseLevel));
            case "10" -> pokedex.sort(Comparator.comparingInt(Pokemon::getEvolutionLevel));
            default -> {
                System.out.println("❌ Invalid sorting option. Showing unsorted list.");
            }
        }

        System.out.printf("%-6s %-15s %-10s %-10s %-5s %-12s %-12s %-8s %-5s %-5s %-5s %-5s %-25s %-15s\n",
                "Dex#", "Name", "Type 1", "Type 2", "Lvl", "EvolvesFrom", "EvolvesTo",
                "EvoLvl", "HP", "ATK", "DEF", "SPD", "Move Set", "Held Item");
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------");

        for (Pokemon p : pokedex) {
            System.out.printf("%04d   %-15s %-10s %-10s %-5d %-12s %-12s %-8d %-5d %-5d %-5d %-5d %-25s %-15s\n",
                    p.getPokedexNumber(),
                    p.getName(),
                    p.getType1(),
                    (p.getType2() != null ? p.getType2() : "None"),
                    p.getBaseLevel(),
                    (p.getEvolvesFrom() > 0 ? String.format("#%04d", p.getEvolvesFrom()) : "None"),
                    (p.getEvolvesTo() > 0 ? String.format("#%04d", p.getEvolvesTo()) : "None"),
                    p.getEvolutionLevel(),
                    p.getHp(), p.getAttack(), p.getDefense(), p.getSpeed(),
                    String.join(", ", p.getMoveSet()),
                    (p.getHeldItem() != null ? p.getHeldItem() : "None"));
        }
    }

    private static void searchPokemon() {
        System.out.print("Search Pokémon by Name or Type: ");
        String query = scanner.nextLine().toLowerCase();
        boolean found = false;

        System.out.printf("%-6s %-15s %-10s %-10s %-5s %-12s %-12s %-8s %-5s %-5s %-5s %-5s %-25s %-15s\n",
                "Dex#", "Name", "Type 1", "Type 2", "Lvl", "EvolvesFrom", "EvolvesTo",
                "EvoLvl", "HP", "ATK", "DEF", "SPD", "Move Set", "Held Item");
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------");

        for (Pokemon p : pokedex) {
            if (p.getName().toLowerCase().contains(query) ||
                    p.getType1().toLowerCase().contains(query) ||
                    (p.getType2() != null && p.getType2().toLowerCase().contains(query))) {

                System.out.printf("%04d   %-15s %-10s %-10s %-5d %-12s %-12s %-8d %-5d %-5d %-5d %-5d %-25s %-15s\n",
                        p.getPokedexNumber(),
                        p.getName(),
                        p.getType1(),
                        (p.getType2() != null ? p.getType2() : "None"),
                        p.getBaseLevel(),
                        (p.getEvolvesFrom() > 0 ? String.format("#%04d", p.getEvolvesFrom()) : "None"),
                        (p.getEvolvesTo() > 0 ? String.format("#%04d", p.getEvolvesTo()) : "None"),
                        p.getEvolutionLevel(),
                        p.getHp(), p.getAttack(), p.getDefense(), p.getSpeed(),
                        String.join(", ", p.getMoveSet()),
                        (p.getHeldItem() != null ? p.getHeldItem() : "None"));
                found = true;
            }
        }
        if (!found) System.out.println("❌ No Pokémon found.");
    }

    // -------------------- MOVES --------------------
    private static void addMove() {
        System.out.print("Enter Move Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Description: ");
        String description = scanner.nextLine();
        System.out.print("Enter Classification (HM/TM): ");
        String classification = scanner.nextLine();
        System.out.print("Enter Type 1: ");
        String type1 = scanner.nextLine();
        System.out.print("Enter Type 2 (or press enter if none): ");
        String type2 = scanner.nextLine();

        moves.add(new Move(name, description, classification, type1, type2));
        System.out.println("✅ Move added!");
    }

    private static void viewAllMoves() {
        if (moves.isEmpty()) {
            System.out.println("No moves found.");
            return;
        }

        System.out.println("\nSort Moves by:");
        System.out.println("[1] Name");
        System.out.println("[2] Classification");
        System.out.println("[3] Type 1");
        System.out.println("[4] Type 2");
        System.out.print("Enter sorting option: ");
        String choice = scanner.nextLine();

        switch (choice) {
            case "1" -> moves.sort(Comparator.comparing(Move::getName, String.CASE_INSENSITIVE_ORDER));
            case "2" -> moves.sort(Comparator.comparing(Move::getClassification, String.CASE_INSENSITIVE_ORDER));
            case "3" -> moves.sort(Comparator.comparing(Move::getType1, String.CASE_INSENSITIVE_ORDER));
            case "4" -> moves.sort(Comparator.comparing(
                    m -> m.getType2() != null ? m.getType2().toLowerCase() : "zzz")); // sort nulls last
            default -> System.out.println("❌ Invalid sorting option. Showing unsorted list.");
        }

        System.out.printf("%-20s %-8s %-10s %-10s %-40s\n",
                "Name", "Class", "Type 1", "Type 2", "Description");
        System.out.println("-----------------------------------------------------------------------------------------------");

        for (Move m : moves) {
            System.out.printf("%-20s %-8s %-10s %-10s %-40s\n",
                    m.getName(),
                    m.getClassification(),
                    m.getType1(),
                    (m.getType2() != null ? m.getType2() : "None"),
                    m.getDescription());
        }
    }

    private static void searchMoves() {
        System.out.print("Search Move by Name or Type: ");
        String query = scanner.nextLine().toLowerCase();
        boolean found = false;

        System.out.printf("%-20s %-8s %-10s %-10s %-40s\n",
                "Name", "Class", "Type 1", "Type 2", "Description");
        System.out.println("-----------------------------------------------------------------------------------------------");

        for (Move m : moves) {
            if (m.getName().toLowerCase().contains(query) ||
                    m.getType1().toLowerCase().contains(query) ||
                    (m.getType2() != null && m.getType2().toLowerCase().contains(query))) {

                System.out.printf("%-20s %-8s %-10s %-10s %-40s\n",
                        m.getName(),
                        m.getClassification(),
                        m.getType1(),
                        (m.getType2() != null ? m.getType2() : "None"),
                        m.getDescription());
                found = true;
            }
        }

        if (!found) System.out.println("❌ No move found.");
    }

    // -------------------- ITEMS --------------------
    private static void viewAllItems() {
        if (items.isEmpty()) {
            System.out.println("No items found.");
            return;
        }

        System.out.println("\nView All Items by:");
        System.out.println("[1] Name");
        System.out.println("[2] Category");
        System.out.println("[3] Buy Price");
        System.out.println("[4] Sell Price");
        System.out.print("Enter sorting option: ");
        String choice = scanner.nextLine();

        switch (choice) {
            case "1" -> items.sort(Comparator.comparing(Item::getName, String.CASE_INSENSITIVE_ORDER));
            case "2" -> items.sort(Comparator.comparing(Item::getCategory, String.CASE_INSENSITIVE_ORDER));
            case "3" -> items.sort(Comparator.comparingInt(Item::getBuyPrice));
            case "4" -> items.sort(Comparator.comparingInt(Item::getSellPrice));
            default -> {
                System.out.println("❌ Invalid sorting option. Showing unsorted list.");
            }
        }

        System.out.printf("%-20s %-15s %-30s %-50s %-10s %-10s\n",
                "Name", "Category", "Effect", "Description", "Buy", "Sell");
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------");

        for (Item item : items) {
            System.out.printf("%-20s %-15s %-30s %-50s ₱%-10d ₱%-10d\n",
                    item.getName(),
                    item.getCategory(),
                    item.getEffect(),
                    item.getDescription(),
                    item.getBuyPrice(),
                    item.getSellPrice()
            );
        }
    }

    private static void searchItems() {
        System.out.print("Search Item by Name or Category: ");
        String query = scanner.nextLine().toLowerCase();
        boolean found = false;

        System.out.printf("%-20s %-15s %-25s %-10s %-10s %-40s\n",
                "Name", "Category", "Effect", "Buy ₽", "Sell ₽", "Description");
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------");

        for (Item i : items) {
            if (i.getName().toLowerCase().contains(query) ||
                    i.getCategory().toLowerCase().contains(query)) {

                System.out.printf("%-20s %-15s %-25s %-10d %-10d %-40s\n",
                        i.getName(),
                        i.getCategory(),
                        i.getEffect(),
                        i.getBuyPrice(),
                        i.getSellPrice(),
                        i.getDescription());
                found = true;
            }
        }

        if (!found) System.out.println("❌ No item found.");
    }

    private static void addTrainer() {
        System.out.print("Enter Trainer ID: ");
        String id = scanner.nextLine();

        // Check if ID already exists
        for (Trainer t : trainers) {
            if (t.getTrainerId().equalsIgnoreCase(id)) {
                System.out.println("❌ Trainer ID already exists.");
                return;
            }
        }

        System.out.print("Enter Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Birthdate (MM/DD/YYYY): ");
        String birthdate = scanner.nextLine();

        System.out.print("Enter Sex (M/F): ");
        String sex = scanner.nextLine();

        System.out.print("Enter Hometown: ");
        String hometown = scanner.nextLine();

        System.out.print("Enter Description: ");
        String description = scanner.nextLine();

        int money = 1_000_000;

        trainers.add(new Trainer(id, name, birthdate, sex, hometown, description, money));
        System.out.println("✅ Trainer added successfully with ₱1,000,000.00(PkD) starting funds!");
    }

    private static void viewAllTrainers() {
        if (trainers.isEmpty()) {
            System.out.println("❌ No trainers found.");
            return;
        }

        System.out.println("\nSort Trainers by:");
        System.out.println("[1] Name");
        System.out.println("[2] Birthdate");
        System.out.println("[3] Sex");
        System.out.println("[4] Hometown");
        System.out.println("[5] Money");
        System.out.print("Enter sorting option: ");
        String option = scanner.nextLine();

        switch (option) {
            case "1" -> trainers.sort(Comparator.comparing(Trainer::getName, String.CASE_INSENSITIVE_ORDER));
            case "2" -> trainers.sort(Comparator.comparing(Trainer::getBirthdate)); // format assumed MM/DD/YYYY
            case "3" -> trainers.sort(Comparator.comparing(Trainer::getSex, String.CASE_INSENSITIVE_ORDER));
            case "4" -> trainers.sort(Comparator.comparing(Trainer::getHometown, String.CASE_INSENSITIVE_ORDER));
            case "5" -> trainers.sort(Comparator.comparingInt(Trainer::getMoney));
            default -> System.out.println("❌ Invalid sorting option. Showing unsorted list.");
        }

        System.out.printf("%-8s %-15s %-12s %-6s %-15s %-25s %-8s\n",
                "ID", "Name", "Birthdate", "Sex", "Hometown", "Description", "Money");
        System.out.println("------------------------------------------------------------------------------------------------");

        for (Trainer t : trainers) {
            System.out.printf("%-8s %-15s %-12s %-6s %-15s %-25s %-8d\n",
                    t.getTrainerId(),
                    t.getName(),
                    t.getBirthdate(),
                    t.getSex(),
                    t.getHometown(),
                    t.getDescription(),
                    t.getMoney());
        }
    }

    private static void searchTrainer() {
        System.out.print("Search Trainer: ");
        String query = scanner.nextLine().toLowerCase();
        boolean found = false;

        System.out.printf("%-8s %-15s %-12s %-6s %-15s %-25s %-8s\n",
                "ID", "Name", "Birthdate", "Sex", "Hometown", "Description", "Money");
        System.out.println("------------------------------------------------------------------------------------------------");

        for (Trainer t : trainers) {
            if (t.getName().toLowerCase().contains(query) ||
                    t.getTrainerId().toLowerCase().contains(query) ||
                    t.getHometown().toLowerCase().contains(query)) {

                System.out.printf("%-8s %-15s %-12s %-6s %-15s %-25s %-8d\n",
                        t.getTrainerId(),
                        t.getName(),
                        t.getBirthdate(),
                        t.getSex(),
                        t.getHometown(),
                        t.getDescription(),
                        t.getMoney());
                found = true;
            }
        }

        if (!found) {
            System.out.println("❌ No matching trainer found.");
        }
    }

}
