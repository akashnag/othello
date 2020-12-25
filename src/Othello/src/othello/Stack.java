package othello;

class Stack 
{
    private Square data[];
    private int top;
    private final int capacity;
    
    public Stack(Stack s)
    {
        capacity = s.capacity;
        top = s.top;
        data = new Square[capacity];
        
        for(int i=0; i<=top; i++) data[i]=new Square(s.data[i]);
    }
    
    public Stack(int n)
    {
        capacity=n;
        top=-1;
        data=new Square[n];
    }
    
    public void push(Square s)
    {
        if(top==capacity-1) 
        {
            System.out.println("Overflow");
            return;
        }
        data[++top]=new Square(s);
    }
    
    public Square pop()
    {
        if(top==-1) 
        {
            System.out.println("Underflow");
            return(null);
        }
        return(new Square(data[top--]));
    }
    
    public boolean isEmpty()
    {
        return(top==-1);
    }
}
