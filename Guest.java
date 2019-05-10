class Guest extends Person
{
    private Visitor coVisitor;

    Guest(Visitor coVisitor)
    {
        super(coVisitor.floor,coVisitor.office);
        this.coVisitor = coVisitor;
        this.isGuest = true;
    }

    Visitor getCoVisitor()
    {
        return this.coVisitor;
    }
}
