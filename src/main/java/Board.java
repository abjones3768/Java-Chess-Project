package src.main.java;
import java.util.ArrayList;

public class Board {
    // Piece data
    public Pawn pawnData = new Pawn();
    public Knight knightData = new Knight();
    public Rook rookData = new Rook();
    public Bishop bishopData = new Bishop();
    public Queen queenData = new Queen();
    public King kingData = new King();

    // Game state data (public visibility for convenience)
    public char turnChar = 'W'; // CHANGE TO WHITE AFTER ENGINE
    public boolean whiteKingMoved = false;
    public boolean blackKingMoved = false;
    public boolean leftWhiteRookMoved = false;
    public boolean rightWhiteRookMoved = false;
    public boolean leftBlackRookMoved = false;
    public boolean rightBlackRookMoved = false;
    public boolean[] enPassant = new boolean[8];
    public boolean checkState = false;

    // Board data
    private char[][] boardPieces = {{'R', 'N', 'B', 'Q', 'K', 'B', 'N', 'R'},
                                    {'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P'},
                                    {'.', '.', '.', '.', '.', '.', '.', '.'},
                                    {'.', '.', '.', '.', '.', '.', '.', '.'},
                                    {'.', '.', '.', '.', '.', '.', '.', '.'},
                                    {'.', '.', '.', '.', '.', '.', '.', '.'},
                                    {'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P'},
                                    {'R', 'N', 'B', 'Q', 'K', 'B', 'N', 'R'}};
    // Arrays are column major order, 0 indexed, and the origin is in the top left
    // Arrays must be accessed as [y][x], but getMove() deals with 0 index and location adjustment
    // As a result, pieces can be referenced from the frontend using digits the same way as chess notation

    private char[][] boardColors = {{'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B'},
                                    {'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B'},
                                    {'.', '.', '.', '.', '.', '.', '.', '.'},
                                    {'.', '.', '.', '.', '.', '.', '.', '.'},
                                    {'.', '.', '.', '.', '.', '.', '.', '.'},
                                    {'.', '.', '.', '.', '.', '.', '.', '.'},
                                    {'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W'},
                                    {'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W'}};
    // Array defining piece colors
    // Moved when other pieces are moved
    // Used to validate which pieces can be chosen in what scenario

    // Default constructor
    public Board()
    {
        return;
    }

