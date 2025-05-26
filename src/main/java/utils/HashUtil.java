/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;



import java.security.MessageDigest;

public class HashUtil {
    public static String sha1(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] result = md.digest(input.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte b : result) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException("Error al cifrar con SHA-1", e);
        }
        
    }
    public static void main(String[] args) {
        String textoplano="1234";
        String cifrado= sha1(textoplano);
        System.out.println("contraseña original"+textoplano);
        System.out.println("contraseña cifrada"+cifrado);
    }
}

