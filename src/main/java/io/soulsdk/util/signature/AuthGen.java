package io.soulsdk.util.signature;


import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import io.soulsdk.sdk.SoulSystem;

/**
 * Created by buyaroff1 on 19/01/16.
 */
public class AuthGen {

    public static String getBasic(
            String userId,
            String sessionToken,
            String httpMethod,
            String uri,
            String requestBody) {
        //String unixTime = String.valueOf(System.currentTimeMillis() / 1000L);
        String unixTime = String.valueOf(SoulSystem.getServerTime() / 1000l);
        //  return "Authorization hmac "+ Base64.encode(""

        return "hmac " + ""
                + userId
                + ":"
                + unixTime
                + ":"
                + getDigest(sessionToken, httpMethod, "/" + uri, requestBody, unixTime);
                // );

    }

    public static String getBasic(
            String userId,
            String sessionToken,
            String httpMethod,
            String uri,
            String requestBody,
            String unixTime) {

        return "hmac " + ""
                + userId
                + ":"
                + unixTime
                + ":"
                + getDigest(sessionToken, httpMethod, "/" + uri, requestBody, unixTime);
    }

    private static String getDigest(
            String sessionToken,
            String httpMethod,
            String uri,
            String requestBody,
            String unixTime) {
        try {
            if(requestBody==null)requestBody="";
            //requestBody = (requestBody!=null && !requestBody.isEmpty())?requestBody+ "+" : "";
            String message = httpMethod + "+" + uri + "+" + requestBody + "+" + unixTime;
            System.out.println(message);
            System.out.println("sessionToken: " + sessionToken);
             return hmacDigest(message, sessionToken);
          //  return message;
        } catch (Exception e) {
            System.out.println("Error");
            e.printStackTrace();
            return "";
        }
    }

    public static String getHmac(String src, String key) {
        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
           // Mac sha256_HMAC = Mac.getInstance("SHA-256");
            SecretKeySpec secret_key = new SecretKeySpec(src.getBytes(), key);
            sha256_HMAC.init(secret_key);
            // System.out.println(secret_key);
            String hash = Base64.encodeBase64Chunked(
                    sha256_HMAC.doFinal(src.getBytes()))
                    .toString();
            System.out.println(hash);
            return hash;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return "Error";
    }

    public static String hmacDigest(String msg, String keyString) {
        String algo = "HMACSHA256";
        String digest = null;
        try {
            SecretKeySpec key = new SecretKeySpec(keyString.getBytes("UTF-8"), algo);
            Mac mac = Mac.getInstance(algo);
            mac.init(key);

            byte[] bytes = mac.doFinal(msg.getBytes("ASCII"));

            StringBuffer hash = new StringBuffer();
            for (int i = 0; i < bytes.length; i++) {
                String hex = Integer.toHexString(0xFF & bytes[i]);
                if (hex.length() == 1) {
                    hash.append('0');
                }
                hash.append(hex);
            }
            digest = hash.toString();
        } catch (UnsupportedEncodingException e) {
        } catch (InvalidKeyException e) {
        } catch (NoSuchAlgorithmException e) {
        }
        System.out.println(digest);
        return digest;
    }
}
