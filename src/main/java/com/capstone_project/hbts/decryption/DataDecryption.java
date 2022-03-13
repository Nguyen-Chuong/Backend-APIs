package com.capstone_project.hbts.decryption;

import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Component
public class DataDecryption {

    static final String SECRET_KEY = "06052000";

    // for data like id
    public Integer convertEncryptedDataToInt(String dataURLEncoded) {
        // url decode
        String URLDecoded = URLDecoder.decode(dataURLEncoded, StandardCharsets.UTF_8);
        // data to encrypt
        String dataEncrypted = null;
        try {
            dataEncrypted = new URI(URLDecoded).getPath();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        // specify secret key
        SecretKeySpec secretKeySpec = new SecretKeySpec(SECRET_KEY.getBytes(), "DES");
        try {
            // new an object to help decrypt data
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5PADDING");
            // decode data encrypt string to byte[]
            byte[] dataNeedToDecrypted = Base64.getDecoder().decode(dataEncrypted);
            // init cipher decrypt mode with key
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            // get the data byte[] decrypted after decrypt
            byte[] byteDecrypted = cipher.doFinal(dataNeedToDecrypted);
            // convert data byte[] to string
            String decrypted = new String(byteDecrypted);
            // convert string data to int
            return Integer.parseInt(decrypted);
        } catch (NoSuchAlgorithmException |
                NoSuchPaddingException |
                InvalidKeyException |
                IllegalBlockSizeException |
                BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    // for data like username
    public String convertEncryptedDataToString(String dataURLEncoded) {
        // url decode
        String URLDecoded = URLDecoder.decode(dataURLEncoded, StandardCharsets.UTF_8);
        // data to encrypt
        String dataEncrypted = null;
        try {
            dataEncrypted = new URI(URLDecoded).getPath();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        // specify secret key
        SecretKeySpec secretKeySpec = new SecretKeySpec(SECRET_KEY.getBytes(), "DES");
        try {
            // new an object to help decrypt data
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5PADDING");
            // decode data encrypt string to byte[]
            byte[] dataNeedToDecrypted = Base64.getDecoder().decode(dataEncrypted);
            // init cipher decrypt mode with key
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            // get the data byte[] decrypted after decrypt
            byte[] byteDecrypted = cipher.doFinal(dataNeedToDecrypted);
            // convert data byte[] to string
            return new String(byteDecrypted);
        } catch (NoSuchAlgorithmException |
                NoSuchPaddingException |
                InvalidKeyException |
                IllegalBlockSizeException |
                BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static URI convertOriginalDataToStringEncrypted(String original) {
        // specify secret key
        SecretKeySpec secretKeySpec = new SecretKeySpec(SECRET_KEY.getBytes(), "DES");
        try {
            // new an object to help decrypt data
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5PADDING");
            // init cipher encrypt mode with key
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            // get the data byte[] after encrypted
            byte[] byteEncrypted = cipher.doFinal(original.getBytes());
            // convert data byte[] to string
            String dataEncrypted = Base64.getEncoder().encodeToString(byteEncrypted);
            String URLEncoded = URLEncoder.encode(dataEncrypted, StandardCharsets.UTF_8);
            URI result = null;
            try {
                result = new URI(URLEncoded);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            return result;
        } catch (NoSuchAlgorithmException |
                NoSuchPaddingException |
                InvalidKeyException |
                IllegalBlockSizeException |
                BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

//    public static void main(String[] args) {
//        System.out.println(convertOriginalDataToStringEncrypted("1"));
//    }

}
