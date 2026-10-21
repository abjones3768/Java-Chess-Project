package src.main.java;
import java.util.ArrayList;

public class Pawn extends Piece
{
    void Piece()
    {
        pieceChar = 'P';
    }
    public ArrayList<Coordinates> getAttackingMoves(final Coordinates loc, final Board boardState)
    {
        ArrayList<Coordinates> coords = new ArrayList<>();
        if (boardState.getColor(loc) == 'W')
        {
            Coordinates coord1 = new Coordinates(loc.x - 1, loc.y - 1);
            Coordinates coord2 = new Coordinates(loc.x + 1, loc.y - 1);
            coords.add(coord1);
            coords.add(coord2);
        }
        else
        {
            Coordinates coord1 = new Coordinates(loc.x - 1, loc.y + 1);
            Coordinates coord2 = new Coordinates(loc.x + 1, loc.y + 1);
            coords.add(coord1);
            coords.add(coord2);
        }
        return coords;
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
            if (boardState.inBounds(locCheck) && boardState.getPiece(locCheck) == '.') // Check directly ahead
            {
                Coordinates locSubmit = new Coordinates(locCheck.x, locCheck.y);
                coords.add(locSubmit);
            }
            locCheck.y -= 1;
            if (boardState.inBounds(locCheck) && loc.y == 6 && boardState.getPiece(locCheck) == '.') // Check 2 spaces ahead if pawn is at starting position
            {
                Coordinates locSubmit = new Coordinates(locCheck.x, locCheck.y);
                coords.add(locSubmit);
            }
            locCheck.x = loc.x - 1;
            locCheck.y = loc.y - 1;
            if (boardState.inBounds(locCheck) && boardState.getColor(locCheck) == 'B') // Check if attackable square to the left is black
            {
                Coordinates locSubmit = new Coordinates(locCheck.x, locCheck.y);
                coords.add(locSubmit);
            }
            locCheck.x += 2;
            if (boardState.inBounds(locCheck) && boardState.getColor(locCheck) == 'B') // Check if attackable square to the right is black
            {
                Coordinates locSubmit = new Coordinates(locCheck.x, locCheck.y);
                coords.add(locSubmit);
            }
        }
        else // Black pawn check
        {
            Coordinates locCheck = new Coordinates(loc.x, loc.y + 1);
            if (boardState.inBounds(locCheck) && boardState.getPiece(locCheck) == '.') // Check directly ahead
            {
                Coordinates locSubmit = new Coordinates(locCheck.x, locCheck.y);
                coords.add(locSubmit);
            }
            locCheck.y += 1;
            if (boardState.inBounds(locCheck) && loc.y == 1 && boardState.getPiece(locCheck) == '.') // Check 2 spaces ahead if pawn is at starting position
            {
                Coordinates locSubmit = new Coordinates(locCheck.x, locCheck.y);
                coords.add(locSubmit);
            }
            locCheck.x = loc.x + 1;
            locCheck.y = loc.y + 1;
            if (boardState.inBounds(locCheck) && boardState.getColor(locCheck) == 'B') // Check if attackable square to the right is white
            {
                Coordinates locSubmit = new Coordinates(locCheck.x, locCheck.y);
                coords.add(locSubmit);
            }
            locCheck.x -= 2;
            if (boardState.inBounds(locCheck) && boardState.getColor(locCheck) == 'B') // Check if attackable square to the left is white
            {
                Coordinates locSubmit = new Coordinates(locCheck.x, locCheck.y);
                coords.add(locSubmit);
            }
        }
        return coords;
    }
}
