package br.com.victor.safebox.config.cryptography;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import javax.crypto.Cipher;
import org.bouncycastle.jce.X509Principal;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.x509.X509V3CertificateGenerator;
import org.springframework.stereotype.Component;

@Component
public class CryptographyUtil {

	private static final String ALGORITHM = "RSA";
	private final KeyPair generateKeyPair;
	private String publicKeyStr;
	private final String passwordKeyStore = "banheiro25";
	
	public CryptographyUtil() throws Exception {
		this.generateKeyPair = generateKeyPair();
	}
	public byte[] encrypt(byte[] inputData) throws Exception {
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, generateKeyPair.getPublic());
		byte[] encryptedBytes = cipher.doFinal(inputData);
		return encryptedBytes;
	}

    public byte[] encrypt(String key,byte[] inputData) throws Exception {
        byte[] publicBytes = Base64.getDecoder().decode(key);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey pubKey = keyFactory.generatePublic(keySpec);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        byte[] encryptedBytes = cipher.doFinal(inputData);
        return encryptedBytes;
    }




    public byte[] decryptFromKeyStore(String emailClient,String password,String inputData) throws Exception {
		KeyStore keysotre = KeyStore();
		PrivateKey privateKey = (PrivateKey) keysotre.getKey(emailClient, password.toCharArray());
		byte[] dataByte = Base64.getDecoder().decode(inputData);
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		byte[] decryptedBytes = cipher.doFinal(dataByte);
		return decryptedBytes;
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

	private Certificate[] getCertChain() throws Exception {
		X509Certificate certificate = generateCertificate(generateKeyPair);
		Certificate[] certChain = new Certificate[1];  
		certChain[0] = certificate;
		return certChain;
	}
	
	@SuppressWarnings("deprecation")
	private X509Certificate generateCertificate(KeyPair keyPair) throws Exception{
		BouncyCastleProvider bc = new BouncyCastleProvider();
		bc.put("Alg.Alias.Signature.1.3.14.3.2.29", "SHA1WithRSAEncryption");
		Security.addProvider(bc);
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
		   return cert.generate(signingKey);
	}

	private void saveKeyStoreFile(KeyStore keyStore) throws Exception {
		File file = new File("keystore");
        keyStore.store(new FileOutputStream(file), passwordKeyStore.toCharArray());
		
	}
	private KeyStore KeyStore() throws Exception {
			File file = new File("keystore");
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
	
	public void saveKeysKeyStore(String alias,String password) throws Exception{
		KeyStore keyStore = KeyStore();
		keyStore.setKeyEntry(alias,this.generateKeyPair.getPrivate(), password.toCharArray(),getCertChain());
		saveKeyStoreFile(keyStore);
	}
	

}
