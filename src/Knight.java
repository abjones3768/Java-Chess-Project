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
        char color = boardState.getColor(loc); // Get color directly, validation can easily be done based on this
        Coordinates locCheck = new Coordinates(loc.x, loc.y);

        locCheck.x -= 1;
        locCheck.y -= 2;
        if (boardState.inBounds(locCheck) && boardState.getColor(locCheck) != color) // Up 2, left 1
        {
            Coordinates locAdd = new Coordinates(locCheck.x, locCheck.y);
            coords.add(locAdd);
        }
        locCheck.x += 2;
        if (boardState.inBounds(locCheck) && boardState.getColor(locCheck) != color) // Up 2, right 1
        {
            Coordinates locAdd = new Coordinates(locCheck.x, locCheck.y);
            coords.add(locAdd);
        }
        locCheck.x += 1;
        locCheck.y += 1;
        if (boardState.inBounds(locCheck) && boardState.getColor(locCheck) != color) // Right 2, up 1
        {
            Coordinates locAdd = new Coordinates(locCheck.x, locCheck.y);
            coords.add(locAdd);
        }
        locCheck.y += 2;
        if (boardState.inBounds(locCheck) && boardState.getColor(locCheck) != color) // Right 2, down 1
        {
            Coordinates locAdd = new Coordinates(locCheck.x, locCheck.y);
            coords.add(locAdd);
        }
        locCheck.x -= 1;
        locCheck.y += 1;
        if (boardState.inBounds(locCheck) && boardState.getColor(locCheck) != color) // Down 2, right 1
        {
            Coordinates locAdd = new Coordinates(locCheck.x, locCheck.y);
            coords.add(locAdd);
        }
        locCheck.x -= 2;
        if (boardState.inBounds(locCheck) && boardState.getColor(locCheck) != color) // Down 2, left 1
        {
            Coordinates locAdd = new Coordinates(locCheck.x, locCheck.y);
            coords.add(locAdd);
        }
        locCheck.x -= 1;
        locCheck.y -= 1;
        if (boardState.inBounds(locCheck) && boardState.getColor(locCheck) != color) // Left 2, down 1
        {
            Coordinates locAdd = new Coordinates(locCheck.x, locCheck.y);
            coords.add(locAdd);
        }
        locCheck.y -= 2;
        if (boardState.inBounds(locCheck) && boardState.getColor(locCheck) != color) // left 2, up 1
        {
            Coordinates locAdd = new Coordinates(locCheck.x, locCheck.y);
            coords.add(locAdd);
        }
        return coords;
    }
}
