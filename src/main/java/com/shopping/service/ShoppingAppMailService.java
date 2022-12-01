package com.shopping.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.shopping.entity.User;

@Service
public class ShoppingAppMailService {
	
	private JavaMailSender javaMailSender;

	@Autowired
	public ShoppingAppMailService(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	public void sendEmail(User user) throws MailException {
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setFrom("testyas612@gmail.com");
		mail.setTo(user.getEmailAddress());
		mail.setSubject("Testing Mail API");
		mail.setText("Hurray ! You have done that dude...");
		javaMailSender.send(mail);
	}
	public void sendEmailWithAttachment(User user) throws MailException, MessagingException {

		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

		helper.setTo(user.getEmailAddress());
		helper.setSubject("Testing Mail API with Attachment");
		helper.setText("Please find the attached document below.");

		ClassPathResource classPathResource = new ClassPathResource("Attachment.pdf");
		helper.addAttachment(classPathResource.getFilename(), classPathResource);

		javaMailSender.send(mimeMessage);
	}

}
