import java.io.IOException; // Add this import
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Playground {
    private ArrayList<Sprite> environment = new ArrayList<>();
    private ExitSprite exit;

    public Playground(String pathName) {
        try {
            final Image imageTree = ImageIO.read(new File("./img/tree.png"));
            final Image imageGrass = ImageIO.read(new File("./img/grass.png"));
            final Image imageRock = ImageIO.read(new File("./img/rock.png"));
            final Image imageExit = createPlaceholderImage();

            BufferedReader bufferedReader = new BufferedReader(new FileReader(pathName));
            String line;
            int lineNumber = 0;

            while ((line = bufferedReader.readLine()) != null) {
                for (int col = 0; col < line.length(); col++) {
                    char element = line.charAt(col);
                    double x = col * 64;
                    double y = lineNumber * 64;

                    switch (element) {
                        case 'T' -> environment.add(new SolidSprite(x, y, imageTree, 64, 64));
                        case ' ' -> environment.add(new Sprite(x, y, imageGrass, 64, 64));
                        case 'R' -> environment.add(new SolidSprite(x, y, imageRock, 64, 64));
                        case '.' -> exit = new ExitSprite(x, y, imageExit, 64, 64);
                    }
                }
                lineNumber++;
            }
        } catch (IOException e) {
            System.err.println("Error loading images: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Error loading playground: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private Image createPlaceholderImage() {
        BufferedImage placeholder = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB); // Create a 64x64 transparent image
        Graphics2D g2 = placeholder.createGraphics();//Get the graphics context to draw
        g2.setColor(Color.RED);//Set the color to RED for the background
        g2.fillRect(0, 0, 64, 64);// Draw a filled rectangle that covers the entire 64x64 area
        g2.setColor(Color.BLACK);// Set the color to BLACK for the text
        g2.drawString("EXIT", 10, 32);
        g2.dispose();// Release resources
        return placeholder;
    }

    public ArrayList<Sprite> getSolidSpriteList() {
        return environment;
    }

    public ArrayList<Displayable> getSpriteList() {
        ArrayList<Displayable> list = new ArrayList<>(environment);
        if (exit != null) list.add(exit);
        return list;
    }

    public ExitSprite getExit() {
        return exit;
    }
}
