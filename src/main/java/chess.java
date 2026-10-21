import java.io.File;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;


public class chess extends Application
{
@Override
public void start(Stage stage)
{
    Image boardImage = new Image(getClass().getResourceAsStream("/Chess_Board.png")); // Find image in resources folder
    ImageView imageView = new ImageView(boardImage); // Create an ImageView to display the board image

    StackPane pane = new StackPane(); // Create a StackPane to hold the board image and pieces
    pane.getChildren().add(imageView); // Add the board image to the StackPane
    Pane pieceLayer = new Pane(); // Create a Pane to hold the pieces on top of the board
    pieceLayer.setPrefSize(boardImage.getWidth(), boardImage.getHeight()); // Set the size of the piece layer to match the board image
    pane.getChildren().add(pieceLayer); // Add the piece layer to the StackPane
    Scene scene = new Scene(pane, boardImage.getWidth(), boardImage.getHeight());  // Set the scene size to match the board image size

    Image pawnImage = new Image(getClass().getResourceAsStream("/White_Pawn.png")); // Find image in resources folder
    ImageView pawnImageView = new ImageView(pawnImage);

    double squareSize = boardImage.getWidth() / 8;

    int row = 6; // Row index for the pawn (0-based, so row 6 is the second-to-last row)
    int col = 2; // Column index for the pawn (0-based)

    double x = col * squareSize; // Calculate the X position based on the column index
    double y = row * squareSize; // Calculate the Y position based on the row index

    pawnImageView.setLayoutX(x); // Set the X position of the pawn image
    pawnImageView.setLayoutY(y); // Set the Y position of the pawn image

    pieceLayer.getChildren().add(pawnImageView); // Add the pawn image to the piece layer

    pawnImageView.setFitWidth(squareSize);
    pawnImageView.setFitHeight(squareSize);
    pawnImageView.setPreserveRatio(true);

    stage.setScene(scene); // Set the scene on the stage
    stage.setTitle("Chess Game"); // Set the title of the window
    stage.show(); // Show the stage
}
    //Board board = new Board();
    public static void main(String[] args)
    {
        launch(args); // Launch the JavaFX application
    }
}



/*         //System.out.println(board.boardData[0][0]);
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
        }); */