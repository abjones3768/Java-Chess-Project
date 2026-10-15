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

class Pawn extends Piece
{
    void Piece()
    {
        pieceChar = 'P';
    }
    public ArrayList<Coordinates> getValidMoves(final Coordinates loc, final Board boardState)
    {
        /* Movement pattern:
        Can always move one space ahead 
        Can move two spaces ahead on starting square
        Can capture one space diagonally ahead and to the left or right
        */
        // todo: implement en passant
        ArrayList<Coordinates> coords = new ArrayList<>();
        if (boardState.getColor(loc) == 'W') // White pawn check
        {
            Coordinates locCheck = new Coordinates(loc.x, loc.y - 1);
            if (boardState.getPiece(locCheck) == ' ') // Check directly ahead
            {
                coords.add(locCheck);
            }
            locCheck.y -= 1;
            if (loc.y == 7 && boardState.getPiece(locCheck) == ' ') // Check 2 spaces ahead if pawn is at starting position
            {
                coords.add(locCheck);
            }
            locCheck.x = loc.x - 1;
            locCheck.y = loc.y - 1;
            if (boardState.getColor(locCheck) == 'B') // Check if attackable square to the left is black
            {
                coords.add(locCheck);
            }
            locCheck.x += 2;
            if (boardState.getColor(locCheck) == 'B') // Check if attackable square to the right is black
            {
                coords.add(locCheck);
            }
        }
        else // Black pawn check
        {
            Coordinates locCheck = new Coordinates(loc.x, loc.y + 1);
            if (boardState.getPiece(locCheck) == ' ') // Check directly ahead
            {
                coords.add(locCheck);
            }
            locCheck.y += 1;
            if (loc.y == 2 && boardState.getPiece(locCheck) == ' ') // Check 2 spaces ahead if pawn is at starting position
            {
                coords.add(locCheck);
            }
            locCheck.x = loc.x + 1;
            locCheck.y = loc.y + 1;
            if (boardState.getColor(locCheck) == 'W') // Check if attackable square to the right is white
            {
                coords.add(locCheck);
            }
            locCheck.x -= 2;
            if (boardState.getColor(locCheck) == 'W') // Check if attackable square to the left is white
            {
                coords.add(locCheck);
            }
        }
        return coords;
    }
}
