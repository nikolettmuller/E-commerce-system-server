package eu.muller.nikolett.e_commerce_system.e_commerce_system_server.service;

import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.dto.LoginRequest;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.dto.LoginResponse;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.dto.RegisterRequest;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.dto.RegisterResponse;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.entity.User;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.exception.DuplicatedEmailException;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.exception.UserNotFoundException;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.mapper.RegisterMapper;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    private final RegisterMapper registerMapper;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authManager;

    private final JwtService jwtService;

    private static final String USER_NOT_FOUND = "User with %s email not found.";

    @Override
    public RegisterResponse register(RegisterRequest register) {
        checkDuplicatedEmail(register);
        hashPassword(register);
        User mappedUser = registerMapper.map(register);
        User newUser = userRepository.save(mappedUser);
        return new RegisterResponse(newUser.getName());
    }

    @Override
    public LoginResponse login(LoginRequest login) {
        authManager.authenticate(new UsernamePasswordAuthenticationToken(
                login.getEmail(),
                login.getPassword()
        ));

        User user = userRepository.findByEmail(login.getEmail())
                .orElseThrow(() -> new UserNotFoundException(String.format(USER_NOT_FOUND, login.getEmail())));

        String jwt = jwtService.generateToken(user);

        return new LoginResponse(jwt);
    }

    private void hashPassword(RegisterRequest register) {
        String encodedPassword = passwordEncoder.encode(register.getPassword());
        register.setPassword(encodedPassword);
    }

    private void checkDuplicatedEmail(RegisterRequest register) {
        if (userRepository.countByEmail(register.getEmail()) > 0) {
            throw new DuplicatedEmailException();
        }
    }
}
