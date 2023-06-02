package eeit163.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import eeit163.service.MailService;

@Controller
public class MailController {
  @Autowired
 private MailService mailService;
 
 @GetMapping("/mail/valid")
 @ResponseBody
 public String valid(){
  mailService.prepareAndSend("123@abc.com","123456");
  return "Mail sent";
 }
 

}