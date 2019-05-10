abstract class Person
{
    int floor;
    int office;
    private boolean _served;
    boolean isGuest;

    Person(int floor,int office)
    {
        this._served = false;
        this.floor = floor;
        this.office = office;
        this.isGuest = false;
    }

    //Accessors
    int getFloor()
    {
        return this.floor;
    }

    int getOffice()
    {
        return this.office;
    }

    boolean served()
    {
        return this._served;
    }

    boolean isGuest()
    {
        return this.isGuest;
    }

    //Mutators
    void serve()
    {
        this._served = true;
    }
}
