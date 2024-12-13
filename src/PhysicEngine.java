import java.util.ArrayList;
import javax.swing.JOptionPane;

public class PhysicEngine implements Engine {
    private ArrayList<DynamicSprite> movingSpriteList;
    private ArrayList<Sprite> environment;
    private ExitSprite exit; // Reference to the ExitSprite (winning condition)

    public PhysicEngine() {
        movingSpriteList = new ArrayList<>();
        environment = new ArrayList<>();
    }

    public void addToEnvironmentList(Sprite sprite) {
        if (sprite != null && !environment.contains(sprite)) {
            environment.add(sprite);
        }
    }

    public void setEnvironment(ArrayList<Sprite> environment, ExitSprite exit) {
        if (environment != null) {
            this.environment = environment;
        }
        this.exit = exit; // Set the exit point
        if (exit == null) {
            System.out.println("Warning: ExitSprite is null. Check your level configuration.");
        }
    }

    public void addToMovingSpriteList(DynamicSprite sprite) {
        if (sprite != null && !movingSpriteList.contains(sprite)) {
            movingSpriteList.add(sprite);
        }
    }

    @Override
    public void update() {
        for (DynamicSprite dynamicSprite : movingSpriteList) {
            dynamicSprite.moveIfPossible(environment);

            // Check if the hero has reached the exit
            if (exit != null && dynamicSprite.getHitBox().intersects(exit.getHitBox())) {
                System.out.println("Hero reached the exit! Ending the game...");
                JOptionPane.showMessageDialog(null, "Congratulations! You escaped the maze!");
                System.exit(0); // End the game
            }
        }
    }
}
