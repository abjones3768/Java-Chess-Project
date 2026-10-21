package src.main.java;
import java.util.ArrayList;

public class Queen extends Piece
{
    void Piece()
    {
        pieceChar = 'Q';
    }
    public ArrayList<Coordinates> getValidMoves(final Coordinates loc, final Board boardState)
    {
        /* Movement pattern:
        Moves in a straight line or diagonally
        */
        // Since a queen is just a rook and a bishop, steal their movement logic.
        ArrayList<Coordinates> coords = new ArrayList<>();
        Rook rook = new Rook();
        Bishop bishop = new Bishop();

        // Get rook moves
        coords = rook.getValidMoves(loc, boardState);

        // Get bishop moves
        ArrayList<Coordinates> coords2 = bishop.getValidMoves(loc, boardState);

        // Iterate through bishop moves and add them to rook moves
        for (int i = 0; i < coords2.size(); i++)
        {
            coords.add(coords2.get(i));
        }
        
        return coords;
    }
}
