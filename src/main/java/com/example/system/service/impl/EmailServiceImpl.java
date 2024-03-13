package com.example.system.service.impl;

import com.example.system.service.EmailService;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {
    @Value("${spring.mail.username}")
    private String fromMail;


    private final JavaMailSender javaMailSender;

    @Autowired
    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    @Async
    public void sendMailTo(String to, String body) {
        try{
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();

            MimeMessageHelper mimeMessageHelper= new MimeMessageHelper(mimeMessage,true);
            mimeMessageHelper.setFrom(fromMail);
            mimeMessageHelper.setTo(to);
//            mimeMessageHelper.setCc(""); if you need...
            mimeMessageHelper.setSubject("Your Confirmation code");
            mimeMessageHelper.setText(
                    "<h1>Підтвердіть свою пошту<h1>" +
                            "<a href=\"" + body + "\" " +
                            "style=\"display: inline-block; padding: 10px 20px; " +
                            "background-color: #007bff; color: #fff; " +
                            "text-decoration: none; border-radius: 5px;\">Підтвердити</a>"
            ,true);
//            for (int i = 0; i < file.length ; i++) {
//                mimeMessageHelper.addAttachment(
//                        file[i].getOriginalFilename(),
//                        new ByteArrayResource(file[i].getBytes())
//                );
//            }
            javaMailSender.send(mimeMessage);

        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }
}
