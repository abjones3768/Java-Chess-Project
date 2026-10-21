package src.main.java;
import java.util.ArrayList;

public class Bishop extends Piece
{
    void Piece()
    {
        pieceChar = 'B';
    }
    public ArrayList<Coordinates> getValidMoves(final Coordinates loc, final Board boardState)
    {
        /* Movement pattern:
        Moves diagonally
        */
        ArrayList<Coordinates> coords = new ArrayList<>();
        char color = boardState.getColor(loc);
        char oppColor;
        if (color == 'W') oppColor = 'B';
        else oppColor = 'W';

        boolean finished = false;
        Coordinates locCheck = new Coordinates(loc.x - 1, loc.y - 1);
        while(!finished) // Up left check
        {
            // If current check is in bounds and check location has no piece
            if (boardState.inBounds(locCheck) && boardState.getColor(locCheck) == '.')
            {
                Coordinates locSubmit = new Coordinates(locCheck.x, locCheck.y);
                coords.add(locSubmit);
                locCheck.x--;
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

        finished = false;
        locCheck = new Coordinates(loc.x + 1, loc.y - 1);
        while(!finished) // Up right check
        {
            // If current check is in bounds and check location has no piece
            if (boardState.inBounds(locCheck) && boardState.getColor(locCheck) == '.')
            {
                Coordinates locSubmit = new Coordinates(locCheck.x, locCheck.y);
                coords.add(locSubmit);
                locCheck.x++;
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

        finished = false;
        locCheck = new Coordinates(loc.x + 1, loc.y + 1);
        while(!finished) // Down right check
        {
            // If current check is in bounds and check location has no piece
            if (boardState.inBounds(locCheck) && boardState.getColor(locCheck) == '.')
            {
                Coordinates locSubmit = new Coordinates(locCheck.x, locCheck.y);
                coords.add(locSubmit);
                locCheck.y++;
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
        locCheck = new Coordinates(loc.x - 1, loc.y + 1);
        while(!finished) // Down left check
        {
            // If current check is in bounds and check location has no piece
            if (boardState.inBounds(locCheck) && boardState.getColor(locCheck) == '.')
            {
                Coordinates locSubmit = new Coordinates(locCheck.x, locCheck.y);
                coords.add(locSubmit);
                locCheck.x--;
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
        return coords;
    }
}
