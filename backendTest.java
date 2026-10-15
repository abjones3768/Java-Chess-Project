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

                    ArrayList<Coordinates> check = new ArrayList<>();
                    boolean invalid = true;

                    switch (board.getPiece(source)) // Validate move destination
                    {
                        case 'P':
                            check = board.pawnData.getValidMoves(source, board);
                            if (check.isEmpty()) // Check for presence of valid moves
                            {
                                System.out.println("Invalid move.");
                                continue;
                            }

                            invalid = true;
                            for (int i = 0; i < check.size(); i++) // Check all moves in list, if a match is found, allow move to go through.
                            {
                                if (check.get(i).x == dest.x && check.get(i).y == dest.y)
                                {
                                    invalid = false;
                                    break;
                                }
                            }
                            if (invalid)
                            {
                                System.out.println("Invalid move.");
                                continue;
                            }
                            break;
                        case 'N':
                            check = board.knightData.getValidMoves(source, board);
                            if (check.isEmpty()) // Check for presence of valid moves
                            {
                                System.out.println("Invalid move.");
                                continue;
                            }

                            invalid = true;
                            for (int i = 0; i < check.size(); i++) // Check all moves in list, if a match is found, allow move to go through.
                            {
                                if (check.get(i).x == dest.x && check.get(i).y == dest.y)
                                {
                                    invalid = false;
                                    break;
                                }
                            }
                            if (invalid)
                            {
                                System.out.println("Invalid move.");
                                continue;
                            }
                            break;
                        default:
                            // this only happens when you try to move a piece with no movement data
                            System.exit(64);
                    }
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
