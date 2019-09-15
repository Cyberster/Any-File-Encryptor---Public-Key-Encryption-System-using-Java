// http://www.macs.hw.ac.uk/~ml355/lore/pkencryption.htm

public class Main {	
	public static void main(String[] args) {		
		FileEncryptionHelper encryptor = new FileEncryptionHelper();
		
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