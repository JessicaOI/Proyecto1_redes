# Proyecto1
To compile the project, you need to execute the following Maven commands in the following order:
First

cd a la carpeta proyecto1redes
```
mvn clean
```
```
mvn compile
```
```
mvn package
```
```
java -jar target/gs-maven-0.1.0.jar
```


## Functionalities require:
Account Management (20% of the operation, 5% for each functionality)

1. Register a new account on the server
2. Log in with an account
3. Log out of an account
4. Delete the account from the server

Communication (80% of the operation, 10% for each functionality)

1. Display all contacts and their status
2. Add a user to contacts
3. Show contact details of a user
4. 1 on 1 communication with any user/contact
5. Participate in group conversations
6. Set presence message
7. Send/receive notifications
8. Send/receive files.



## File Sesion.java:
* Coneccion: Establishes a connection to the XMPP server, allowing the user to interact with the chat service.
* Deconeccion: Terminates the current session, disconnecting the user from the XMPP server.
* Creation: Facilitates the creation of a new user account on the XMPP server, requiring the user to input relevant credentials.
* eliminar: Facilitates the deletion of the user account from the XMPP server after confirming the user’s credentials for security purposes.
* Presencia: Allows the user to set and display their online presence or status on the XMPP server, such as available, away, etc.
* Contactos: Manages the user’s contact list, allowing them to view all connected friends and colleagues.
* presenceChanged: Detects and handles the event when a contact changes their online presence or status.
* AddContacto: Provides functionality to add a new contact to the user’s list, facilitating smoother communication.
* noti: Displays relevant notifications related to chat actions, ensuring the user is always informed.
* newIncomingMessage: Detects and manages incoming messages, alerting the user when a new message arrives.
* chatContacto: Enables the user to initiate and engage in a chat with a specific contact from their list.
* DetallesContactos: Displays additional details or information about contacts in the user’s list, providing more context.

## File Conexion.java:
* main: The primary method that initiates the program, acting as the entry point and handling user actions and interactions.
* performAction: Executes a specific action or function based on the user’s choice or input, streamlining the user experience.
