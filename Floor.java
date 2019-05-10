abstract class Floor extends Container
{
    Entrance entrance;

    Floor(int maxCapacity)
    {
        super(maxCapacity);
        this.entrance = new Entrance();
    }

    void exitFromContainer(boolean front)
    {
        super.exit(front);
    }

    Person exit(boolean front)
    {
        if(this.count == 0)
            return null;
        super.exit(front);
        return this.entrance.exit(front);
    }
}
