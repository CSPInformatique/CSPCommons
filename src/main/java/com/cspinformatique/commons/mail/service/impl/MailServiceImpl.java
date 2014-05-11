package com.cspinformatique.commons.mail.service.impl;

import java.io.IOException;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.cspinformatique.commons.mail.model.Mail;
import com.cspinformatique.commons.mail.service.MailService;

public class MailServiceImpl implements MailService {
	private String PROPERTY_HOST = "mail.host";
	private String PROPERTY_PORT = "mail.port";
	private String PROPERTY_USERNAME = "mail.username";
	private String PROPERTY_PASSWORD = "mail.password";
	private String PROPERTY_TRANSPORT_PROTOCOL = "mail.transport.protocol";
	private String PROPERTY_DEBUG = "mail.debug";
	private String PROPERTY_SMTP_AUTH = "mail.smtp.auth";
	private String PROPERTY_SMTP_STARTTLS_ENABLE = "mail.smtp.starttls.enable";
	
	private JavaMailSender javaMailSender;
	private Properties properties;
	
	public MailServiceImpl(){
		this.initProperties();
		this.initJavaMailSender();
	}
	
	private void initJavaMailSender(){		
		JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
		
		javaMailSender.setHost(properties.getProperty(PROPERTY_HOST));
		javaMailSender.setPort(Integer.valueOf(properties.getProperty(PROPERTY_PORT)));
		javaMailSender.setUsername(properties.getProperty(PROPERTY_USERNAME));
		javaMailSender.setPassword(properties.getProperty(PROPERTY_PASSWORD));
		javaMailSender.setDefaultEncoding("UTF-8");
		
		Properties javaMailProperties = new Properties();
		javaMailProperties.setProperty(PROPERTY_TRANSPORT_PROTOCOL, properties.getProperty(PROPERTY_TRANSPORT_PROTOCOL));
		javaMailProperties.setProperty(PROPERTY_SMTP_AUTH, properties.getProperty(PROPERTY_SMTP_AUTH));
		javaMailProperties.setProperty(PROPERTY_SMTP_STARTTLS_ENABLE, properties.getProperty(PROPERTY_SMTP_STARTTLS_ENABLE));
		javaMailProperties.setProperty(PROPERTY_DEBUG, properties.getProperty(PROPERTY_DEBUG));
		
		javaMailSender.setJavaMailProperties(javaMailProperties);
	}
	
	private void initProperties(){
		try{
			Resource resource = new ClassPathResource("mail.properties");
			this.properties = PropertiesLoaderUtils.loadProperties(resource);
		}catch(IOException ioEx){
			throw new RuntimeException(ioEx);
		}
	}
	
	@Override
	public void sendMail(Mail mail) {		
		SimpleMailMessage message = new SimpleMailMessage();
		
		message.setFrom(mail.getFrom());
		message.setTo(mail.getTo());
		message.setSubject(mail.getSubject());		
		message.setText(mail.getContent());
		
		this.javaMailSender.send(message);
	}
}
