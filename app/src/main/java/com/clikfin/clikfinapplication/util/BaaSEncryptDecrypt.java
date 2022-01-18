package com.clikfin.clikfinapplication.util;

import android.util.Log;

import com.clikfin.clikfinapplication.network.APIClient;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.annotation.PostConstruct;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class BaaSEncryptDecrypt {

    private static final String TAG = BaaSEncryptDecrypt.class.getName();
    private static String secretKeyLoanTap = APIClient.LOANTAP_APIKEY;

    private static SecretKeySpec secretKeySpecLoanTap;
    private static Cipher cipherLoanTap;
    private byte[] iv = new byte[16];

    BaaSEncryptDecrypt(){
        setup();
    }

    public static void setup() {
        try {
            secretKeySpecLoanTap = new SecretKeySpec(secretKeyLoanTap.getBytes("UTF-8"), "AES");
            cipherLoanTap = Cipher.getInstance("AES/ECB/PKCS5PADDING");
        } catch (UnsupportedEncodingException e) {
            Log.e(TAG, "Error while creating Crypto instance " + e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            Log.e(TAG, "Error while creating Crypto instance " + e.getMessage());
        } catch (NoSuchPaddingException e) {
            Log.e(TAG, "Error while creating Crypto instance " + e.getMessage());
        }
    }


    /**
     * Encrypt the string with this internal algorithm.
     *
     * @param toBeEncrypt string object to be encrypt.
     * @return returns encrypted string.
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidAlgorithmParameterException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    public static String encrypt(String toBeEncrypt) {
        try {
            setup();
            cipherLoanTap.init(Cipher.ENCRYPT_MODE, secretKeySpecLoanTap);
            byte[] encrypted = cipherLoanTap.doFinal(toBeEncrypt.getBytes());
            Log.e(TAG, "encrypt:"+toBeEncrypt+"    " + android.util.Base64.encodeToString(encrypted, Base64.URL_SAFE));
            return android.util.Base64.encodeToString(encrypted, Base64.URL_SAFE).replace("\n","");

        } catch (IllegalBlockSizeException | BadPaddingException | InvalidKeyException e) {
            e.getMessage();
        }
        return "";
    }

    /**
     * Decrypt this string with the internal algorithm. The passed argument should be encrypted using
     * {@link #encrypt(String) encrypt} method of this class.
     *
     * @param encrypted encrypted string that was encrypted using {@link #encrypt(String) encrypt} method.
     * @return decrypted string.
     * @throws InvalidAlgorithmParameterException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
   /* public String decrypt(String encrypted) throws BusinessException {
        try {
            cipherLoanTap.init(Cipher.DECRYPT_MODE, secretKeySpecLoanTap);
            byte[] decryptedBytes = cipherLoanTap.doFinal(Base64.decodeBase64(encrypted));
            return new String(decryptedBytes);

        } catch (IllegalBlockSizeException e) {
            throw new BusinessException("Decryption failed", e.getMessage(), "400");
        } catch (BadPaddingException e) {
            throw new BusinessException("Decryption failed", e.getMessage(), "400");
        } catch (InvalidKeyException e) {
            throw new BusinessException("Decryption failed", e.getMessage(), "400");
        }
    }*/
}