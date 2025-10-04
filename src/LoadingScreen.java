import javax.swing.*;
import java.awt.*;

public class LoadingScreen extends JWindow {
    private JProgressBar progressBar;
    private JButton startButton;
    private JButton exitButton;
    private JPanel buttonPanel;

    public LoadingScreen() {
        JPanel content = (JPanel) getContentPane();
        content.setBackground(Color.WHITE);
        content.setLayout(new BorderLayout(10, 10));

        // Logo
        JLabel logoLabel = new JLabel();
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        ImageIcon icon = new ImageIcon("pokeball.png");
        Image img = icon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        logoLabel.setIcon(new ImageIcon(img));

        // Title
        JLabel title = new JLabel("Loading Enhanced Pokedex...", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        // Progress Bar
        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);

        // Buttons
        startButton = new JButton("Start");
        exitButton = new JButton("Exit");
        startButton.setEnabled(false); // disabled until load completes
        startButton.setVisible(false); // hidden until fade-in
        exitButton.setVisible(false);

        buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(startButton);
        buttonPanel.add(exitButton);

        // Layout
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(progressBar, BorderLayout.NORTH);
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);

        content.add(logoLabel, BorderLayout.NORTH);
        content.add(title, BorderLayout.CENTER);
        content.add(bottomPanel, BorderLayout.SOUTH);

        // Size & Center
        int width = 400;
        int height = 250;
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screen.width - width) / 2, (screen.height - height) / 2, width, height);

        // Button actions
        startButton.addActionListener(e -> {
            setVisible(false);
            dispose();
            new MainMenuGUI(); // show main menu
        });

        exitButton.addActionListener(e -> System.exit(0));
    }

    public void showSplashThenMenu() {
        setVisible(true);

        new Thread(() -> {
            for (int i = 0; i <= 100; i++) {
                try {
                    Thread.sleep(30);
                } catch (InterruptedException ignored) {}
                final int progress = i;
                SwingUtilities.invokeLater(() -> progressBar.setValue(progress));
            }

            SwingUtilities.invokeLater(() -> {
                progressBar.setString("Loading Complete!");
                fadeInButtons();
            });
        }).start();
    }

    private void fadeInButtons() {
        startButton.setVisible(true);
        exitButton.setVisible(true);

        // Simulate fade-in effect
        new Thread(() -> {
            float opacity = 0f;
            while (opacity < 1f) {
                opacity += 0.1f;
                final float currentOpacity = opacity;
                SwingUtilities.invokeLater(() -> {
                    startButton.setOpaque(true);
                    exitButton.setOpaque(true);
                    startButton.setBackground(new Color(0, 120, 215, (int) (255 * currentOpacity)));
                    exitButton.setBackground(new Color(220, 0, 0, (int) (255 * currentOpacity)));
                });
                try {
                    Thread.sleep(50);
                } catch (InterruptedException ignored) {}
            }
            startButton.setEnabled(true);
        }).start();
    }

    public static void showLoadingThenMenu() {
        LoadingScreen splash = new LoadingScreen();
        splash.showSplashThenMenu();
    }
}
