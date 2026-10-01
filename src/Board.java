package src;

public class Board {
    public char[][] boardData = {{'R', 'N', 'B', 'Q', 'K', 'B', 'N', 'R'}, //b lack
                                {'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P'},
                                {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
                                {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
                                {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
                                {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
                                {'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P'},
                                {'R', 'N', 'B', 'Q', 'K', 'B', 'N', 'R'}};  //white
                                                
    public Board()
    {

    }
    public void movePiece(int source[], int dest[]) // Array of 2 coordinates, 0 indexed
    {
        boardData[dest[0]][dest[1]] = boardData[source[0]][source[1]];
        boardData[source[0]][source[1]] = ' ';
    }
}

