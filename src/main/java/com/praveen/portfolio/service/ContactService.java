package com.praveen.portfolio.service;

import com.praveen.portfolio.model.ContactForm;
import com.praveen.portfolio.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    public void saveSenderMailMessage(String name, String email, String phone, String message) {
        ContactForm contactDetails = new ContactForm();
        contactDetails.setFullName(name);
        contactDetails.setEmail(email);
        contactDetails.setPhone(phone);
        // Replace CRLF with \n consistently
        message = message.replace("\r\n", "\n");
        contactDetails.setMessage(message);
        contactRepository.save(contactDetails);
    }
}
