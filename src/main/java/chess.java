import java.util.ArrayList;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class chess extends Application
{
    private Board board = new Board();
    private boolean gameOver = false;
    private Label turnLabel =  new Label("White's Turn");
    private Pane highlightLayer = new Pane();
    private Pane checkLayer = new Pane();
    private Scene menuScene;
    private Scene gameScene;
    private GridPane moveHistory = new GridPane();
    private ScrollPane historyScroll = new ScrollPane();
    private int moveNumber = 1;


private Piece getPieceData(Coordinates position)
{
    switch (board.getPiece(position))
    {
        case 'P':
            return board.pawnData;

        case 'R':
            return board.rookData;

        case 'B':
            return board.bishopData;
        
        case 'N':
            return board.knightData;
        
        case 'Q':
            return board.queenData;
        case 'K':
            return board.kingData;
        default:
            return null;
    }
}

private String getImageName(Coordinates position)
{
    char piece = board.getPiece(position);
    char color = board.getColor(position);

    String colorName;

    if (color == 'W')
        colorName = "White_";
    else if (color == 'B')
        colorName = "Black_";
    else
        return null;

    switch (piece)
    {
        case 'P':
            return colorName + "Pawn.png";

        case 'R':
            return colorName + "Rook.png";

        case 'B':
            return colorName + "Bishop.png";

        case 'N':
            return colorName + "Knight.png";

        case 'Q':
            return colorName + "Queen.png";

        case 'K':
            return colorName + "King.png";

        default:
            return null;
    }
}

private void populateBoard(double squareSize, Pane pieceLayer)
{
    for (int row = 0; row < 8; row++)
    {
        for (int col = 0; col < 8; col++)
        {
            Coordinates position = new Coordinates(col, row);

            String imageName = getImageName(position);

            if (imageName != null)
            {
                ImageView pieceView = createPiece(
                    imageName,
                    position,
                    squareSize,
                    pieceLayer
                );

                enableMovement(
                    pieceView,
                    position,
                    squareSize,
                    pieceLayer
                );
            }
        }
    }
}

private void refreshBoard(double squareSize, Pane pieceLayer){
    pieceLayer.getChildren().clear();

    populateBoard(squareSize, pieceLayer);
}

private ImageView createPiece(String imageName, Coordinates position,
                              double squareSize, Pane pieceLayer)
{
    Image image = new Image(
        getClass().getResourceAsStream("/" + imageName)
    );

    ImageView pieceView = new ImageView(image);

    pieceView.setFitWidth(squareSize);
    pieceView.setFitHeight(squareSize);
    pieceView.setPreserveRatio(true);
    pieceView.setPickOnBounds(true);

    pieceView.setLayoutX(position.x * squareSize);
    pieceView.setLayoutY(position.y * squareSize);

    pieceLayer.getChildren().add(pieceView);

    return pieceView;
}
private void showPromotionPopup(Coordinates position)
{
    Stage popup = new Stage();
    popup.setTitle("Pawn Promotion");
    popup.initModality(Modality.APPLICATION_MODAL);
    popup.setResizable(false);

    Label title = new Label("Choose a piece");

    HBox options = new HBox(10);
    options.setAlignment(Pos.CENTER);

    char color = board.getColor(position);

    String colorName;

    if (color == 'W')
        colorName = "White_";
    else
        colorName = "Black_";

    String[] names = {"Queen", "Rook", "Bishop", "Knight"};
    char[] pieces = {'Q', 'R', 'B', 'N'};

    for (int i = 0; i < names.length; i++)
    {
        final char selectedPiece = pieces[i];

        Image image = new Image(
            getClass().getResourceAsStream(
                "/" + colorName + names[i] + ".png"
            )
        );

        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(65);
        imageView.setFitHeight(65);
        imageView.setPreserveRatio(true);

        Button button = new Button(names[i], imageView);
        button.setContentDisplay(
            javafx.scene.control.ContentDisplay.TOP
        );

        button.setOnAction(event -> {
            board.promotePawn(position, selectedPiece);
            popup.close();
        });

        options.getChildren().add(button);
    }

    VBox layout = new VBox(15, title, options);
    layout.setAlignment(Pos.CENTER);
    layout.setPadding(new Insets(20));

    Scene scene = new Scene(layout);
    popup.setScene(scene);

    popup.setOnCloseRequest(event -> {
        event.consume();
    });

    popup.showAndWait();
}

private void updateTurnLabel()
{
    String turn;

    if (board.turnChar == 'W')
    {
        turn = "White's turn";
    } 
    else
    {
        turn = "Black's Turn";
    }

    if (board.isCheck(board.turnChar))
    {
        turnLabel.setText(turn + " - CHECK!");
        turnLabel.setTextFill(Color.RED);
    }
    else
    {
        turnLabel.setText(turn);
        turnLabel.setTextFill(Color.WHITE);
    }
}

// Resets the game to its starting state
private void restartGame(double squareSize, Pane pieceLayer)
{
    board = new Board();

    gameOver = false;

    highlightLayer.getChildren().clear();
    checkLayer.getChildren().clear();

    resetMoveHistory();

    refreshBoard(squareSize, pieceLayer);
    updateTurnLabel();
}

// Displays the result when the game ends
private void showGameOverPopup(boolean inCheck,
                              double squareSize,
                              Pane pieceLayer)
{
    Stage popup = new Stage();

    popup.setTitle("Game Over");
    popup.initModality(Modality.APPLICATION_MODAL);
    popup.setResizable(false);

    Label title = new Label();
    Label result = new Label();

    // Determine whether the game ended in checkmate or stalemate
    if (inCheck)
    {
        title.setText("Checkmate!");

        if (board.turnChar == 'W')
            result.setText("Black Wins!");
        else
            result.setText("White Wins!");
    }
    else
    {
        title.setText("Stalemate!");
        result.setText("It's a Draw!");
    }

    title.setStyle(
        "-fx-font-size: 28px;" +
        "-fx-font-weight: bold;"
    );

    result.setStyle("-fx-font-size: 20px;");

    Label message = new Label(
        "Would you like to play again?"
    );

    Button restart = new Button("Play Again");
    Button close = new Button("Close");

    restart.setOnAction(event -> {
        restartGame(squareSize, pieceLayer);
        popup.close();
    });

    close.setOnAction(event -> {
        popup.close();
    });

    HBox buttons = new HBox(15, restart, close);
    buttons.setAlignment(Pos.CENTER);

    VBox layout = new VBox(
        15, title, result, message, buttons
    );

    layout.setAlignment(Pos.CENTER);
    layout.setPadding(new Insets(25));

    Scene scene = new Scene(layout, 350, 220);

    popup.setScene(scene);
    popup.showAndWait();
}


// Determines whether the game has ended
private void checkGameStatus(double squareSize,
                             Pane pieceLayer)
{
    boolean inCheck = board.isCheck(board.turnChar);
    boolean hasMoves = board.moveExists(board.turnChar);

    if (!hasMoves)
    {
        gameOver = true;

        if (inCheck)
        {
            if (board.turnChar == 'W')
                turnLabel.setText("Checkmate! Black Wins!");
            else
                turnLabel.setText("Checkmate! White Wins!");
        }
        else
        {
            turnLabel.setText("Stalemate! Draw!");
        }

        showGameOverPopup(inCheck, squareSize, pieceLayer);
    }
}


private void highlightKingInCheck(double squareSize)
{
    checkLayer.getChildren().clear();

    if (!board.isCheck(board.turnChar))
        return;

    for (int row = 0; row < 8; row++)
    {
        for (int col = 0; col < 8; col++)
        {
            Coordinates position = new Coordinates(col, row);

            if (board.getPiece(position) == 'K' && board.getColor(position) == board.turnChar)
            {
                Rectangle highlight = new Rectangle(col * squareSize, row * squareSize, squareSize, squareSize);

                highlight.setFill(Color.RED);
                highlight.setOpacity(0.65);

                checkLayer.getChildren().add(highlight);

                return;
            }
        }
    }
}

private void showValidMoves(Coordinates position, double squareSize)
{
    highlightLayer.getChildren().clear();

    Piece piece = getPieceData(position);

    if(piece == null)
    {
        return;
    }

    Rectangle selected = new Rectangle(position.x * squareSize, position.y * squareSize, squareSize, squareSize);

    selected.setFill(Color.YELLOW);
    selected.setOpacity(0.4);

    highlightLayer.getChildren().add(selected);

    ArrayList<Coordinates> validMoves = piece.getValidMoves(position, board);

    for(Coordinates move : validMoves)
    {
        // Only highlight legal moves
        if (board.considerCheck(position, move))
        {
            Circle dot = new Circle(move.x * squareSize + squareSize / 2, move.y * squareSize + squareSize / 2, squareSize/6);

            dot.setFill(Color.LIMEGREEN);
            dot.setOpacity(0.7);
            highlightLayer.getChildren().add(dot);
        }
    }
}

private void enableMovement(ImageView pieceView, Coordinates position,
                            double squareSize, Pane pieceLayer)
{
    pieceView.setOnMousePressed(event -> {
        
        if (gameOver)
            return;

        Piece piece = getPieceData(position);

        // Check if it is this piece's turn!
        if(board.getColor(position) != board.turnChar)
        {
            System.out.println("It is not your turn!");
            return;
        }

        if (piece == null)
            return;

        showValidMoves(position, squareSize);

        ArrayList<Coordinates> validMoves =
            piece.getValidMoves(position, board);

        for (Coordinates move : validMoves)
        {
            System.out.println("Valid move: " + move.x + ", " + move.y);
        }
    });

    pieceView.setOnMouseDragged(event -> {
       if (gameOver)
       return;
       // Prevent dragging the opposing player's pieces
       if (board.getColor(position) != board.turnChar)
       {
        return;
       }

        Point2D mouse = pieceLayer.sceneToLocal(
            event.getSceneX(), event.getSceneY()
        );

        double dragX = mouse.getX() -
            pieceView.getBoundsInLocal().getWidth() / 2;

        double dragY = mouse.getY() -
            pieceView.getBoundsInLocal().getHeight() / 2;

        pieceView.setLayoutX(dragX);
        pieceView.setLayoutY(dragY);
    });

    pieceView.setOnMouseReleased(event -> {
        if (gameOver)
        return;

        highlightLayer.getChildren().clear();

        // Prevent moving the opposing player's pieces
        if (board.getColor(position) != board.turnChar)
        {
            return;
        }

        Point2D mouse = pieceLayer.sceneToLocal(
            event.getSceneX(), event.getSceneY()
        );

        int destCol = (int) Math.floor(mouse.getX() / squareSize);
        int destRow = (int) Math.floor(mouse.getY() / squareSize);

        Coordinates dest = new Coordinates(destCol, destRow);

        Piece piece = getPieceData(position);

        if (piece != null && board.inBounds(dest))
        {
            ArrayList<Coordinates> validMoves =
                piece.getValidMoves(position, board);

            if (piece.validMove(validMoves, dest) && board.considerCheck(position, dest)) 
            {
                char movingColor = board.getColor(position);
                char movingPiece = board.getPiece(position);

                String notation = createMoveNotation(position, dest);

                // move pieces
                board.movePiece(position, dest);

                // Check for pawn promotion
                if (board.getPiece(dest) == 'P')
                {
                    char color = board.getColor(dest);

                    if ((color == 'W' && dest.y == 0) || (color == 'B' && dest.y == 7))
                    {
                        showPromotionPopup(dest);
                    }
                    // Add promotion notation
                    if (movingPiece == 'P' &&
                        board.getPiece(dest) != 'P')
                    {
                        notation += "=" + board.getPiece(dest);
                    }
                }

                // Switch to the other player's turn
                if (board.turnChar == 'W')
                {
                    board.turnChar = 'B';
                }
                else
                {
                    board.turnChar = 'W';
                }

                // Add check or checkmate notation
                if (board.isCheck(board.turnChar))
                {
                    if (!board.moveExists(board.turnChar))
                    {
                        notation += "#";
                    }
                    else
                    {
                        notation += "+";
                    }
                }

recordMove(notation, movingColor);

                refreshBoard(squareSize, pieceLayer);

                updateTurnLabel();

                highlightKingInCheck(squareSize);

                checkGameStatus(squareSize, pieceLayer);

                System.out.println("Valid movement!");

                if(board.turnChar == 'W')
                {
                    System.out.println("White's turn!");
                }
                else
                {
                    System.out.println("Black's turn!");
                }
                return; 
        }
    }
        pieceView.setLayoutX(position.x * squareSize);
        pieceView.setLayoutY(position.y * squareSize);
    });
}

// Creates a styled chessboard coordinate label
private Label createCoordinateLabel(String text,
                                    double width,
                                    double height)
{
    Label label = new Label(text);

    label.setMinSize(width, height);
    label.setPrefSize(width, height);
    label.setMaxSize(width, height);

    label.setAlignment(Pos.CENTER);

    label.setStyle(
        "-fx-font-size: 16px;" +
        "-fx-font-weight: bold;" +
        "-fx-text-fill: white;"
    );

    return label;
}

// Surrounds the chessboard with letters and numbers
private GridPane createBoardCoordinates(StackPane pane,
                                        double squareSize)
{
    GridPane boardGrid = new GridPane();

    boardGrid.setAlignment(Pos.CENTER);

    double borderSize = 25;

    // Add the chessboard to the center
    boardGrid.add(pane, 1, 1, 8, 8);

    for (int i = 0; i < 8; i++)
    {
        String letter = String.valueOf((char) ('a' + i));
        String number = String.valueOf(8 - i);

        // Top letters
        boardGrid.add(
            createCoordinateLabel(letter, squareSize, borderSize),
            i + 1, 0
        );

        // Bottom letters
        boardGrid.add(
            createCoordinateLabel(letter, squareSize, borderSize),
            i + 1, 9
        );

        // Left numbers
        boardGrid.add(
            createCoordinateLabel(number, borderSize, squareSize),
            0, i + 1
        );

        // Right numbers
        boardGrid.add(
            createCoordinateLabel(number, borderSize, squareSize),
            9, i + 1
        );
    }

    return boardGrid;
}

// Converts board coordinates into chess notation
private String getSquareName(Coordinates position)
{
    char file = (char) ('a' + position.x);
    int rank = 8 - position.y;

    return "" + file + rank;
}

// Creates the move history panel
private VBox createMoveHistoryPanel()
{
    Label title = new Label("Move History");

    title.setStyle(
        "-fx-font-size: 20px;" +
        "-fx-font-weight: bold;" +
        "-fx-text-fill: white;"
    );

    moveHistory.setHgap(10);
    moveHistory.setVgap(8);
    moveHistory.setPadding(new Insets(10));

    moveHistory.getColumnConstraints().addAll(
        new ColumnConstraints(35),
        new ColumnConstraints(90),
        new ColumnConstraints(90)
    );

    resetMoveHistory();

    historyScroll.setContent(moveHistory);
    historyScroll.setFitToWidth(true);
    historyScroll.setPrefHeight(600);

    historyScroll.setHbarPolicy(
        ScrollPane.ScrollBarPolicy.NEVER
    );

    VBox panel = new VBox(10, title, historyScroll);

    panel.setAlignment(Pos.TOP_CENTER);
    panel.setPadding(new Insets(10));
    panel.setPrefWidth(270);

    panel.setStyle(
        "-fx-background-color: #353535;" +
        "-fx-background-radius: 8px;"
    );

    return panel;
}

// Clears the move history and restores its headings
private void resetMoveHistory()
{
    moveHistory.getChildren().clear();

    moveNumber = 1;

    Label number = new Label("#");
    Label white = new Label("White");
    Label black = new Label("Black");

    number.setStyle("-fx-font-weight: bold;");
    white.setStyle("-fx-font-weight: bold;");
    black.setStyle("-fx-font-weight: bold;");

    moveHistory.add(number, 0, 0);
    moveHistory.add(white, 1, 0);
    moveHistory.add(black, 2, 0);
}

// Adds a completed move to the history
private void recordMove(String notation, char color)
{
    Label move = new Label(notation);

    move.setStyle("-fx-font-size: 15px;");

    if (color == 'W')
    {
        moveHistory.add(
            new Label(moveNumber + "."),
            0, moveNumber
        );

        moveHistory.add(move, 1, moveNumber);
    }
    else
    {
        moveHistory.add(move, 2, moveNumber);

        moveNumber++;
    }

    // Automatically scroll to the newest move
    javafx.application.Platform.runLater(() -> {
        historyScroll.setVvalue(1.0);
    });
}

// Generates notation before the piece is moved
private String createMoveNotation(Coordinates source,
                                  Coordinates dest)
{
    char piece = board.getPiece(source);

    boolean capture = board.getPiece(dest) != '.';

    String notation = "";

    // Castling
    if (piece == 'K' &&
        Math.abs(dest.x - source.x) == 2)
    {
        if (dest.x > source.x)
            return "O-O";
        else
            return "O-O-O";
    }

    // En passant
    if (piece == 'P' && source.x != dest.x)
    {
        capture = true;
    }

    // Add the piece letter, except for pawns
    if (piece != 'P')
    {
        notation += piece;
    }
    else if (capture)
    {
        notation += (char) ('a' + source.x);
    }

    // Add capture symbol
    if (capture)
    {
        notation += "x";
    }

    // Add destination square
    notation += getSquareName(dest);

    return notation;
}

// Creates the main menu screen
private Scene createMainMenu(Stage stage)
{
    // Load chess piece images
    ImageView whiteKing = new ImageView(
        new Image(getClass().getResourceAsStream("/White_King.png"))
    );

    ImageView blackKing = new ImageView(
        new Image(getClass().getResourceAsStream("/Black_King.png"))
    );

    whiteKing.setFitWidth(100);
    whiteKing.setFitHeight(100);
    whiteKing.setPreserveRatio(true);

    blackKing.setFitWidth(100);
    blackKing.setFitHeight(100);
    blackKing.setPreserveRatio(true);

    // Display the kings beside each other
    HBox logo = new HBox(15, whiteKing, blackKing);
    logo.setAlignment(Pos.CENTER);

    // Main title
    Label title = new Label("C H E S S");

    title.setStyle(
        "-fx-font-size: 60px;" +
        "-fx-font-weight: bold;" +
        "-fx-text-fill: white;"
    );

    // Subtitle
    Label subtitle = new Label("Local Two-Player Chess");

    subtitle.setStyle(
        "-fx-font-size: 18px;" +
        "-fx-text-fill: #aaaaaa;"
    );

    // Create menu buttons
    Button playButton = new Button("Play Game");
    Button exitButton = new Button("Exit Game");

    playButton.setPrefSize(240, 50);
    exitButton.setPrefSize(240, 50);

    // Style the play button
    playButton.setStyle(
        "-fx-background-color: #eeeeee;" +
        "-fx-text-fill: #242424;" +
        "-fx-font-size: 18px;" +
        "-fx-font-weight: bold;" +
        "-fx-background-radius: 8px;" +
        "-fx-cursor: hand;"
    );

    // Style the exit button
    exitButton.setStyle(
        "-fx-background-color: #444444;" +
        "-fx-text-fill: white;" +
        "-fx-font-size: 18px;" +
        "-fx-font-weight: bold;" +
        "-fx-background-radius: 8px;" +
        "-fx-cursor: hand;"
    );

    // Button functionality
    playButton.setOnAction(event -> {
        stage.setScene(gameScene);
        stage.sizeToScene();
        stage.centerOnScreen();
    });

    exitButton.setOnAction(event -> {
        stage.close();
    });

    // Organize the title and subtitle
    VBox titleLayout = new VBox(5, title, subtitle);
    titleLayout.setAlignment(Pos.CENTER);

    // Organize the buttons
    VBox buttons = new VBox(15, playButton, exitButton);
    buttons.setAlignment(Pos.CENTER);

    // Main menu layout
    VBox menuLayout = new VBox(
        30, logo, titleLayout, buttons
    );

    menuLayout.setAlignment(Pos.CENTER);
    menuLayout.setPadding(new Insets(30));
    menuLayout.setStyle("-fx-background-color: #242424;");

    return new Scene(menuLayout, 800, 800);
}
    
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

    highlightLayer.setMouseTransparent(true);
    checkLayer.setMouseTransparent(true);

    pane.getChildren().add(checkLayer);
    pane.getChildren().add(highlightLayer);
    pane.getChildren().add(pieceLayer); // Add the piece layer to the StackPane

    // Style the turn indicator
    turnLabel.setStyle(
        "-fx-font-size: 22px;" +
        "-fx-font-weight: bold;"
    );

    turnLabel.setTextFill(Color.WHITE);
    
    // Restart game button
    Button restartButton = new Button("Restart Game");

    restartButton.setStyle(
        "-fx-background-color: #444444;" +
        "-fx-text-fill: white;" +
        "-fx-font-size: 14px;" +
        "-fx-font-weight: bold;" +
        "-fx-cursor: hand;" +
        "-fx-padding: 10px 20px;"
    );

    restartButton.setOnAction(event -> {
        restartGame(boardImage.getWidth() / 8, pieceLayer);
    });

    GridPane boardGrid = createBoardCoordinates(
    pane, boardImage.getWidth() / 8
    );

    VBox historyPanel = createMoveHistoryPanel();

        HBox gameLayout = new HBox(
        15, boardGrid, historyPanel
    );

    gameLayout.setAlignment(Pos.CENTER);

    VBox root = new VBox(
        10, turnLabel, gameLayout, restartButton
    );

    
    root.setAlignment(Pos.CENTER);
    root.setStyle("-fx-background-color: #242424;");
    root.setPadding(new Insets(10));

    gameScene = new Scene(root);

    double squareSize = boardImage.getWidth() / 8;

    // Automatically create all chess pieces
    populateBoard(squareSize, pieceLayer);

    menuScene = createMainMenu(stage);

    gameScene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
        if (event.getCode() == KeyCode.F11) {
            stage.setFullScreen(!stage.isFullScreen());
            event.consume();
        }
    });

    menuScene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
        if (event.getCode() == KeyCode.F11) {
            stage.setFullScreen(!stage.isFullScreen());
            event.consume();
        }
    });

    stage.setScene(menuScene);
    stage.setTitle("Chess Game");
    stage.show();
}
    //Board board = new Board();
    public static void main(String[] args)
    {
        launch(args); // Launch the JavaFX application
    }
}