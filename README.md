# Cliente-Servidor SSL en Java

Este proyecto consiste en una aplicación cliente-servidor implementada en Java que utiliza SSL para establecer una conexión segura. El servidor escucha las conexiones entrantes en un puerto específico y el cliente se conecta al servidor, intercambiando mensajes cifrados para garantizar la seguridad de la comunicación.

## Tecnologías utilizadas
- Java Standard Edition
- SSL/TLS (Secure Sockets Layer / Transport Layer Security)
- Keystore y Truststore de Java para la gestión de certificados

## Requisitos previos
1. **Java Development Kit (JDK):** Instala JDK 8 o superior.
2. **Certificado SSL:** Crea o utiliza un certificado SSL para el servidor.
3. **Herramienta Keytool:** Utiliza la herramienta `keytool` de Java para crear y gestionar el keystore y el truststore.

## Configuración inicial

### Generación del keystore para el servidor
Ejecuta el siguiente comando para crear un keystore que contendrá el certificado del servidor:
```
bash
keytool -genkey -keyalg RSA -keystore servidor.keystore -storepass contrasena-keystore -keypass contrasena-clave -alias servidor
```

### Configuración del truststore para el cliente
Importa el certificado del servidor al truststore del cliente:

```
bash
keytool -import -trustcacerts -file servidor-certificado.crt -keystore cliente.truststore -storepass contrasena-truststore -alias servidor
```

### Funcionamiento
## Servidor:

1. Escucha conexiones entrantes en un puerto seguro.

2. Establece una conexión SSL utilizando un keystore.

3. Interactúa con el cliente intercambiando mensajes cifrados.

## Cliente:

1. Carga un truststore para verificar la identidad del servidor.

2. Se conecta al servidor utilizando SSL.

3. Intercambia mensajes con el servidor de forma segura.
