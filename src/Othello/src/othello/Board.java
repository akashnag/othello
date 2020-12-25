package othello;

import java.awt.*;

class Board 
{
    // constants
    public static final int SIZE = 8;    
    public static final int OFFSET_X = 20;
    public static final int OFFSET_Y = 100;
    public static final int SQUARE_SIZE = 70;
    
    public static Color squareBackground = new Color(0,126,175);
    public static Color squareBorder = new Color(97,179,204);
    public static Color panelBackground = new Color(4,26,33);
    public static Color squareHighlight = new Color(7,92,129);
    public static Color blackPiece = new Color(35,23,29);
    public static Color whitePiece = new Color(252,252,252);
    
    // data members
    private SquareContent board[][];
    
    public Board()
    {
        board=new SquareContent[SIZE][SIZE];
        for(int i=0; i<SIZE; i++)
        {
            for(int j=0; j<SIZE; j++) board[i][j]=SquareContent.Blank;
        }
        
        int start = (SIZE - 2)/2;
        board[start][start]=SquareContent.Black;
        board[start+1][start+1]=SquareContent.Black;
        board[start+1][start]=SquareContent.White;
        board[start][start+1]=SquareContent.White;        
    }
    
    public Board(Board b)
    {
        board=new SquareContent[SIZE][SIZE];
        for(int i=0; i<SIZE; i++)
        {
            for(int j=0; j<SIZE; j++) board[i][j]=b.board[i][j];
        }
    }
    
    public int countBlanks()
    {
        int c=0;
        for(int i=0; i<SIZE; i++)
        {
            for(int j=0; j<SIZE; j++)
            {
                if(isBlank(i,j)) c++;
            }
        }
        return c;
    }
    
    public int getSize()
    {
        return(SIZE);
    }
    
    public boolean isBlank(int row, int col)
    {
        return(board[row][col]==SquareContent.Blank);
    }
    
    public boolean isBlank(Square s)
    {
        return(isBlank(s.row,s.col));
    }
    
    public void updateSquare(Square s)
    {
        s.content=board[s.row][s.col];
    }
    
    public void setContent(Square s, Player p)
    {
        board[s.row][s.col]=(p==Player.Black ? SquareContent.Black : SquareContent.White);
    }
    
    public Square getSquareFromCoordinates(int x, int y)
    {
        int max = (SIZE * SQUARE_SIZE);
        
        if(x<OFFSET_X || y<OFFSET_Y || x>OFFSET_X+max || y>OFFSET_Y+max) return(null);
        
        int row=(y-OFFSET_Y)/SQUARE_SIZE;
        int col=(x-OFFSET_X)/SQUARE_SIZE;
        
        return(getSquare(row,col));
    }
    
    public boolean isValid(Square s)
    {
        return(s.row >= 0 && s.col >= 0 && s.row < SIZE && s.col < SIZE);
    }
    
    public boolean isValid(int row, int col)
    {
        return(row >= 0 && col >= 0 && row < SIZE && col < SIZE);
    }
    
    public Square getSquare(int row, int col)
    {
        if(!isValid(row,col)) return(null);
        return(new Square(row,col,board[row][col]));
    }
    
    public void drawBoard(Graphics g, boolean showMoves, GameController game)
    {
        // draw outline
        Square potMoves[] = (showMoves ? game.getPlayerMoves(this) : null);
        
        g.setColor(panelBackground);
        g.fillRect(OFFSET_X-3, OFFSET_Y-3, 7+(SIZE*SQUARE_SIZE), 7+(SIZE*SQUARE_SIZE));
        
        int mid = SQUARE_SIZE / 2;
        int cirs = (int)(SQUARE_SIZE * 0.75);
        int ciro = (int)(SQUARE_SIZE * 0.125);
        
        for(int x=0; x<SIZE; x++)       // columns
        {
            int nx=(OFFSET_X + (x * SQUARE_SIZE));
            for(int y=0; y<SIZE; y++)   // rows
            {
                int ny=(OFFSET_Y + (y * SQUARE_SIZE));
                
                g.setColor(squareBorder);
                g.fillRect(nx, ny, SQUARE_SIZE, SQUARE_SIZE);
                
                boolean inPot = false;
                if(showMoves)
                {
                    for(int i=0; i<potMoves.length; i++)
                    {
                        if(potMoves[i].col==x && potMoves[i].row==y)
                        {
                            inPot=true;
                            break;
                        }
                    }
                }
                
                g.setColor(inPot ? squareHighlight : squareBackground);
                g.fillRect(nx+1, ny+1, SQUARE_SIZE-2, SQUARE_SIZE-2);
                
                if(board[y][x]==SquareContent.Blank) continue;
                
                Color col = ( board[y][x] == SquareContent.Black ? blackPiece : whitePiece );
                g.setColor(col);
                g.fillOval(nx+ciro, ny+ciro, cirs, cirs);
            }
        }
        
        g.setColor(squareBorder);
        
        // WARNING: following section is board-size specific (only for 8x8)
        g.fillOval(OFFSET_X-7 + (2*SQUARE_SIZE), OFFSET_Y-7 + (2*SQUARE_SIZE), 14, 14);
        g.fillOval(OFFSET_X-7 + (6*SQUARE_SIZE), OFFSET_Y-7 + (2*SQUARE_SIZE), 14, 14);
        g.fillOval(OFFSET_X-7 + (2*SQUARE_SIZE), OFFSET_Y-7 + (6*SQUARE_SIZE), 14, 14);
        g.fillOval(OFFSET_X-7 + (6*SQUARE_SIZE), OFFSET_Y-7 + (6*SQUARE_SIZE), 14, 14);
    }
}
