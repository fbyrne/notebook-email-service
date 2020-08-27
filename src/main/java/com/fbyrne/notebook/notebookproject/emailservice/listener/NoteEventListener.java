package com.fbyrne.notebook.notebookproject.emailservice.listener;

import lombok.extern.apachecommons.CommonsLog;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import static com.fbyrne.notebook.notebookproject.emailservice.listener.NoteEventListenerConfiguration.*;

@Service
@CommonsLog
public class NoteEventListener {

    @Autowired
    private JavaMailSender emailSender;

    @RabbitListener(queues = NOTES_CREATED_QUEUE)
    public void sendNoteCreatedEmail(@Header(name = "username") String username, @Header(name = "email") String email, @Payload String content){
        sendEventMail(email, content, "Note Created");
    }

    private void sendEventMail(String email, String content, String subject) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@notebook.com");
        message.setTo(email);
        message.setSubject(subject);
        message.setText(content);
        emailSender.send(message);
    }

    @RabbitListener(queues = NOTES_UPDATED_QUEUE)
    public void sendNoteUpdatedEmail(@Header(name = "username") String username, @Header(name = "email") String email, @Payload String content){
        sendEventMail(email, content, "Note Updated");
    }

    @RabbitListener(queues = NOTES_DELETED_QUEUE)
    public void sendNoteDeletedEmail(@Header(name = "username") String username, @Header(name = "email") String email, @Payload String content){
        sendEventMail(email, content, "Note Deleted");
    }
}
