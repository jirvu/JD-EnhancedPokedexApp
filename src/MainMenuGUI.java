import javax.swing.*;
import java.awt.*;

public class MainMenuGUI extends JFrame {

    public MainMenuGUI() {
        setTitle("Enhanced Pokedex - Main Menu");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 1, 10, 10));

        JButton pokemonBtn = new JButton("Pokémon Management");
        JButton movesBtn = new JButton("Moves Management");
        JButton itemsBtn = new JButton("Items Management");
        JButton trainersBtn = new JButton("Trainer Management");
        JButton exitBtn = new JButton("Exit");

        add(pokemonBtn);
        add(movesBtn);
        add(itemsBtn);
        add(trainersBtn);
        add(exitBtn);

        pokemonBtn.addActionListener(e -> new PokemonGUI(PokedexApp.getSeededPokemon()));
        movesBtn.addActionListener(e -> new MovesGUI(PokedexApp.getMoves()));
        itemsBtn.addActionListener(e -> new ItemsGUI(PokedexApp.getItems()));
        trainersBtn.addActionListener(e -> new TrainersGUI(PokedexApp.getTrainers()));
        exitBtn.addActionListener(e -> System.exit(0));

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoadingScreen::showLoadingThenMenu);
    }
}
