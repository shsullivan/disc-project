package com.sullivan.disc.dto;

import com.sullivan.disc.model.Manufacturer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MoldDTO {

    // Attributes
    private Integer moldId;
    private Integer manufacturer;
    private String mold;
}
