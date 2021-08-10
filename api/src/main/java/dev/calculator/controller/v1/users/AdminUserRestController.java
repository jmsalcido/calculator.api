package dev.calculator.controller.v1.users;

import dev.calculator.model.v1.network.ApiError;
import dev.calculator.model.v1.network.PageResponse;
import dev.calculator.model.v1.network.UserModifyRequest;
import dev.calculator.model.v1.network.UserResponse;
import dev.calculator.service.v1.UserService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/users/")
@Secured({"ROLE_ADMIN"})
public class AdminUserRestController {

    private final UserService userService;

    public AdminUserRestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping ("/create")
    public ResponseEntity<Object> createUser(@RequestBody UserModifyRequest userRequest) {
        if (userRequest.getId() > 0) {
            userRequest.setId(0);
        }

        if (ObjectUtils.isEmpty(userRequest.getPassword())) {
            ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST,
                    "Password can't be empty",
                    "Password is empty");
            return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
        }

        UserResponse userResponse = userService.modifyUser(userRequest);
        return ResponseEntity.ok(userResponse);
    }

    @PutMapping("/update")
    public ResponseEntity<UserResponse> updateUser(@RequestBody UserModifyRequest userModifyRequest) {
        UserResponse userResponse = userService.modifyUser(userModifyRequest);
        return ResponseEntity.ok(userResponse);
    }

    @DeleteMapping ("/delete/{userId}")
    public ResponseEntity<Long> deleteUser(@PathVariable long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok(userId);
    }

    @GetMapping("/")
    public ResponseEntity<PageResponse> getUsers(@RequestParam(required = false, defaultValue = "") String username,
                                                               @RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "10") int size
    ) {
        PageRequest pageRequest = PageRequest.of(page, size);
        PageResponse result = userService.getUsersByUsername(username, pageRequest);

        return ResponseEntity.ok(result);
    }
}
