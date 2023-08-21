package com.jessica;

import java.io.IOException;
import java.util.Collection;
import java.util.Scanner;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.SmackConfiguration;
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
import org.jivesoftware.smackx.muc.MucEnterConfiguration;
// import org.jivesoftware.smackx.muc.DiscussionHistory;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.jivesoftware.smackx.muc.MultiUserChatException;
import org.jivesoftware.smackx.muc.MultiUserChatManager;
import org.jxmpp.jid.BareJid;
import org.jxmpp.jid.EntityBareJid;
import org.jxmpp.jid.Jid;
import org.jxmpp.jid.impl.JidCreate;
import org.jxmpp.jid.parts.Localpart;
import org.jxmpp.jid.parts.Resourcepart;
import org.jxmpp.stringprep.XmppStringprepException;
import org.jivesoftware.smackx.muc.MultiUserChatException.NotAMucServiceException;
import org.jivesoftware.smackx.muc.MultiUserChatException.MucNotJoinedException;


public class Sesion {

    // iniciar sesion
    public static AbstractXMPPConnection Coneccion(String user, String pass)
            throws SmackException, IOException, XMPPException, InterruptedException {
    //conexion con el servidor
        XMPPTCPConnectionConfiguration config = XMPPTCPConnectionConfiguration.builder()
                .setHost("alumchat.xyz")
                .setXmppDomain("alumchat.xyz")
                .setPort(5222)
                .setSecurityMode(ConnectionConfiguration.SecurityMode.disabled)
                .setUsernameAndPassword(user, pass)
                .build();

        AbstractXMPPConnection connection = new XMPPTCPConnection(config);
        connection.connect(); // Se establece la conexion con el servidor
        connection.login(); // "Inicia sesion"

        return connection;
    }

    // cerrar sesion
    public static String Deconeccion(AbstractXMPPConnection connection)
            throws SmackException, IOException, XMPPException, InterruptedException {

        connection.disconnect();

        return "Desconectado";

    }

