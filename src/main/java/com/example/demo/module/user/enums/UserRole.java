package com.example.demo.module.user.enums;

import lombok.Getter;

@Getter
public enum UserRole {
    COMMON("Common"),
    ADMIN("Admin"),
    VIP("VIP");

    private final String displayName;

    UserRole(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}

