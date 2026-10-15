import src.*;
import java.util.Scanner;
import java.util.ArrayList;

/*
Todo:
bishop movement
rook movement
queen movement (literally just the above two in one thing)
king movement (hard)
*/

public class backendTest {
    Scanner input = new Scanner(System.in);
    Board board = new Board();

    char turnChar = 'B';

    public void main()
    {
        while(true) // Game loop
        {
            board._printBoard(); // Display board

            // Swap move (by default, turnChar is 'B', which swaps it to 'W' on first turn.)
            if (turnChar == 'B') 
            {
                turnChar = 'W';
                System.out.println("White to move\n");
            }
            else 
            {
                turnChar = 'B';
                System.out.println("Black to move\n");
            }

            boolean finished = false; // Loop flag
            while (!finished)
            {
                System.out.println("Select a piece:");
                Coordinates source = board._getMove(); // Gets move
                if (board.getColor(source) != turnChar) continue; // Validate piece was correct color

                System.out.println("\nSelect a destination:");
                Coordinates dest = board._getMove();
                if (board.getColor(dest) == turnChar) continue; // Validate piece was not current turn color

                ArrayList<Coordinates> check = new ArrayList<>(); // List of coordinates

                switch (board.getPiece(source)) // Validate move destination
                {
                    case 'P':
                        // Each piece has getValidMoves, which returns a list
                        // of coordinates, where the coordinates are all valid moves
                        check = board.pawnData.getValidMoves(source, board);

                        // validMove takes the list, and returns true if dest is
                        // contained in that list. If it returns false, move was
                        // invalid, do continue to redo loop until a valid move is chosen
                        if (!board.pawnData.validMove(check, dest))         
                        {
                            System.out.println("Invalid move.");
                            continue;
                        }
                        else break;
                    case 'N':
                        check = board.knightData.getValidMoves(source, board);
                        if (!board.knightData.validMove(check, dest))
                        {
                            System.out.println("Invalid move.");
                            continue;
                        }
                        else break;
                    default:
                        // this only happens when you try to move a piece with no movement data
                        // once all pieces are implemented this shouldnt happen
                        System.exit(64);
                }
                board.movePiece(source, dest); // Unconditionally moves piece, as it has been validated by this point
                finished = true;
            } // End while
        } // End game loop
    } // End main
} // End class definition
