package com.nejlasahin.booklistingproject.model.enumeration;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public enum RoleNameEnum {
    ROLE_READER,
    ROLE_AUTHOR;

    private static final Set<String> roleNameSet = Arrays.stream(RoleNameEnum.values()).map(RoleNameEnum::name).collect(Collectors.toSet());

    public static boolean isValid(String roleName) {
        return roleNameSet.contains(roleName);
    }
}
