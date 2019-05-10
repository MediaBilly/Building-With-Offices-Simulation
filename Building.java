class Building extends Container
{
    private NormalFloor[] floors;
    private Ground ground;
    private Lift lift;
    private Queue visitors;

    Building(int capacity,int floorCapacity,int groundCapacity,int officeCapacity,int liftCapacity)
    {
        super(capacity);
        this.floors = new NormalFloor[4];
        this.ground = new Ground(groundCapacity);
        this.visitors = new Queue();
        for (int i = 0;i < 4;i++)
            this.floors[i] = new NormalFloor(floorCapacity,i+1,officeCapacity);
        this.lift = new Lift(liftCapacity);
        System.out.println("A new building is ready for serving citizens!");
    }

    boolean enter(Visitor visitor)
    {
        if(visitor.getCoVisitor() != null)
        {
            if(!super.hasNFreeSpace(2))
            {
                System.out.println("Please, you and your guest come tomorrow");
                return false;
            }
            else
            {
                if(!this.ground.enter(visitor))
                {
                    this.visitors.push(visitor);
                    this.visitors.push(visitor.getCoVisitor());
                    System.out.println("Visitor: Please, come tomorrow");
                    System.out.println("Guest: Please, come tomorrow");
                    super.enter(visitor);
                    super.enter(visitor.getCoVisitor());
                }
                else
                {
                    super.enter(visitor);
                    this.enter(visitor.getCoVisitor());
                }
                return true;
            }
        }
        else
        {
            if(!super.enter(visitor))
            {
                System.out.println("Visitor: Please, come tomorrow");
                return false;
            }
            else
            {
                if(!this.ground.enter(visitor))
                {
                    this.visitors.push(visitor);
                    System.out.println("Visitor: Please, come tomorrow");
                }
                return true;
            }
        }
    }

    boolean enter(Guest guest)
    {
        if(!super.enter(guest))
        {
            System.out.println("Guest: Please, come tomorrow");
            return false;
        }
        else
        {
            this.ground.enter(guest);
            return true;
        }
    }

    Person exit(boolean front)
    {
        if(this.count == 0)
            return null;
        System.out.println("I cannot believe I finished!");
        super.exit(front);
        return null;
    }


    boolean empty()
    {
        return this.ground.empty();
    }

    Person getFrontVisitor()
    {
        return this.ground.getFrontVisitor();
    }

    void runCycles(int cycles)
    {
        for (int i = 0;i < cycles;i++)
            this.lift.operate(this,this.floors,this.ground);
    }

    void refreshGround()
    {
        Visitor vis = (Visitor)this.ground.exit(true,false);
        if (vis == null)
            return;
        if(vis.getCoVisitor() != null)
        {
            //When 2 visitors get in the lift insert 2 from outside the building to the ground(if there are at least 2 available)
            this.ground.exit(true,false);
            if(this.visitors.size() > 1)
            {
                Person p1 = this.visitors.getFront();
                if(((Visitor)p1).getCoVisitor() != null)//insert 1 visitor and 1 guest
                {
                    if (this.ground.enter((Visitor)this.visitors.getFront()))
                    {
                        this.visitors.pop();
                        this.ground.enter((Guest)this.visitors.pop());
                    }
                    else
                    {
                        if (this.visitors.has2VisitorsWithoutGuests())
                        {
                            this.ground.enter(this.visitors.popVisitorWithoutGuest());
                            this.ground.enter(this.visitors.popVisitorWithoutGuest());
                        }
                        else
                            this.ground.enter(this.visitors.popVisitorWithoutGuest());
                    }
                }
                else //Insert 2 visitors
                {
                    if (this.visitors.has2VisitorsWithoutGuests())
                    {
                        this.ground.enter(this.visitors.popVisitorWithoutGuest());
                        this.ground.enter(this.visitors.popVisitorWithoutGuest());
                    }
                }
            }
            else if(this.visitors.size() == 1)//Insert 1 visitor
            {
                //Otherwise check if there is at least 1 available and insert him to the ground
                Person p = this.visitors.getFront();
                if(p.isGuest())
                    this.ground.enter((Guest)this.visitors.pop());
                else
                    this.ground.enter((Visitor)this.visitors.pop());
            }
        }
        else
        {
            //When a visitor gets in the lift insert one from outside the building to the ground(if there is at least one available)
            if(this.visitors.size() > 0)
            {
                //If the visitor that is going to enter has a guest try to insert both of them if there is space
                if (((Visitor)this.visitors.getFront()).getCoVisitor() != null)
                {
                    if (this.ground.enter((Visitor)this.visitors.getFront()))
                    {
                        this.visitors.pop();
                        this.ground.enter((Guest)this.visitors.pop());
                    }
                    else//Otherwise, try to insert just insert 1 visitor without guest
                    {
                        if(this.visitors.getVisitorWithoutGuest() != null)
                            this.ground.enter(this.visitors.popVisitorWithoutGuest());
                    }
                }
                else//If the visitor that is going to enter does not have a visitor, just insert him to the ground
                    this.ground.enter(this.visitors.popVisitorWithoutGuest());
            }
        }
    }
}
