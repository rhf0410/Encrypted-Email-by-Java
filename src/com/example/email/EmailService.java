package com.example.email;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.example.util.MailEncryptionUtil;

public class EmailService {
	public void sendPlainTextMail(Email email){
		String host = "localhost";
		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", host);
		Session session = Session.getDefaultInstance(properties);
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(email.getFrom()));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(email.getToAsList()));
			message.setSubject(email.getSubject());
			message.setText(email.getMessage());
			//Transport.send(message);
			Transport.send(MailEncryptionUtil.encryptMessage(message));
			System.out.println("Sent message successfully.");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
