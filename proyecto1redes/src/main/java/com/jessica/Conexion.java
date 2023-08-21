package com.jessica;

import org.jivesoftware.smack.*;
import org.jivesoftware.smack.packet.Presence;

import java.io.IOException;

import java.util.*;

public class Conexion {
    public static void main(String[] args) throws SmackException, IOException, XMPPException, InterruptedException {

        Scanner sc = new Scanner(System.in);

        int choice;

        // primer menu
        do {

            System.out.println("Bienvenido, que desea realizar ahora?");
            System.out.println("1. Iniciar Sesion");
            System.out.println("2. Crear usuario");
            System.out.println("3. Salir");
            System.out.print("Opcion: ");
            choice = sc.nextInt();
            performAction(choice);
        } while (choice != 3); // Salir

        System.out.println("Disconnected");

    }

    public static void performAction(int choice)
            throws SmackException, IOException, XMPPException, InterruptedException {

        Scanner sc = new Scanner(System.in);
        switch (choice) {
            case 1:
                System.out.println("Ingrese su nombre de usuario");
                String user = sc.nextLine();
                System.out.println("Ingrese su password");
                String pass = sc.nextLine();

                AbstractXMPPConnection iniciado = Sesion.Coneccion(user, pass);
                System.out.println("Se ha iniciado sesion");
                do {
                    // segundo menu cuando la sesion este iniciada

                    System.out
                            .println(
                                    "\n Que desea hacer ahora?: \n 1.Mostrar todos los contactos\n 2.Agregar un usuario a los contactos\n 3.Mostrar detalles de contacto de un usuario\n 4.Chat con un contacto \n 5.Chat con un grupo\n 6.Definir mensaje de presencia\n 7.Notificaciones\n 8.Archivos\n 9.Cerrar sesion\n 10.Borrar Cuenta\n");

                    choice = sc.nextInt();
                    switch (choice) {
                        case 1:
                            // mostrar contactos
                            Sesion.Contactos(iniciado);
                            break;
                        case 2:
                            // agregar usuario a contactos
                            Sesion.AddContacto(iniciado);
                            break;
                        case 3:
                            // mostrar detalles de contacto de un usuario
                            Sesion.DetallesContactos(iniciado);
                            break;
                        case 4:
                            // chat con un contacto
                            Sesion.chatContacto(iniciado);
                            break;
                        case 5:
                            // chat con un grupo
                            // Sesion.chatGrupo(iniciado);
                            break;
                        case 6:
                            // mensaje de presencia
                            Sesion.Presencia(iniciado);
                            break;                            
                        case 7:
                            // notificaciones
                            break; 
                        case 8:
                            // archivos
                            Sesion.sendFile(iniciado);
                            break;
                        case 9:// desconectarse
                            Sesion.Deconeccion(iniciado);
                            break;
                        case 10:
                            // Eliminar cuenta
                            Sesion.eliminar();
                            choice = 9;  // Esto hará que el loop termine y regrese al primer menú
                            break;
                        default:
                            System.out.println("Ingrese una opcion correcta");
                    }

                } while (choice != 9);

                break;
            case 2:
                System.out.println("Creando cuenta");

                Sesion.Creation();

                break;
            case 3:

                break;

            default:
                System.out.println("Ingrese una opcion correcta");
        }
    }

}
