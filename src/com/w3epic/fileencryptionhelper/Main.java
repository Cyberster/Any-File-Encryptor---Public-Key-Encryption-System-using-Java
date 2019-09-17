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
//		String[] parts = fileToBeEncryptedPath.split("\\.");
//		String extension = parts[parts.length - 1];
//		encryptedFilePath = fileToBeEncryptedPath.replace("." + extension, "") + "_encrypted." + extension;
//		
//		if (encryptor.encryptFile(receiverPublicKeyFilePath, fileToBeEncryptedPath, encryptedFilePath))
//			System.out.println("Encrypted successfully to: " + encryptedFilePath);
//		else
//			System.out.println("Encryption failed!");
		
		
		
		
		// decrypt
		String privateKeyFilePath = "./receiver_keys/private.der";
		String encryptedFilePath2 = "f7d3885e-0089-466c-8ed4-f9408f6d589f.jpg";//"./encryptedFile";
		String decryptedFilePath = "./decryptedFile.jpg";
		
		String[] parts = encryptedFilePath2.split("\\.");
		String extension = parts[parts.length - 1];
		decryptedFilePath = encryptedFilePath2.replace("." + extension, "") + "_decrypted." + extension;
		
		FileEncryptionHelper encryptor2 = new FileEncryptionHelper();
		
		if (encryptor2.decryptFile(privateKeyFilePath, encryptedFilePath2, decryptedFilePath))
			System.out.println("Decrypted successfully to: " + decryptedFilePath);
		else
			System.out.println("Decryption failed!");
		
	}
}
