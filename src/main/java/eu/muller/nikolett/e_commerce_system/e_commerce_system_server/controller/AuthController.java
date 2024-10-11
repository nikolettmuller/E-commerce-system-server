package eu.muller.nikolett.e_commerce_system.e_commerce_system_server.controller;


import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.dto.LoginRequest;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.dto.LoginResponse;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.dto.RegisterRequest;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.dto.RegisterResponse;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
    public ResponseEntity<RegisterResponse> register(@Validated @RequestBody RegisterRequest register) {
        return ResponseEntity.ok(authService.register(register));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Validated @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }
}
