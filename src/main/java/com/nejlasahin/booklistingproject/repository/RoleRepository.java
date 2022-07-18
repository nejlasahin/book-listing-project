package com.nejlasahin.booklistingproject.repository;

import com.nejlasahin.booklistingproject.model.Role;
import com.nejlasahin.booklistingproject.model.enumeration.RoleNameEnum;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoleRepository extends MongoRepository<Role, String> {
    Optional<Role> findByName(RoleNameEnum name);
}
