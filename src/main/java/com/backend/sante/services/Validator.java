package com.backend.sante.services;

public interface Validator {
    boolean isValidEmail(String email);
    boolean isValidPhoneNumber(String phoneNumber);
    boolean isValidCIN(String cin);
}
