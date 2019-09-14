import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;

// http://www.macs.hw.ac.uk/~ml355/lore/pkencryption.htm

public class Main {

	public static void main(String[] args) {
		FileEncryption secure;
		try {
			secure = new FileEncryption();
		
			File publicKeyFile1 	= new File("./keys2/public.der");
			File privateKeyFile1 	= new File("./keys1/private.der");
			File encryptedKeyFile1 	= new File("./keys1/shared_public1.der");
			//File encryptedKeyFile1 	= new File("./keys2/public.der"); // out
			File fileToEncrypt 		= new File("./fileToEncrypt.jpg");
			File encryptedFile 		= new File("./encryptedFile");
			
			// to encrypt a file
			secure.makeKey();
			secure.saveKey(encryptedKeyFile1, publicKeyFile1);
			secure.encrypt(fileToEncrypt, encryptedFile);
	
			// to decrypt it again
			//File publicKeyFile2 	= new File("./keys2/public.der");
			File privateKeyFile2 	= new File("./keys2/private.der");
			File encryptedKeyFile2 	= new File("./keys1/shared_public1.der");
			File unencryptedFile 	= new File("./unencryptedFile.jpg");
			
			secure.loadKey(encryptedKeyFile2, privateKeyFile2);
			secure.decrypt(encryptedFile, unencryptedFile);
		} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
