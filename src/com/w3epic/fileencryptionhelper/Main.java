package com.w3epic.fileencryptionhelper;

// http://www.macs.hw.ac.uk/~ml355/lore/pkencryption.htm

public class Main {	
	public static void main(String[] args) {		
//		FileEncryptionHelper encryptor = new FileEncryptionHelper();
//		
//		// encrypt
//		String receiverPublicKeyFilePath = "./receiver_keys/public.der";
//		String fileToBeEncryptedPath = "./69449593_2414227948816523_124365601303953408_o.png.jpg";
//		String encryptedFilePath = "./encryptedFile";
//		
//		encryptor.encryptFile(receiverPublicKeyFilePath, fileToBeEncryptedPath, encryptedFilePath);
		
		
		// decrypt
		String privateKeyFilePath = "./receiver_keys/private.der";
		String encryptedFilePath2 = "./encryptedFile";
		String decryptedFilePath = "./decryptedFile.jpg";
		
		FileEncryptionHelper encryptor2 = new FileEncryptionHelper();
		encryptor2.decryptFile(privateKeyFilePath, encryptedFilePath2, decryptedFilePath);
		
	}
}
