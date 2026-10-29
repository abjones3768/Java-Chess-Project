package src.main.java;

import java.util.ArrayList;

/*
To implement a piece:
1. In the constructor, assign appropriate pieceChar
2. getChar() simply returns pieceChar
3. validMove returns true if dest is in check. Normal ArrayList methods are inadequate
    because they compare the location instead of values
4. getValidMoves is implemented on a per piece basis. This is the algorithm that 
    dictates move logic
*/
public abstract class Piece {
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
