package othello;

class AI 
{
    public static Square getAIMove(Board board, GameController game)
    {
        AITree tree = new AITree(board,game);
        return(tree.getBestMove());
    }
}
