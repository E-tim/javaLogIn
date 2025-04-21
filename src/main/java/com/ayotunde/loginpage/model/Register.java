package com.ayotunde.loginpage.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Document(collection = "myUsers")
public class Register {
    @Id
    private String id;
    private String username;
    private String email;
    private String password;

}