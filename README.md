# Any File Encryptor - Public Key Encryption System using Java

This project is a helper class and extension of -

[Link to the original source](http://www.macs.hw.ac.uk/~ml355/lore/pkencryption.htm) - Encrypting files with Public Key Encryption in Java


## Generate public and private RSA keys:
========================================
To generate a private key of length 2048 bits:

```
openssl genrsa -out private.pem 2048
```

To get it into the required (PKCS#8, DER) format:

```
openssl pkcs8 -topk8 -in private.pem -outform DER -out private.der -nocrypt
```

To generate a public key from the private key:

```
openssl rsa -in private.pem -pubout -outform DER -out public.der
```

## File format: size of encrypted bytes + encrypted bytes + shared public key

## Sender's side:

=================

* Step 1:		Generate and save shared public key from sender's private key + receiver's public key
* Step 2:		Encrypt src's byte array using RSA using sender's private key + shared public key
* Step 3:		Create temp file
* Step 4:		Append encrypted file size (8 byte) to temp file
* Step 5:		Append byte array of encrypted file to temp file
* Step 6:		Append shared public key to temp file
* Step 7:		Rename the temp file to encrypted file
* Step 8:		Send encrypted file to the receiver
* Step 9:		Delete shared public key as already attached to the encrypted file

## Receiver's side:

===================

* Step 1:		After receiving, load whole encrypted file into byte array
* Step 2:		Read first 8 byte and convert it to equivalent long value to get encrypted file size
* Step 3:		Read next up to encrypted file size and save it to encrypted file
* Step 4:		Calculate shared public key size i.e. (whole size - 8 - encrypted fule size)
* Step 5:		Read next up to shared public key size and save it to shared public key file
* Step 6:		Load receiver's private key and sender's shared public key
* Step 7:		Decrypt using receiver's private key and sender's shared public key
* Step 8:		Delete encrypted file and shared public key file
