package com.sullivan.disc.dto;

import com.sullivan.disc.model.Disc;
import lombok.*;

import java.math.BigDecimal;

// Lombok annotations to avoid boiler plate code
@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class DiscDTO {

    // Attributes
    private Integer discID;
    private ManufacturerDTO manufacturer;
    private MoldDTO mold;
    private String plastic;
    private String color;
    private Integer condition;
    private String description;
    private ContactDTO contact;
    private String foundAt; // Course where disc was found
    private boolean returned;
    private boolean sold;
    private BigDecimal MSRP;
    private BigDecimal resaleValue;
}
