package springboot.starter.main.infrastructure.email;

import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class DefaultEmailSenderService implements EmailSenderService
{
    private final JavaMailSender mailSender;
    private final EmailTemplateService emailTemplateService;

    public DefaultEmailSenderService(JavaMailSender mailSender, EmailTemplateService emailTemplateService)
    {
        this.mailSender = mailSender;
        this.emailTemplateService = emailTemplateService;
    }

    @Override
    public void sendEmail(String to, String subject, String templateName, Map<String, String> placeholders)
    {
        try
        {
            // Load and process the template
            String template = emailTemplateService.loadTemplate(templateName);
            String emailContent = emailTemplateService.processTemplate(template, placeholders);

            // Create and send the email
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(emailContent, false); // Enable HTML content if needed

            mailSender.send(message);
        } catch (Exception e)
        {
            throw new RuntimeException("Failed to send email: " + e.getMessage(), e);
        }
    }
}
