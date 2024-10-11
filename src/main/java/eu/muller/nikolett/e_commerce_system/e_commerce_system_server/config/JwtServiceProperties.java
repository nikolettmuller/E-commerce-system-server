package eu.muller.nikolett.e_commerce_system.e_commerce_system_server.config;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Validated
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties("security.jwt")
public class JwtServiceProperties {

    @NotBlank
    private String secretKey;

    @NotNull
    private Long expirationTime;

}
