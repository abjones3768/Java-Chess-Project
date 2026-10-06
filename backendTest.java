import src.*;
import java.util.Scanner;

/*
TODO:
move restrictions for different pieces, will be implemented in their classes
find some way to associate the chars in the boardPiece array with those objects
move restrictions should just be a function that takes in the piece location and the destination
    and returns true or false if the move is valid or not
*/

public class backendTest {
    Board board = new Board();
    boolean turn; // False is white turn, true is black turn
    Scanner input = new Scanner(System.in);

    public static final String RESET = "\u001B[0m";
    public static final String WHITE = "\033[38;5;255m";
    public static final String BLACK = "\033[90m";
    public static final String BROWN = "\033[0;33m";

    public void main()
    {
        while(true) // Game loop
        {
            printBoard();
            if(!turn) // White turn
            {
                System.out.println("White to move\n");
                turn = !turn;
                boolean finished = false;
                while (!finished)
                {
                    System.out.println("Select a piece:");
                    Coordinates source = board.getMove();
                    if (board.boardColors[source.y][source.x] != 'W') continue; // Validate piece was white

                    System.out.println("\nSelect a destination:");
                    Coordinates dest = board.getMove();
                    if (board.boardColors[dest.y][dest.x] == 'W') continue; // Validate piece was not white

                    board.movePiece(source, dest);
                    finished = true;
                }
            }
            else // Black turn
            {
                System.out.println("Black to move\n");
                turn = !turn;
                boolean finished = false;
                while (!finished)
                {
                    System.out.println("Select a piece:");
                    Coordinates source = board.getMove();
                    if (board.boardColors[source.y][source.x] != 'B') continue; // Validate piece was white

                    System.out.println("\nSelect a destination:");
                    Coordinates dest = board.getMove();
                    if (board.boardColors[dest.y][dest.x] == 'B') continue; // Validate piece was not white

                    board.movePiece(source, dest);
                    finished = true;
                }
            }
        }
    }
    public void printBoard()
    {
        // Clears screen somehow idk
        System.out.print("\033[H\033[2J");
        System.out.flush();

        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                if (board.boardColors[i][j] == 'W')
                {
                    System.out.print(WHITE + board.boardPieces[i][j] + " " + RESET);
                }
                else if (board.boardColors[i][j] == 'B')
                {
                    System.out.print(BLACK + board.boardPieces[i][j] + " " + RESET);
                }
                else System.out.print(BROWN + board.boardPieces[i][j] + " " + RESET);
            }
            System.out.println();
        }
    }
}
