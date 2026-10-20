package src;
import java.util.Scanner;
import java.util.ArrayList;

public class Board {
    // Boilerplate
    Scanner input = new Scanner(System.in);

    // Piece data
    public Pawn pawnData = new Pawn();
    public Knight knightData = new Knight();
    public Rook rookData = new Rook();
    public Bishop bishopData = new Bishop();
    public Queen queenData = new Queen();
    public King kingData = new King();

    // Game state data (public visibility for convenience)
    public char turnChar = 'B';
    public boolean whiteKingMoved = false;
    public boolean blackKingMoved = false;
    public boolean leftWhiteRookMoved = false;
    public boolean rightWhiteRookMoved = false;
    public boolean leftBlackRookMoved = false;
    public boolean rightBlackRookMoved = false;

    // Board data
    private char[][] boardPieces = {{'R', 'N', 'B', 'Q', 'K', 'B', 'N', 'R'},
                                    {'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P'},
                                    {'.', '.', '.', '.', '.', '.', '.', '.'},
                                    {'.', '.', '.', '.', '.', '.', '.', '.'},
                                    {'.', '.', '.', '.', '.', '.', '.', '.'},
                                    {'.', '.', '.', '.', '.', '.', '.', '.'},
                                    {'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P'},
                                    {'R', 'N', 'B', 'Q', 'K', 'B', 'N', 'R'}};
    // Arrays are column major order, 0 indexed, and the origin is in the top left
    // Arrays must be accessed as [y][x], but getMove() deals with 0 index and location adjustment
    // As a result, pieces can be referenced from the frontend using digits the same way as chess notation

    private char[][] boardColors = {{'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B'},
                                    {'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B'},
                                    {'.', '.', '.', '.', '.', '.', '.', '.'},
                                    {'.', '.', '.', '.', '.', '.', '.', '.'},
                                    {'.', '.', '.', '.', '.', '.', '.', '.'},
                                    {'.', '.', '.', '.', '.', '.', '.', '.'},
                                    {'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W'},
                                    {'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W'}};
    // Array defining piece colors
    // Moved when other pieces are moved
    // Used to validate which pieces can be chosen in what scenario

    // Board Methods
    public void movePiece(Coordinates source, Coordinates dest)
    {
        if (getPiece(source) == 'R') // Rook movement check
        {
            if (getColor(source) == 'W')
            {
                if (source.x == 0) leftWhiteRookMoved = true;
                if (source.x == 7) rightWhiteRookMoved = true;
            }
            else
            {
                if (source.x == 0) leftBlackRookMoved = true;
                if (source.x == 7) rightBlackRookMoved = true;
            }
        }
        if (getPiece(source) == 'K') // King movement check, need to also do rook moves here later
        {
            if (getColor(source) == 'W') 
            {
                whiteKingMoved = true;
                if (dest.x > source.x + 1)
                {
                    rightWhiteRookMoved = true;
                }
                if (dest.x < source.x - 1)
                {
                    leftWhiteRookMoved = true;
                }
            }
            else 
            {
                blackKingMoved = true;
                if (dest.x > source.x + 1)
                {
                    rightBlackRookMoved = true;
                }
                if (dest.x < source.x - 1)
                {
                    leftBlackRookMoved = true;
                }
            }
        }
        boardPieces[dest.y][dest.x] = boardPieces[source.y][source.x]; // Copy piece
        boardColors[dest.y][dest.x] = boardColors[source.y][source.x]; // Copy color
        boardPieces[source.y][source.x] = '.'; // Delete piece
        boardColors[source.y][source.x] = '.'; // Delete color
    }
    public char getColor(Coordinates coord) // Returns corresponding color
    {
        return boardColors[coord.y][coord.x];
    }
    public char getPiece(Coordinates coord) // Returns corresponding piece
    {
        return boardPieces[coord.y][coord.x];
    }
    public boolean inBounds(Coordinates coord) // Bounds checking
    {
        if (coord.x > -1 && coord.x < 8 && coord.y > -1 && coord.y < 8) return true;
        else return false;
    }

    // The remaining data and methods are for text based input and output, intended
    // for the testing environment. Should not be used in proper front end implementation
    public final String _RESET = "\u001B[0m";
    public final String _WHITE = "\033[38;5;255m";
    public final String _BLACK = "\033[90m";
    public final String _BROWN = "\033[0;33m";
    public final String _GREENBG = "\033[42m";

    public Coordinates _getMove() // Get move input and convert into usable format
    {
        Coordinates move = new Coordinates();
        boolean invalid = true;
        while (invalid)
        {
            System.out.print("Enter X coordinate: ");
            int x = input.nextInt();
            if (x > 8 || x < 1) continue; // Validate input is in bounds

            System.out.print("Enter Y coordinate: ");
            int y = input.nextInt();
            if (y > 8 || y < 1) continue; // Validate input is in bounds

            if (y == 8) y = 1; //idk how to do this elegantly 
            else if (y == 7) y = 2;
            else if (y == 6) y = 3;
            else if (y == 5) y = 4;
            else if (y == 4) y = 5;
            else if (y == 3) y = 6;
            else if (y == 2) y = 7;
            else if (y == 1) y = 8;

            move.x = --x;
            move.y = --y;
            invalid = false;
        }
        return move;
    }
    public void _printBoard(ArrayList<Coordinates> highlight)
    {
        // Clears screen somehow idk
        System.out.print("\033[H\033[2J");
        System.out.flush();

        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                boolean colorMove = false;
                if (!highlight.isEmpty())
                {
                    Coordinates current = new Coordinates(j, i);
                    for (int k = 0; k < highlight.size(); k ++) // evil time complexity
                    {
                        if (current.x == highlight.get(k).x && current.y == highlight.get(k).y)
                        {
                            colorMove = true;
                        }
                    }
                }
                if (boardColors[i][j] == 'W')
                {
                    if (colorMove)
                    {
                        System.out.print(_WHITE + _GREENBG + boardPieces[i][j] + " " + _RESET);
                    }
                    else
                    {
                        System.out.print(_WHITE + boardPieces[i][j] + " " + _RESET);
                    }
                }
                else if (boardColors[i][j] == 'B')
                {
                    if (colorMove)
                    {
                        System.out.print(_BLACK + _GREENBG + boardPieces[i][j] + " " + _RESET);
                    }
                    else
                    {
                        System.out.print(_BLACK + boardPieces[i][j] + " " + _RESET);
                    }
                }
                else 
                {
                    if (colorMove)
                    {
                        System.out.print(_BROWN + _GREENBG + boardPieces[i][j] + " " + _RESET);
                    }
                    else
                    {
                        System.out.print(_BROWN + boardPieces[i][j] + " " + _RESET);
                    }
                }
            }
            System.out.println();
        }
    }
}

