package eu.muller.nikolett.e_commerce_system.e_commerce_system_server.controller;

import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.dto.UserResponse;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "User info", description = "User information related API")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Get user info", description = "User information provider")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful user found"),
                            @ApiResponse(responseCode = "400", description = "bad request"),
                            @ApiResponse(responseCode = "403", description = "forbidden request")})
    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponse> findUser(@Parameter(description = "User id") @PathVariable Integer id) {
        return ResponseEntity.ok(userService.findUserById(id));
    }
}
