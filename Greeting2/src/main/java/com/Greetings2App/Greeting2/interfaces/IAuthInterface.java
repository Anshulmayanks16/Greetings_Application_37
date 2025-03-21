package com.Greetings2App.Greeting2.interfaces;



import com.Greetings2App.Greeting2.UserDTO.AuthUserDTO;
import com.Greetings2App.Greeting2.UserDTO.LoginDTO;
import com.Greetings2App.Greeting2.UserDTO.PassDTO;
import org.springframework.stereotype.Service;

@Service
public interface IAuthInterface {

    public String register(AuthUserDTO user);


    public String login(LoginDTO user);

    public AuthUserDTO forgotPassword(PassDTO pass, String email);

    public String resetPassword(String email, String currentPass, String newPass);
}
