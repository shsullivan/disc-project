package com.sullivan.disc.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Lombok annotations to avoid boiler plate code
@Getter
@Setter
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class DiscDTO {

    // Attributes
    private int discID;
    private String manufacturer;
    private String mold;
    private String plastic;
    private String color;
    private int condition;
    private String description;
    private String contactFirstName;
    private String contactLastName;
    private String contactPhone;
    private String foundAt; // Course where disc was found
    private boolean returned;
    private boolean sold;
    private double MSRP;
    private double resaleValue;
}
