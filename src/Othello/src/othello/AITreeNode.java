package othello;

public class AITreeNode 
{
    public static final int LEVEL_CUTOFF = 2;
    
    private AIMove data;
    private AITreeNode children[];
    private int level;
    
    public AITreeNode(AIMove m, int level)
    {
        this.level = level;
        data = new AIMove(m);
        children = null;
        
        if(level<LEVEL_CUTOFF) computeChildren();
    }
    
    private void computeChildren()
    {
        int n = data.game.countPlayerMoves(data.board);
        children = new AITreeNode[n];
        
        Square x[] = data.game.getPlayerMoves(data.board);
        for(int i=0; i<n; i++)
        {
            Board xBoard = new Board(data.board);
            GameController game = new GameController(data.game);
                        
            AIMove m = new AIMove(xBoard, x[i], game);
            children[i] = new AITreeNode(m,level+1);
        }
    }
    
    public AIScore getMoveScore(boolean max)
    {
        //System.out.println("["+level+"] getMoveScore("+max+"): Child: "+(children==null ? "None": children.length)+", score: "+data.score+", move: "+data.move);
        if(children==null) return(new AIScore(data.move, data.score));
        
        int optScore = (max ? 1 : Integer.MAX_VALUE);
        AIScore optMove = null;
        
        for(int i=0; i<children.length; i++)
        {
            AIScore ais = children[i].getMoveScore(!max);
            if(ais==null) continue;
            //System.out.println("Score:"+ais.score);
            if((max && ais.score>optScore) || (!max && ais.score<optScore))
            {
                optScore = ais.score;
                optMove = new AIScore(ais);
            }
        }
        
        return(optMove);
    }
}
