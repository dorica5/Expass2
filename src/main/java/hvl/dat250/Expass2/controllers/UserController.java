package hvl.dat250.Expass2.controllers;

import hvl.dat250.Expass2.DomainManager;
import hvl.dat250.Expass2.ResourceNotFoundException;
import hvl.dat250.Expass2.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserController {
    private final DomainManager manager;

    public UserController(@Autowired DomainManager manager) {
        this.manager = manager;
    }

    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = manager.getUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable String id) {
        try {
            User user = manager.getUser(id);
            return ResponseEntity.ok(user);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestParam String username, @RequestParam String email) {
        User user = manager.createUser(username, email);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable String id, @RequestParam String username, @RequestParam String email) {
        try {
            User updatedUser = manager.updateUser(id, username, email);
            return ResponseEntity.ok(updatedUser);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        try {
            manager.deleteUser(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    //for expass 3
    @PostMapping("/login")
    public ResponseEntity<User> loginUser(@RequestParam String username, @RequestParam String email) {
        try {
            User user = manager.verifyUser(username, email);
            return ResponseEntity.ok(user);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }



}