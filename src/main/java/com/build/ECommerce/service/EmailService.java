package com.build.ECommerce.service;

import com.build.ECommerce.entity.Order;
import com.build.ECommerce.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public void sendOrderConfirmationEmail(Order order) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo("vhadjz5965@minitts.net");
        message.setSubject("Order Confirmation");
        message.setText("Your Order has been confirmed. Order Id : "+order.getId());
        mailSender.send(message);
    }

    public void sendEmailConfirmation(User user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(user.getEmail());
        message.setSubject("Confirm your Email");
        message.setText("Please confirm your email by entering this code "+user.getConfirmationCode());
        mailSender.send(message);
    }
}
