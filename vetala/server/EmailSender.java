package server;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.Multipart;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import java.io.FileReader;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

public class EmailSender extends Thread {

	public EmailSender() {
		Setup.reload();
	}

	String target;
	String subject;
	String content;
	
	Map<String, String> settings = new TreeMap<>();
	
	@Override
	public void run() {
		try {
			Properties detail = new Properties();
			detail.put("mail.smtp.host",          Setup.emailServer);
			detail.put("mail.smtp.port",          Setup.emailPort);
			detail.put("mail.smtp.ssl.protocols", Setup.emailSecurity);
			detail.put("mail.smtp.auth",            "true");
			detail.put("mail.smtp.starttls.enable", "true");
			Session session = Session.getInstance(
				detail, 
				new EmailCredential
					(Setup.emailAddress, Setup.emailPassword)
			);

			Message message = new MimeMessage(session);
			String sender = Setup.emailSender + 
							"<" + Setup.emailAddress + ">";
			message.setFrom(new InternetAddress(sender));
			message.setRecipients(
				Message.RecipientType.TO,
				InternetAddress.parse(target)
			);
			message.setSubject(subject);

			MimeBodyPart body = new MimeBodyPart();
			body.setContent(content, "text/html; charset=utf-8");
			Multipart part = new MimeMultipart();
			part.addBodyPart(body);

			message.setContent(part);
			Transport.send(message);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}

class EmailCredential extends Authenticator {
	EmailCredential(String address, String password) {
		this.address = address;
		this.password = password;
	}
	
	String address;
	String password;

	@Override protected PasswordAuthentication 
	getPasswordAuthentication() {
		return new PasswordAuthentication(address, password);
	}
}

