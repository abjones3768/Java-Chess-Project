import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class chess extends Application
{
    // private ImageView[][] boardPieces = {{'R', 'N', 'B', 'Q', 'K', 'B', 'N', 'R'},
    //                                 {'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P'},
    //                                 {'.', '.', '.', '.', '.', '.', '.', '.'},
    //                                 {'.', '.', '.', '.', '.', '.', '.', '.'},
    //                                 {'.', '.', '.', '.', '.', '.', '.', '.'},
    //                                 {'.', '.', '.', '.', '.', '.', '.', '.'},
    //                                 {'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P'},
    //                                 {'R', 'N', 'B', 'Q', 'K', 'B', 'N', 'R'}};
    /*
    Create an 8x8 array of ImageViews, corresponding to the default board layout.
    To populate the board, double for loop through the array and do the 
    x = col * squareSize math stuff to populate them in the correct positions.
    When any given ImageView is clicked, take the coordinates of the clicked piece and do:

    while (true) // All of this should be in a loop that only ends when a full move is made
        Coordinates source = new Coordinates(); (add coordinates to this struct, where coordinates are
        0 indexed and the origin is in the top left of the array (i think))

        if (board.getColor(source) != board.turnChar) continue; // This validates the piece color

        ArrayList<Coordinates> check = new ArrayList<>(); // List of coordinates

        switch (board.getPiece(source))
        {
            // Each piece has getValidMoves, which returns a list
            // of coordinates, where the coordinates are all valid moves
            case 'P':
                check = board.pawnData.getValidMoves(source, board);
                break;
            case 'N':
                check = board.knightData.getValidMoves(source, board);
                break;
            case 'R':
                check = board.rookData.getValidMoves(source, board);
                break;
            case 'B':
                check = board.bishopData.getValidMoves(source, board);
                break;
            case 'Q':
                check = board.queenData.getValidMoves(source, board);
                break;
            case 'K':
                check = board.kingData.getValidMoves(source, board);
                break;
            default:
                System.exit(64);
        }

        // Redo loop if piece has no valid moves (such as a rook in the starting position)
        if (check.isEmpty()) continue;

        // Iterate through all moves in check, remove them from check if the position would result in a check
        for (int i = 0; i < check.size(); i++)
        {
            Coordinates dest = check.get(i);
            if (!board.considerCheck(source, dest))
            {
                check.remove(dest);
                i--;
            }
        }

        // At this point, the chosen piece has valid moves and is attached to the mouse. No permanent
        // board state change should have occured, the chosen piece is simply primed to move.

        Coordinates dest = new Coordinates(); // Populate this struct with a target location (either from a
        second click while the piece is attached to the mouse or the square the mouse released on, either or)

        // Redo loop if the chosen target square has the same color piece as the current player
        if (board.getColor(dest) == board.turnChar) continue;

        // This double checks to make sure dest is in check
        switch (board.getPiece(source))
        {
            case 'P':
                if (!board.pawnData.validMove(check, dest))         
                {
                    continue;
                }
                break;
            case 'N':
                if (!board.knightData.validMove(check, dest))
                {
                    continue;
                }
                else break;
            case 'R':
                if (!board.rookData.validMove(check, dest))
                {
                    continue;
                }
                else break;
            case 'B':
                if (!board.bishopData.validMove(check, dest))
                {
                    continue;
                }
                else break;
            case 'Q':
                if (!board.queenData.validMove(check, dest))
                {
                    continue;
                }
                else break;
            case 'K':
                if (!board.kingData.validMove(check, dest))
                {
                    continue;
                }
                else break;
            default:
                System.exit(64);
        }

        // At this point, a move has been fully validated and is legal.
        // Along with board.movePiece(), the ImageView array also needs to be manually changed.
        // After some validation, movePiece() just copies the source piece and color to the
        // destination square and erases the original, the same process can be done in the ImageView array
        board.movePiece(source, dest);

        // Pawn promotion is done here as well. This needs some GUI feature for selection but
        // the backend logic is unchanged
        if (board.getPiece(dest) == 'P' && (dest.y == 0 || dest.y == 7))
        {
            char choice = 'X';
            // prompt for piece selection here somehow, change choice to piece character
            board.promotePawn(dest, choice);
        }

        // End loop
     */
@Override
public void start(Stage stage)
{
    Image boardImage = new Image(getClass().getResourceAsStream("/Chess_Board.png")); // Find image in resources folder
    ImageView imageView = new ImageView(boardImage); // Create an ImageView to display the board image

    StackPane pane = new StackPane(); // Create a StackPane to hold the board image and pieces
    pane.getChildren().add(imageView); // Add the board image to the StackPane
    Pane pieceLayer = new Pane(); // Create a Pane to hold the pieces on top of the board

    pieceLayer.setPrefSize(boardImage.getWidth(), boardImage.getHeight()); // Set the preferred size of the piece layer to match the board image size
    pieceLayer.setMinSize(boardImage.getWidth(), boardImage.getHeight()); // Set the minimum size of the piece layer to match the board image size
    pieceLayer.setMaxSize(boardImage.getWidth(), boardImage.getHeight()); // Set the maximum size of the piece layer to match the board image size

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

    pawnImageView.setOnMousePressed(event -> {
        System.out.println("pawn pressed");
    });

    pawnImageView.setOnMouseDragged(event -> {
        // Convert mouse position into pieceLayer coordinates
        Point2D mouse = pieceLayer.sceneToLocal(
            event.getSceneX(),
            event.getSceneY()
        );

        // Center the pawn on the mouse
        double dragX = mouse.getX() - pawnImageView.getBoundsInLocal().getWidth() / 2;
        double dragY = mouse.getY() - pawnImageView.getBoundsInLocal().getHeight() / 2;

        pawnImageView.setLayoutX(dragX);
        pawnImageView.setLayoutY(dragY);
    });

    pawnImageView.setPickOnBounds(true);

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



// old java swing code using for reference
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