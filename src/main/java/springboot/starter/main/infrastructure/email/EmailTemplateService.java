package springboot.starter.main.infrastructure.email;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EmailTemplateService
{

    public String loadTemplate(String templateName) throws IOException
    {
        String resourcePath = "templates/" + templateName + ".txt";
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(resourcePath);

        if (inputStream == null)
        {
            throw new IOException("Template file not found: " + resourcePath);
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8)))
        {
            return reader.lines().collect(Collectors.joining("\n"));
        }
    }

    public String processTemplate(String template, Map<String, String> placeholders)
    {
        String processedTemplate = template;
        for (Map.Entry<String, String> entry : placeholders.entrySet())
        {
            String placeholder = "{{ " + entry.getKey() + " }}";
            processedTemplate = processedTemplate.replace(placeholder, entry.getValue());
        }
        return processedTemplate;
    }
}
