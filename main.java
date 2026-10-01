import src.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class main
{
    public static void main()
    {
        Board thing = new Board();
        SwingUtilities.invokeLater(() -> {
            try 
            {
                File file = new File("src/Chess_Board.png");
                BufferedImage image = ImageIO.read(file);

                ImageIcon imageIcon = new ImageIcon(image);
                JLabel imageLabel = new JLabel(imageIcon);
                
                // 3. Set up the JFrame
                JFrame frame = new JFrame("Image Display");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                
                // Use a proper layout manager (FlowLayout works well for a simple image)
                frame.setLayout(new FlowLayout());
                frame.add(imageLabel);
                
                // 4. Automatically size the frame around its components
                frame.pack();
                
                // Center the window on the screen and display it
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
            catch (IOException e) 
            {
                System.err.println("Error: Could not load the image file.");
                e.printStackTrace();
            }
        });
    }
}