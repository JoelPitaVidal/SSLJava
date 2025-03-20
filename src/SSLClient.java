import javax.net.ssl.*;
import java.io.*;
import java.security.KeyStore;

public class SSLClient {
    public static void main(String[] args) throws Exception {
        String host = "localhost"; // Dirección del servidor
        int puerto = 8443; // Puerto del servidor

        // Cargar el almacén de claves (opcional para el cliente)
        KeyStore trustStore = KeyStore.getInstance("JKS");
        try (FileInputStream trustStoreInput = new FileInputStream("cliente.truststore")) {
            trustStore.load(trustStoreInput, "contrasena-truststore".toCharArray());
        }

        // Configurar el gestor de confianza
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(trustStore);

        // Configurar el contexto SSL
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, trustManagerFactory.getTrustManagers(), null);

        // Crear el socket SSL
        SSLSocketFactory socketFactory = sslContext.getSocketFactory();
        try (SSLSocket socket = (SSLSocket) socketFactory.createSocket(host, puerto)) {
            System.out.println("Conexión segura establecida con el servidor.");

            // Configurar streams de entrada y salida
            PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Enviar y recibir mensajes
            salida.println("Hola desde el cliente SSL.");
            System.out.println("Mensaje recibido del servidor: " + entrada.readLine());
        } catch (IOException e) {
            System.err.println("Error en la conexión: " + e.getMessage());
        }
    }
}
