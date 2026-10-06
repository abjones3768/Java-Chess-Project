import src.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class chess
{

    //Board board = new Board();
    public static void main(String[] args)
    {
        //System.out.println(board.boardData[0][0]);
        //System.out.println(board.boardData[3][3]);
        //int coord1[] = {0, 0};
        //int coord2[] = {3, 3};
        //board.movePiece(coord1, coord2);
        //System.out.println(board.boardData[0][0]);
        //System.out.println(board.boardData[3][3]);
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