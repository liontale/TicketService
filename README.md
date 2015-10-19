# TicketService
TicketService application

Overview
1. Have desigend and implemented the classes involved in building a TicketService application
2. Unit test have also been implemented
3. The Driver (main) has yet to be completed
4. I have leveraged some of the Java 8 features

To run :
mvn clean install


Design 
I will clean up/revist the initial design document and upload that as well, but for now to provide 
you with some insight to how I approached the TicketService exercise.

1. Read/understand the exercise writeup
2. Factor out nouns that were mentioned in it, and pay attendion to the verbs (actions) to be performed
3. This helped identify the Class diagram
4. The interface spelled out two class, TicketService and SeatHold, the other class that I derived from the list of nouns 
  was the Seat class
5. With these classes and their relationships hammered out, my next task was to figure out the data structure 
6. I choose data structure TreeSet (no duplicates, and I could organize the Seats based on  seat#) 
7. With use of lambda expressions, finding the best available seats in an ordered set by min/max levels resulted in 
   clean code.
8. As I was writing the code I wrote the Unit tests.(I do feel I would have benefit from a mocking framework for two reasons)
 a. The unit tests would be more isolated 
 b. The test code would be cleaner
 c. This is something I plan to clean at a later time
9. At this point I have not written the driver program, that is my next task.

 



