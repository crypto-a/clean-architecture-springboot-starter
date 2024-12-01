package springboot.starter.main.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import springboot.starter.main.infrastructure.email.DefaultEmailSenderService;
import springboot.starter.main.infrastructure.email.EmailSenderService;
import springboot.starter.main.infrastructure.email.EmailTemplateService;

import java.util.Properties;

@Configuration
public class EmailConfig
{
    @Value("${spring.mail.username}")
    private String emailServiceUsername;

    @Value("${spring.mail.password}")
    private String emailServicePassword;

    @Bean
    public JavaMailSender javaMailSender()
    {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com"); // Replace with your SMTP server
        mailSender.setPort(587); // Typically 587 for TLS, 465 for SSL

        mailSender.setUsername(emailServiceUsername); // Replace with your email
        mailSender.setPassword(emailServicePassword); // Replace with your app password

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "false"); // Set to true for debug logs

        return mailSender;
    }

    @Bean
    public EmailTemplateService emailTemplateService()
    {
        return new EmailTemplateService();
    }

    @Bean
    public EmailSenderService emailSenderService(JavaMailSender mailSender, EmailTemplateService emailTemplateService)
    {
        return new DefaultEmailSenderService(mailSender, emailTemplateService);
    }
}
