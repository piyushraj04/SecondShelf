package com.secondshelf.enums;

public enum PaymentMethod {

    UPI("UPI"),
    CARD("Card"),
    NET_BANKING("Net Banking"),
    CASH_ON_DELIVERY("Cash on Delivery");

    private final String displayName;

    PaymentMethod(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}