import java.util.Random;

class Visitor extends Person
{
    private int priority;
    private Guest coVisitor;
    private static Random rand = new Random();

    Visitor()
    {
        super((rand.nextInt() & Integer.MAX_VALUE) % 4 + 1,(rand.nextInt() & Integer.MAX_VALUE) % 10 + 1);
        this.priority = 0;
        this.coVisitor = null;
        this.isGuest = false;
    }

    //Accessors
    int getPriority()
    {
        return this.priority;
    }

    Guest getCoVisitor()
    {
        return this.coVisitor;
    }

    void setPriority(int priority)
    {
        this.priority = priority;
    }

    //Mutators
    void setCoVisitor(Guest coVisitor)
    {
        this.coVisitor = coVisitor;
    }
}
