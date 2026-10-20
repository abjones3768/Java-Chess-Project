package src;
import java.util.ArrayList;

public class King extends Piece
{
    private boolean recurse = true;
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
        char color = boardState.getColor(loc);
        Coordinates locCheck = new Coordinates(loc.x, loc.y);
        locCheck.x += 1;
        if (boardState.inBounds(locCheck) && boardState.getColor(locCheck) != color) // Right check
        {
            Coordinates locAdd = new Coordinates(locCheck.x, locCheck.y);
            coords.add(locAdd);
        }
        locCheck.y += 1;
        if (boardState.inBounds(locCheck) && boardState.getColor(locCheck) != color) // Down right check
        {
            Coordinates locAdd = new Coordinates(locCheck.x, locCheck.y);
            coords.add(locAdd);
        }
        locCheck.x -= 1;
        if (boardState.inBounds(locCheck) && boardState.getColor(locCheck) != color) // Down check
        {
            Coordinates locAdd = new Coordinates(locCheck.x, locCheck.y);
            coords.add(locAdd);
        }
        locCheck.x -= 1;
        if (boardState.inBounds(locCheck) && boardState.getColor(locCheck) != color) // Down left check
        {
            Coordinates locAdd = new Coordinates(locCheck.x, locCheck.y);
            coords.add(locAdd);
        }
        locCheck.y -= 1;
        if (boardState.inBounds(locCheck) && boardState.getColor(locCheck) != color) // Left check
        {
            Coordinates locAdd = new Coordinates(locCheck.x, locCheck.y);
            coords.add(locAdd);
        }
        locCheck.y -= 1;
        if (boardState.inBounds(locCheck) && boardState.getColor(locCheck) != color) // Up left check
        {
            Coordinates locAdd = new Coordinates(locCheck.x, locCheck.y);
            coords.add(locAdd);
        }
        locCheck.x += 1;
        if (boardState.inBounds(locCheck) && boardState.getColor(locCheck) != color) // Up check
        {
            Coordinates locAdd = new Coordinates(locCheck.x, locCheck.y);
            coords.add(locAdd);
        }
        locCheck.x += 1;
        if (boardState.inBounds(locCheck) && boardState.getColor(locCheck) != color) // Right check
        {
            Coordinates locAdd = new Coordinates(locCheck.x, locCheck.y);
            coords.add(locAdd);
        }
        if (color == 'W') // White castling check
        {
            if (!boardState.whiteKingMoved)
            {
                if (!boardState.leftWhiteRookMoved)
                {
                    locCheck.x = loc.x - 1;
                    locCheck.y = loc.y;
                    if (boardState.getPiece(locCheck) == '.')
                    {
                        locCheck.x -= 1;
                        if (boardState.getPiece(locCheck) == '.')
                        {
                            Coordinates locAdd = new Coordinates(locCheck.x, locCheck.y);
                            coords.add(locAdd);
                        }
                    }
                }
                if (!boardState.rightWhiteRookMoved)
                {
                    locCheck.x = loc.x + 1;
                    locCheck.y = loc.y;
                    if (boardState.getPiece(locCheck) == '.')
                    {
                        locCheck.x += 1;
                        if (boardState.getPiece(locCheck) == '.')
                        {
                            Coordinates locAdd = new Coordinates(locCheck.x, locCheck.y);
                            coords.add(locAdd);
                        }
                    }
                }
            }
        }
        else if (color == 'B') // Black castling check
        {
            if (!boardState.blackKingMoved)
            {
                if (!boardState.leftBlackRookMoved)
                {
                    locCheck.x = loc.x - 1;
                    locCheck.y = loc.y;
                    if (boardState.getPiece(locCheck) == '.')
                    {
                        locCheck.x -= 1;
                        if (boardState.getPiece(locCheck) == '.')
                        {
                            Coordinates locAdd = new Coordinates(locCheck.x, locCheck.y);
                            coords.add(locAdd);
                        }
                    }
                }
                if (!boardState.rightBlackRookMoved)
                {
                    locCheck.x = loc.x + 1;
                    locCheck.y = loc.y;
                    if (boardState.getPiece(locCheck) == '.')
                    {
                        locCheck.x += 1;
                        if (boardState.getPiece(locCheck) == '.')
                        {
                            Coordinates locAdd = new Coordinates(locCheck.x, locCheck.y);
                            coords.add(locAdd);
                        }
                    }
                }
            }
        }
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                locCheck.x = j;
                locCheck.y = i;
                ArrayList<Coordinates> check = new ArrayList<>();
                if (boardState.getColor(locCheck) == 'B')
                {
                    switch (boardState.getPiece(locCheck))
                    {
                        case 'P':
                            check = boardState.pawnData.getAttackingMoves(locCheck, boardState);
                            break;
                        case 'N':
                            check = boardState.knightData.getValidMoves(locCheck, boardState);
                            break;
                        case 'R':
                            check = boardState.rookData.getValidMoves(locCheck, boardState);
                            break;
                        case 'B':
                            check = boardState.bishopData.getValidMoves(locCheck, boardState);
                            break;
                        case 'Q':
                            check = boardState.queenData.getValidMoves(locCheck, boardState);
                            break;
                        case 'K':
                            if (recurse)
                            {
                                recurse = false;
                                check = boardState.kingData.getValidMoves(locCheck, boardState);
                            }
                            break;
                        default:
                            break;
                    }
                }
                for (int k = 0; k < check.size(); k++) 
                {
                    for (int l = 0; l < coords.size(); l++) // quad nested for loop
                    {
                        if (check.get(k).x == coords.get(l).x && check.get(k).y == coords.get(l).y)
                        {
                            coords.remove(l);
                        }
                    }
                }
            }
        }
        recurse = true;
        // Make sure to have a special case for doing this with check.
        
        return coords;
    }
}
