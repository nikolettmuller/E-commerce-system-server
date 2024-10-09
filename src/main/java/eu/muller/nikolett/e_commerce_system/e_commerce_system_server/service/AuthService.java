package eu.muller.nikolett.e_commerce_system.e_commerce_system_server.service;

import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.dto.RegisterRequest;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.dto.RegisterResponse;

public interface AuthService {

    RegisterResponse register(RegisterRequest register);
}
