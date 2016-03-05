package main.storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class FileSecurity {
	
	private static final String ALGORITHM = "AES";
	private static final String TRANSFORMATION = "AES";

	public static void encrypt(File file, String key) {
		execute(file, key, Cipher.ENCRYPT_MODE);
	}

	public static void decrypt(File file, String key) {
		execute(file, key, Cipher.DECRYPT_MODE);
	}
	
	public static void execute(File file, String key, int cipherMode) {
		try {
			Key secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
			Cipher cipher = Cipher.getInstance(TRANSFORMATION);
			cipher.init(cipherMode, secretKey);
			
			byte[] inputBytes = new byte[(int) file.length()];
			FileInputStream fileInputStream = new FileInputStream(file);
			fileInputStream.read(inputBytes);
			
			PrintWriter printWriter = new PrintWriter(file);
			printWriter.close();
			
			byte[] outputBytes = cipher.doFinal(inputBytes);
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			fileOutputStream.write(outputBytes);
			
			fileInputStream.close();
			fileOutputStream.close();
		}
		catch (NoSuchPaddingException | NoSuchAlgorithmException 
				| InvalidKeyException | BadPaddingException 
				| IllegalBlockSizeException | IOException e) {
			e.printStackTrace();
		}
	}
}
