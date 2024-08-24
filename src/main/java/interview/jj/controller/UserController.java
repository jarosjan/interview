package interview.jj.controller;

import interview.jj.annotation.AuthenticatedEmail;
import interview.jj.model.UserEditRequest;
import interview.jj.model.UserResponse;
import interview.jj.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Delete user")
    @DeleteMapping
    public void deleteUser(@AuthenticatedEmail String email) {
        log.info("Deleting user with email: {}", email);
        userService.deleteUser(email);
    }

    @Operation(summary = "Get user")
    @GetMapping
    public UserResponse getUser(@AuthenticatedEmail String email) {
        log.info("Getting user with email: {}", email);
        return userService.getUser(email);
    }

    @Operation(summary = "Update user")
    @PutMapping
    public UserResponse updateUser(@AuthenticatedEmail String email, @RequestBody UserEditRequest userRequest) {
        log.info("Updating user with email: {}", email);
        return userService.updateUser(email, userRequest);
    }

}
