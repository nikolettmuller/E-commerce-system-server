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
public class ProductOwnerOrAdminSecurity implements AuthorizationManager<RequestAuthorizationContext> {

    private final UserRepository userRepository;

    @Override
    public AuthorizationDecision check(Supplier authenticationSupplier, RequestAuthorizationContext ctx) {
        Long orderId = Long.parseLong(ctx.getVariables().get("id"));
        var authentication = (Authentication) authenticationSupplier.get();
        return new AuthorizationDecision(hasUserId(authentication, orderId));
    }
    public boolean hasUserId(Authentication authentication, Long orderId) {
        return isAdmin(authentication) || isOwnOrder(authentication, orderId);

    }

    private boolean isAdmin(Authentication authentication) {
        return authentication.getAuthorities().stream().anyMatch(role -> UserRole.ADMIN.toString().equals(role.getAuthority()));
    }

    private boolean isOwnOrder(Authentication authentication, Long orderId) {
        var userOptional = userRepository.findByEmail(authentication.getName());
        if (userOptional.isEmpty() || userOptional.get().getOrders() == null) {
            return false;
        } else {
            return userOptional.get().getOrders().stream().anyMatch(order -> Objects.equals(order.getId(), orderId));
        }
    }

}