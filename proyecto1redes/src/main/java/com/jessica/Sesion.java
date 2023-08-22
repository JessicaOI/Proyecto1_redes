package com.jessica;

import java.io.IOException;
import java.util.Collection;
import java.util.Scanner;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.SmackException.NoResponseException;
import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.SmackException.NotLoggedInException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.XMPPException.XMPPErrorException;
import org.jivesoftware.smack.chat2.Chat;
import org.jivesoftware.smack.chat2.ChatManager;
import org.jivesoftware.smack.chat2.IncomingChatMessageListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.PresenceBuilder;
import org.jivesoftware.smack.packet.StanzaFactory;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterEntry;
import org.jivesoftware.smack.roster.RosterListener;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smackx.iqregister.AccountManager;
import org.jxmpp.jid.BareJid;
import org.jxmpp.jid.EntityBareJid;
import org.jxmpp.jid.Jid;
import org.jxmpp.jid.impl.JidCreate;
import org.jxmpp.jid.parts.Localpart;
import org.jxmpp.stringprep.XmppStringprepException;




public class Sesion {

    // Making the log in
    public static AbstractXMPPConnection Coneccion(String user, String pass)
            throws SmackException, IOException, XMPPException, InterruptedException {
    //connect to the server
        XMPPTCPConnectionConfiguration config = XMPPTCPConnectionConfiguration.builder()
                .setHost("alumchat.xyz")
                .setXmppDomain("alumchat.xyz")
                .setPort(5222)
                .setSecurityMode(ConnectionConfiguration.SecurityMode.disabled)
                .setUsernameAndPassword(user, pass)
                .build();

        AbstractXMPPConnection connection = new XMPPTCPConnection(config);
        connection.connect(); // Establish connection with the server
        connection.login(); // "Login"

        return connection;
    }

    // Log out function
    public static String Deconeccion(AbstractXMPPConnection connection)
            throws SmackException, IOException, XMPPException, InterruptedException {

        connection.disconnect();

        return "Desconectado";

    }

