package io.soulsdk.sdk.helpers.chat;

import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import apache.commons.codec.binary.StringUtils;
import apache.commons.codec.digest.DigestUtils;
import io.soulsdk.model.dto.chat.ChatMessage;
import io.soulsdk.util.signature.Base64;
import log.Log;

/**
 * @author kitttn
 */

public class ChatMessageEncoder {
    private static final String TAG = "ChatMessageEncoder";
    private Gson gson = new Gson();
    private Cipher cipher;
    private String chatSalt = "";
    private byte[] initVector = StringUtils.getBytesUtf8("0123456789012345");

    public ChatMessageEncoder(String chatSalt) {
        this.chatSalt = chatSalt;
        try {
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "init: Can't initialize cipher!");
        }
    }

    public ChatMessage decode(String channelName, String encodedMsg) {
        return ChatMessageFabric.create(decodeString(channelName, encodedMsg));
    }

    public String decodeString(String channelName, String encodedMsg) {
        if (checkInitError())
            return null;

        String key = channelName + "/" + chatSalt;
        try {
            byte[] encodedKey = createSha256HexFromString(key);
            SecretKeySpec secretKey = new SecretKeySpec(encodedKey, "AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(initVector));
            byte[] decodedBase64 = Base64.decodeBase64(encodedMsg.getBytes());
            byte[] result = cipher.doFinal(decodedBase64);
            return new String(result, "utf-8");
        } catch (Exception e) {
            Log.e(TAG, "decode: Can't decode object: " + encodedMsg);
            e.printStackTrace();
            return "";
        }
    }

    public byte[] createSha256HexFromString(String key) throws UnsupportedEncodingException {
        String res = DigestUtils.sha256Hex(key).substring(0, 32).toLowerCase();
        return StringUtils.getBytesUtf8(res);
    }

    public String encode(String channelName, ChatMessage message) {
        return Base64.encodeBytes(encodeBytes(channelName, message));
    }

    public byte[] encode(String channelName, String toEncode) {
        if (checkInitError())
            return new byte[]{};

        String key = channelName + "/" + chatSalt;
        try {
            byte[] encodedKey = createSha256HexFromString(key);
            SecretKeySpec secretKey = new SecretKeySpec(encodedKey, "AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(initVector));
            return cipher.doFinal(StringUtils.getBytesUtf8(toEncode));
        } catch (Exception e) {
            Log.e(TAG, "encode: Can't encode message!");
            e.printStackTrace();
            return new byte[]{};
        }
    }

    public byte[] encodeBytes(String channelName, ChatMessage message) {
        String encoded = gson.toJson(message);
        return encode(channelName, encoded);
    }

    private boolean checkInitError() {
        if (chatSalt.isEmpty()) {
            Log.i(TAG, "checkInitError: Please, initialize api key!");
            return true;
        }

        return false;
    }
}
