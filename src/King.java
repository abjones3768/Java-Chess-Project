package src;
import java.util.ArrayList;

public class King extends Piece
{
    void Piece()
    {
        pieceChar = 'K';
    }
    public ArrayList<Coordinates> getValidMoves(final Coordinates loc, final Board boardState)
    {
        /* Movement pattern:
        Moves one square in any direction
        Cannot move to a square that a piece can attack on the next turn
        Can castle
        Cannot castle through an attacking pieces line of sight
        */
        ArrayList<Coordinates> coords = new ArrayList<>();
        // this is gonna be stupid
        // First, build a list of all the squares around the king
        // Include castling here (uses flag in board state for whether a king has moved yet)
        // Then, iterate through every piece on the board and get their valid moves
        // If any overlap, remove those from the list
        // Make sure to have a special case for doing this with check.
        // Also be sure to have special case for pawns since they cant capture moving forward
        // Also also pawn en passant
        
        return coords;
    }
}
