import java.util.Random;

class AskisiJava {

    public static void main(String[] args)
    {
        int N,Nf,Ng,No,Nl,K,L;
        Building building;
        Person[] visitors;
        Random rand = new Random();
        //Read the arguments
        if(args.length < 7)
        {
            System.out.println("Not enough arguments");
            return;
        }
        if(args.length > 7)
        {
            System.out.println("Too many arguments");
            return;
        }
        N = Integer.parseInt(args[0]);
        if((Nf = Integer.parseInt(args[1])) >= N/3)
        {
            System.out.println("Floor capacity must be less that the 1/3rd of the total capacity");
            return;
        }
        if((Ng = Integer.parseInt(args[2])) >= N/2)
        {
            System.out.println("Ground capacity must be less that the half of the total capacity");
            return;
        }
        if((No = Integer.parseInt(args[3])) >= Nf)
        {
            System.out.println("Office capacity must be less than the floor capacity");
            return;
        }
        if(No < 2)
        {
            System.out.println("Office capacity must be at least 2");
            return;
        }
        if((Nl = Integer.parseInt(args[4])) <= No)
        {
            System.out.println("Lift capacity must be greater than the office capacity");
            return;
        }
        K = Integer.parseInt(args[5]);
        L = Integer.parseInt(args[6]);
        //Create the building
        building = new Building(N,Nf,Ng,No,Nl);
        //Create the visitors
        visitors = new Person[K];
        for (int i = 0; i < K; i++)
        {
            if(i > 0)
            {
                if(!visitors[i-1].isGuest())
                {
                    if((rand.nextInt() & Integer.MAX_VALUE) % 2 == 1)//Next visitor is companion of the previous
                    {
                        visitors[i] = new Guest((Visitor) visitors[i-1]);
                        ((Visitor)visitors[i-1]).setCoVisitor((Guest) visitors[i]);
                    }
                    else
                        visitors[i] = new Visitor();
                }
                else
                    visitors[i] = new Visitor();
            }
            else
                visitors[i] = new Visitor();
        }
        //Try to enter the visitors in the building
        for (int i = 0; i < K; i++)
        {
            if(!visitors[i].isGuest())
                building.enter((Visitor)visitors[i]);
        }
        //Run the L lift cycles
        building.runCycles(L);
    }
}
