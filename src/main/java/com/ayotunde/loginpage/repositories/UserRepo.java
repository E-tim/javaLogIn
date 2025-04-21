package com.ayotunde.loginpage.repositories;

import com.ayotunde.loginpage.model.Register;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends MongoRepository<Register, String> {}