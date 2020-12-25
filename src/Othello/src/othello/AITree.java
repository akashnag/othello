package othello;

class AITree 
{
    private AITreeNode roots[];
    
    public AITree(Board board, GameController game)
    {
        int n = game.countPlayerMoves(board);
        //System.out.println("Root: "+n+" nodes");
        roots = new AITreeNode[n];
        
        Square x[] = game.getPlayerMoves(board);
        for(int i=0; i<n; i++)
        {
            Board xBoard = new Board(board);
            GameController xGame = new GameController(game);
            
            AIMove m = new AIMove(xBoard, x[i], xGame);
            roots[i] = new AITreeNode(m,0);
        }
    }
    
    public Square getBestMove()
    {
        int max = 1;
        AIScore best = null;
        
        for(int i=0; i<roots.length; i++)
        {
            AIScore ais = roots[i].getMoveScore(false);
            //System.out.println(ais.score);
            if(ais.score > max)
            {
                max=ais.score;
                best = new AIScore(ais);
            }
        }
        
        if(best==null) return(null);
        return(new Square(best.move));
    }
}
