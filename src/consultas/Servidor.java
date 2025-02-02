
package consultas;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringJoiner;

public class Servidor extends Thread {
    
    private final Socket skCliente;
    private static final int PORT = 2000;
    private static final String FILE = "lista.txt";
    private String usuario;

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
             DataOutputStream out = new DataOutputStream(skCliente.getOutputStream());) {
            
            //lectura del nombre del cliente y de la opcion seleccionada
            usuario = in.readUTF();
            System.out.println("Conectado el usuario: " + usuario);
            
            while (true) {   
                
                int opcion = in.readInt();
                
                switch (opcion) {
                    //introducir valor
                    case 1 -> almacenarNumero(in);
                    //consultar total de registros almacenados
                    case 2 -> out.writeInt(numeroLineas(FILE));
                    //obtener la lista de numeros almacenados
                    case 3 -> listaDeNumeros(out);
                    //consultar cuantos numeros ha almacenado un cliente concreto
                    case 4 -> cantidadDelUsuario(out);
                    //recibir archivo conteniendo los numeros almacenados por un cliente
                    case 5 -> {
                    }
                    case 6 -> {
                        System.err.println(usuario+" ha desconectado");
                        return;
                    }
                    default -> System.out.println("Error de seleccion por el cliente");
                }
            }
            
        } catch (Exception e) {
            System.out.println("siombre");
        }
        
    }
    
    private void almacenarNumero(DataInputStream in) throws IOException {
        //se reciben los datos
        String datos = in.readUTF();
        //se escriben los datos en el archivo
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE, true))) {
            pw.println(datos);
            pw.flush();
            //se muestra por pantalla
            System.out.println("Se almacena el numero: "+datos);
        }
    }
    
    private int numeroLineas(String archivo) throws FileNotFoundException, IOException {
        int lineas = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(new File(archivo)))){
            while (br.readLine() != null) {
               lineas++;
            }
        }
        return lineas;
    }
    
    private void listaDeNumeros(DataOutputStream out) throws IOException {
        File archivo = new File(FILE);
        
        if (!archivo.exists()) {
            out.writeUTF("El archivo no existe");
            return;
        }
        
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            // Evita el salto de línea al final
            StringJoiner lista = new StringJoiner("\n"); 
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] numero = linea.split(":");
                // Agrega sin necesidad de manejar el último salto de línea
                lista.add(numero[1]); 
            }
            out.writeUTF(lista.toString());
        }

    }

    private void cantidadDelUsuario(DataOutputStream out) throws IOException {
        File archivo = new File(FILE);
        
        if (!archivo.exists()) {
            out.writeUTF("El archivo no existe");
            return;
        }
        
        int lineas = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))){
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.startsWith(usuario+":")) {
                    lineas++;
                }
            }
        }
        out.writeInt(lineas);
    }
        

}
