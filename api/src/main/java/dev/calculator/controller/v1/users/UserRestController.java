package dev.calculator.controller.v1.users;

import dev.calculator.model.User;
import dev.calculator.model.v1.network.UserResponse;
import dev.calculator.service.v1.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/users/")
public class UserRestController {

    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> signUp(@RequestBody User user) {
        userService.signUp(user);

        return ResponseEntity.ok(user);
    }

    @GetMapping("/profile")
    public ResponseEntity<UserResponse> getUserProfile(Authentication authentication) {
        User user = userService.findUserByUsername(authentication.getName());
        UserResponse userResponse = userService.createUserResponse(user);

        return ResponseEntity.ok(userResponse);
    }
}
