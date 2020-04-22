package com.ct.webscrapping;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class AmazonScrapper {

	public static void main(String[] args) throws IOException, AddressException {

		final String URL = "https://www.amazon.in/Prestige-PGMFB-Sandwich-Toaster-Plates/dp/B00935MGKK/ref=sr_1_3?crid=3VMFJKWAPX1RO&dchild=1&keywords=sandwich+maker+grill+and+toast&qid=1587305326&sprefix=sandwich%2Caps%2C1785&sr=8-3";
		Document document = Jsoup.connect(URL).get();

		// Retrieving the price of the product

		String priceId = "priceblock_ourprice";
		Element priceElement = document.getElementById(priceId);
		String price = priceElement.text();

		// Retrieving the discount the product

		String savingsId = "regularprice_savings";
		Element savingsElement = document.getElementById(savingsId);
		String savings = savingsElement.text();

		System.out.println("The price of the product today is: " + price + "\n" + "and " + savings);
		mailClient(price, savings);

	}

	private static void mailClient(String price, String savings) throws AddressException {

		// Recipient's email ID needs to be mentioned.
		String to = "khaleedhmf@gmail.com";
		
		// Sender's email ID needs to be mentioned
		String from = "tiron073@gmail.com";

		// Assuming you are sending email from through gmails smtp
		String host = "smtp.gmail.com";

		// Get system properties
		Properties properties = System.getProperties();

		// Setup mail server
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", "465");
		properties.put("mail.smtp.ssl.enable", "true");
		properties.put("mail.smtp.auth", "true");

		// Get the Session object.// and pass username and password
		Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

			protected PasswordAuthentication getPasswordAuthentication() {

				return new PasswordAuthentication("tiron073@gmail.com", "************");

			}

		});

		try {
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// Set To: header field of the header.
			 message.addRecipient(Message.RecipientType.BCC, new InternetAddress(to));

			// Set Subject: header field
			message.setSubject("Amazon - Product Price Alert!");

			// Now set the actual message
			message.setText("The price of the product today is: " + price + "\n" + "and " + savings + "\n\n\n\n" + "Regards," + "\n"+ "Amazon Scraper Admin.");

			System.out.println("sending...");
			// Send message
			Transport.send(message);
			System.out.println("Sent message successfully....");
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}

	}

}
