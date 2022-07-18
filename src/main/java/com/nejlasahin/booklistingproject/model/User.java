package com.nejlasahin.booklistingproject.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class User {

    @Id
    private String id;

    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    @Transient
    private String fullName;

    @DBRef
    private Set<Role> roles = new HashSet<>();

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
