package com.sullivan.disc.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactDTO {

    // Attributes

    private Integer contactId;

    @NotBlank(message = "Contact first name is required. Enter \"N/A\" if unknown.")
    @Size(max = 50, message = "Contact first name cannot be longer than 50 characters")
    private String firstName;

    @NotBlank(message = "Contact last name is required. Enter \"N/A\" if unknown.")
    @Size(max = 50, message = "Contact last name cannot be longer than 50 characters")
    private String lastName;

    @Pattern(regexp = "\\d{10}", message = "Phone number must be 10 digits")
    private String phone;

}
