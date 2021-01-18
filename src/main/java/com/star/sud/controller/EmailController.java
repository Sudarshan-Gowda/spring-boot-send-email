package com.star.sud.controller;

import java.io.IOException;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.star.sud.util.EmailUtil;

@RestController
public class EmailController {

	@Autowired
	private EmailUtil util;

	@RequestMapping(value = "/sendemail")
	public String sendEmail() {
		try {
			util.sendmail();
			return "Email sent successfully";
		} catch (MessagingException | IOException e) {
			e.printStackTrace();
			return "Failed to send email";
		}

	}
}