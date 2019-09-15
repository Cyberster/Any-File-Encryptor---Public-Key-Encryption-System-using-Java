import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.security.GeneralSecurityException;

// http://www.macs.hw.ac.uk/~ml355/lore/pkencryption.htm

public class Main {

//	public static void main(String[] args) {
//		FileEncryption secure;
//		try {
//			secure = new FileEncryption();
//		
//			File publicKeyFile1 	= new File("./receiver_keys/public.der");		// in
//			File privateKeyFile1 	= new File("./sender_keys/private.der");		// in
//			File encryptedKeyFile1 	= new File("./sender_keys/shared_public.der");	// out
//			File fileToEncrypt 		= new File("./fileToEncrypt.jpg");
//			File encryptedFile 		= new File("./encryptedFile");
//			
//			//secure.loadKey(encryptedKeyFile1, privateKeyFile1);
//			
//			/** file format: size of encrypted bytes + encrypted bytes + shared public key **/
//			
////			// to encrypt a file
////			secure.makeKey();
////			secure.saveKey(encryptedKeyFile1, publicKeyFile1);
////			secure.encrypt(fileToEncrypt, encryptedFile);
////			
////			// append encrypted file size to a temp file
////			FileOutputStream fos = new FileOutputStream("./temp.bin"); // append , true
////			fos.write(longToBytes(encryptedFile.length()));
////			
////			// append encrypted file to the temp file
////			byte[] encryptedFileBytes = Files.readAllBytes(encryptedFile.toPath());
////			fos.write(encryptedFileBytes);
////			
////			// append public key to the temp file
////			byte[] sharedPublicKeyFileBytes = Files.readAllBytes(encryptedKeyFile1.toPath());
////			fos.write(sharedPublicKeyFileBytes);
////			fos.close();
////			
////			// rename temp file
////			encryptedFile.delete();
////			new File("./temp.bin").renameTo(encryptedFile.getAbsoluteFile());
//			
//			
//			
//			
//			// read file size
//			File fileIn = new File("./encryptedFile");
//			FileInputStream fis = new FileInputStream(fileIn);
//			
//			int bufLen = Long.SIZE / Byte.SIZE;
//			byte[] buffer = new byte[bufLen];
//			System.out.println(bufLen);
//			fis.read(buffer);
//			
//			long wholeFileSize = fileIn.length() - bufLen;
//			long encryptedFileSize = -1;
//			long sharedPublicKeyFileSize = -1;
//			encryptedFileSize = bytesToLong(buffer);
//			sharedPublicKeyFileSize = wholeFileSize - encryptedFileSize;
//			
//			System.out.println("wholeFileSize: " + wholeFileSize + " bytes");
//			System.out.println("encryptedFileSize: " + encryptedFileSize + " bytes");
//			System.out.println("sharedPublicKeyFileSize: " + sharedPublicKeyFileSize + " bytes");
//			
//			byte[] encryptedBytes = new byte[(int) encryptedFileSize];
//			fis.read(encryptedBytes);
//			Files.write(new File("./encrpd").toPath(), encryptedBytes);
//			
//			byte[] sharedPublicKeyBytes = new byte[(int) sharedPublicKeyFileSize];
//			fis.read(sharedPublicKeyBytes);
//			Files.write(new File("./sharedPublicKey.der").toPath(), sharedPublicKeyBytes);			
//			
//			fis.close();
//			
//	
//			// to decrypt it again
////			File publicKeyFile2 	= new File("./receiver_keys/public.der");
//			File privateKeyFile2 	= new File("./receiver_keys/private.der");		// in
////			File encryptedKeyFile2 	= new File("./sender_keys/shared_public.der");	// in
//			File encryptedKeyFile2 	= new File("./sharedPublicKey.der");	// in
//			File encryptedFile2 	= new File("./encrpd");			
//			File unencryptedFile2 	= new File("./unencryptedFile.jpg");			
//			
//			secure.loadKey(encryptedKeyFile2, privateKeyFile2);
//			secure.decrypt(encryptedFile2, unencryptedFile2);
//			
//			// delete temp files
//			encryptedKeyFile2.delete();
//			encryptedFile2.delete();
//		} catch (GeneralSecurityException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//	
//	
//	// https://stackoverflow.com/a/4485196/2383990
//	public static byte[] longToBytes(long x) {
//	    ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
//	    buffer.putLong(x);
//	    return buffer.array();
//	}
//
//	public static long bytesToLong(byte[] bytes) {
//	    ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
//	    buffer.put(bytes);
//	    buffer.flip();//need flip 
//	    return buffer.getLong();
//	}
	
	public static void main(String[] args) {
//		File publicKeyFile1 	= new File("./receiver_keys/public.der");		// in
//		File privateKeyFile1 	= new File("./sender_keys/private.der");		// in
//		File encryptedKeyFile1 	= new File("./sender_keys/shared_public.der");	// out
//		File fileToEncrypt 		= new File("./fileToEncrypt.jpg");
//		File encryptedFile 		= new File("./encryptedFile");
		
		FileEncryptor encryptor = new FileEncryptor();
		
		// encrypt
		String receiverPublicKeyFilePath = "./receiver_keys/public.der";
		String fileToBeEncryptedPath = "./fileToEncrypt.jpg";
		String encryptedFilePath = "./encryptedFile";
		
		encryptor.encryptFile(receiverPublicKeyFilePath, fileToBeEncryptedPath, encryptedFilePath);
		
		
		
		// decrypt
		String privateKeyFilePath = "./receiver_keys/private.der";
		encryptedFilePath = "./encryptedFile";
		String decryptedFilePath = "./decryptedFile.jpg";
		
		encryptor.decryptFile(privateKeyFilePath, encryptedFilePath, decryptedFilePath);
		
	}
}

class FileEncryptor {
	private FileEncryption secure;
	
	public FileEncryptor() {
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
