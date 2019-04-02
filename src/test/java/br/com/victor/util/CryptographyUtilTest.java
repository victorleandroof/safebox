package br.com.victor.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.util.Base64;

import br.com.victor.safebox.config.cryptography.CryptographyUtil;
import org.junit.Test;

public class CryptographyUtilTest {
	
	@Test
	public void testeCryptography() throws Exception {
		CryptographyUtil cryp = new CryptographyUtil();
		String privatekey = cryp.getPrivateKeyStr();
		String publickey = cryp.getPublicKeyStr();
		String texto = "teste";
		String textoencryp = Base64.getEncoder()
				.encodeToString((cryp.encrypt(publickey,texto.getBytes())));
		String textodecryp = new String(cryp.decrypt(privatekey,textoencryp));
		assertThat(texto).isEqualTo(textodecryp);
	}
	
	@Test
	public void testaCriacaoKeyStore() throws NoSuchAlgorithmException, NoSuchProviderException, KeyStoreException, CertificateException, FileNotFoundException, IOException, InvalidKeyException, IllegalStateException, SignatureException {
		CryptographyUtil cryp = new CryptographyUtil();
		cryp.saveKeysKeyStore("teste", "test11");
	}
	
}
