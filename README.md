# Enunciado: Servidor Multicliente con Almacenamiento de Números

## Descripción  
La aplicación servidor debe atender a múltiples clientes, permitiendo que cada cliente se conecte al servidor.  

Por cada cliente que se conecte, el servidor creará un hilo dedicado para gestionar su comunicación.  

## Funcionalidades del Cliente  
El cliente podrá elegir entre las siguientes opciones:  

- **Almacenar un número en un archivo:**  
  - Se guardará en un archivo de texto llamado `numeros.txt`.  
  - El formato de almacenamiento será: `NombreCliente: Número`
    - **Ejemplo:** `C3: 4`  

- **Consultar el número total de registros almacenados.**  
- **Obtener la lista completa de números almacenados.**  
- **Consultar cuántos números ha almacenado un cliente específico.**  
- **Recibir un archivo que contenga únicamente los números almacenados por el cliente.**  

**Fuente:** [Permalink](https://github.com/DiscoDurodeRoer/ejercicios-java-youtube/blob/29ee1e3b900cea3703e73f079a9a7a9b182d180d/sockets/sockets%2007/enunciado.txt)

