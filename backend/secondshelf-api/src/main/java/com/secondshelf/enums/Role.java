package com.secondshelf.enums;

public enum Role {
    ADMIN("Manages the platform, users, listings, orders, and system operations."),
    SELLER("Lists, manages, and sells books while handling inventory and orders."),
    BUYER("Browses, purchases, and manages books, orders, cart, and wishlist.");
    private final String description;

    Role(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
    
}
