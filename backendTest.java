import src.*;
import java.util.Scanner;
import java.util.ArrayList;

/*
Todo:
king movement (hard)
make some build system thing so half of the repo isnt class files
*/

public class backendTest {
    Scanner input = new Scanner(System.in);
    Board board = new Board();
    final ArrayList<Coordinates> blank = new ArrayList<>();

    public void main()
    {
        while(true) // Game loop
        {
            board._printBoard(blank); // Display board

            // Swap move (by default, turnChar is 'B', which swaps it to 'W' on first turn.)
            if (board.turnChar == 'B') 
            {
                board.turnChar = 'W';
                System.out.println("White to move\n");
            }
            else 
            {
                board.turnChar = 'B';
                System.out.println("Black to move\n");
            }

            boolean finished = false; // Loop flag
            while (!finished)
            {
                System.out.println("Select a piece:");
                Coordinates source = board._getMove(); // Gets move
                if (board.getColor(source) != board.turnChar) continue; // Validate piece was correct color

                ArrayList<Coordinates> check = new ArrayList<>(); // List of coordinates
                switch (board.getPiece(source)) // Get valid moves to augment terminal output
                {
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
                board._printBoard(check);

                System.out.println("\nSelect a destination:");
                Coordinates dest = board._getMove();
                if (board.getColor(dest) == board.turnChar) continue; // Validate piece was not current turn color

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
                    case 'R':
                        check = board.rookData.getValidMoves(source, board);
                        if (!board.rookData.validMove(check, dest))
                        {
                            System.out.println("Invalid move.");
                            continue;
                        }
                        else break;
                    case 'B':
                        check = board.bishopData.getValidMoves(source, board);
                        if (!board.bishopData.validMove(check, dest))
                        {
                            System.out.println("Invalid move.");
                            continue;
                        }
                        else break;
                    case 'Q':
                        check = board.queenData.getValidMoves(source, board);
                        if (!board.queenData.validMove(check, dest))
                        {
                            System.out.println("Invalid move.");
                            continue;
                        }
                        else break;
                    case 'K':
                        check = board.kingData.getValidMoves(source, board);
                        if (!board.kingData.validMove(check, dest))
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

                if (board.getPiece(dest) == 'K' && (source.x + 1 < dest.x || source.x - 1 > dest.x)) // Castling check
                {
                    if (dest.x == 6)
                    {
                        dest.x = 5;
                        source.x = 7;
                    }
                    if (dest.x == 2)
                    {
                        dest.x = 3;
                        source.x = 0;
                    }
                    board.movePiece(source, dest); // Move rook
                }
                finished = true;
            } // End while
        } // End game loop
    } // End main
} // End class definition
