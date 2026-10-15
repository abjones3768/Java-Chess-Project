package src;

import java.util.ArrayList;

/*
To implement a piece:
1. In the constructor, assign appropriate pieceChar
2. Implement getValidMoves, which returns an ArrayList of all valid move locations
*/
public abstract class Piece {
    // public abstract void Piece(); // Assign character
    protected char pieceChar; // Character data
    public char getChar() // Read character
    {
        return pieceChar;
    }

    public abstract ArrayList<Coordinates> getValidMoves(final Coordinates loc, final Board boardState); // Algorithm for determining valid moves
}
