package com.tyaathome.douyudanmakuhelper.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class MessageUtils {

    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();

    public static String receive(byte[] bytes) {
        String str = new String(bytes);
        // Copy from stackoverflow
        String message = bytesToHex(bytes);
        // Get first "/"
        int slashIndex = message.indexOf("2F") / 2;
        String messageType = new String();
        for (int i = 18; i < slashIndex; i++) {
            messageType += (char) bytes[i];
        }

        // Determine type of message
        if (messageType.equals("chatmsg")) {
            // "/nn@="
            int nicknameIndex = message.indexOf("2F6E6E403D") / 2;
            // "/txt@="
            int textIndex = message.indexOf("2F747874403D") / 2;
            // "/cid@="
            int textEndIndex = message.indexOf("2F636964403D") / 2;

            String nickname = changeToChinese(bytes, nicknameIndex, textIndex, 5);
            String decodedNickname = decodeMessage(nickname);
            String text = changeToChinese(bytes, textIndex, textEndIndex, 6);
            String decodedText = decodeMessage(text);
            String result = decodedNickname + ": " + decodedText;
            System.out.println(result);
            return result;
        }
        return null;
    }

    private static String changeToChinese(byte[] receiveMsg, int indexStart, int indexEnd, int num) {
        String text = new String();
        for (int i = indexStart + num; i < indexEnd; i++) {
            if (receiveMsg[i] < 32 || receiveMsg[i] > 126) {
                try {
                    text += "%" + Integer.toHexString((receiveMsg[i] & 0x000000FF) | 0xFFFFFF00).substring(6);
                } catch (StringIndexOutOfBoundsException e) {
                }
            } else {
                text += (char) receiveMsg[i];
            }
        }
        return text;
    }

    private static String decodeMessage(String message) {
        String decodedMessage = message;
        try {
            decodedMessage = URLDecoder.decode(message, "utf-8");
        } catch (UnsupportedEncodingException e) {
        }
        decodedMessage = decode(decodedMessage);
        return decodedMessage;
    }

    public static String decode(String str) {
        return str.replace("@A", "@").replace("@S", "/");
    }

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static byte[] sendMessageContent(String content) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            int[] messageLength = new int[0];
            messageLength = new int[]{calcMessageLength(content), 0x00, 0x00, 0x00};
            int[] code = new int[]{0xb1, 0x02, 0x00, 0x00};
            int[] end = new int[]{0x00};

            for (int i : messageLength)
                byteArrayOutputStream.write(i);
            for (int i : messageLength)
                byteArrayOutputStream.write(i);
            for (int i : code)
                byteArrayOutputStream.write(i);
            byteArrayOutputStream.write(content.getBytes("UTF-8"));
            for (int i : end)
                byteArrayOutputStream.write(i);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byteArrayOutputStream.toByteArray();
    }

    private static int calcMessageLength(String content) throws UnsupportedEncodingException {
        return 4 + 4 + (content == null ? 0 : content.getBytes("UTF-8").length) + 1;
    }

}
