/**
 * 
 */
package com.kd.example.notifications;

import java.io.File;
import java.io.FileInputStream;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * @author kuldeep
 * 
 */
public class SendMail {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		sendSimpleMail();
		sendMailWithHtmlContent();
		sendMailWithAttachement();
	}

	public static void sendMailWithHtmlContent() {

		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(new File("mails.properties")));
		} catch (Exception e) {
			e.printStackTrace();
		}

		SMTPAuthenticator auth = new SMTPAuthenticator();
		Session session = Session.getInstance(prop, auth);
		session.setDebug(true);

		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(prop
					.getProperty("mail.smtp.user")));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(
					prop.getProperty("smtp.mailTo")));
			message.setSubject("Eail with Html content " + new Date());
			message.setContent("<h1>This is actual message</h1>", "text/html");
			Transport transport = session.getTransport("smtp");
			transport.connect(prop.getProperty("mail.smtp.host"),
					Integer.valueOf(prop.getProperty("mail.smtp.port")),
					prop.getProperty("mail.smtp.user.username"),
					prop.getProperty("mail.smtp.user.password"));
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void sendSimpleMail() {

		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(new File("mails.properties")));
		} catch (Exception e) {
			e.printStackTrace();
		}

		SMTPAuthenticator auth = new SMTPAuthenticator();
		Session session = Session.getInstance(prop, auth);
		session.setDebug(true);

		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(prop
					.getProperty("mail.smtp.user")));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(
					prop.getProperty("smtp.mailTo")));
			message.setSubject("Simple Email with text only. " + new Date());
			message.setText("Hi this is simple testing mail.");
			Transport transport = session.getTransport("smtp");
			transport.connect(prop.getProperty("mail.smtp.host"),
					Integer.valueOf(prop.getProperty("mail.smtp.port")),
					prop.getProperty("mail.smtp.user.username"),
					prop.getProperty("mail.smtp.user.password"));
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void sendMailWithAttachement() {

		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(new File("mails.properties")));
		} catch (Exception e) {
			e.printStackTrace();
		}

		SMTPAuthenticator auth = new SMTPAuthenticator();
		Session session = Session.getInstance(prop, auth);
		session.setDebug(true);

		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(prop
					.getProperty("mail.smtp.user")));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(
					prop.getProperty("smtp.mailTo")));
			message.setSubject("Email with attachment " + new Date());

			BodyPart messageBodyPart1 = new MimeBodyPart();
			messageBodyPart1.setText("Please find attachement.");

			MimeBodyPart messageBodyPart2 = new MimeBodyPart();

			String pathToAttachment = "pom.xml";
			DataSource source = new FileDataSource(pathToAttachment);

			messageBodyPart2.setDataHandler(new DataHandler(source));
			messageBodyPart2.setFileName(pathToAttachment);
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart1);
			multipart.addBodyPart(messageBodyPart2);
			message.setContent(multipart);

			Transport transport = session.getTransport("smtp");
			transport.connect(prop.getProperty("mail.smtp.host"),
					Integer.valueOf(prop.getProperty("mail.smtp.port")),
					prop.getProperty("mail.smtp.user.username"),
					prop.getProperty("mail.smtp.user.password"));
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();

			System.out.println("message sent....");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
