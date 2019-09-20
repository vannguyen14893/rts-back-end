package com.cmc.recruitment.utils;

import java.util.Date;
import java.util.Random;

import javax.activation.DataHandler;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component("emailService")
public class EmailHelper {
	@Autowired
	public JavaMailSender emailSender;

//	private  SimpleDateFormat dateParser = new SimpleDateFormat("dd-MM-yyyy HH:mm");
	final static Logger LOG = Logger.getLogger(EmailHelper.class);

	/**
	 * 
	 * @description: for send 1 simple email.
	 * @author: NHPhong.
	 * @create_date: Jan 22, 2018.
	 * @modifer: NHPhong.
	 * @modifer_date: Jan 22, 2018.
	 * @param to
	 *            :is email of user
	 * @param subject
	 * @param text
	 *            :is the content of the email to send
	 * @return
	 */
	public void sendSimpleMessage(String from, String to, String subject, String text) throws MailException {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(from);
		message.setTo(to);
		message.setSubject(subject);
		message.setText(text);
		emailSender.send(message);
	}

	/**
	 * 
	 * @description: use for random new password.
	 * @author: NHPhong.
	 * @create_date: Jan 23, 2018.
	 * @modifer: NHPhong.
	 * @modifer_date: Jan 23, 2018.
	 * @return newPass
	 */
	public String createRandomPassWord() {
		StringBuilder newPass = new StringBuilder();
		Random rnd = new Random();
		while (newPass.length() < 8) { // length of the random string.
			newPass.append(rnd.nextInt(9));
		}
		return newPass.toString();
	}

	/**
	 * @author: NHPhong.
	 * @description: use for send email with text is Tag HTML.
	 * @param from
	 * @param to
	 * @param subject
	 * @param text
	 *            : accept tag HTML
	 * @throws MessagingException
	 */
	public void sendMessage(String from, String to, String subject, String text) throws MessagingException {
		MimeMessage message = emailSender.createMimeMessage();
		MimeMessageHelper hepler = new MimeMessageHelper(message, true, "utf-8");
		hepler.setFrom(from);
		hepler.setTo(to);
		hepler.setSubject(subject);
		hepler.setText(text, true);
		emailSender.send(message);
	}

	public void sendMessage(String from, String[] to, String[] cc, String subject, String text)
			throws MessagingException {
		MimeMessage message = emailSender.createMimeMessage();
		MimeMessageHelper hepler = new MimeMessageHelper(message, true, "utf-8");
		hepler.setFrom(from);
		hepler.setTo(to);
		hepler.setCc(cc);
		hepler.setSubject(subject);
		hepler.setText(text, true);
		emailSender.send(message);
	}

	public void sendMessage(String from, String[] to, String subject, String text) throws MessagingException {
		MimeMessage message = emailSender.createMimeMessage();
		MimeMessageHelper hepler = new MimeMessageHelper(message, true, "utf-8");
		hepler.setFrom(from);
		hepler.setTo(to);
		hepler.setSubject(subject);
		hepler.setText(text, true);
		emailSender.send(message);
	}
	public void sendMeeting(String from, String[] to, String subject, String text, String start, String end, String location) throws MessagingException{
		MimeMessage message = emailSender.createMimeMessage();
		message.setFrom(new InternetAddress(from));

		// Set TO
		if (to != null && (to.length > 0)) {
			InternetAddress[] address = new InternetAddress[to.length];

			for (int i = 0; i < to.length; i++) {
				address[i] = new InternetAddress(to[i]);
			}

			message.setRecipients(Message.RecipientType.TO, address);
		}

		// Set subject
		message.setSubject(subject);
		// Create iCalendar message
		StringBuffer messageText = new StringBuffer();
		messageText.append("BEGIN:VCALENDAR\n" + "PRODID:-//Microsoft Corporation//Outlook 9.0 MIMEDIR//EN\n"
				+ "VERSION:2.0\n" + "METHOD:REQUEST\n" + "BEGIN:VEVENT\n" + "ORGANIZER:MAILTO:");
		messageText.append(from);
		messageText.append("\n" + "DTSTART:");
		messageText.append(start);
		messageText.append("\n" + "DTEND:");
		messageText.append(end);
		messageText.append("\n" + "LOCATION:");
		messageText.append(location);
		messageText.append("\n" + "UID:");
		messageText.append("LIS-42");
		messageText.append("\n" + "DTSTAMP:");
		messageText.append(new Date());
		messageText.append("\n" + "DESCRIPTION;ALTREP=\"CID:<eventDescriptionHTML>\"");
		messageText.append("\n" + "BEGIN:VALARM\n" + "TRIGGER:-PT15M\n" + "ACTION:DISPLAY\n"
				+ "DESCRIPTION:Reminder\n" + "END:VALARM\n" + "END:VEVENT\n" + "END:VCALENDAR");

		Multipart mp = new MimeMultipart();

		MimeBodyPart meetingPart = new MimeBodyPart();
		meetingPart.setDataHandler(
				new DataHandler(new StringDataSource(messageText.toString(), "text/calendar", "meetingRequest")));
		mp.addBodyPart(meetingPart);
		MimeBodyPart descriptionPart = new MimeBodyPart();
		descriptionPart.setDataHandler(
				new DataHandler(new StringDataSource(text, "text/html", "eventDescription")));
		descriptionPart.setContentID("<eventDescriptionHTML>");
		mp.addBodyPart(descriptionPart);

		message.setContent(mp);

		// send message
		emailSender.send(message);
	}
	
}
