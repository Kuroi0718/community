package eeit163.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import eeit163.model.Mail;
import eeit163.model.MailRepository;

@Service
public class MailService {
 
    private JavaMailSenderImpl mailSender;
    
    @Autowired
    MailRepository mailDao;
 
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
    
	public void insertMail(Mail mail) {
		mailDao.save(mail);
	}
	
	@Transactional
	public void deleteById(Integer id) {
		mailDao.deleteById(id);
	}
	
	public Mail findById(Integer id) {
		return mailDao.findById(id).get();
	}
	
	@Transactional
	public Integer checkEmailByNumbers(Integer a,Integer b,Integer c,Integer d,Integer e,Integer f,String email) {
		List<Mail> list = mailDao.findByEmail(email);
		Integer result=0;
		for(int i=0;i<list.size();i++) {
			if(list.get(i).getFirst().equals(a)&&list.get(i).getSecond().equals(b)&&list.get(i).getThird().equals(c)&&list.get(i).getForth().equals(d)&&list.get(i).getFifth().equals(e)&&list.get(i).getSixth().equals(f)) {
				result=list.get(i).getId();
			}
			mailDao.deleteById(list.get(i).getId());
		}
		return result;
	}
 
}