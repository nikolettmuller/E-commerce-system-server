package eu.muller.nikolett.e_commerce_system.e_commerce_system_server.config;

import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.entity.UserRole;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.function.Supplier;

@Component
@RequiredArgsConstructor
public class UserOrAdminSecurity implements AuthorizationManager<RequestAuthorizationContext> {

    private final UserRepository userRepository;

    @Override
    public AuthorizationDecision check(Supplier authenticationSupplier, RequestAuthorizationContext ctx) {
        Long userId = Long.parseLong(ctx.getVariables().get("id"));
        var authentication = (Authentication) authenticationSupplier.get();
        return new AuthorizationDecision(hasUserId(authentication, userId));
    }
    public boolean hasUserId(Authentication authentication, Long userId) {
        return isAdmin(authentication) || isOwnResource(authentication, userId);

    }

    private boolean isAdmin(Authentication authentication) {
        return authentication.getAuthorities().stream().anyMatch(role -> UserRole.ADMIN.toString().equals(role.getAuthority()));
    }

    private boolean isOwnResource(Authentication authentication, Long userId) {
        var userOptional = userRepository.findByEmail(authentication.getName());
        return userOptional.filter(user -> Objects.equals(Long.valueOf(user.getId()), userId)).isPresent();
    }

}