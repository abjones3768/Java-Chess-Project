package src.main.java;
import java.util.ArrayList;

public class Rook extends Piece
{
    void Piece()
    {
        pieceChar = 'R';
    }
    public ArrayList<Coordinates> getValidMoves(final Coordinates loc, final Board boardState)
    {
        /* Movement pattern:
        Moves in a straight line
        */
        ArrayList<Coordinates> coords = new ArrayList<>();
        char color = boardState.getColor(loc);
        char oppColor;
        if (color == 'W') oppColor = 'B';
        else oppColor = 'W';

        boolean finished = false;
        Coordinates locCheck = new Coordinates(loc.x - 1, loc.y);
        while(!finished) // Left check
        {
            // If current check is in bounds and check location has no piece
            if (boardState.inBounds(locCheck) && boardState.getColor(locCheck) == '.')
            {
                Coordinates locSubmit = new Coordinates(locCheck.x, locCheck.y);
                coords.add(locSubmit);
                locCheck.x--;
            }
            // If current check is in bounds and check location is opponent piece
            else if (boardState.inBounds(locCheck) && boardState.getColor(locCheck) == oppColor)
            {
                Coordinates locSubmit = new Coordinates(locCheck.x, locCheck.y);
                coords.add(locSubmit);
                finished = true;
            }
            // At this point, check is either not in bounds or a piece of the same color has been found.
            // Either way, terminate loop.
            else finished = true;
        }

        finished = false;
        locCheck = new Coordinates(loc.x + 1, loc.y);
        while(!finished) // Right check
        {
            // If current check is in bounds and check location has no piece
            if (boardState.inBounds(locCheck) && boardState.getColor(locCheck) == '.')
            {
                Coordinates locSubmit = new Coordinates(locCheck.x, locCheck.y);
                coords.add(locSubmit);
                locCheck.x++;
            }
            // If current check is in bounds and check location is opponent piece
            else if (boardState.inBounds(locCheck) && boardState.getColor(locCheck) == oppColor)
            {
                Coordinates locSubmit = new Coordinates(locCheck.x, locCheck.y);
                coords.add(locSubmit);
                finished = true;
            }
            // At this point, check is either not in bounds or a piece of the same color has been found.
            // Either way, terminate loop.
            else finished = true;
        }

        finished = false;
        locCheck = new Coordinates(loc.x, loc.y + 1);
        while(!finished) // Down check
        {
            // If current check is in bounds and check location has no piece
            if (boardState.inBounds(locCheck) && boardState.getColor(locCheck) == '.')
            {
                Coordinates locSubmit = new Coordinates(locCheck.x, locCheck.y);
                coords.add(locSubmit);
                locCheck.y++;
            }
            // If current check is in bounds and check location is opponent piece
            else if (boardState.inBounds(locCheck) && boardState.getColor(locCheck) == oppColor)
            {
                Coordinates locSubmit = new Coordinates(locCheck.x, locCheck.y);
                coords.add(locSubmit);
                finished = true;
            }
            // At this point, check is either not in bounds or a piece of the same color has been found.
            // Either way, terminate loop.
            else finished = true;
        }

        finished = false;
        locCheck = new Coordinates(loc.x, loc.y - 1);
        while(!finished) // Up check
        {
            // If current check is in bounds and check location has no piece
            if (boardState.inBounds(locCheck) && boardState.getColor(locCheck) == '.')
            {
                Coordinates locSubmit = new Coordinates(locCheck.x, locCheck.y);
                coords.add(locSubmit);
                locCheck.y--;
            }
            // If current check is in bounds and check location is opponent piece
            else if (boardState.inBounds(locCheck) && boardState.getColor(locCheck) == oppColor)
            {
                Coordinates locSubmit = new Coordinates(locCheck.x, locCheck.y);
                coords.add(locSubmit);
                finished = true;
            }
            // At this point, check is either not in bounds or a piece of the same color has been found.
            // Either way, terminate loop.
            else finished = true;
        }
        return coords;
    }
}