    // Copy constructor
    public Board(Board rhs)
    {
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                this.boardPieces[i][j] = rhs.boardPieces[i][j];
                this.boardColors[i][j] = rhs.boardColors[i][j];
            }
        }
    }

    // Board Methods
    public void movePiece(Coordinates source, Coordinates dest)
    {
        Coordinates passantcheck = new Coordinates(dest.x, dest.y + 1);
        if (getPiece(source) == 'P' && getColor(source) == 'W' && getPiece(dest) == '.' && getPiece(passantcheck) == 'P' && enPassant[dest.x]) // White en passant check
        {
            boardPieces[passantcheck.y][passantcheck.x] = '.'; // Delete piece
            boardColors[passantcheck.y][passantcheck.x] = '.'; // Delete color
        }
        passantcheck.y -= 2;
        if (getPiece(source) == 'P' && getColor(source) == 'B' && getPiece(dest) == '.' && getPiece(passantcheck) == 'P' && enPassant[dest.x]) // Black en passant check
        {
            boardPieces[passantcheck.y][passantcheck.x] = '.'; // Delete piece
            boardColors[passantcheck.y][passantcheck.x] = '.'; // Delete color
        }
        resetPassant();
        if (getPiece(source) == 'P' && (dest.y > source.y + 1 || dest.y < source.y - 1)) // En passant check
        {
            enPassant[source.x] = true;
        }
        if (getPiece(source) == 'R') // Rook movement check
        {
            if (getColor(source) == 'W')
            {
                if (source.x == 0) leftWhiteRookMoved = true;
                if (source.x == 7) rightWhiteRookMoved = true;
            }
            else
            {
                if (source.x == 0) leftBlackRookMoved = true;
                if (source.x == 7) rightBlackRookMoved = true;
            }
        }
        if (getPiece(source) == 'K') // King movement check
        {
            if (getColor(source) == 'W') 
            {
                whiteKingMoved = true;
                if (dest.x > source.x + 1)
                {
                    rightWhiteRookMoved = true;
                }
                if (dest.x < source.x - 1)
                {
                    leftWhiteRookMoved = true;
                }
            }
            else 
            {
                blackKingMoved = true;
                if (dest.x > source.x + 1)
                {
                    rightBlackRookMoved = true;
                }
                if (dest.x < source.x - 1)
                {
                    leftBlackRookMoved = true;
                }
            }
        }
        if (getPiece(source) == 'K' && (source.x + 1 < dest.x || source.x - 1 > dest.x)) // Castling check
            {
                Coordinates newSource = new Coordinates(source.x, source.y);
                Coordinates newDest = new Coordinates(dest.x, dest.y);
                if (newDest.x == 6)
                {
                    newDest.x = 5;
                    newSource.x = 7;
                }
                if (newDest.x == 2)
                {
                    newDest.x = 3;
                    newSource.x = 0;
                }
                if (getPiece(newSource) == 'R') // Rook movement check
                {
                    if (getColor(newSource) == 'W')
                    {
                        if (newSource.x == 0) leftWhiteRookMoved = true;
                        if (newSource.x == 7) rightWhiteRookMoved = true;
                    }
                    else
                    {
                        if (newSource.x == 0) leftBlackRookMoved = true;
                        if (newSource.x == 7) rightBlackRookMoved = true;
                    }
                }
                // Manually move rook because the engine somehow got in an infinite loop when i recursively called here
                boardPieces[newDest.y][newDest.x] = boardPieces[newSource.y][newSource.x]; // Copy piece
                boardColors[newDest.y][newDest.x] = boardColors[newSource.y][newSource.x]; // Copy color
                boardPieces[newSource.y][newSource.x] = '.'; // Delete piece
                boardColors[newSource.y][newSource.x] = '.'; // Delete color
            }
        boardPieces[dest.y][dest.x] = boardPieces[source.y][source.x]; // Copy piece
        boardColors[dest.y][dest.x] = boardColors[source.y][source.x]; // Copy color
        boardPieces[source.y][source.x] = '.'; // Delete piece
        boardColors[source.y][source.x] = '.'; // Delete color
    }
    public boolean isCheck(char color)
    {
        Coordinates locCheck = new Coordinates();
        ArrayList<Coordinates> coords = new ArrayList<>();
        ArrayList<Coordinates> check = new ArrayList<>();
        if (color == 'W')
        {
            for (int i = 0; i < 8; i++) // White check
            {
                for (int j = 0; j < 8; j++)
                {
                    locCheck.x = j;
                    locCheck.y = i;
                    if (getColor(locCheck) == 'B')
                    {
                        switch (getPiece(locCheck))
                        {
                            case 'P':
                                check = pawnData.getAttackingMoves(locCheck, this);
                                break;
                            case 'N':
                                check = knightData.getValidMoves(locCheck, this);
                                break;
                            case 'R':
                                check = rookData.getValidMoves(locCheck, this);
                                break;
                            case 'B':
                                check = bishopData.getValidMoves(locCheck, this);
                                break;
                            case 'Q':
                                check = queenData.getValidMoves(locCheck, this);
                                break;
                            case 'K':
                                kingData.recurse = false;
                                check = kingData.getValidMoves(locCheck, this);
                                break;
                            default:
                                break;
                        }
                    }
                    if (!check.isEmpty())
                    {
                        for (int k = 0; k < check.size(); k++)
                        {
                            coords.add(check.get(k));
                        }
                    }
                }
            }
            for (int i = 0; i < coords.size(); i++)
            {
                if (inBounds(coords.get(i)) && (getPiece(coords.get(i)) == 'K' && getColor(coords.get(i)) == 'W'))
                {
                    checkState = true;
                    return true;
                }
            }
        }
        if (color == 'B')
        {
            for (int i = 0; i < 8; i++) // Black check
            {
                for (int j = 0; j < 8; j++)
                {
                    locCheck.x = j;
                    locCheck.y = i;
                    if (getColor(locCheck) == 'W')
                    {
                        switch (getPiece(locCheck))
                        {
                            case 'P':
                                check = pawnData.getAttackingMoves(locCheck, this);
                                break;
                            case 'N':
                                check = knightData.getValidMoves(locCheck, this);
                                break;
                            case 'R':
                                check = rookData.getValidMoves(locCheck, this);
                                break;
                            case 'B':
                                check = bishopData.getValidMoves(locCheck, this);
                                break;
                            case 'Q':
                                check = queenData.getValidMoves(locCheck, this);
                                break;
                            case 'K':
                                kingData.recurse = false;
                                check = kingData.getValidMoves(locCheck, this);
                                break;
                            default:
                                break;
                        }
                    }
                    if (!check.isEmpty())
                    {
                        for (int k = 0; k < check.size(); k++)
                        {
                            coords.add(check.get(k));
                        }
                    }
                }
            }
            for (int i = 0; i < coords.size(); i++)
            {
                if (inBounds(coords.get(i)) && (getPiece(coords.get(i)) == 'K' && getColor(coords.get(i)) == 'B'))
                {
                    checkState = true;
                    return true;
                }
            }
        }
        checkState = false;
        return false;
    }
    public boolean moveExists(char color)
    {
        ArrayList<Coordinates> check = new ArrayList<>();
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                Coordinates source = new Coordinates(i, j);
                if(this.getColor(source) == color)
                {
                    switch (this.getPiece(source))
                    {
                        case 'P':
                            check = this.pawnData.getValidMoves(source, this);
                            break;
                        case 'N':
                            check = this.knightData.getValidMoves(source, this);
                            break;
                        case 'R':
                            check = this.rookData.getValidMoves(source, this);
                            break;
                        case 'B':
                            check = this.bishopData.getValidMoves(source, this);
                            break;
                        case 'Q':
                            check = this.queenData.getValidMoves(source, this);
                            break;
                        case 'K':
                            check = this.kingData.getValidMoves(source, this);
                            break;
                        default:
                            System.exit(64);
                    }
                    for (int k = 0; k < check.size(); k++)
                    {
                        Coordinates dest = check.get(k);
                        if (!this.considerCheck(source, dest))
                        {
                            check.remove(dest);
                            k--;
                        }
                    }
                    if (!check.isEmpty()) return true;
                }
            }
        }
        return false;
    }
    public char getColor(Coordinates coord) // Returns corresponding color
    {
        return boardColors[coord.y][coord.x];
    }
    public char getPiece(Coordinates coord) // Returns corresponding piece
    {
        return boardPieces[coord.y][coord.x];
    }
    public boolean inBounds(Coordinates coord) // Bounds checking
    {
        if (coord.x > -1 && coord.x < 8 && coord.y > -1 && coord.y < 8) return true;
        else return false;
    }
    public void promotePawn(Coordinates coord, char piece)
    {
        boardPieces[coord.y][coord.x] = piece;
        return;
    }
    public boolean considerCheck(Coordinates source, Coordinates dest) // Returns false if move would result in check
    {
        Board nextBoard = new Board(this);
        nextBoard.movePiece(source, dest);
        if (nextBoard.isCheck(nextBoard.getColor(dest))) 
        {
            return false;
        }
        return true;
    }

    private void resetPassant()
    {
        for (int i = 0; i < 8; i++)
        {
            enPassant[i] = false;
        }
        return;
    }
}

