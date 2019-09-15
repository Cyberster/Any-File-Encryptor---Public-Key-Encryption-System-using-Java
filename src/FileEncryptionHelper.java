import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.security.GeneralSecurityException;

class FileEncryptionHelper {
	private FileEncryption secure;
	
	public FileEncryptionHelper() {
		try {
			secure = new FileEncryption();
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		}
	}
	
	public boolean encryptFile(String receiverPublicKeyFilePath, 
			String fileToBeEncryptedPath, String encryptedFilePath) {
		File publicKeyFile1 	= new File(receiverPublicKeyFilePath);		// in
		File publicKeyToBeSharedFile 	= new File("./shared_public_key.der");		// out
		File fileToEncrypt 		= new File(fileToBeEncryptedPath);
		File encryptedFile 		= new File(encryptedFilePath);
		
		try {
			// to encrypt a file
			secure.makeKey();
			secure.saveKey(publicKeyToBeSharedFile, publicKeyFile1);
			secure.encrypt(fileToEncrypt, encryptedFile);
			
			// append encrypted file size to a temp file
			FileOutputStream fos = new FileOutputStream("./temp.bin"); // append , true
			fos.write(longToBytes(encryptedFile.length()));
			
			// append encrypted file to the temp file
			byte[] encryptedFileBytes = Files.readAllBytes(encryptedFile.toPath());
			fos.write(encryptedFileBytes);
			
			// append public key to the temp file
			byte[] sharedPublicKeyFileBytes = Files.readAllBytes(publicKeyToBeSharedFile.toPath());
			fos.write(sharedPublicKeyFileBytes);
			fos.close();
			
			// rename temp file
			encryptedFile.delete();
			new File("./temp.bin").renameTo(encryptedFile.getAbsoluteFile());
			
			// delete shared public key as already attached to the encrypted file
			publicKeyToBeSharedFile.delete();
			
			return true;
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			return false;
		}
	}
	
	public boolean decryptFile(String privateKeyFilePath, String encryptedFilePath, String decryptedFilePath) {
		try {
			// read file size
			File fileIn = new File(encryptedFilePath);
			FileInputStream fis = new FileInputStream(fileIn);
			
			int bufLen = Long.SIZE / Byte.SIZE;
			byte[] buffer = new byte[bufLen];
			fis.read(buffer);
			
			long wholeFileSize = fileIn.length() - bufLen;
			long encryptedFileSize = -1;
			long sharedPublicKeyFileSize = -1;
			encryptedFileSize = bytesToLong(buffer);
			sharedPublicKeyFileSize = wholeFileSize - encryptedFileSize;
			
//			System.out.println("wholeFileSize: " + wholeFileSize + " bytes");
//			System.out.println("encryptedFileSize: " + encryptedFileSize + " bytes");
//			System.out.println("sharedPublicKeyFileSize: " + sharedPublicKeyFileSize + " bytes");
			
			byte[] encryptedBytes = new byte[(int) encryptedFileSize];
			fis.read(encryptedBytes);
			Files.write(new File("./encrypted.tmp").toPath(), encryptedBytes);
			
			byte[] sharedPublicKeyBytes = new byte[(int) sharedPublicKeyFileSize];
			fis.read(sharedPublicKeyBytes);
			Files.write(new File("./sharedPublicKey.der").toPath(), sharedPublicKeyBytes);			
			
			fis.close();
			
	
			// to decrypt it again
			File privateKeyFile 		= new File(privateKeyFilePath);			// in
			File sharedPublicKeyFile 	= new File("./sharedPublicKey.der");	// in
			File encryptedFile 			= new File("./encrypted.tmp");			
			File unencryptedFile 		= new File(decryptedFilePath);			
			
			secure.loadKey(sharedPublicKeyFile, privateKeyFile);
			secure.decrypt(encryptedFile, unencryptedFile);
			
			// delete temp files
			sharedPublicKeyFile.delete();
			encryptedFile.delete();
			
			return true;
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			return false;
		}
	}
	
	// https://stackoverflow.com/a/4485196/2383990
	public static byte[] longToBytes(long x) {
	    ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
	    buffer.putLong(x);
	    return buffer.array();
	}

	public static long bytesToLong(byte[] bytes) {
	    ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
	    buffer.put(bytes);
	    buffer.flip();//need flip 
	    return buffer.getLong();
	}
}

