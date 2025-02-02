
package consultas;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;

public class Cliente extends Thread {
    private final String HOST = "localhost";
    private final int PORT = 2000;
    private boolean activo = true;
    private final Random random = new Random();
    private final Scanner teclado = new Scanner(System.in);

    @Override
    public void run() {
        try (Socket skClient = new Socket(HOST, PORT);
                DataInputStream in = new DataInputStream(skClient.getInputStream());
                DataOutputStream out = new DataOutputStream(skClient.getOutputStream());) {
            
            //pedimos nombre de usuario
            System.out.print("¿Cual es su nombre de usuario?: ");
            String usuario = teclado.nextLine();
            //mandamos el usuario al servidor
            out.writeUTF(usuario);
            
            while (activo) {

                mostrarMenu();
                
                int opcion = leerOpcion();
                
                //mandamos al servidor la opcion elegida para proceder
                out.writeInt(opcion);
                
                switch (opcion) {
                    case 1 -> {
                        //generamos el numero aleatorio
                        int numero = random.nextInt(1, 1000);
                        //mostramos el numero por pantalla
                        System.out.println("Numero generado: " + numero);
                        //mandamos el numero generado en el formato establecido
                        out.writeUTF(usuario+":"+numero);
                    }
                    case 2 -> {System.out.println("holi 2");
                    }
                    case 3 -> {System.out.println("holi 3");
                    }
                    case 4 -> {System.out.println("holi 4");
                    }
                    case 5 -> {System.out.println("holi 5");
                    }
                    case 6 -> activo = false;
                    default -> {System.out.println("Error seleccion");}
                }
            }
            
        } catch (Exception e) {
        }
    }
    
    private void mostrarMenu() {
        System.out.println("-- Gestión de números --");
        System.out.println("1. Almacenar un número");
        System.out.println("2. Total de numeros almacenados");
        System.out.println("3. Mostrar lista de numeros almacenados");
        System.out.println("4. Total de numeros almacenados de un usuario");
        System.out.println("5. Recibir archivo con los numeros del cliente");
        System.out.println("6. Finalizar");
        System.out.print("Introduzca una opción: ");
    }
    
    private int leerOpcion() {
        while (!teclado.hasNextInt()) {
            System.out.print("Entrada inválida. Introduzca un número válido: ");
            teclado.next();
        }
        return teclado.nextInt();
    }
}