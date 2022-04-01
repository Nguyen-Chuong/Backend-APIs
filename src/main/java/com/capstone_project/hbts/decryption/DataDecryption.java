package com.capstone_project.hbts.decryption;

import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Component
public class DataDecryption {

    static final String SECRET_KEY = "06052000";

    // for data like id
    public Integer convertEncryptedDataToInt(String dataURLEncoded) {
        String URLDecoded = URLDecoder.decode(dataURLEncoded, StandardCharsets.UTF_8);
        String dataEncrypted = null;
        try {
            dataEncrypted = new URI(URLDecoded).getPath();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
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
            String decrypted = new String(byteDecrypted);
            return Integer.parseInt(decrypted);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    // for data like username
    public String convertEncryptedDataToString(String dataURLEncoded) {
        String URLDecoded = URLDecoder.decode(dataURLEncoded, StandardCharsets.UTF_8);
        String dataEncrypted = null;
        try {
            dataEncrypted = new URI(URLDecoded).getPath();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
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
            return new String(byteDecrypted);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String hmacSHA512(final String key, final String data) {
        try {
            if (key == null || data == null) {
                throw new NullPointerException();
            }
            final Mac hmac512 = Mac.getInstance("HmacSHA512");
            byte[] hmacKeyBytes = key.getBytes();
            final SecretKeySpec secretKey = new SecretKeySpec(hmacKeyBytes, "HmacSHA512");
            hmac512.init(secretKey);
            // convert data to byte
            byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
            // encrypt data to byte
            byte[] result = hmac512.doFinal(dataBytes);
            StringBuilder stringBuilder = new StringBuilder(2 * result.length);
            for (byte b : result) {
                stringBuilder.append(String.format("%02x", b & 0xff));
            }
            return stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

}
