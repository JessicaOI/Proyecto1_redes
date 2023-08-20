package com.jessica;

import java.io.IOException;
import java.util.Scanner;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smackx.iqregister.AccountManager;
import org.jxmpp.jid.parts.Localpart;

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
}
