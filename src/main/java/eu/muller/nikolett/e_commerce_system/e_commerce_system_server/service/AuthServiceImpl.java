package eu.muller.nikolett.e_commerce_system.e_commerce_system_server.service;

import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.dto.RegisterRequest;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.dto.RegisterResponse;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    @Override
    public RegisterResponse register(RegisterRequest register) {
        return null;
    }
}
