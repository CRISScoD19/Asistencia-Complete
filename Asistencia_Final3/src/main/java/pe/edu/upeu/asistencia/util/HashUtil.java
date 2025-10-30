package pe.edu.upeu.asistencia.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Clase utilitaria para encriptación de contraseñas usando SHA-256
 * Investigación adicional: SHA-256 es un algoritmo de hash criptográfico
 * que convierte texto en una cadena de 64 caracteres hexadecimales.
 */
public class HashUtil {

    /**
     * Encripta una contraseña usando SHA-256
     * @param password Contraseña en texto plano
     * @return Hash SHA-256 en formato hexadecimal
     */
    public static String sha256(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(password.getBytes(StandardCharsets.UTF_8));

            // Convertir bytes a hexadecimal
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al encriptar contraseña con SHA-256", e);
        }
    }

    /**
     * Verifica si una contraseña coincide con un hash
     * @param password Contraseña en texto plano
     * @param hash Hash almacenado
     * @return true si coinciden, false en caso contrario
     */
    public static boolean verificar(String password, String hash) {
        String passwordHash = sha256(password);
        return passwordHash.equals(hash);
    }
}
