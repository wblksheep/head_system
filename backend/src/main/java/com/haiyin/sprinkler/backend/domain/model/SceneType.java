package com.haiyin.sprinkler.backend.domain.model;

public enum SceneType {
    ALLOCATE, MAINTAIN, INSPECTION;

    public static boolean isValid(String type) {
        try {
            valueOf(type.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
