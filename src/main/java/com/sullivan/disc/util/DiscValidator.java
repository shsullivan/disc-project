package com.sullivan.disc.util;

public final class DiscValidator {

    // This class is strictly for utility. To protect against instantiation, the constructor must be made private
    private DiscValidator() {

    }

    // Helper method to validate all text fields with a max length
    private static String validateTextField(String input, String fieldName, int maxLength) {
        if (input == null) {
            throw new IllegalArgumentException(fieldName + " cannot be null");
        } else if (input.isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be empty");
        } else if (input.length() > maxLength) {
            throw new IllegalArgumentException(fieldName + " cannot be longer than " + maxLength + " characters");
        }
        return input;
    }
    // Helper method to validate text fields that do not have a max length restriction
    // currently only used once, but created for scalability
    private static String validateNotEmpty(String input, String fieldName) {
        if (input == null || input.isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be empty");
        }
        return input;
    }

    public static int validatePositiveInt(String input, String fieldName) {
        try {
            int value = Integer.parseInt(input.trim());
            if (value <= 0) {
                throw new IllegalArgumentException(fieldName + " must be greater than 0");
            }
            return value;
        }
        catch (NumberFormatException e) {
            throw new IllegalArgumentException(fieldName + " must be a valid number");
        }
    }

    public static double validatePositiveDouble(String input, String fieldName) {
        try {
            double value = Double.parseDouble(input.trim());
            if (value < 0) {
                throw new IllegalArgumentException(fieldName + " must be greater than 0");
            }
            return value;
        }
        catch (NumberFormatException e) {
            throw new IllegalArgumentException(fieldName + " must be a valid value");
        }
    }

    public static boolean validateBooleanInput(String input, String fieldName) {
        input = input.trim().toLowerCase();
        if (input.equals("true") || input.equals("false")) {
            return Boolean.parseBoolean(input);
        } else {
            throw new IllegalArgumentException(fieldName + " must be true or false");
        }
    }

    public static String validateManufacturer(String input) {
        return validateTextField(input, "Manufacturer", 20);
    }

    public static String validateMold(String input) {
        return validateTextField(input, "Mold", 20);
    }

    public static String validatePlastic(String input) {
        return validateTextField(input, "Plastic", 20);
    }

    public static String validateColor(String input) {
        return validateTextField(input, "Color", 20);
    }

    public static int validateCondition(String input) {
        int condition  = validatePositiveInt(input, "Condition");

        if (condition < 1 || condition > 10) {
            throw new IllegalArgumentException("Condition must be between 1 and 10");
        }
        return condition;
    }

    public static String validateDescription(String input) {
        return validateNotEmpty(input, "Description");
    }

    public static String validateContactName(String input) {
        return validateTextField(input, "Contact Name", 40);
    }

    public static String validateContactPhone(String input) {
        if (input == null || input.isEmpty()) {
            throw new IllegalArgumentException("Contact Phone cannot be empty");
        } else if (input.length() != 10) {
            throw new IllegalArgumentException("Contact Phone must be 10 digits");
        } else if (!input.matches("\\d{10}")) {
            throw new IllegalArgumentException("Contact Phone must contain only digits");
        }
        return input;
    }

    public static String validateFoundAt(String input) {
        return validateTextField(input, "Found at location", 50);
    }
}

