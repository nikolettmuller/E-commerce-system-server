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

    @Column
    private String name;

    @Column
    private String email;

    @Column
    private String password;

    @Column
    private Timestamp createdAt;

    @Column
    @Enumerated(EnumType.STRING)
    private UserRole role;
}
