package com.witchend.domain.enums;

public enum CharacterClass {
    DIAMOND("다이아몬드"),
    JADE("제이드");

    private final String description;

    CharacterClass(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
