import java.awt.*;

public class ExitSprite extends SolidSprite {
    public ExitSprite(double x, double y, Image image, double width, double height) {
        super(x, y, image, width, height);
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(image, (int) x, (int) y, (int) width, (int) height, null);
    }
}
