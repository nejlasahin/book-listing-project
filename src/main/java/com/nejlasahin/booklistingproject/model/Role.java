package com.nejlasahin.booklistingproject.model;

import com.nejlasahin.booklistingproject.model.enumeration.RoleNameEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "roles")
public class Role {

    @Id
    @Field("id")
    private String id;

    @Indexed
    @Field("name")
    private RoleNameEnum name;
}
