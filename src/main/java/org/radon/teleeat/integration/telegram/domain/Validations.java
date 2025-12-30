package org.radon.teleeat.integration.telegram.domain;

public class Validations {

    public static boolean isValidPhoneNumber(String value) {
        if (value == null) {
            return false;
        }

        for (char c : value.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }

        return value.length() == 11;
    }
}