    // crear un usuario nuevo
    public static void Creation()
            throws SmackException, IOException, XMPPException, InterruptedException {
        System.out.println("(nota: no es necesario agregar el servidor se le añadira automaticamente el de alumchat.xyz)");
        System.out.println("Ingrese el nombre de usuario que desea utilizar (ejemplo: jessica):");
        Scanner sc = new Scanner(System.in);
        String usuario = sc.nextLine();

        System.out.println("Ingrese su contraseña:");
        String password = sc.nextLine();

        try {
            XMPPTCPConnectionConfiguration config = XMPPTCPConnectionConfiguration.builder()
                    .setHost("alumchat.xyz")
                    .setXmppDomain("alumchat.xyz")
                    .setPort(5222)
                    .setSecurityMode(ConnectionConfiguration.SecurityMode.disabled)
                    .build();
            AbstractXMPPConnection connection = new XMPPTCPConnection(config);
            connection.connect();

            AccountManager accountManager = AccountManager.getInstance(connection);

            // se hace la prueba
            try {
                if (accountManager.supportsAccountCreation()) {
                    accountManager.sensitiveOperationOverInsecureConnection(true);
                    accountManager.createAccount(Localpart.from(usuario), password);
                    System.out.println("\nSe ha creado su cuenta, ya puede iniciar sesion\n");
                } else {
                    System.out.println("Account creation is not supported by this server");
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error al crear la cuenta");
            }
            connection.disconnect();

        } catch (Exception e) {
            System.out.println(e);
            System.out.println("\n Su cuenta no fue creada");

        }

    }

     // eliminar cuenta
     public static void eliminar() {

        System.out.println("Ingrese su usuario para confirmar la eliminacion");
        Scanner sc = new Scanner(System.in);
        String user = sc.nextLine();

        System.out.println("Ingrese su contraseña");
        String pass = sc.nextLine();

        // verifica la cuenta que se eliminara
        try {
            XMPPTCPConnectionConfiguration config = XMPPTCPConnectionConfiguration.builder()
                    .setHost("alumchat.xyz")
                    .setXmppDomain("alumchat.xyz")
                    .setPort(5222)
                    .setSecurityMode(ConnectionConfiguration.SecurityMode.disabled)
                    .setUsernameAndPassword(user, pass)
                    .build();
            AbstractXMPPConnection connection = new org.jivesoftware.smack.tcp.XMPPTCPConnection(config);
            connection.connect();
            connection.login();

            AccountManager accountManager = AccountManager.getInstance(connection); // se conecta y asi sabe que cuenta eliminar
            accountManager.deleteAccount();
            System.out.println("Cuenta eliminada\n");

        } catch (Exception e) {
            System.out.println(e);
            System.out.println("\n Su cuenta no fue eliminada\n");
        }
    }

    // Definir presencia en el usuario
    public static void Presencia(AbstractXMPPConnection connection) throws NotConnectedException, InterruptedException {
        StanzaFactory stanzaFactory = connection.getStanzaFactory();
        PresenceBuilder presenceBuilder = stanzaFactory.buildPresenceStanza();

        System.out.println("Presencia\n 1.Available\n 2.Do not disturb\n 3.Away");
        Scanner sc = new Scanner(System.in);
        int coose = sc.nextInt();
        switch (coose) {
            case 1:// available
                Presence presence = presenceBuilder.setMode(Presence.Mode.available).build();
                presence = stanzaFactory.buildPresenceStanza()
                        .setPriority(10)
                        .build();
                connection.sendStanza(presence);
                break;
            case 2:// do not disturb
                presence = presenceBuilder.setMode(Presence.Mode.dnd).build();
                presence = stanzaFactory.buildPresenceStanza()
                        .setPriority(10)
                        .build();
                connection.sendStanza(presence);
                break;
            case 3:// away
                presence = presenceBuilder.setMode(Presence.Mode.away).build();
                connection.sendStanza(presence);
                break;
            default:
                System.out.println("Elija una opcion correcta");
        }

    }    

    //Acciones

     // ver los contactos
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

    // Chat con un grupo
    public static void chatGrupo(AbstractXMPPConnection connection)
        throws XmppStringprepException, NotAMucServiceException,
            XMPPErrorException, NoResponseException, NotConnectedException, InterruptedException,
            MucNotJoinedException {

        int opcion = 0;
        Scanner sc = new Scanner(System.in);
        System.out.println("Ingrese el nombre del grupo:");
        String grupo = sc.nextLine();
        System.out.println("Ingrese su nombre de usuario:");
        String nombre = sc.nextLine();

        EntityBareJid mucJid = JidCreate.entityBareFrom(grupo + "@conference.alumchat.xyz");
        Resourcepart nickname = Resourcepart.from(nombre);

        // Get the MUC manager
        MultiUserChatManager mucManager = MultiUserChatManager.getInstanceFor(connection);
        MultiUserChat muc = mucManager.getMultiUserChat(mucJid);

        // Create a MucEnterConfiguration
        MucEnterConfiguration.Builder mucEnterConfigBuilder = muc.getEnterConfigurationBuilder(nickname)
                .requestNoHistory(); // se define el no mostrar el historial

        MucEnterConfiguration mucEnterConfiguration = mucEnterConfigBuilder.build();

        // unirse al group chat
        muc.join(mucEnterConfiguration);
        muc.addMessageListener(new MessageListener() {
            @Override
            public void processMessage(Message message) {
                if (message.getBody() != null) {
                    System.out.println(message.getFrom() + ": " + message.getBody());
                }
            }
        });
        do {
            System.out.println("1. Ver chat\n 2.Salir al menu principal");
            opcion = sc.nextInt();

            System.out.println("Escriba su mensaje");
            String mensaje = sc.nextLine();

            muc.sendMessage(mensaje);
        } while (opcion != 2);
        muc.leave();
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
