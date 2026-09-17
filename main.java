import src.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;

public class main
{
    public static void main()
    {
        Board thing = new Board();

SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Auto-Scale Demo");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            
            // 1. By default, JFrame content pane uses BorderLayout
            JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
            panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            
            // 2. Add some components
            panel.add(new JLabel("This window sizes itself automatically!", SwingConstants.CENTER));

            frame.add(panel);
            
            // 3. CRITICAL: Automatically scale the frame around the elements
            frame.pack();
            
            // Center the window on the user's screen after packing
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

});
    }
}