package othello;

class Square 
{
    protected int row;
    protected int col;
    protected SquareContent content;
    
    public Square(int r, int c)
    {
        row=r;
        col=c;
        content=SquareContent.Blank;
    }
    
    public Square(int r, int c, SquareContent sc)
    {
        row=r;
        col=c;
        content=sc;
    }
    
    public Square(Square s)
    {
        row=s.row;
        col=s.col;
        content=s.content;
    }
    
    public boolean isBlank()
    {
        return(content==SquareContent.Blank);
    }
    
    public boolean areSame(Player p)
    {
        return((content==SquareContent.Black && p==Player.Black) || (content==SquareContent.White && p==Player.White));
    }
    
    @Override
    public String toString()
    {
        return("{("+row+","+col+")-["+content.toString()+"]}");
    }
    
    @Override
    public boolean equals(Object s)
    {
        if (this == s) return true;
     
        if (s instanceof Square) 
        {
            Square sq = (Square)s;
            return(row==sq.row && col==sq.col);
        }
        return false;
    }
    
    @Override
    public int hashCode()
    {
        int result=17;

        int c1=Integer.hashCode(row);
        int c2=Integer.hashCode(col);

        result = 31 * result + c1;
        result = 31 * result + c2;

        return result;
    }
}
