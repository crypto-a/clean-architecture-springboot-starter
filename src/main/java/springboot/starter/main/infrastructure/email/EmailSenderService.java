package springboot.starter.main.infrastructure.email;

import java.util.Map;

public interface EmailSenderService
{
    /**
     *
     * @param to to the email receiver
     * @param subject to the user subject
     * @param templateName name of the template
     * @param placeholders a list of all the placeholders for email.
     */
    void sendEmail(
            String to,
            String subject,
            String templateName,
            Map<String, String> placeholders
    );
}
