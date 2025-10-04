import java.util.ArrayList;
import java.util.List;

public class Pokemon {
    private int pokedexNumber;
    private String name;
    private String type1;
    private String type2; // optional
    private int baseLevel;

    private int evolvesFrom; // Pokédex number of pre-evolution
    private int evolvesTo;   // Pokédex number of next evolution
    private int evolutionLevel;

    private int hp;
    private int attack;
    private int defense;
    private int speed;

    private List<String> moveSet; // Max 4
    private String heldItem;

    public Pokemon(
            int pokedexNumber, String name,
            String type1, String type2,
            int baseLevel,
            int evolvesFrom, int evolvesTo, int evolutionLevel,
            int hp, int attack, int defense, int speed
    ) {
        this.pokedexNumber = pokedexNumber;
        this.name = name;
        this.type1 = type1;
        this.type2 = type2.isEmpty() ? null : type2;
        this.baseLevel = baseLevel;

        this.evolvesFrom = evolvesFrom;
        this.evolvesTo = evolvesTo;
        this.evolutionLevel = evolutionLevel;

        this.hp = hp;
        this.attack = attack;
        this.defense = defense;
        this.speed = speed;

        this.moveSet = new ArrayList<>();
        this.moveSet.add("Tackle");
        this.moveSet.add("Defend");

        this.heldItem = null;
    }

    // Simulated cry method
    public void cry() {
        System.out.println(name + "!!!");
    }

    // Add move (max 4)
    public boolean learnMove(String move) {
        if (moveSet.size() >= 4) {
            System.out.println(name + " already knows 4 moves. Forget a move to learn a new one.");
            return false;
        }
        moveSet.add(move);
        return true;
    }

    // Forget a move
    public boolean forgetMove(String move) {
        if (moveSet.contains(move) && !move.equals("Tackle") && !move.equals("Defend")) {
            moveSet.remove(move);
            return true;
        }
        return false;
    }

    // Level Up
    public void levelUp() {
        this.baseLevel++;
        checkEvolution();
        // Check for evolution
        if (evolvesTo > 0 && baseLevel >= evolutionLevel) {
            evolve();
        }
    }

    // Check Evolution
    private void checkEvolution() {
        if (evolvesTo > 0 && baseLevel >= evolutionLevel) {
            evolve();
        }
    }

    // Evolution
    private void evolve() {
        Pokemon evolvedForm = PokedexApp.getSeededPokemon().stream()
                .filter(p -> p.getPokedexNumber() == evolvesTo)
                .findFirst()
                .orElse(null);

        if (evolvedForm != null) {
            System.out.println(this.name + " evolved into " + evolvedForm.getName() + "!");
            this.name = evolvedForm.getName();
            this.pokedexNumber = evolvedForm.getPokedexNumber();
            this.type1 = evolvedForm.getType1();
            this.type2 = evolvedForm.getType2();
            this.hp = evolvedForm.getHp();
            this.attack = evolvedForm.getAttack();
            this.defense = evolvedForm.getDefense();
            this.speed = evolvedForm.getSpeed();
            this.evolvesTo = evolvedForm.getEvolvesTo();
            this.evolutionLevel = evolvedForm.getEvolutionLevel();
        }
    }

    public void evolveWithStone() {
        if (evolvesTo > 0) {
            Pokemon evolvedForm = PokedexApp.getSeededPokemon().stream()
                    .filter(p -> p.getPokedexNumber() == evolvesTo)
                    .findFirst()
                    .orElse(null);

            if (evolvedForm != null) {
                System.out.println(name + " evolved into " + evolvedForm.getName() + " using a stone!");

                // Update stats and data
                this.name = evolvedForm.getName();
                this.pokedexNumber = evolvedForm.getPokedexNumber();
                this.type1 = evolvedForm.getType1();
                this.type2 = evolvedForm.getType2();
                this.hp = evolvedForm.getHp();
                this.attack = evolvedForm.getAttack();
                this.defense = evolvedForm.getDefense();
                this.speed = evolvedForm.getSpeed();
                this.evolvesTo = evolvedForm.getEvolvesTo();
                this.evolutionLevel = evolvedForm.getEvolutionLevel();

                // Ensure Pokémon reaches its evolution level
                if (this.baseLevel < evolvedForm.getBaseLevel()) {
                    this.baseLevel = evolvedForm.getBaseLevel();
                }
            }
        } else {
            System.out.println(name + " cannot evolve with a stone.");
        }
    }

    // Add Stat Setters
    public void setHp(int hp) {
        this.hp = Math.max(0, hp); // Prevent negative HP
    }

    public void setAttack(int attack) {
        this.attack = Math.max(0, attack);
    }

    public void setDefense(int defense) {
        this.defense = Math.max(0, defense);
    }

    public void setSpeed(int speed) {
        this.speed = Math.max(0, speed);
    }

    public void setBaseLevel(int baseLevel) {
        this.baseLevel = baseLevel;
        checkEvolution();
    }

    // Set held item (replaces existing)
    public void setHeldItem(String item) {
        this.heldItem = item;
    }

    // Getters (for search/display)
    public String getName()     { return name; }
    public String getType1()    { return type1; }
    public String getType2()    { return type2; }
    public int getPokedexNumber() { return pokedexNumber; }
    public int getBaseLevel() { return baseLevel; }
    public int getHp()        { return hp; }
    public int getAttack()    { return attack; }
    public int getDefense()   { return defense; }
    public int getSpeed()     { return speed; }
    public int getEvolutionLevel()  { return evolutionLevel; }
    public int getEvolvesFrom()     { return evolvesFrom; }
    public int getEvolvesTo()       { return evolvesTo; }
    public List<String> getMoveSet() { return moveSet; }
    public String getHeldItem() { return heldItem; }

    // Display Summary
    @Override
    public String toString() {
        return String.format(
                "#%04d: %s [%s%s] Lv.%d\nStats - HP: %d | ATK: %d | DEF: %d | SPD: %d\nEvolves from #%03d to #%03d @ Lv.%d\nMoves: %s\nHeld Item: %s\n",
                pokedexNumber, name, type1, (type2 != null ? "/" + type2 : ""), baseLevel,
                hp, attack, defense, speed,
                evolvesFrom, evolvesTo, evolutionLevel,
                String.join(", ", moveSet),
                (heldItem != null ? heldItem : "None")
        );
    }
}
