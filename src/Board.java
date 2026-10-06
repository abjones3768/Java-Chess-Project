package src;

//import src.*;
import java.util.Scanner;

public class Board {
    // -- Boilerplate -- //
    Scanner input = new Scanner(System.in);

    // -- Data -- //
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

    // -- Board Methods -- //
    public void movePiece(Coordinates source, Coordinates dest) // Array of 2 coordinates
    {
        boardPieces[dest.y][dest.x] = boardPieces[source.y][source.x]; // Copy piece
        boardColors[dest.y][dest.x] = boardColors[source.y][source.x]; // Copy color
        boardPieces[source.y][source.x] = '.'; // Delete piece
        boardColors[source.y][source.x] = '.'; // Delete color
    }
    public char getColor(Coordinates coord)
    {
        return boardColors[coord.y][coord.x];
    }

    // -- Testing Environment -- //
    public static final String _RESET = "\u001B[0m";
    public static final String _WHITE = "\033[38;5;255m";
    public static final String _BLACK = "\033[90m";
    public static final String _BROWN = "\033[0;33m";

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
    public void _printBoard()
    {
        // Clears screen somehow idk
        System.out.print("\033[H\033[2J");
        System.out.flush();

        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                if (boardColors[i][j] == 'W')
                {
                    System.out.print(_WHITE + boardPieces[i][j] + " " + _RESET);
                }
                else if (boardColors[i][j] == 'B')
                {
                    System.out.print(_BLACK + boardPieces[i][j] + " " + _RESET);
                }
                else System.out.print(_BROWN + boardPieces[i][j] + " " + _RESET);
            }
            System.out.println();
        }
    }
}

