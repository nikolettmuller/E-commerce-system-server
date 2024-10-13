package eu.muller.nikolett.e_commerce_system.e_commerce_system_server.controller;


import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.dto.LoginRequest;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.dto.LoginResponse;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.dto.RegisterRequest;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.dto.RegisterResponse;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Authentication related APIs")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "Register user", description = "Register new user with role")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful registration"),
                            @ApiResponse(responseCode = "400", description = "bad request")})
    @PostMapping(path = "/users" , produces = "application/json")
    public ResponseEntity<RegisterResponse> register(@Validated @RequestBody RegisterRequest register) {
        log.info("Registering user: {}, {}", register.getEmail(), register.getName());
        return ResponseEntity.ok(authService.register(register));
    }

    @Operation(summary = "Login user", description = "Login with registered user")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful login"),
                            @ApiResponse(responseCode = "400", description = "bad credentials")})
    @PostMapping(path = "/login", produces = "application/json")
    public ResponseEntity<LoginResponse> login(@Validated @RequestBody LoginRequest loginRequest) {
        log.info("User login attempt for: {}", loginRequest.getEmail());
        return ResponseEntity.ok(authService.login(loginRequest));
    }
}
