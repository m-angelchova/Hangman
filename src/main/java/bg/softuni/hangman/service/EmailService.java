package bg.softuni.hangman.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Locale;
import java.util.UUID;

@Service
public class EmailService {
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;


    public EmailService(JavaMailSender javaMailSender,
                        TemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }

    public void sendRegistrationEmail(String userEmail, String fullName) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

        try {
            mimeMessageHelper.setFrom("hangman@example.com");
            mimeMessageHelper.setTo(userEmail);
            mimeMessageHelper.setSubject("Welcome to Hangman!");
            mimeMessageHelper.setText(generateEmailText(fullName), true);

            javaMailSender.send(mimeMessageHelper.getMimeMessage());

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }

    private String generateEmailText(String fullName) {
        Context ctx = new Context();
        ctx.setLocale(Locale.getDefault());
        ctx.setVariable("fullName", fullName);

//        String userCode = generateActivationCode();
//        String activationLink = "http://localhost:8080/activate?code=" + userCode;
//
//        ctx.setVariable("activationLink", activationLink);

        return templateEngine.process("email/registration", ctx);
    }

    private String generateActivationCode() {
        return UUID.randomUUID().toString();
    }



//    private void connectUserToCode(){
//
//    }

}
