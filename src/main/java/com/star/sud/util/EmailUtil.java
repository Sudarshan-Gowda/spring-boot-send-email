package com.star.sud.util;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EmailUtil {

	@Value("${mail.smtp.auth}")
	private String auth;

	@Value("${mail.smtp.starttls.enable}")
	private String starttlsEnable;

	@Value("${mail.smtp.host}")
	private String host;

	@Value("${mail.smtp.starttls.enable}")
	private String port;

	@Value("${mail.userId}")
	private String fromUser;

	@Value("${mail.userCredentials}")
	private String userCredentials;

	public void sendmail() throws AddressException, MessagingException, IOException {
		String fileName = "/Users/sudarshan/Desktop/test.txt";
		String to = "user01@gmail.com,user02@gmail.com";
		String subject = "Email Test from Spring App";
		String content = "Welcome to Spring Boot Application";
		senEmailWithAttachment(to, subject, content, fileName);
	}

	private void senEmailWithAttachment(String to, String subject, String content, String file)
			throws MessagingException, IOException {
		Message msg = new MimeMessage(getSeesion());
		msg.setFrom(new InternetAddress(fromUser, false));

		msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
		msg.setSubject(subject);
		msg.setContent(content, "text/html");
		msg.setSentDate(new Date());

		MimeBodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setContent(content, "text/html");

		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(messageBodyPart);
		MimeBodyPart attachPart = new MimeBodyPart();

		attachPart.attachFile(file);
		multipart.addBodyPart(attachPart);
		msg.setContent(multipart);
		Transport.send(msg);
	}

	public void senEmail(String to, String subject, String content) throws MessagingException, IOException {
		Message msg = new MimeMessage(getSeesion());
		msg.setFrom(new InternetAddress(fromUser, false));

		msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
		msg.setSubject(subject);
		msg.setContent(content, "text/html");
		msg.setSentDate(new Date());

		Transport.send(msg);
	}

	private Properties getProps() {
		Properties props = new Properties();
		props.put("mail.smtp.auth", auth);
		props.put("mail.smtp.starttls.enable", starttlsEnable);
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", port);
		return props;
	}

	private Session getSeesion() {
		return Session.getInstance(getProps(), new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(fromUser, userCredentials);
			}
		});
	}

}