    // Create new Username
    public static void Creation()
            throws SmackException, IOException, XMPPException, InterruptedException {
        // Notify the user about the default server
        System.out.println("(nota: no es necesario agregar el servidor se le añadira automaticamente el de alumchat.xyz)");
        // Prompt user for a username
        System.out.println("Ingrese el nombre de usuario que desea utilizar (ejemplo: jessica):");
        Scanner sc = new Scanner(System.in);
        String usuario = sc.nextLine();

        // Prompt user for a password
        System.out.println("Ingrese su contraseña:");
        String password = sc.nextLine();
        
        // Attempt to create a connection and account on the server
        try {
            // Set up connection configuration to the XMPP server
            XMPPTCPConnectionConfiguration config = XMPPTCPConnectionConfiguration.builder()
                    .setHost("alumchat.xyz")
                    .setXmppDomain("alumchat.xyz")
                    .setPort(5222)
                    .setSecurityMode(ConnectionConfiguration.SecurityMode.disabled)
                    .build();
             // Establish the connection using the configuration
            AbstractXMPPConnection connection = new XMPPTCPConnection(config);
            connection.connect();
            // Instantiate the AccountManager to manage user account actions
            AccountManager accountManager = AccountManager.getInstance(connection);

            // Check if the server supports account creation (test)
            try {
                if (accountManager.supportsAccountCreation()) {
                    accountManager.sensitiveOperationOverInsecureConnection(true);
                    // Create an account with the provided username and password
                    accountManager.createAccount(Localpart.from(usuario), password);
                    System.out.println("\nSe ha creado su cuenta, ya puede iniciar sesion\n");
                } else {
                    System.out.println("Account creation is not supported by this server");
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error al crear la cuenta");
            }
            // Disconnect from the server
            connection.disconnect();

        } catch (Exception e) {
            System.out.println(e);
            System.out.println("\n Su cuenta no fue creada");

        }

    }

     // Delete user
     public static void eliminar() {

        // Prompt the user for their username to confirm account deletion
        System.out.println("Ingrese su usuario para confirmar la eliminacion");
        Scanner sc = new Scanner(System.in);
        String user = sc.nextLine();

        System.out.println("Ingrese su contraseña");
        String pass = sc.nextLine();

        // Attempt to verify and delete the account on the server
        try {
            // Set up connection configuration to the XMPP server with provided credentials
            XMPPTCPConnectionConfiguration config = XMPPTCPConnectionConfiguration.builder()
                    .setHost("alumchat.xyz")
                    .setXmppDomain("alumchat.xyz")
                    .setPort(5222)
                    .setSecurityMode(ConnectionConfiguration.SecurityMode.disabled)
                    .setUsernameAndPassword(user, pass)
                    .build();
            // Establish the connection using the configuration
            AbstractXMPPConnection connection = new org.jivesoftware.smack.tcp.XMPPTCPConnection(config);
            connection.connect();
            connection.login();

            // Instantiate the AccountManager to manage user account actions
            AccountManager accountManager = AccountManager.getInstance(connection); // se conecta y asi sabe que cuenta eliminar
            // Delete the logged-in account
            accountManager.deleteAccount();
            System.out.println("Cuenta eliminada\n");

        } catch (Exception e) {
            System.out.println(e);
            System.out.println("\n Su cuenta no fue eliminada\n");
        }
    }

    // Define presence in the user
    public static void Presencia(AbstractXMPPConnection connection) throws NotConnectedException, InterruptedException {
        Scanner sc = new Scanner(System.in);
    
        // Ask for presence mode
        System.out.println("Presencia\n 1.Available\n 2.Do not disturb\n 3.Away");
        int choose = sc.nextInt();
        sc.nextLine();  // Consume newline left-over
    
        PresenceBuilder presenceBuilder = connection.getStanzaFactory().buildPresenceStanza();
        presenceBuilder.setPriority(10);  // Set priority for all
    
        switch (choose) {
            case 1: // available
                presenceBuilder.setMode(Presence.Mode.available);
                break;
            case 2: // do not disturb
                presenceBuilder.setMode(Presence.Mode.dnd);
                break;
            case 3: // away
                presenceBuilder.setMode(Presence.Mode.away);
                break;
            default:
                System.out.println("Elija una opcion correcta");
                return;  // Exit function if invalid choice
        }
    
        // Ask for status
        System.out.println("Desea agregar un status?");
        System.out.println("Status\n 1.si\n 2.no\n ");
        choose = sc.nextInt();
        sc.nextLine();  // Consume newline left-over
    
        if (choose == 1) {
            System.out.println("Escriba su status");
            String status = sc.nextLine();
            presenceBuilder.setStatus(status);
        } else if (choose != 2) {
            System.out.println("Elija una opcion correcta");
            return;  // Exit function if invalid choice
        }
    
        // Send the stanza
        Presence presence = presenceBuilder.build();
        connection.sendStanza(presence);
    }
    

    //Actions

     // View contacts
    public static void Contactos(AbstractXMPPConnection connection)
            throws SmackException, IOException, XMPPException, InterruptedException {

        try {

            Roster roster = Roster.getInstanceFor(connection);
            roster.addRosterListener(new RosterListener() {
                @Override
                public void entriesAdded(java.util.Collection<Jid> addresses) {
                    // Handle new entries
                }

                @Override
                public void entriesUpdated(java.util.Collection<Jid> addresses) {
                    // Handle updated entries
                }

                @Override
                public void entriesDeleted(java.util.Collection<Jid> addresses) {
                    // Handle deleted entries
                }

                @Override
                public void presenceChanged(Presence presence) {
                    // Handle presence changes
                }
            });

            roster.reload();
            for (RosterEntry entry : roster.getEntries()) {
                System.out.println("User: " + entry.getJid());
                Presence presence = roster.getPresence(entry.getJid());
                System.out.println("Presence: " + presence.getStatus());
                System.out.println("Availability: " + presence.getMode());
                System.out.println("-------------------------------");
            }

            connection.disconnect();
        } catch (SmackException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    // envio de solicitud y agregar contacto
    public static void AddContacto(AbstractXMPPConnection connection)
            throws NotLoggedInException, NoResponseException, XMPPErrorException,
            NotConnectedException, InterruptedException, XmppStringprepException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Ingrese el usuario que desea añadir a contactos:");
        final String targetJID = sc.nextLine();
        final Roster roster = Roster.getInstanceFor(connection);
        BareJid jid = JidCreate.bareFrom(targetJID + "@alumchat.xyz");

        roster.addRosterListener(new RosterListener() {
            @Override
            public void entriesAdded(Collection<Jid> addresses) {
                // Handle new entries
            }

            @Override
            public void entriesUpdated(Collection<Jid> addresses) {
                // Handle updated entries
            }

            @Override
            public void entriesDeleted(Collection<Jid> addresses) {
                // Handle deleted entries
            }

            @Override
            public void presenceChanged(Presence presence) {
                if (presence.getType() == Presence.Type.subscribe) {
                    BareJid fromJID = presence.getFrom().asBareJid();

                    try {
                        // Aceptar el request
                        roster.createItemAndRequestSubscription(fromJID, targetJID, null);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        roster.createItemAndRequestSubscription(jid, targetJID, null);
    }

    // listener para las notificaciones
    public static void noti(AbstractXMPPConnection connection) {
        ChatManager chatManager = ChatManager.getInstanceFor(connection);
        chatManager.addIncomingListener(new IncomingChatMessageListener() {
            @Override
            public void newIncomingMessage(EntityBareJid from, Message message, Chat chat) {
                System.out.println("Mensaje de: " + from + ": " + message.getBody());
            }
        });
    }

    // Funcion para envio de mensajes

     // Chat con un contacto
    public static void chatContacto(AbstractXMPPConnection connection)
            throws SmackException, IOException, XMPPException, InterruptedException {
        int opcion = 0;
        String mensaje = "";
        org.jivesoftware.smack.chat2.ChatManager chat = org.jivesoftware.smack.chat2.ChatManager
                .getInstanceFor(connection);

        chat.addIncomingListener(new IncomingChatMessageListener() {
            @Override
            public void newIncomingMessage(EntityBareJid from, Message message, Chat chat) {
                System.out.println("New message from " + from + ": " + message.getBody());
            }
        });

        Scanner sc = new Scanner(System.in);
        System.out.println("Ingrese el usuario de la persona a la que va dirigida el mensaje");
        String nombre = sc.nextLine();
        EntityBareJid jid = JidCreate.entityBareFrom(nombre + "@alumchat.xyz");

        // notificaciones
        noti(connection);
        // loop para la conversacion 1 a 1
        do {

            Chat chat2 = chat.chatWith(jid);

            System.out.println("Ingrese el mensaje:");
            mensaje = sc.nextLine();
            chat2.send(mensaje);

            System.out.println("1.Continuar chateando \n2.Salir del chat");
            opcion = sc.nextInt();
            sc.nextLine();

        } while (opcion != 2);

    }


    // Mostrar detalles de contacto de un usuario
    public static void DetallesContactos(AbstractXMPPConnection connection) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Ingrese el nombre de usuario del contacto (sin el dominio):");
        String username = sc.nextLine();
        Roster roster = Roster.getInstanceFor(connection);
        BareJid contactJid;
        try {
            contactJid = JidCreate.bareFrom(username + "@alumchat.xyz");
            RosterEntry entry = roster.getEntry(contactJid);
            if (entry != null) {
                System.out.println("Nombre de usuario: " + entry.getJid());
                System.out.println("Nombre: " + entry.getName());
                System.out.println("Tipo de suscripción: " + entry.getType());
                System.out.println("Grupos: " + entry.getGroups());
                Presence presence = roster.getPresence(contactJid);
                System.out.println("Estado de presencia: " + presence.getStatus());
                System.out.println("Modo de presencia: " + presence.getMode());
            } else {
                System.out.println("Contacto no encontrado.");
            }
        } catch (XmppStringprepException e) {
            System.out.println("Error al obtener detalles del contacto.");
            e.printStackTrace();
        }
    }
    

}
