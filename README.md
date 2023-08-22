# Proyecto1
Para compilar el proyecto se necesita ejecutar los siguientes comandos de maven en el siguiente orden:
primero cd a la carpeta proyecto1redes
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


## Funcionalidades:
Administración de cuentas (20% del funcionamiento, 5% cada funcionalidad)
1) Registrar una nueva cuenta en el servidor
2) Iniciar sesión con una cuenta
3) Cerrar sesión con una cuenta
4) Eliminar la cuenta del servidor

Comunicación (80% del funcionamiento, 10% cada funcionalidad)
1) Mostrar todos los contactos y su estado
2) Agregar un usuario a los contactos
3) Mostrar detalles de contacto de un usuario
4) Comunicación 1 a 1 con cualquier usuario/contacto
5) Participar en conversaciones grupales
6) Definir mensaje de presencia
7) Enviar/recibir notificaciones
8) Enviar/recibir archivos



## Archivo Sesion.java:
Coneccion: Establishes a connection to the XMPP server, allowing the user to interact with the chat service.
Deconeccion: Terminates the current session, disconnecting the user from the XMPP server.
Creation: Facilitates the creation of a new user account on the XMPP server, requiring the user to input relevant credentials.
eliminar: Facilitates the deletion of the user account from the XMPP server after confirming the user’s credentials for security purposes.
Presencia: Allows the user to set and display their online presence or status on the XMPP server, such as available, away, etc.
Contactos: Manages the user’s contact list, allowing them to view all connected friends and colleagues.
entriesAdded: Handles the event when new contacts are added to the user’s contact list.
entriesUpdated: Handles the event when existing contacts in the user’s list are updated or modified.
entriesDeleted: Handles the event when contacts are removed from the user’s list.
presenceChanged: Detects and handles the event when a contact changes their online presence or status.
AddContacto: Provides functionality to add a new contact to the user’s list, facilitating smoother communication.
noti: Displays relevant notifications related to chat actions, ensuring the user is always informed.
newIncomingMessage: Detects and manages incoming messages, alerting the user when a new message arrives.
chatContacto: Enables the user to initiate and engage in a chat with a specific contact from their list.
DetallesContactos: Displays additional details or information about contacts in the user’s list, providing more context.

## Archivo Conexion.java:
main: The primary method that initiates the program, acting as the entry point and handling user actions and interactions.
performAction: Executes a specific action or function based on the user’s choice or input, streamlining the user experience.
