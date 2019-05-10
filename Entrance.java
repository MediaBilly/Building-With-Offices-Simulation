class Entrance
{
    private Queue visitors;

    Entrance()
    {
        this.visitors = new Queue();
        System.out.println("The Entrance has been created!");
    }

    void enter(Person visitor)
    {
        this.visitors.push(visitor);
    }

    Person exit(boolean front)
    {
        return front ? this.visitors.pop() : this.visitors.popBack();
    }

    Person getFrontVisitor()
    {
        return this.visitors.getFront();
    }

    Person getBackVisitor()
    {
        return this.visitors.getBack();
    }

    Person getVisitorWithOfiice(int office)
    {
        return this.visitors.getWithOffice(office);
    }

    Person popVisitorWithOfiice(int office)
    {
        return this.visitors.popWithOffice(office);
    }
}
