package br.com.victor.safebox.config.cryptography;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import javax.crypto.Cipher;

import org.bouncycastle.jce.X509Principal;
import org.bouncycastle.x509.X509V3CertificateGenerator;

public class CryptographyUtil {
	private static final String ALGORITHM = "RSA";
	private KeyPair generateKeyPair;
	private String publicKeyStr;
	private String privateKeyStr;
	private String passwordKeyStore = "banheiro25";
	
	public CryptographyUtil() throws NoSuchAlgorithmException, NoSuchProviderException, KeyStoreException {
		this.generateKeyPair = generateKeyPair();
	}
	public byte[] encrypt(String publicKeyStr,byte[] inputData) throws Exception {
		byte[] publicKey  = Base64.getDecoder().decode(publicKeyStr);
		PublicKey key = KeyFactory.getInstance(ALGORITHM).generatePublic(new X509EncodedKeySpec(publicKey));
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] encryptedBytes = cipher.doFinal(inputData);
		return encryptedBytes;
	}
	

	public byte[] decrypt(String privateKeyStr,String inputData) throws Exception {
		byte[] privatekey  = Base64.getDecoder().decode(privateKeyStr);
		byte[] dataByte = Base64.getDecoder().decode(inputData);
		PrivateKey key = KeyFactory.getInstance(ALGORITHM).generatePrivate(new PKCS8EncodedKeySpec(privatekey));
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, key);
		byte[] decryptedBytes = cipher.doFinal(dataByte);
		return decryptedBytes;
	}
	public byte[] decryptFromKeyStore(String emailClient,String password,String inputData) throws Exception {
		KeyStore keysotre = KeyStore();
		PrivateKey privateKey = (PrivateKey) keysotre.getKey(emailClient, password.toCharArray());
		String privateKeyStr = Base64.getEncoder().encodeToString(privateKey.getEncoded());
		return decrypt(privateKeyStr, inputData);
	}
	public  KeyPair generateKeyPair() throws NoSuchAlgorithmException, NoSuchProviderException {
		KeyPairGenerator keyGen = KeyPairGenerator.getInstance(ALGORITHM);
		keyGen.initialize(2048);
		KeyPair generateKeyPair = keyGen.generateKeyPair();
		return generateKeyPair;
	}
	
	public String getPublicKeyStr() {
		this.publicKeyStr = Base64.getEncoder().encodeToString(generateKeyPair.getPublic().getEncoded());
		return publicKeyStr;
	}
	public String getPrivateKeyStr() {
		this.privateKeyStr = Base64.getEncoder().encodeToString(generateKeyPair.getPrivate().getEncoded());
		return privateKeyStr;
	}
	private Certificate[] getCertChain() throws CertificateEncodingException, InvalidKeyException, IllegalStateException, NoSuchProviderException, NoSuchAlgorithmException, SignatureException {
		X509Certificate certificate = generateCertificate(generateKeyPair);
		Certificate[] certChain = new Certificate[1];  
		certChain[0] = certificate;
		return certChain;
	}
	
	@SuppressWarnings("deprecation")
	private X509Certificate generateCertificate(KeyPair keyPair) throws CertificateEncodingException, InvalidKeyException, IllegalStateException, NoSuchProviderException, NoSuchAlgorithmException, SignatureException{  
		   X509V3CertificateGenerator cert = new X509V3CertificateGenerator();   
		   cert.setSerialNumber(BigInteger.valueOf(Math.abs(new Random().nextLong())));   //or generate a random number  
		   cert.setSubjectDN(new X509Principal("CN=test"));  //see examples to add O,OU etc  
		   cert.setIssuerDN(new X509Principal("CN=test")); //same since it is self-signed  
		   cert.setPublicKey(keyPair.getPublic());  
		   Date d = new Date();
		   Calendar c = Calendar.getInstance();
		   c.setTime(d);
		   c.add(Calendar.DATE, +365);
		   cert.setNotBefore(new Date());  
		   cert.setNotAfter(c.getTime());  
		   cert.setSignatureAlgorithm("SHA1WithRSAEncryption");   
		    PrivateKey signingKey = keyPair.getPrivate();    
		   return cert.generate(signingKey, "BC");  
	}
	private void saveKeyStoreFile(KeyStore keyStore) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, FileNotFoundException, IOException {
		File file = new File("keystore");
        keyStore.store(new FileOutputStream(file), passwordKeyStore.toCharArray());
		
	}
	private KeyStore KeyStore() throws KeyStoreException, NoSuchAlgorithmException, CertificateException, FileNotFoundException, IOException {
			File file = new File("keystore.jks");
		    KeyStore keyStore = KeyStore.getInstance("JCEKS");
		    if (file.exists()) {
		        // if exists, load
		        keyStore.load(new FileInputStream(file), passwordKeyStore.toCharArray());
		    } else {
		        // if not exists, create
		        keyStore.load(null, null);
		        keyStore.store(new FileOutputStream(file), passwordKeyStore.toCharArray());
		    }
		    return keyStore;
	}
	
	public void saveKeysKeyStore(String alias,String password) throws InvalidKeyException, KeyStoreException, IllegalStateException, NoSuchProviderException, NoSuchAlgorithmException, SignatureException, CertificateException, FileNotFoundException, IOException{
		KeyStore keyStore = KeyStore();
		keyStore.setKeyEntry(alias,this.generateKeyPair.getPrivate(), password.toCharArray(),getCertChain());
		saveKeyStoreFile(keyStore);
	}
	

}
