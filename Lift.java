import java.util.Random;

class Lift extends Container
{
    private int currentFloor;
    private Queue visitors;

    Lift(int maxCapacity)
    {
        super(maxCapacity);
        this.currentFloor = 0;
        this.visitors = new Queue();
        System.out.println("A lift has been created");
    }

    boolean enter(Visitor visitor)
    {
        if(visitor.getCoVisitor() != null)
        {
            if(super.hasNFreeSpace(2))
            {
                System.out.println("Visitor " + visitor.getPriority() + " and his guest in the lift");
                this.visitors.push(visitor);
                super.enter(visitor);
                return true;
            }
            else if (super.hasNFreeSpace(1))
            {
                System.out.println("Visitor " + visitor.getPriority() + " You are allowed to enter but your guest not.So both not.");
                return false;
            }
            else
            {
                System.out.println("Visitor " + visitor.getPriority() + " You and your guest are not allowed to enter!");
                return false;
            }
        }
        else
        {
            if (super.enter(visitor))
            {
                System.out.println("Visitor " + visitor.getPriority() + " in the lift");
                this.visitors.push(visitor);
                return true;
            }
            else
            {
                System.out.println("Visitor " + visitor.getPriority() + " You are not allowed to enter!");
                return false;
            }
        }
    }

    boolean enter(Guest guest)
    {
        if(this.hasNFreeSpace(1) && this.visitors.getBack() == guest.getCoVisitor())
        {
            System.out.println("Guest of visitor " + (guest.getCoVisitor()).getPriority() + " in the lift");
            this.visitors.push(guest);
            super.enter(guest);
            return true;
        }
        else
        {
            System.out.println("Guest of visitor " + (guest.getCoVisitor()).getPriority() + " You are not allowed to enter!");
            return false;
        }
    }

    Person exit(boolean front)
    {
        if(this.count == 0)
            return null;
        super.exit(front);
        if(this.currentFloor == 0)//Extract to ground
            return this.visitors.popServed();
        else
            return this.visitors.popWithFloor(this.currentFloor);
    }

    private boolean stop_up()
    {
        if(this.currentFloor < 4)
        {
            this.currentFloor++;
            return true;
        }
        else
            return false;
    }

    private boolean stop_down()
    {
        if(this.currentFloor > 0)
        {
            this.currentFloor--;
            return true;
        }
        else
            return false;
    }

    private Person popServedVisitor()
    {
        if(this.visitors.getServed() != null)
            return this.exit(true);
        else
            return null;
    }

    private void empty_all(Building building,Ground ground)
    {
        Person person;
        while ((person = this.popServedVisitor()) != null)
        {
            if (person.isGuest)
                ground.enter((Guest)person);
            else
                ground.enter((Visitor)person);
        }
        while (ground.exit(true,true) != null)
        {
            building.exit(true);
            //building.refreshGround();
        }
    }

    void operate(Building building,NormalFloor[] floors,Ground ground)
    {
        int office = 0;
        Visitor vis = null;
        Random rand = new Random();
        //Enter visitors to the lift until it fills
        while(!building.empty())
        {
            vis = (Visitor)building.getFrontVisitor();
            if(vis.getCoVisitor() != null)
            {
                if(this.enter(vis))
                {
                    this.enter(vis.getCoVisitor());
                    building.refreshGround();
                }
                else
                    break;
            }
            else
            {
                if(this.enter(vis))
                    building.refreshGround();
                else
                    break;
            }
        }
        //Step up to the 4 floors of the building
        while(this.stop_up())
        {
            //Entering passengers to the current floor
            while(this.visitors.getWithFloor(this.currentFloor) != null && floors[this.currentFloor - 1].enter(vis = (Visitor)this.visitors.getWithFloor(this.currentFloor)))
            {
                if (vis.getCoVisitor() != null)
                {
                    this.exit(true);
                    this.exit(true);
                    //Try to serve the visitor that just enter the current floor
                    if(floors[this.currentFloor - 1].serveVisitor())
                    {
                        //Try to serve the guest that just enter the current floor
                        floors[this.currentFloor - 1].enter(vis.getCoVisitor());
                        floors[this.currentFloor - 1].serveGuest();
                    }
                    else
                        floors[this.currentFloor - 1].enter(vis.getCoVisitor());
                }
                else
                {
                    this.exit(true);
                    //Try to serve the passenger that just enter the current floor
                    floors[this.currentFloor - 1].serveVisitor();
                }
            }
        }
        //Step down to the 4 floors of the building
        do
        {
            if(this.currentFloor > 0)//Landing at floor
            {
                //Enter served visitors to the lift until it fills
                while(floors[this.currentFloor - 1].getOfficeCount() > 0 && (vis = floors[this.currentFloor - 1].getServedVisitorFromOffice(office = (rand.nextInt() & Integer.MAX_VALUE) % 10)) == null) ;//Get a random office that has a served visitor
                while(floors[this.currentFloor - 1].getOfficeCount() > 0 && this.enter(vis))
                {
                    if(vis.getCoVisitor() != null)
                    {
                        floors[this.currentFloor - 1].popServedVisitorFromOffice(office);//Get the visitor that just exited the office to the lift
                        floors[this.currentFloor - 1].popServedVisitorFromOffice(office);//Get the guest that just exited the office to the lift
                        this.enter(vis.getCoVisitor());
                    }
                    else
                        floors[this.currentFloor - 1].popServedVisitorFromOffice(office);//Get the visitor that just exited the office to the lift
                    while(floors[this.currentFloor - 1].getOfficeCount() > 0 && (vis = floors[this.currentFloor - 1].getServedVisitorFromOffice(office = (rand.nextInt() & Integer.MAX_VALUE) % 10)) == null) ;//Get a random office that has a served visitor
                }
                //Fill each office with visitors that are waiting for it
                floors[this.currentFloor - 1].serveRestVisitors();
            }
		    else//Landing at the ground
            {
                //Extract all served visitors from the lift and then from the building
                this.empty_all(building,ground);
            }
        } while(this.stop_down());
    }
}
