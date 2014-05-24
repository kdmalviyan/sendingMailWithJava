package com.kd.example.notifications;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class SMTPAuthenticator extends Authenticator {
	public PasswordAuthentication getPasswordAuthentication() {
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(new File("mails.properties")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new PasswordAuthentication(prop.getProperty("mail.smtp.user"), prop.getProperty("mail.smtp.user.password"));
	}
}
