package com.praveen.portfolio.controller;

import com.praveen.portfolio.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ContactController {

    @Value("${spring.mail.username}")
    private String supportEmail;

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
            sendMessage.setTo(supportEmail);
            sendMessage.setSubject("New Contact Request from " + name);
            sendMessage.setText(
                    "Name: " + name + "\n" +
                            "Email: " + email + "\n" +
                            "Phone: " + phone + "\n\n" +
                            "Message:\n" + message
            );
            mailSender.send(sendMessage);

            // ✅ Message to visitor
            SimpleMailMessage replyMessage = new SimpleMailMessage();
            replyMessage.setTo(email);  // visitor’s email
            replyMessage.setSubject("Thanks for contacting me, " + name + "!");
            replyMessage.setText(
                    "Hi " + name + ",\n\n" +
                            "Thank you for contacting me via my portfolio site. \n" +
                            "I’ve received your message and will get back to you soon." +
                            "Best regards,\n" +
                            "Praveen"
            );

            mailSender.send(replyMessage);


            contactService.saveSenderMailMessage(name,email,phone,message);

        } catch (Exception e) {
            e.printStackTrace();
        }
        redirectAttributes.addFlashAttribute("successMessage", "Message sent successfully!");
        return "redirect:/contact"; // reload contact.html with success/error msg
    }
}

