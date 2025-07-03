package com.sullivan.disc.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Lombok annotation to avoid boiler plate
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DiscCreateDTO {

    // Attributes with validation annotation
    @NotBlank(message = "Manufacturer is required")
    @NotNull(message = "Manufacturer is required")
    @Max(value = 20, message = "Manufacturer can not be longer than 20 characters")
    private String manufacturer;

    @NotBlank(message = "Mold is required")
    @NotNull(message = "Mold is required")
    @Max(value = 20, message = "Mold can not be longer than 20 characters")
    private String mold;

    @NotBlank(message = "Plastic is required")
    @NotNull(message = "Plastic is required")
    @Max(value = 20, message = "Plastic can not be longer than 20 characters")
    private String plastic;

    @NotBlank(message = "Color is required")
    @NotNull(message = "Color is required")
    @Max(value = 20, message = "Color can not be longer than 20 characters")
    private String color;

    @Min(value = 1, message = "Condition must be between 1 and 10")
    @Max(value = 10, message = "Condition must be between 1 and 10")
    private int condition;

    @NotNull(message = "Description is required. Enter \"N/A\" if not needed")
    @NotEmpty(message = "Description is required. Enter \"N/A\" if not needed")
    @Max(value = 50, message = "Description has a max length of 50 characters")
    private String description;

    @NotNull(message = "Contact first name is required. Enter \"N/A\" if unknown.")
    @NotEmpty(message = "Contact first name is required. Enter \"N/A\" if unknown.")
    @Max(value = 50, message = "Contact first name cannot be longer than 50 characters")
    private String contactFirstName;

    @NotNull(message = "Contact last name is required. Enter \"N/A\" if unknown.")
    @NotEmpty(message = "Contact last name is required. Enter \"N/A\" if unknown.")
    @Max(value = 50, message = "Contact last name cannot be longer than 50 characters")
    private String contactLastName;

    @Pattern(regexp = "\\d{10}", message = "Phone number must be 10 digits")
    private String contactPhone;

    @NotNull(message = "Found location is required. Enter \"N/A\" if unknown.")
    @NotEmpty(message = "Found location is required. Enter \"N/A\" if unknown.")
    @Max(value = 50, message = "Found location cannot be longer than 50 characters")
    private String foundAt; // Course where disc was found

    @Positive(message = "MSRP must be positive")
    private double MSRP;

}
