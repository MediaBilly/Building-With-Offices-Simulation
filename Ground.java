class Ground extends Floor
{
    private static int priority = 1;
    private Queue servedVisitors;

    Ground(int maxCapacity)
    {
        super(maxCapacity);
        this.servedVisitors = new Queue();
        System.out.println("A Ground has been created");
    }

    private void wait(Visitor visitor)
    {
        visitor.setPriority(priority++);
        System.out.println("Waiting for the lift");
    }

    boolean enter(Visitor visitor)
    {
        if (!visitor.served())
        {
            if (super.hasNFreeSpace(visitor.getCoVisitor() == null ? 1 : 2))
            {
                this.entrance.enter(visitor);
                super.enter(visitor);
                this.wait(visitor);
                return true;
            }
            else
                return false;
        }
        else
        {
            this.servedVisitors.push(visitor);
            System.out.println("Served visitor " + visitor.getPriority() + " entered the ground");
            return true;
        }
    }

    boolean enter(Guest guest)
    {
        if (!guest.served())
        {
            if (super.enter(guest))
            {
                this.entrance.enter(guest);
                System.out.println("A guest is waiting for the lift");
                return true;
            }
            else
                return false;
        }
        else
        {
            this.servedVisitors.push(guest);
            System.out.println("Guest of served visitor " + guest.getCoVisitor().getPriority() + " entered the ground");
            return true;
        }
    }

    Person getFrontVisitor()
    {
        return this.entrance.getFrontVisitor();
    }

    boolean empty()
    {
        return this.count == 0;
    }

    Person exit(boolean front,boolean exitServed)
    {
        Person per;
        if (exitServed)
        {
            per = this.servedVisitors.pop();
            if (per != null)
            {
                if (per.isGuest())
                    System.out.println("Guest of served visitor " + ((Guest)per).getCoVisitor().getPriority() + " exited the ground");
                else
                    System.out.println("Served visitor " + ((Visitor)per).getPriority() + " exited the ground");
            }
            return per;
        }
        else
            return super.exit(front);
    }
}
