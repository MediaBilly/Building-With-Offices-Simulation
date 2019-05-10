abstract class Container
{
    int count;
    private int maxCapacity;
    Container(int maxCapacity)
    {
        this.count = 0;
        this.maxCapacity = maxCapacity;
    }

    boolean hasNFreeSpace(int N)
    {
        return this.count <= this.maxCapacity - N;
    }

    private boolean _enter()
    {
        if (this.count >= this.maxCapacity)
            return false;
        else
        {
            this.count++;
            return true;
        }
    }

    boolean enter(Visitor visitor)
    {
        return this._enter();
    }

    boolean enter(Guest guest)
    {
        return this._enter();
    }

    Person exit(boolean front)
    {
        if (this.count > 0)
            this.count--;
        return null;
    }
}
