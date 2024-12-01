package springboot.starter.main.infrastructure.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springboot.starter.main.usecase.auth.email_confirmation.ConfirmEmailInputBoundary;

@RestController
@RequestMapping("/api/v1/auth")
public class EmailConfirmationController
{
    private final ConfirmEmailInputBoundary confirmEmailInteractor;

    @Autowired
    public EmailConfirmationController(ConfirmEmailInputBoundary confirmEmailInteractor)
    {
        this.confirmEmailInteractor = confirmEmailInteractor;
    }

    @GetMapping("/confirm")
    public ResponseEntity<?> confirmEmail(@RequestParam("token") String token)
    {
        confirmEmailInteractor.confirmEmail(token);
        return ResponseEntity.ok("Email successfully confirmed!");
    }
}
