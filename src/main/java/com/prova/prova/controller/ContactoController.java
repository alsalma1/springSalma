package com.prova.prova.controller;

import com.prova.prova.model.Contacto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Service
@CrossOrigin(origins = "http://localhost:3000")
public class ContactoController {

    @Autowired
    private JavaMailSender mailSender;

    @PostMapping("/contacto")
    public String sendContactEmail(@RequestBody Contacto contacto) {
        SimpleMailMessage messageToUser = new SimpleMailMessage();
        messageToUser.setFrom(contacto.getEmail());
        messageToUser.setTo("itsmeme330@gmail.com"); // Cambia la dirección de correo del administrador aquí
        messageToUser.setSubject("Nuevo mensaje de contacto");
        messageToUser.setText(contacto.getMessage() + ",\n\n"+contacto.getName()+".");

        mailSender.send(messageToUser);

        SimpleMailMessage messageToAdmin = new SimpleMailMessage();
        messageToAdmin.setFrom("itsmeme330@gmail.com");
        messageToAdmin.setTo(contacto.getEmail());
        messageToAdmin.setSubject("Gracias por tu mensaje");
        messageToAdmin.setText("Hola " + contacto.getName() + ",\n\nGracias por tu mensaje. Nos pondremos en contacto contigo lo antes posible.\n\nSaludos,\nSabor Badalona");

        mailSender.send(messageToAdmin);

        return "Se ha enviado el mensaje correctamenete al administardor";
    }

}
