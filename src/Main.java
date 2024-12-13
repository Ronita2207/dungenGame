import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class Main {

    JFrame displayZoneFrame;
    RenderEngine renderEngine;
    GameEngine gameEngine;
    PhysicEngine physicEngine;

    private int timeLeft = 300; // Timer: 5 minutes (300 seconds)

    public Main() throws Exception {
        displayZoneFrame = new JFrame("Java Labs");
        displayZoneFrame.setSize(400, 600);
        displayZoneFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Hero setup
        DynamicSprite hero = new DynamicSprite(200, 300,
                ImageIO.read(new File("./img/heroTileSheetLowRes.png")), 48, 50);

        // Engines
        renderEngine = new RenderEngine(displayZoneFrame);
        physicEngine = new PhysicEngine();
        gameEngine = new GameEngine(hero);

        // Load Playground level
        Playground level = new Playground("./data/level1.txt");

        // Get the ExitSprite
        ExitSprite exit = level.getExit();

        // Add elements to RenderEngine and PhysicEngine
        renderEngine.addToRenderList(level.getSpriteList());
        renderEngine.addToRenderList(hero);
        if (exit != null) renderEngine.addToRenderList(exit); // Add exit sprite for rendering
        physicEngine.addToMovingSpriteList(hero);
        physicEngine.setEnvironment(level.getSolidSpriteList(), exit);

        // Timers for the game loop
        Timer renderTimer = new Timer(50, (time) -> renderEngine.update());
        Timer gameTimer = new Timer(50, (time) -> gameEngine.update());
        Timer physicTimer = new Timer(50, (time) -> physicEngine.update());

        renderTimer.start();
        gameTimer.start();
        physicTimer.start();

        // Countdown Timer
        Timer countdownTimer = new Timer(1000, (e) -> {
            timeLeft--;
            renderEngine.setTimeLeft(timeLeft);
            if (timeLeft <= 0) {
                JOptionPane.showMessageDialog(displayZoneFrame, "Game Over! Time's up.");
                System.exit(0); // End the game
            }
        });
        countdownTimer.start();

        displayZoneFrame.addKeyListener(gameEngine);
        displayZoneFrame.getContentPane().add(renderEngine);
        displayZoneFrame.setVisible(true);
    }

    public static void main(String[] args) throws Exception {
        new Main();
    }
}
