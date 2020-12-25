package othello;

import java.util.HashSet;

class GameController 
{
    public boolean animate;
    private OthelloApp app;
    
    private Player currentPlayer;
    private Board board;
    private Queue queue;
    
    public GameController(GameController g)
    {
        this.currentPlayer = g.currentPlayer;
        this.queue = null;
        this.board = new Board(g.board);
    }
    
    public GameController(OthelloApp a, Player p)
    {
        app=a;
        currentPlayer=p;
    }
    
    public Board getModifiedBoard()
    {
        return(board);
    }
    
    public Player getCurrentTurn()
    {
        return(currentPlayer);
    }
    
    public boolean canPlayerMove(Board b)
    {
        int n = b.getSize();
        for(int i=0; i<n; i++)
        {
            for(int j=0; j<n; j++)
            {
                Board xBoard = new Board(b);
                if(playerMove(xBoard, new Square(i,j), false)>1) return(true);
            }
        }
        
        return(false);
    }
    
    public int countPlayerMoves(Board b)
    {
        int n = b.getSize(), c=0;
        for(int i=0; i<n; i++)
        {
            for(int j=0; j<n; j++)
            {
                Board xBoard = new Board(b);
                Square s = new Square(i,j);
                
                if(!xBoard.isBlank(s)) continue;
                if(playerMove(xBoard, s, false)>1) c++;
            }
        }
        
        return(c);
    }
    
    public Square[] getPlayerMoves(Board b)
    {
        Square x[] = new Square[countPlayerMoves(b)];
        int n = b.getSize(), k = -1;
        for(int i=0; i<n; i++)
        {
            for(int j=0; j<n; j++)
            {
                Board xBoard = new Board(b);
                Square s = new Square(i,j);
                xBoard.updateSquare(s);
                
                if(!xBoard.isBlank(s)) continue;
                if(playerMove(xBoard, s, false)>1) x[++k]=s;
            }
        }
        return x;
    }
    
    public boolean canPlayerMove(Board b, Square s)
    {
        Board xBoard = new Board(b);
        if(playerMove(xBoard, s, false)>1) return(true);
        return(false);
    }
    
    public int playerMove(Board b, Square s, boolean switchTurn)
    {
        this.board = b;
        this.board.updateSquare(s);
        
        if(!s.isBlank()) return 0;        
        b.setContent(s, currentPlayer);
        if(animate)
        {
            app.forceRedraw();
            try {
                Thread.sleep(1000);
            } catch(Exception e) { }
        }
        
        int colorCount = enumerateSquares(s);
        
        if(switchTurn) switchTurn();
        
        return colorCount+1;
    }
    
    public Player getWinner(Board b)
    {
        int x[]=getPlayerCounts(b);
        int wc = x[0], bc = x[1];
        return( wc == bc ? null : (wc > bc ? Player.White : Player.Black));
    }
    
    public int[] getPlayerCounts(Board b)
    {
        int bc = 0, wc = 0;
        int n = b.getSize();
        for(int i=0; i<n; i++)
        {
            for(int j=0; j<n; j++)
            {
                Square sc = b.getSquare(i, j);
                if(sc.content==SquareContent.Black) bc++;
                if(sc.content==SquareContent.White) wc++;
            }
        }
        
        return(new int[]{ wc, bc });
    }
    
    public void switchTurn()
    {
        currentPlayer = (currentPlayer == Player.Black ? Player.White : Player.Black);
    }
    
    private int enumerateSquares(Square s)
    {
        if(!s.isBlank()) return 0;
        
        int colorCount=0;
        Square nb[] = getNeighbours(s);
        for(int i=0; i<nb.length; i++)
        {
            if(nb[i]==null) continue;
            
            if(canProceed(nb[i], Direction.values()[i]) && !board.isBlank(nb[i])) 
            {
                while(!queue.isEmpty()) 
                {
                    colorCount++;
                    board.setContent(queue.retrieve(),currentPlayer);
                    if(animate)
                    {
                        app.forceRedraw();
                        try {
                            Thread.sleep(1000);
                        } catch(Exception e) { }
                    }
                }
            }                
        }
        
        return colorCount;
    }
    
    private boolean canProceed(Square s, Direction dir)
    {
        int row = s.row, col = s.col;
                
        if(board.isBlank(row,col)) return(false);
        
        // TopLeft, Left, BottomLeft, Top, Bottom, TopRight, Right, BottomRight;
        int rowOffsets[] = { -1, 0, 1, -1, 1, -1, 0, 1 };
        int colOffsets[] = { -1, -1, -1, 0, 0, 1, 1, 1 };
        
        int ro = rowOffsets[dir.ordinal()];
        int co = colOffsets[dir.ordinal()];
        
        queue = new Queue(board.getSize() * board.getSize());
        
        while(board.isValid(row, col))
        {
            Square sq=board.getSquare(row, col);
            
            if(sq.isBlank()) return(false);
            if(sq.areSame(currentPlayer)) return(true);
            
            queue.insert(sq);
            
            row+=ro;
            col+=co;
        }
        
        return(false);
    }
    
    private Square[] getNeighbours(Square s)
    {
        Square x[]=new Square[8];
        int k=-1;
        
        for(int c=-1; c<=1; c++)
        {
            for(int r=-1; r<=1; r++)
            {
                if(c==0 && r==0) continue;
                x[++k]=board.getSquare(s.row+r, s.col+c);
            }
        }
        
        return x;
    }
}
