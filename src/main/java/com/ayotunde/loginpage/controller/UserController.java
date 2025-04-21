package com.ayotunde.loginpage.controller;

import com.ayotunde.loginpage.model.Register;
import com.ayotunde.loginpage.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserRepo userRepo;

    @PostMapping
    public Register addUser(@RequestBody Register register) {
        return userRepo.save(register);
    }

    @GetMapping("/dashboard")
    public List<Register> getUser() { return userRepo.findAll(); }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable String id) {
        if (!userRepo.existsById(id)) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
//            return ResponseEntity.notFound().build();
        }
        userRepo.deleteById(id);
        return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
//        return ResponseEntity.ok().build();
    }

    @PutMapping("/update/{id}")
    public Register updateUser(@PathVariable String id, @RequestBody Register register) {
        return userRepo.findById(id).map(user -> {
            user.setEmail(register.getEmail());
            user.setPassword(register.getPassword());
            user.setUsername(register.getUsername());
            return userRepo.save(user);
        }).orElseThrow(()-> new RuntimeException("User not found"));
    }




}
