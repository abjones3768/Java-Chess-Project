package src;

public class Board {
    private char boardData[][] = new char[8][8];
                                                // {'R', 'N', 'B', 'Q', 'K', 'B', 'N', 'R'},
                                                // {'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P'},
                                                // {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
                                                // {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
                                                // {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
                                                // {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
                                                // {'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P'},
                                                // {'R', 'N', 'B', 'Q', 'K', 'B', 'N', 'R'};
    public Board()
    {

    }
    public void movePiece() // Array of 2 coordinates, 0 indexed
    {
        //boardData[dest[0]][dest[1]] = boardData[source[0]][source[1]];
    }
}

