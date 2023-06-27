package com.example.healthcare.applicationlayer;

import com.scottyab.aescrypt.AESCrypt;

import java.security.GeneralSecurityException;

public class Encryption {
    public String encryptData(String data) throws GeneralSecurityException {
        String encryptedData;
        encryptedData = AESCrypt.encrypt("csecu", data);
        return encryptedData;
    }

    public String decryptData(String data) throws GeneralSecurityException {
        String decryptedData;
        decryptedData = AESCrypt.decrypt("csecu", data);
        return decryptedData;
    }
}
