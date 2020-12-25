package othello;

class Queue
{
    private Square data[];
    private int front;
    private int rear;
    private final int capacity;
    
    public Queue(Queue q)
    {
        capacity = q.capacity;
        front = q.front;
        rear = q.rear;
        data = new Square[capacity];
        
        for(int i=0; i<capacity; i++) 
            data[i]=(q.data[i]==null ? null : new Square(q.data[i]));
    }
    
    public Queue(int n)
    {
        capacity=n;
        front=-1;
        rear=-1;
        data=new Square[n];
    }
    
    public void insert(Square s)
    {
        if(rear==capacity-1) 
        {
            System.out.println("Overflow");
            return;
        }
        data[++rear]=new Square(s);
        if(front==-1) front=rear;
    }
    
    public Square retrieve()
    {
        if(front==-1) 
        {
            System.out.println("Underflow");
            return(null);
        }
        Square x=new Square(data[front++]);
        if(front>rear)
        {
            front=-1;
            rear=-1;
        }
        return x;
    }
    
    public boolean isEmpty()
    {
        return(front==-1);
    }
}
