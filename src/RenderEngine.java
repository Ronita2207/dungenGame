import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class RenderEngine extends JPanel implements Engine {
    private ArrayList<Displayable> renderList;
    private int timeLeft = 0; // For displaying countdown timer

    public RenderEngine(JFrame jFrame) {
        renderList = new ArrayList<>();
    }

    public void setTimeLeft(int timeLeft) {
        this.timeLeft = timeLeft;
        this.repaint();
    }

    public void addToRenderList(Displayable displayable) {
        if (displayable != null && !renderList.contains(displayable)) {
            renderList.add(displayable);
        }
    }

    public void addToRenderList(ArrayList<Displayable> displayable) {
        for (Displayable d : displayable) {
            if (d != null && !renderList.contains(d)) {
                renderList.add(d);
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // Render all displayable objects
        for (Displayable renderObject : renderList) {
            if (renderObject != null) {
                renderObject.draw(g);
            }
        }

        // Draw the enhanced countdown timer
        drawEnhancedTimer(g);
    }

    /**
     * Draws an enhanced timer with dynamic color and a semi-transparent background.
     *
     * @param g The Graphics object used for drawing.
     */
    private void drawEnhancedTimer(Graphics g) {
        // Timer background: semi-transparent black rectangle
        g.setColor(new Color(0, 0, 0, 150)); // ARGB: 150 transparency
        g.fillRoundRect(10, 10, 180, 50, 15, 15); // Rounded rectangle for style

        // Dynamic color for the timer text
        if (timeLeft > 60) {
            g.setColor(Color.GREEN); // Green when time > 60 seconds
        } else if (timeLeft > 30) {
            g.setColor(Color.ORANGE); // Orange when time <= 60 but > 30
        } else {
            g.setColor(Color.RED); // Red when time <= 30 seconds
        }

        // Timer text style
        g.setFont(new Font("Arial", Font.BOLD, 22));
        g.drawString("Time Left: " + timeLeft + "s", 20, 40);
    }

    @Override
    public void update() {
        this.repaint();
    }
}
