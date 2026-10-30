import src.main.java.Board;
import src.main.java.Coordinates;

import java.util.Scanner;
import java.util.ArrayList;

/*
Todo:
engine
*/

public class backendTest {
    Scanner input = new Scanner(System.in);
    Board board = new Board();
    final ArrayList<Coordinates> blank = new ArrayList<>();

    public void main()
    {
        while(true) // Game loop
        {
            // Display board
            board._printBoard(blank);

            // Check for stalemate
            if (!board.moveExists('W') || !board.moveExists('B')) 
            {
                if (board.isCheck('W'))
                {
                    System.out.println("black wins");
                    System.exit(0);
                }
                if (board.isCheck('B'))
                {
                    System.out.println("white wins");
                    System.exit(0);
                }
                System.out.println("stalemate");
                System.exit(0);
            }
            
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

            // Check for check (sets flag in board data as well as telling player)
            if (board.isCheck('W') || board.isCheck('B'))
            {
                System.out.println("Check!");
            }

            // Loop to get turn
            boolean finished = false;
            while (!finished)
            {
                // Retrieve move
                System.out.println("Select a piece:");
                Coordinates source = board._getMove();

                 // Validate piece was correct color
                if (board.getColor(source) != board.turnChar) continue;

                 // List of coordinates
                ArrayList<Coordinates> check = new ArrayList<>();

                 // Get valid moves of source piece
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

                // Redo loop if piece has no valid moves
                if (check.isEmpty()) continue;

                // Iterate through moves to check if it would result in a check (illegal move)
                for (int i = 0; i < check.size(); i++)
                {
                    Coordinates dest = check.get(i);
                    if (!board.considerCheck(source, dest))
                    {
                        check.remove(dest);
                        i--;
                    }
                }

                // Print board and retrieve move
                board._printBoard(check);
                System.out.println("\nSelect a destination:");
                Coordinates dest = board._getMove();

                // Validate piece was not current turn color (would result in capturing own piece)
                if (board.getColor(dest) == board.turnChar) continue;

                 // Validate move destination
                switch (board.getPiece(source))
                {
                    case 'P':
                        // validMove takes the list of moves, and returns true if dest is
                        // contained in that list. If it returns false, move was
                        // invalid, do continue to redo loop until a valid move is chosen
                        if (!board.pawnData.validMove(check, dest))         
                        {
                            System.out.println("Invalid move.");
                            continue;
                        }
                        break;
                    case 'N':
                        if (!board.knightData.validMove(check, dest))
                        {
                            System.out.println("Invalid move.");
                            continue;
                        }
                        else break;
                    case 'R':
                        if (!board.rookData.validMove(check, dest))
                        {
                            System.out.println("Invalid move.");
                            continue;
                        }
                        else break;
                    case 'B':
                        if (!board.bishopData.validMove(check, dest))
                        {
                            System.out.println("Invalid move.");
                            continue;
                        }
                        else break;
                    case 'Q':
                        if (!board.queenData.validMove(check, dest))
                        {
                            System.out.println("Invalid move.");
                            continue;
                        }
                        else break;
                    case 'K':
                        if (!board.kingData.validMove(check, dest))
                        {
                            System.out.println("Invalid move.");
                            continue;
                        }
                        else break;
                    default:
                        // this only happens when you try to move a piece with no movement data
                        System.exit(64);
                }

                // Unconditionally moves piece, as it has been validated by this point
                board.movePiece(source, dest);

                // Pawn promotion
                if (board.getPiece(dest) == 'P' && (dest.y == 0 || dest.y == 7))
                {
                    char choice = 'X';
                    System.out.println("Select a piece (R, B, N, Q)");
                    choice = Character.toUpperCase(input.nextLine().charAt(0));
                    while (choice != 'R' && choice != 'B' && choice != 'N' && choice != 'Q')
                    {
                        System.out.println("Select a piece (R, B, N, Q)");
                        choice = Character.toUpperCase(input.nextLine().charAt(0));
                    }
                    board.promotePawn(dest, choice);
                }
                finished = true;
            } // End while
        } // End game loop
    } // End main
} // End class definition
