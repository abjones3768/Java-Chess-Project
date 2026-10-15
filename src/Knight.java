package src;
import java.util.ArrayList;

public class Knight extends Piece
{
    void Piece()
    {
        pieceChar = 'N';
    }
    public ArrayList<Coordinates> getValidMoves(final Coordinates loc, final Board boardState)
    {
        /* Movement pattern:
        Moves in an L shape, or two squares in one direction and one square in a perpendicular direction
        Jumps over pieces
        */
        ArrayList<Coordinates> coords = new ArrayList<>();
        if (boardState.getColor(loc) == 'W') // White knight check
        {
            Coordinates locCheck = new Coordinates(loc.x - 1, loc.y + 2);
            if (boardState.inBounds(locCheck) && boardState.getColor(locCheck) != 'W') // Up 2, left 1
            {
                Coordinates locSubmit = new Coordinates(locCheck.x, locCheck.y);
                coords.add(locSubmit);
            }

            locCheck.x += 2;
            if (boardState.inBounds(locCheck) && boardState.getColor(locCheck) != 'W') // Up 2, right 1
            {
                Coordinates locSubmit = new Coordinates(locCheck.x, locCheck.y);
                coords.add(locSubmit);
            }

            locCheck.y -= 4;
            if (boardState.inBounds(locCheck) && boardState.getColor(locCheck) != 'W') // Down 2, right 1
            {
                Coordinates locSubmit = new Coordinates(locCheck.x, locCheck.y);
                coords.add(locSubmit);
            }

            locCheck.x -= 2;
            if (boardState.inBounds(locCheck) && boardState.getColor(locCheck) != 'W') // Down 2, left 1
            {
                Coordinates locSubmit = new Coordinates(locCheck.x, locCheck.y);
                coords.add(locSubmit);
            }

            locCheck.y -= 1;
            locCheck.x -= 1;
            if (boardState.inBounds(locCheck) && boardState.getColor(locCheck) != 'W') // Down 1, left 2
            {
                Coordinates locSubmit = new Coordinates(locCheck.x, locCheck.y);
                coords.add(locSubmit);
            }

            locCheck.x += 4;
            if (boardState.inBounds(locCheck) && boardState.getColor(locCheck) != 'W') // Down 1, right 2
            {
                Coordinates locSubmit = new Coordinates(locCheck.x, locCheck.y);
                coords.add(locSubmit);
            }

            locCheck.y -= 2;
            if (boardState.inBounds(locCheck) && boardState.getColor(locCheck) != 'W') // Up 1, right 2
            {
                Coordinates locSubmit = new Coordinates(locCheck.x, locCheck.y);
                coords.add(locSubmit);
            }

            locCheck.x -= 4;
            if (boardState.inBounds(locCheck) && boardState.getColor(locCheck) != 'W') // Up 1, left 2
            {
                Coordinates locSubmit = new Coordinates(locCheck.x, locCheck.y);
                coords.add(locSubmit);
            }
        }
        else // Black knight check
        {
            
        }
        return coords;
    }
}
