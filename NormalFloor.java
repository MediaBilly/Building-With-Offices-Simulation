class NormalFloor extends Floor
{
    private int id;
    private int officeCount;
    private Office[] offices;

    NormalFloor(int maxCapacity,int id,int officeCapacity)
    {
        super(maxCapacity);
        this.id = id;
        this.officeCount = 0;
        this.offices = new Office[10];
        for (int i = 0;i < 10;i++)
            this.offices[i] = new Office(officeCapacity,i+1);
        System.out.println("A Floor has been created with number " + this.id);
    }

    boolean enter(Visitor visitor)
    {
        if(visitor.getCoVisitor() != null)
        {
            if(super.hasNFreeSpace(2))
            {
                System.out.println("Visitor " + visitor.getPriority() + " Entering floor number " + this.id);
                this.entrance.enter(visitor);
                super.enter(visitor);
                return true;
            }
            else if(super.hasNFreeSpace(1))
            {
                System.out.println("Visitor " + visitor.getPriority() + ": Sorry, floor number " + this.id + " does not have enough space for your guest");
                return false;
            }
            else
            {
                System.out.println("Visitor " + visitor.getPriority() + ": Sorry, floor number " + this.id + " is full");
                return false;
            }
        }
        else
        {
            if(super.enter(visitor))
            {
                System.out.println("Visitor " + visitor.getPriority() + " Entering floor number " + this.id);
                this.entrance.enter(visitor);
                return true;
            }
            else
            {
                System.out.println("Visitor " + visitor.getPriority() + ": Sorry, floor number " + this.id + " is full");
                return false;
            }
        }
    }

    boolean enter(Guest guest)
    {
        if(super.enter(guest))
        {
            System.out.println("Guest of visitor " + (guest.getCoVisitor()).getPriority() + " Entering floor number " + this.id);
            this.entrance.enter(guest);
            return true;
        }
        else
        {
            System.out.println("Guest of visitor " + (guest.getCoVisitor()).getPriority() + ":Sorry, floor number " + this.id + " is full");
            return false;
        }
    }

    boolean serveVisitor()
    {
        if(this.entrance.getBackVisitor() == null)
            return false;
        if(this.offices[this.entrance.getBackVisitor().getOffice() - 1].enter((Visitor)this.entrance.getBackVisitor()))
        {
            this.exit(false);
            this.officeCount++;
            return true;
        }
        else
            return false;
    }

    boolean serveGuest()
    {
        if(this.entrance.getBackVisitor() == null)
            return false;
        if(this.offices[this.entrance.getBackVisitor().getOffice() - 1].enter((Guest) this.entrance.getBackVisitor()))
        {
            this.exit(false);
            this.officeCount++;
            return true;
        }
        else
            return false;
    }

    void serveRestVisitors()
    {
        Visitor vis;
        for (int i = 0;i < 10;i++)
        {
            while (this.entrance.getVisitorWithOfiice(i+1) != null && this.offices[i].enter(vis = (Visitor)this.entrance.getVisitorWithOfiice(i+1)))
            {
                if(vis.getCoVisitor() != null)
                {
                    this.entrance.popVisitorWithOfiice(i+1);
                    this.offices[i].enter((Guest) this.entrance.getVisitorWithOfiice(i+1));
                    this.entrance.popVisitorWithOfiice(i+1);
                    super.exitFromContainer(true);
                    super.exitFromContainer(true);
                    this.officeCount+=2;
                }
                else
                {
                    this.entrance.popVisitorWithOfiice(i+1);
                    super.exitFromContainer(true);
                    this.officeCount++;
                }
            }
        }
    }

    Visitor getServedVisitorFromOffice(int office)
    {
        return this.offices[office].getFirstServedVisitor();
    }

    Person popServedVisitorFromOffice(int office)
    {
        this.officeCount--;
        return this.offices[office].exit(true);
    }

    int getOfficeCount()
    {
        return this.officeCount;
    }
}
