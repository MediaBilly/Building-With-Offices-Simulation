# Public-Service-Building-Simulation

A Public Service Building Simulator created as a university project for the Object Oriented Programming course at Winter Semester
2018.The simulation has a building with a ground floor,a lift and 4 floors.The ground floor consists of an entrance space.Each
floor consists of an entrance space with 10 offices.The program runs for a specific amount of simulation cycles.In each cycle,
visitors with their companion(if the program gives then one) enter the ground floor.Then they stop up in each of the 4 floors
using the lift and then go to their offices to get served.After that they stop down the floors using the lift and at the end they
get off the lift and go to the ground to get out of the building.Note that if a passenger has a companion,the companion has the 
same floor and office destinations.

## Compilation:
```
javac AskisiJava.java
```

## Running:
```
java AskisiJava <building capacity> <floor capacity> <ground capacity> <office capacity> <lift capacity> <total visitors> <simulation cycles>
```

## Clear Object Files:
```
rm *.class
```
