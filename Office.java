class Office extends Container
{
    private int id;
    private Queue visitors;

    Office(int maxCapacity,int id)
    {
        super(maxCapacity);
        this.id = id;
        this.visitors = new Queue();
        System.out.println("Office number " + this.id + " has been created");
    }

    boolean enter(Visitor visitor)
    {
        if(visitor.getCoVisitor() != null)
        {
            if(super.hasNFreeSpace(2))
            {
                System.out.println("Visitor " + visitor.getPriority() + " Entering office number " + this.id);
                super.enter(visitor);
                this.visitors.push(visitor);
                visitor.serve();
                return true;
            }
            else
            {
                System.out.println("Visitor " + visitor.getPriority() + " Please, wait outside with your guest for entrance in office number " + this.id);
                return false;
            }
        }
        else
        {
            if(super.enter(visitor))
            {
                System.out.println("Visitor " + visitor.getPriority() + " Entering office number " + this.id);
                this.visitors.push(visitor);
                visitor.serve();
                return true;
            }
            else
            {
                System.out.println("Visitor " + visitor.getPriority() + " Please, wait outside for entrance in office number " + this.id);
                return false;
            }
        }
    }

    boolean enter(Guest guest)
    {
        if(super.enter(guest))
        {
            System.out.println("Guest of visitor " + (guest.getCoVisitor()).getPriority() + " Entering office number " + this.id);
            this.visitors.push(guest);
            guest.serve();
            return true;
        }
        else
        {
            System.out.println("Guest of visitor " + (guest.getCoVisitor()).getPriority() + " Please, wait outside for entrance in office number " + this.id);
            return false;
        }
    }

    Person exit(boolean front)
    {
        if(this.count == 0)
            return null;
        super.exit(front);
        return this.visitors.popServed();
    }

    Visitor getFirstServedVisitor()
    {
        return (Visitor)this.visitors.getServed();
    }
}
