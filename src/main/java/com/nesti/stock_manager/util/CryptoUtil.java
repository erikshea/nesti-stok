package com.nesti.stock_manager.util;

import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


//Creating the symmetric
//class which implements
//the symmetric
public class CryptoUtil {

	static String currentKey = "xp17AFyIVydCo/LfhsSRCQmLqO3OATY04BPZ2pLzKZE=";
	static String currentVector = "/M7hlIbKozj+jbFxlW53Uw==";
	
	// Algorythm/Mode/padding
	private static final String AES_CIPHER_ALGORITHM = "AES/CBC/PKCS5PADDING";

	// Create new random SecretKey
	public static SecretKey createAESKey() throws Exception {
		SecureRandom securerandom = new SecureRandom();
		KeyGenerator keygenerator = KeyGenerator.getInstance("AES");

		keygenerator.init(256, securerandom);
		SecretKey key = keygenerator.generateKey();

		return key;
	}

	// Create new random vector (salt)
	public static byte[] createInitializationVector() {

		// Used with encryption
		byte[] initializationVector = new byte[16];
		SecureRandom secureRandom = new SecureRandom();
		secureRandom.nextBytes(initializationVector);
		return initializationVector;
	}


	public static byte[] Encrypt(String plainText, SecretKey secretKey, byte[] initializationVector)
			throws Exception {
		Cipher cipher = Cipher.getInstance(AES_CIPHER_ALGORITHM);

		IvParameterSpec ivParameterSpec = new IvParameterSpec(initializationVector);

		cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);

		return cipher.doFinal(plainText.getBytes());
	}

	public static String Decrypt(byte[] cipherText, SecretKey secretKey, byte[] initializationVector)
			throws Exception {
		Cipher cipher = Cipher.getInstance(AES_CIPHER_ALGORITHM);

		IvParameterSpec ivParameterSpec = new IvParameterSpec(initializationVector);

		cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);

		byte[] result = cipher.doFinal(cipherText);

		return new String(result);
	}

	public static String Decrypt(String cipherString, SecretKey secretKey, byte[] initializationVector)
			throws Exception {
		var cipherText = Base64.getDecoder().decode(cipherString);
		Cipher cipher = Cipher.getInstance(AES_CIPHER_ALGORITHM);

		IvParameterSpec ivParameterSpec = new IvParameterSpec(initializationVector);

		cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);

		byte[] result = cipher.doFinal(cipherText);

		return new String(result);
	}
	
	public static String Decrypt(String encryptedString) {
		var initializationVector = Base64.getDecoder().decode(currentVector);
		var secretKey = StringToSecretKey(currentKey);
		
		String result = "";
		try {
			result = Decrypt(encryptedString, secretKey, initializationVector);
		} catch (Exception ex){}
		
		return result;
	}
	
	public static String Encrypt(String plainText){
		var initializationVector = Base64.getDecoder().decode(currentVector);
		var secretKey = StringToSecretKey(currentKey);
		
		byte[] result = null;
		
		try {
			result = Encrypt(plainText, secretKey, initializationVector);
		} catch (Exception ex){}
		
		return Base64.getEncoder().encodeToString(result);
	}
	
	public static SecretKey StringToSecretKey(String keyString) {
		byte[] decodedCurrentKey = Base64.getDecoder().decode(keyString);
		return new SecretKeySpec(decodedCurrentKey, 0, decodedCurrentKey.length, "AES");
	}
	
	
	// Driver code
	public static void main(String args[]) throws Exception {
		var test = Encrypt("1234");
		System.out.println(test);
		
		System.out.println(Decrypt(test));
	}
}