package othello;

class AIMove 
{
    protected GameController game;            
    protected Square move;
    protected Board board;
    protected int score;
    
    public AIMove(AIMove m)
    {
        this(m.board, m.move, m.game);
    }
    
    public AIMove(Board b, Square m, GameController g)
    {
        // stores the board AFTER the move has been performed
        this.game = new GameController(g);
        this.board = new Board(b);
        this.move = new Square(m);
        updateScore();
        //System.out.println("Constructor: "+this.move+", "+score+", "+board.getSquare(move.row,move.col));
    }
    
    public void updateScore()
    {
        Board xBoard=new Board(board);
        score = game.playerMove(xBoard, move, false);
    }
}
