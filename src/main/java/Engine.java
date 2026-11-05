package src.main.java;
import src.main.java.Coordinates;
import src.main.java.Board;

import java.util.ArrayList;

// Full class dedicated to the engine
// From the outside, simply call getMove(), pass in board state, recieve a move.
public class Engine 
{
    int depth = 1;
    public Engine(int newDepth)
    {
        if (newDepth > 1) depth = newDepth;
    }

    // Theory: Iterate through each move of each piece, call branchRecurse with one of those moves,
    // branchRecurse calls itself with decreasing depth, doing every move in the current position.
    public Coordinates getMove(final Board board)
    {
        ArrayList<ArrayList<Coordinates>> moves = new ArrayList<>();
        ArrayList<Coordinates> sources = new ArrayList<>();
        ArrayList<branchData> branches = new ArrayList<>();
        int k = 0;
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                Coordinates source = new Coordinates(i, j);
                if (board.getColor(source) != board.turnChar) continue;
                ArrayList<Coordinates> check = new ArrayList<>();
                boolean none = false;
                switch (board.getPiece(source))
                {
                    case 'P':
                        check = board.pawnData.getValidMoves(source, board);
                        sources.add(source);
                        break;
                    case 'N':
                        check = board.knightData.getValidMoves(source, board);
                        sources.add(source);
                        break;
                    case 'R':
                        check = board.rookData.getValidMoves(source, board);
                        sources.add(source);
                        break;
                    case 'B':
                        check = board.bishopData.getValidMoves(source, board);
                        sources.add(source);
                        break;
                    case 'Q':
                        check = board.queenData.getValidMoves(source, board);
                        sources.add(source);
                        break;
                    case 'K':
                        check = board.kingData.getValidMoves(source, board);
                        sources.add(source);
                        break;
                    default:
                        none = true;
                        break;
                }
                if (none) break;
                moves.add(check);
            }
        }
        int x = 0;
        for (int i = 0; i < moves.size(); i++)
        {
            for (int j = 0; j < moves.get(i).size(); j++)
            {
                System.out.println("Checking branch " + x);
                x++;
                branches.add(branchRecurse(board, this.depth, sources.get(k), moves.get(i).get(j)));
            }
            k++;
        }
        for (int i = 0; i < branches.size(); i++)
        {
            System.out.println("Branch " + i + ": " + branches.get(i).checkmates + " checkmates and " + branches.get(i).points + " points.");
        }
        Coordinates lol = new Coordinates(1 , 2);
        return lol;
    }

    public branchData branchRecurse(Board board, int curDepth, Coordinates source, Coordinates dest)
    {
        branchData branch = new branchData();
        Board nextBoard = new Board(board);
        switch (nextBoard.getPiece(dest))
        {
            case 'P':
                if (nextBoard.turnChar == 'W') branch.points += 1;
                else branch.points -= 1;
                break;
            case 'N':
                if (nextBoard.turnChar == 'W') branch.points += 3;
                else branch.points -= 3;
                break;
            case 'R':
                if (nextBoard.turnChar == 'W') branch.points += 5;
                else branch.points -= 5;
                break;
            case 'B':
                if (nextBoard.turnChar == 'W') branch.points += 3;
                else branch.points -= 3;
                break;
            case 'Q':
                if (nextBoard.turnChar == 'W') branch.points += 9;
                else branch.points -= 9;
                break;
            default:
                break;
        }
        nextBoard.movePiece(source, dest);
        if (nextBoard.getColor(dest) == 'W') nextBoard.turnChar = 'B';
        else nextBoard.turnChar = 'W';
        if (!nextBoard.moveExists('W') || !nextBoard.moveExists('B')) 
        {
            branchData newBranch = new branchData();
            newBranch.points += branch.points;
            if (board.turnChar == 'W') newBranch.checkmates = 1;
            else newBranch.checkmates = -1;
            return newBranch;
        }
        if (curDepth > 1)
        {
            curDepth--;
            ArrayList<ArrayList<Coordinates>> moves = new ArrayList<>();
            ArrayList<Coordinates> sources = new ArrayList<>();
            int k = 0;
            for (int i = 0; i < 8; i++)
            {
                for (int j = 0; j < 8; j++)
                {
                    Coordinates newSource = new Coordinates(i, j);
                    if (board.getColor(newSource) != nextBoard.turnChar) continue;
                    ArrayList<Coordinates> check = new ArrayList<>();
                    boolean none = false;
                    switch (nextBoard.getPiece(newSource))
                    {
                        case 'P':
                            check = nextBoard.pawnData.getValidMoves(newSource, nextBoard);
                            sources.add(newSource);
                            break;
                        case 'N':
                            check = nextBoard.knightData.getValidMoves(newSource, nextBoard);
                            sources.add(newSource);
                            break;
                        case 'R':
                            check = nextBoard.rookData.getValidMoves(newSource, nextBoard);
                            sources.add(newSource);
                            break;
                        case 'B':
                            check = nextBoard.bishopData.getValidMoves(newSource, nextBoard);
                            sources.add(newSource);
                            break;
                        case 'Q':
                            check = nextBoard.queenData.getValidMoves(newSource, nextBoard);
                            sources.add(newSource);
                            break;
                        case 'K':
                            check = nextBoard.kingData.getValidMoves(newSource, nextBoard);
                            sources.add(newSource);
                            break;
                        default:
                            none = true;
                            break;
                    }
                    if (none) break;
                    moves.add(check);
                }
            }
            int x = 0;
            for (int i = 0; i < moves.size(); i++)
            {
                for (int j = 0; j < moves.get(i).size(); j++)
                {
                    branchData newBranch = branchRecurse(nextBoard, curDepth, sources.get(k), moves.get(i).get(j));
                    branch.points += newBranch.points;
                    branch.checkmates += newBranch.checkmates;
                }
                k++;
            }
        }
        return branch;
    }

    class branchData
    {
        // Number of points accumulated in a branch.
        // Points are accumulated based on standard piece values, where pawn is 1, bishop is 3, etc.
        // Number of checkmates have higher priority, so points is used as a tiebreaker in most cases
        public int points;
        // Number of checkmates found in a branch
        public int checkmates;
    }
}
