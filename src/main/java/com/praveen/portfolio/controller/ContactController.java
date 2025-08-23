package com.praveen.portfolio.controller;

import com.praveen.portfolio.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ContactController {

    private final JavaMailSender mailSender;

    @Autowired
    private ContactService contactService;

    public ContactController(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @PostMapping("/contact")
    public String submitContact(@RequestParam String name,
                                @RequestParam String email,
                                @RequestParam String phone,
                                @RequestParam String message,
                                RedirectAttributes redirectAttributes) {
        try {
            SimpleMailMessage sendMessage = new SimpleMailMessage();
            sendMessage.setTo(email);
            sendMessage.setSubject("New Contact Request from " + name);
            sendMessage.setText("Email: " + email + "\nPhone: " + phone + "\n\nMessage:\n" + message);
            mailSender.send(sendMessage);

            contactService.saveSenderMailMessage(name,email,phone,message);

        } catch (Exception e) {
            e.printStackTrace();
        }
        redirectAttributes.addFlashAttribute("successMessage", "Message sent successfully!");
        return "redirect:/contact"; // reload contact.html with success/error msg
    }
}

