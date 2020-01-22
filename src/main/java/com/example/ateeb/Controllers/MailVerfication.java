package com.example.ateeb.Controllers;

import com.sun.mail.smtp.SMTPTransport;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.ws.rs.Path;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

@RestController
public class MailVerfication
{

    @GetMapping("/sendmail/{id}")
    public String getAllBooks(@PathVariable("id") String id) throws IOException, MessagingException {

        Random rand = new Random();

        // Generate random integers in range 0 to 999
        int rand_int1 = rand.nextInt(100000);
        String code = ""+rand_int1;

        Properties props = System.getProperties();
        props.put("mail.smtps.host","smtp.gmail.com");
        props.put("mail.smtps.auth","true");
        Session session = Session.getInstance(props, null);
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress("mail@tovare.com"));;
        msg.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(id+"@nu.edu.pk", false));
        msg.setSubject("Email Verification ");
        msg.setText("Please enter the verification code: "+code);
        msg.setHeader("Email Verfication", "Tov Are's program");
        msg.setSentDate(new Date());
        SMTPTransport t =
                (SMTPTransport)session.getTransport("smtps");
        t.connect("smtp.gmail.com", "personalorganiserproject@gmail.com", "lotus@123");
        t.sendMessage(msg, msg.getAllRecipients());
        System.out.println("Response: " + t.getLastServerResponse());
        t.close();
        return code;

    }
}
