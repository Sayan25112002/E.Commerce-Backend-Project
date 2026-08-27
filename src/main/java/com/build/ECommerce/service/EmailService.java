package com.build.ECommerce.service;

import com.build.ECommerce.entity.Order;
import com.build.ECommerce.entity.OrderItem;
import com.build.ECommerce.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

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
        message.setSubject("Order Confirmation - Order # "+order.getId());
        StringBuilder messageText = new StringBuilder();
        messageText.append("Order Confirmed \n");
        messageText.append("Hello\n\n");
        messageText.append("Your Order has been placed successfully\n\n");
        messageText.append("Order ID: "+order.getId()+"\n");
        messageText.append("Order Status: "+order.getStatus()+"\n");
        messageText.append("Order Date: "+order.getCreatedAt()+"\n\n");
        messageText.append("Order Details:\n");
        messageText.append("--------------------------------\n");
        for(OrderItem orderItem : order.getOrderItems()) {
            BigDecimal subTotal = orderItem.getPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity()));
            messageText.append("Product: ")
                    .append(orderItem.getProduct().getName())
                    .append("\n");
            messageText.append("Quantity: ")
                    .append(orderItem.getQuantity())
                    .append("\n");
            messageText.append("Price per item: ")
                    .append(orderItem.getPrice())
                    .append("\n");
            messageText.append("SubTotal: $")
                    .append(subTotal)
                    .append("\n\n");
        }
        messageText.append("---------------------------------\n");
        messageText.append("Thank You For Shopping with Us!\n\n");
        messageText.append("Best Regards\n");
        messageText.append("Ecommerce Team");
        message.setText(messageText.toString());
        mailSender.send(message);
    }

    public void sendDeliveringConfirmationEmail(Order order) {
        SimpleMailMessage deliveringMessage = new SimpleMailMessage();
        deliveringMessage.setFrom(fromEmail);
        deliveringMessage.setTo("vhadjz5965@minitts.net");
        deliveringMessage.setSubject("Order Delivery - Order # "+order.getId());
        StringBuilder deliveringText = new StringBuilder();
        deliveringText.append("Order Delivery \n");
        deliveringText.append("Your order "+order.getId()+" is on delivery\n");
        deliveringText.append("Order ID: "+order.getId()+"\n");
        deliveringText.append("Order Status: "+order.getStatus()+"\n");
        deliveringText.append("Order Address: "+order.getAddress()+"\n");
        deliveringText.append("Your Order will be delivered on the given address\n");
        deliveringText.append("---------------------------------\n");
        deliveringText.append("Thank You For Shopping with Us!\n\n");
        deliveringText.append("Best Regards\n");
        deliveringText.append("Ecommerce Team");
        deliveringMessage.setText(deliveringText.toString());
        mailSender.send(deliveringMessage);
    }

    public void sendDeliveredConfirmationEmail(Order order) {
        SimpleMailMessage deliveredMessage = new SimpleMailMessage();
        deliveredMessage.setFrom(fromEmail);
        deliveredMessage.setTo("vhadjz5965@minitts.net");
        deliveredMessage.setSubject("Order Delivered - Order # "+order.getId());
        StringBuilder deliveredText = new StringBuilder();
        deliveredText.append("Order Delivered \n");
        deliveredText.append("Your order "+order.getId()+" has been delivered to your given address\n");
        deliveredText.append("Order ID: "+order.getId()+"\n");
        deliveredText.append("Order Status: "+order.getStatus()+"\n");
        deliveredText.append("Order Address: "+order.getAddress()+"\n");
        deliveredText.append("We hope you are satisfied with your success");
        deliveredText.append("---------------------------------\n");
        deliveredText.append("Thank You For Shopping with Us!\n\n");
        deliveredText.append("Best Regards\n");
        deliveredText.append("Ecommerce Team");
        deliveredMessage.setText(deliveredText.toString());
        mailSender.send(deliveredMessage);
    }

    public void sendCancelledConfirmationEmail(Order order) {
        SimpleMailMessage cancelledMessage = new SimpleMailMessage();
        cancelledMessage.setFrom(fromEmail);
        cancelledMessage.setTo("vhadjz5965@minitts.net");
        cancelledMessage.setSubject("Order Cancelled - Order # "+order.getId());
        StringBuilder cancelledText = new StringBuilder();
        cancelledText.append("Order Cancelled \n");
        cancelledText.append("Your order "+order.getId()+" has been cancelled\n");
        cancelledText.append("Order ID: "+order.getId()+"\n");
        cancelledText.append("Order Status: "+order.getStatus()+"\n");
        cancelledText.append("If you didn't cancel your oder, please contact ECommerce Teams");
        cancelledText.append("--------------------------------\n");
        cancelledText.append("Thank You !\n\n");
        cancelledText.append("Best Regards\n");
        cancelledText.append("Ecommerce Team");
        cancelledMessage.setText(cancelledText.toString());
        mailSender.send(cancelledMessage);
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
