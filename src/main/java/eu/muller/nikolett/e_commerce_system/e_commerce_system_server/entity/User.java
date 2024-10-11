package eu.muller.nikolett.e_commerce_system.e_commerce_system_server.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String email;

    private String password;

    private Timestamp createdAt;

    @Enumerated(EnumType.STRING)
    private UserRole role;
}
