package com.example.util;

import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.Security;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Date;

import javax.mail.Address;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;

import org.bouncycastle.cms.CMSAlgorithm;
import org.bouncycastle.cms.jcajce.JceCMSContentEncryptorBuilder;
import org.bouncycastle.cms.jcajce.JceKeyTransRecipientInfoGenerator;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.mail.smime.SMIMEEnvelopedGenerator;

public class MailEncryptionUtil {
	public static MimeMessage encryptMessage(MimeMessage message) throws Exception{
		Security.addProvider(new BouncyCastleProvider());
		
		SMIMEEnvelopedGenerator gen = new SMIMEEnvelopedGenerator();
		X509Certificate recipientCert = getRecipientPublicCertificate(message);

		gen.addRecipientInfoGenerator(new JceKeyTransRecipientInfoGenerator(recipientCert).setProvider("BC"));
		
		MimeBodyPart msg = new MimeBodyPart();
		msg.setContent(message.getContent(), message.getContentType());
		
		MimeBodyPart mp = gen.generate(msg, new JceCMSContentEncryptorBuilder(CMSAlgorithm.RC2_CBC).setProvider("BC").build());
		message.setContent(mp.getContent(), mp.getContentType());
		message.saveChanges();
		
		return message;
	}
	
	public static MimeMessage signMessage(MimeMessage message){
		Security.addProvider(new BouncyCastleProvider());
		
		
		return null;
	}
	
	private static X509Certificate getRecipientPublicCertificate(MimeMessage message) throws Exception{
		for(Address address: message.getAllRecipients()){
			System.out.println(address.toString());
		}
		
		CertificateFactory factory = CertificateFactory.getInstance("X.509");
		ClassLoader classLoader = MailEncryptionUtil.class.getClassLoader();
		File file = new File(classLoader.getResource("com/example/resources/test03.cer").getFile());
		FileInputStream fis = new FileInputStream(file);
		KeyStore ks = KeyStore.getInstance("PKCS12");
		ks.load(fis, "123456".toCharArray());
		X509Certificate recipientCert = (X509Certificate) ks.getCertificate("solapure");
		return recipientCert;
	}
}
