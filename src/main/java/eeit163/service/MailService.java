package eeit163.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
public class MailService {
 
    private JavaMailSenderImpl mailSender;
 
    @Autowired
    public MailService(JavaMailSenderImpl mailSender) {
        this.mailSender = mailSender;
    }
 
    public void prepareAndSend(String recipient, String message) {
       MimeMessagePreparator messagePreparator = mimeMessage -> {
             MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
             messageHelper.setFrom("eeit163sender@outlook.com");
             messageHelper.setTo(recipient);
             messageHelper.setSubject(message);
             messageHelper.setText(message);
         };
         try {
             mailSender.send(messagePreparator);
             System.out.println("已寄出");
         } catch (MailException e) {
        	 System.out.println("寄件失敗");
         }
    }
 
}