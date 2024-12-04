package springboot.starter.main.infrastructure.helloWorld;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController
{
    @GetMapping("/api/hello")
    public String helloWorld()
    {
        return "Hello, World!";
    }

    @GetMapping("/api/hello-secured/")
    public String helloSecured()
    {
        return "Hello, Secured!";
    }
}
