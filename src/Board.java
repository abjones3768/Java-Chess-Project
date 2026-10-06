package src;

import src.*;
import java.util.Scanner;

public class Board {
    Scanner input = new Scanner(System.in);
    public char[][] boardPieces = {{'R', 'N', 'B', 'Q', 'K', 'B', 'N', 'R'},
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

    public char[][] boardColors = {{'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B'},
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

    public Board()
    {

    }
    public void movePiece(Coordinates source, Coordinates dest) // Array of 2 coordinates, 0 indexed
    {
        boardPieces[dest.y][dest.x] = boardPieces[source.y][source.x]; // Copy piece
        boardColors[dest.y][dest.x] = boardColors[source.y][source.x]; // Copy color
        boardPieces[source.y][source.x] = '.'; // Delete piece
        boardColors[source.y][source.x] = '.'; // Delete color
    }

    public Coordinates getMove() // Get move input and convert into usable format
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
}

