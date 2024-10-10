package eu.muller.nikolett.e_commerce_system.e_commerce_system_server.controller;


import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.dto.RegisterRequest;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.dto.RegisterResponse;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/users")
    public RegisterResponse register(@RequestBody RegisterRequest register) {
        return authService.register(register);
    }
}
