
package consultas;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor extends Thread {
    
    private final Socket skCliente;
    private static final int PORT = 2000;

    public Servidor(Socket skCliente) {
        this.skCliente = skCliente;
    }

    public static void main(String[] args) {
        
        try (ServerSocket skServer = new ServerSocket(PORT)) {
            System.out.println("Servidor iniciado en el puerto " + PORT);
            while (true) {                
                Socket socket = skServer.accept();
                new Servidor(socket).start();
            }
        } catch (Exception e) {
            System.out.println("ERROR CONEXION AL SERVIDOR");
        }
    }

    @Override
    public void run() {
        
        try (DataInputStream in = new DataInputStream(skCliente.getInputStream());
             DataOutputStream out = new DataOutputStream(skCliente.getOutputStream())) {
            
            //lectura del nombre del cliente y de la opcion seleccionada
            String usuario = in.readUTF();
            System.out.println("Conectado el usuario: " + usuario);
            
            
            FileWriter fw = new FileWriter("lista.txt", true);
            
            while (true) {   
                
                int opcion = in.readInt();
                
                switch (opcion) {
                    //introducir valor
                    case 1 -> {
                        //se reciben los datos
                        String datos = in.readUTF();
                        //se escriben los datos en el archivo
                        fw.write(datos+System.lineSeparator());
                        fw.flush();
                        //se muestra por pantalla
                        System.out.println("Se almacena el numero: "+datos);
                    }
                    //consultar total de registros almacenados
                    case 2 -> {
                    }
                    //obtener la lista de numeros almacenados
                    case 3 -> {
                    }
                    //consultar cuantos numeros ha almacenado un cliente concreto
                    case 4 -> {
                    }
                    //recibir archivo conteniendo los numeros almacenados por un cliente
                    case 5 -> {
                    }
                    case 6 -> System.err.println("Ssuario desconectado");
                    default -> System.out.println("Error de seleccion por el cliente");
                }
            }
            
        } catch (Exception e) {
            
        }
        
    }

}
