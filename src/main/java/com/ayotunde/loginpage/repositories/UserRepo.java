package com.ayotunde.loginpage.repositories;

import com.ayotunde.loginpage.model.Register;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface UserRepo extends MongoRepository<Register, String> {
    Optional<Register> findByEmail(String email);  // 🔥 New method
}
