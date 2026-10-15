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
    public boolean validMove(ArrayList<Coordinates> check, Coordinates dest)
    {
        if (check.isEmpty()) // Check for presence of valid moves
        {
            return false;
        }

        for (int i = 0; i < check.size(); i++) // Check all moves in list, if a match is found, allow move to go through.
        {
            if (check.get(i).x == dest.x && check.get(i).y == dest.y)
            {
                return true;
            }
        }
        return false;
    }
    public abstract ArrayList<Coordinates> getValidMoves(final Coordinates loc, final Board boardState); // Algorithm for determining valid moves
}
