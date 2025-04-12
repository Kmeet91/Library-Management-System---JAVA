package project.library.RESTController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.library.entities.User;
import project.library.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //manager and admin have permission
    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        logger.info("Fetching all users...");
        List<User> users = userService.getAllUsers();
        return users.isEmpty() ? ResponseEntity.status(HttpStatus.NO_CONTENT).body("No users found")
                               : ResponseEntity.ok(users);
    }

    //manager and admin have permission
    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUserById(@PathVariable int id) {
        logger.info("Fetching user with ID: {}", id);
        User user = userService.getUserById(id);
        return user != null ? ResponseEntity.ok(user)
                            : ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }

    //manager and admin have permission
    @PostMapping("/users")
    public ResponseEntity<?> addUsers(@RequestBody List<User> users) {
        logger.info("Adding a new user...");
        List<User> newUser = userService.addUsers(users);
        return newUser != null ? ResponseEntity.status(HttpStatus.CREATED).body(newUser)
                               : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to add user");
    }

    //manager and admin have permission
    @PutMapping("/user/{id}")
    public ResponseEntity<?> updateUser(@PathVariable int id, @RequestBody User updatedUser) {
        logger.info("Updating user with ID: {}", id);
        updatedUser.setId(id);
        User updated = userService.updateUser(updatedUser);
        return updated != null ? ResponseEntity.ok(updated)
                               : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to update user");
    }

    //only admin has permission
    @DeleteMapping("/user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable int id) {
        logger.info("Deleting user with ID: {}", id);
        return userService.deleteUser(id) ? ResponseEntity.ok("User successfully deleted")
                                          : ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }
}
