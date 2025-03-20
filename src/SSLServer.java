import javax.net.ssl.*;
import java.io.*;
import java.security.KeyStore;

public class SSLServer {
    public static void main(String[] args) throws Exception {
        int puerto = 8443; // Puerto para el servidor SSL

        // Cargar el almacén de claves (keystore)
        KeyStore keyStore = KeyStore.getInstance("JKS");
        try (FileInputStream keyStoreInput = new FileInputStream("servidor.keystore")) {
            keyStore.load(keyStoreInput, "contrasena-keystore".toCharArray());
        }

        // Configurar el gestor de claves
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(keyStore, "contrasena-clave".toCharArray());

        // Configurar el contexto SSL
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(keyManagerFactory.getKeyManagers(), null, null);

        // Crear el socket del servidor SSL
        SSLServerSocketFactory socketFactory = sslContext.getServerSocketFactory();
        SSLServerSocket serverSocket = (SSLServerSocket) socketFactory.createServerSocket(puerto);
        System.out.println("Servidor SSL escuchando en el puerto " + puerto);

        // Escuchar y aceptar conexiones
        while (true) {
            try (SSLSocket socket = (SSLSocket) serverSocket.accept()) {
                System.out.println("Cliente conectado.");
                BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);

                // Comunicación sencilla con el cliente
                salida.println("Conexión segura establecida.");
                System.out.println("Mensaje recibido del cliente: " + entrada.readLine());
            } catch (IOException e) {
                System.err.println("Error en la conexión: " + e.getMessage());
            }
        }
    }
}
