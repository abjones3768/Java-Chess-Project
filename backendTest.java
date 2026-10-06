import src.*;
import java.util.Scanner;

/*
Todo:
move restrictions for different pieces, will be implemented in their classes
find some way to associate the chars in the boardPiece array with those objects
move restrictions should just be a function that takes in the piece location and the destination
    and returns true or false if the move is valid or not
*/

public class backendTest {
    Scanner input = new Scanner(System.in);
    Board board = new Board();

    boolean turn; // False is white turn, true is black turn

    public void main()
    {
        while(true) // Game loop
        {
            board._printBoard();
            if(!turn) // White turn
            {
                System.out.println("White to move\n");
                turn = !turn;
                boolean finished = false;
                while (!finished)
                {
                    System.out.println("Select a piece:");
                    Coordinates source = board._getMove();
                    if (board.getColor(source) != 'W') continue; // Validate piece was white

                    System.out.println("\nSelect a destination:");
                    Coordinates dest = board._getMove();
                    if (board.getColor(dest) == 'W') continue; // Validate piece was not white

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
                    Coordinates source = board._getMove();
                    if (board.getColor(source) != 'B') continue; // Validate piece was white

                    System.out.println("\nSelect a destination:");
                    Coordinates dest = board._getMove();
                    if (board.getColor(dest) == 'B') continue; // Validate piece was not white

                    board.movePiece(source, dest);
                    finished = true;
                }
            }
        }
    }
}
