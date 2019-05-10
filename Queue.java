class Queue
{
    private class Node
    {
        Person visitor;
        Node next;
        Node prev;
    }

    private int count;
    private Node front;
    private Node back;
    Queue()
    {
        this.count = 0;
        this.front = this.back = null;
    }
    void push(Person visitor)
    {
        Node tmp = new Node();
        tmp.visitor = visitor;
        tmp.next = null;
        if(this.front == null && this.back == null)//First time
        {
            tmp.prev = null;
            this.front = tmp;
            this.back = tmp;
        }
        else
        {
            tmp.prev = this.back;
            this.back.next = tmp;
            this.back = tmp;
        }
        this.count++;
    }

    Person pop()
    {
        Person ret = null;
        Node toDel = this.front;
        if(toDel != null)
        {
            ret = toDel.visitor;
            this.front = this.front.next;
            if(this.front != null)
                this.front.prev= null;
            else
                this.back = null;
            this.count--;
        }
        return ret;
    }

    Person popBack()
    {
        Person ret = null;
        Node toDel = this.back;
        if(toDel != null)
        {
            ret = toDel.visitor;
            this.back = this.back.prev;
            if(this.back == null)
                this.front = null;
            else
                this.back.next = null;
            this.count--;
        }
        return ret;
    }

    Person popWithFloor(int floor)
    {
        Person ret;
        Node toDel = this.front;
        while(toDel != null && toDel.visitor.getFloor() != floor)
            toDel = toDel.next;
        if(toDel == this.front)
            ret = this.pop();
        else if(toDel == null)
        ret = null;
        else
        {
            toDel.prev.next = toDel.next;
            if(toDel == this.back)
            this.back = toDel.prev;
        else
            toDel.next.prev = toDel.prev;
            ret = toDel.visitor;
            this.count--;
        }
        return ret;
    }

    Person popWithOffice(int office)
    {
        Person ret;
        Node toDel = this.front;
        while(toDel != null && toDel.visitor.getOffice() != office)
            toDel = toDel.next;
        if(toDel == this.front)
            ret = this.pop();
        else if(toDel == null)
            ret = null;
        else
        {
            toDel.prev.next = toDel.next;
            if(toDel == this.back)
                this.back = toDel.prev;
            else
                toDel.next.prev = toDel.prev;
            ret = toDel.visitor;
            this.count--;
        }
        return ret;
    }

    Person popServed()
    {
        Person ret;
        Node toDel = this.front;
        while(toDel != null && !toDel.visitor.served())
            toDel = toDel.next;
        if(toDel == this.front)
            ret = this.pop();
        else if(toDel == null)
            ret = null;
        else
        {
            toDel.prev.next = toDel.next;
            if(toDel == this.back)
                this.back = toDel.prev;
            else
                toDel.next.prev = toDel.prev;
            ret = toDel.visitor;
            this.count--;
        }
        return ret;
    }

    Visitor popVisitorWithoutGuest()
    {
        Visitor ret;
        Node toDel = this.front;
        while (toDel != null && toDel.visitor.isGuest() || toDel != null && !toDel.visitor.isGuest() && ((Visitor)toDel.visitor).getCoVisitor() != null)
            toDel = toDel.next;
        if(toDel == this.front)
            ret = (Visitor)this.pop();
        else if (toDel == null)
            ret = null;
        else
        {
            toDel.prev.next = toDel.next;
            if(toDel == this.back)
                this.back = toDel.prev;
            else
                toDel.next.prev = toDel.prev;
            ret = (Visitor)toDel.visitor;
            this.count--;
        }
        return ret;
    }

    Person getFront()
    {
        return this.front == null ? null : this.front.visitor;
    }

    Person getBack()
    {
        return this.back == null ? null : this.back.visitor;
    }

    Person getWithFloor(int floor)
    {
        Node tmp = this.front;
        while (tmp != null && tmp.visitor.getFloor() != floor)
            tmp = tmp.next;
        return tmp == null ? null : tmp.visitor;
    }

    Person getWithOffice(int office)
    {
        Node tmp = this.front;
        while (tmp != null && tmp.visitor.getOffice() != office)
            tmp = tmp.next;
        return tmp == null ? null : tmp.visitor;
    }

    Person getServed()
    {
        Node tmp = this.front;
        while (tmp != null && !tmp.visitor.served())
            tmp = tmp.next;
        return tmp == null ? null : tmp.visitor;
    }

    Visitor getVisitorWithoutGuest()
    {
        Node tmp = this.front;
        while (tmp != null && tmp.visitor.isGuest() || tmp != null && !tmp.visitor.isGuest() && ((Visitor)tmp.visitor).getCoVisitor() != null)
            tmp = tmp.next;
        return tmp == null ? null : (Visitor)tmp.visitor;
    }

    boolean has2VisitorsWithoutGuests()
    {
        Node tmp = this.front;
        while (tmp != null && tmp.visitor.isGuest() || tmp != null && !tmp.visitor.isGuest() && ((Visitor)tmp.visitor).getCoVisitor() != null)
            tmp = tmp.next;
        if (tmp != null)
        {
            tmp = tmp.next;
            while (tmp != null && tmp.visitor.isGuest() || tmp != null && !tmp.visitor.isGuest() && ((Visitor)tmp.visitor).getCoVisitor() != null)
                tmp = tmp.next;
            return tmp != null;
        }
        else
            return false;
    }

    int size()
    {
        return this.count;
    }
}
