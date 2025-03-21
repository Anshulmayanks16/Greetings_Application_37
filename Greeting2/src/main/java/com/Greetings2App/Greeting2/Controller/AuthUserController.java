package com.Greetings2App.Greeting2.Controller;

import com.Greetings2App.Greeting2.Models.AuthUser;
import com.Greetings2App.Greeting2.Service.AuthenticationService;
import com.Greetings2App.Greeting2.UserDTO.AuthUserDTO;
import com.Greetings2App.Greeting2.UserDTO.LoginDTO;
import com.Greetings2App.Greeting2.UserDTO.MailDTO;
import jakarta.validation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.Greetings2App.Greeting2.Service.EmailService;

@RestController
@RequestMapping("/auth")
public class AuthUserController {
    @Autowired
    private final AuthenticationService authenticationService;
     public AuthUserController(AuthenticationService authenticationService){
         this.authenticationService = authenticationService;
     }
     //UC9
     @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody AuthUserDTO authUserDTO){
         return ResponseEntity.ok(authenticationService.registerUser(authUserDTO));
     }
     @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginDTO loginDTO){
         return ResponseEntity.ok(authenticationService.loginUser(loginDTO));
     }
    @PostMapping(path="/sendMail")
    public String sendMail(@RequestBody MailDTO user){

        EmailService.sendEmail(user.getTo(), user.getSubject(), user.getBody());
        return "Mail Sent";
    }
}
