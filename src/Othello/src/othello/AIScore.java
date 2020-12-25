package othello;

class AIScore 
{
    protected final Square move;
    protected final int score;
    
    public AIScore(Square m, int s)
    {
        move=new Square(m);
        score=s;
    }
    
    public AIScore(AIScore s)
    {
        this(s.move, s.score);
    }
}
