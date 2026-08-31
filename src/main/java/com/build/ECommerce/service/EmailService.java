package com.build.ECommerce.service;

import com.build.ECommerce.entity.Order;
import com.build.ECommerce.entity.OrderItem;
import com.build.ECommerce.entity.User;
import com.build.ECommerce.exception.ResourceNotFoundException;
import com.build.ECommerce.repository.OrderRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PatchMapping;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final OrderRepository orderRepository;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public void sendOrderConfirmationEmail(Order order) {
        User user = order.getUser();
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(user.getEmail());
        message.setSubject("Order Confirmation - Order # "+order.getId());
        StringBuilder messageText = new StringBuilder();
        messageText.append("Order Confirmed \n " + "Hello\n\n " + "Your Order has been placed successfully\n\n" + "Order ID: ")
                .append(order.getId()).append("\n")
                .append("Order Status: ")
                .append(order.getStatus())
                .append("\n")
                .append("Order Date: ")
                .append(order.getCreatedAt())
                .append("\n\n")
                .append("Order Details:\n")
                .append("--------------------------------\n");
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
        User user = order.getUser();
        SimpleMailMessage deliveringMessage = new SimpleMailMessage();
        deliveringMessage.setFrom(fromEmail);
        deliveringMessage.setTo(user.getEmail());
        deliveringMessage.setSubject("Order Delivery - Order # "+order.getId());
        String deliveringText = "Order Delivery \n" + "Your order "
                + order.getId() + " is on delivery\n"
                + "Order ID: " + order.getId() + "\n"
                + "Order Status: " + order.getStatus() + "\n"
                + "Order Address: " + order.getAddress() + "\n"
                + "Your Order will be delivered on the given address\n"
                + "---------------------------------\n"
                + "Thank You For Shopping with Us!\n\n"
                + "Best Regards\n"
                + "Ecommerce Team";
        deliveringMessage.setText(deliveringText);
        mailSender.send(deliveringMessage);
    }

    public void sendDeliveredConfirmationEmail(Order order) throws JRException, MessagingException {
        User user = order.getUser();
        byte[] bill = generateBill(order.getId());
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(fromEmail);
        helper.setTo(user.getEmail());
        helper.setSubject("Order Delivered - Order # "+order.getId());
        String deliveredText = "Order Delivered \n" +
                "Your order " + order.getId() + " has been delivered to your given address\n" +
                "Order ID: " + order.getId() + "\n" +
                "Order Status: " + order.getStatus() + "\n" +
                "Order Address: " + order.getAddress() + "\n" +
                "We hope you are satisfied with your success" +
                "---------------------------------\n" +
                "Thank You For Shopping with Us!\n\n" +
                "Best Regards\n" +
                "Ecommerce Team";
        helper.setText(deliveredText);
        helper.addAttachment("Order-Bill-"+order.getId()+".pdf",new ByteArrayResource(bill));
        mailSender.send(message);
    }

    public void sendCancelledConfirmationEmail(Order order) {
        User user = order.getUser();
        SimpleMailMessage cancelledMessage = new SimpleMailMessage();
        cancelledMessage.setFrom(fromEmail);
        cancelledMessage.setTo(user.getEmail());
        cancelledMessage.setSubject("Order Cancelled - Order # "+order.getId());
        String cancelledText = "Order Cancelled \n" +
                "Your order " + order.getId() + " has been cancelled\n" +
                "Order ID: " + order.getId() + "\n" +
                "Order Status: " + order.getStatus() + "\n" +
                "If you didn't cancel your oder, please contact ECommerce Teams" +
                "--------------------------------\n" +
                "Thank You !\n\n" +
                "Best Regards\n" +
                "Ecommerce Team";
        cancelledMessage.setText(cancelledText);
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

    public byte[] generateBill(Long id) throws JRException {
        String resourceDir = System.getProperty("user.dir")+"\\src\\main\\resources\\report\\";
        Path path = Paths.get(resourceDir,"Bill.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(path.toString());
        Order order = orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        JRBeanCollectionDataSource orderDataSource = new JRBeanCollectionDataSource(List.of(order));
        Map<String,Object> data = new HashMap<>();
        for(Field field : order.getClass().getDeclaredFields()){
            field.setAccessible(true);
            try {
                data.put(field.getName(),field.get(order));
            }catch(IllegalAccessException e){
                throw new JRRuntimeException(e);
            }
        }
        List<Map<String,Object>> orderItems = order
                .getOrderItems()
                .stream()
                .map(item->{
                    Map<String,Object> itemData = new HashMap<>();
                    itemData.put("product",item.getProduct().getName());
                    itemData.put("quantity",item.getQuantity());
                    itemData.put("price",item.getPrice());
                    return itemData;
                })
                .toList();
        data.put("orderItems",orderItems);
        Map<String,Object> parameter = new HashMap<>();
        parameter.put("data",data);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,parameter,orderDataSource);
        return JasperExportManager.exportReportToPdf(jasperPrint);
    }
}
