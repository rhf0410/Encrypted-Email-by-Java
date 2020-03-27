package com.example.email;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.example.templates.EmailTemplate;

public class Main {

	private static EmailService emailService = new EmailService();
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String from = "rhf0410@testmail.com";
		String to = "user2@testmail.com";
		String subject = "Java mail which has been encrypted";
		
		EmailTemplate template = new EmailTemplate("hello-world-plain.txt");
		Map<String, String> replacements = new HashMap<String, String>();
		replacements.put("user", "Huifeng");
		replacements.put("today", String.valueOf(new Date()));
		String message = template.getTemplate(replacements);
		
		Email email = new Email(from, to, subject, message);
		
		emailService.sendPlainTextMail(email);
	}

}
