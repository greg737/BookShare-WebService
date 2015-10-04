# BookShare-WebService
Service Overview

The web service made is to be used by users to share books with each other. A user is able to add books he has to his library and make request to other users for books that the user have. A request may have a message and/or a meetup location. 
A user can retrieve all or a set amount of books or request that he or she may have or books that exist in the web service. The user can also view a specific user, request or book and also get a list of other users, authors or books. If a user logs in the web service and uses the cookie, the web service will return the users who are in the same city. A user may also get all the other users that own a specific book. Any request that returns a list can have size parameter to limit the number of results returned.
An asynchronous query to let user to subscribe to receive alerts when a new request to the user was made but does not work.

Service Design

An abstract class of Person is implemented for the User and Author class because both classes must have a gender and a first and last name. 
A book can be owned by multiple different users but a user can only have one copy of the book.
A request must have two users because it needs a requestor and the owner of the book. Any request can have more than one requestor requesting the same book from a user and also may have a location that the requestor may want to meet the user at.
An author must have a book because the author is persisted by the book class. This is done by using cascading type of PERSIST. 
The list of books in the user uses lazy fetching because a client might want to only view the users in the same city or find a user with a specific username. 
The DTO of the author class contains a list of books id instead of a list of books object. This is done is to prevent the cycles when generating the XML of the author class. 
All requests that returns a list can be limited by adding a QueryParam field.
 

Quality attributes

Usability

The web service is very usable because the client can query books that they have or books owned by other user and may fetch for requests that is sent to the client. The client can also find the users who owns a specific book and do not have to manually search each user if they have the book.

Performance

The web service is has decent performance as many of the classes that have a list uses lazy fetching and the DTO for the User class does not contain the list of books the user has. When a user queries for an author, the client receives a list of books id instead. Therefore the web service does not have to process and sent more data and does it if only required. 

