package eu.muller.nikolett.e_commerce_system.e_commerce_system_server.dto;

import lombok.Data;


@Data
public class RegisterRequest {

    private String name;

    private String email;

    private String password;

    private String role;
}
