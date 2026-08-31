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
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo("vhadjz5965@minitts.net");
        message.setSubject("Order Confirmation - Order # "+order.getId());
        StringBuilder messageText = new StringBuilder();
        messageText.append("Order Confirmed \n " +
                "Hello\n\n "+
                "Your Order has been placed successfully\n\n"+
                "Order ID: "+order.getId()+"\n"+
                "Order Status: "+order.getStatus()+"\n"+
                "Order Date: "+order.getCreatedAt()+"\n\n"+
                "Order Details:\n"+
                "--------------------------------\n");
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

    public void sendDeliveredConfirmationEmail(Order order) throws JRException, MessagingException {
        byte[] bill = generateBill(order.getId());
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(fromEmail);
        helper.setTo("vhadjz5965@minitts.net");
        helper.setSubject("Order Delivered - Order # "+order.getId());
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
        helper.setText(deliveredText.toString());
        helper.addAttachment("Order-Bill-"+order.getId()+".pdf",new ByteArrayResource(bill));
        mailSender.send(message);
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

    public byte[] generateBill(Long id) throws JRException {
        String resourceDir = System.getProperty("user.dir")+"\\src\\main\\resources\\report\\";
        Path path = Paths.get(resourceDir,"Bill.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(path.toString());
        Order order = orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        JRBeanCollectionDataSource orderDataSource = new JRBeanCollectionDataSource(orderRepository.findAll());
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
