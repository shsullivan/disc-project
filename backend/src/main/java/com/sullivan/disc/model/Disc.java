package com.sullivan.disc.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(force = true)
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "discs")
public class Disc {

    // Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long discID;

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

    @Column(insertable = false, updatable = false) // Resale value is now calculated @ the DB level
    private double resaleValue;

    @Override
    public String toString() {
        return discID + "|" + manufacturer + "|" + mold + "|" + plastic + "|" + color + "|" + condition + "|" +
                description + "|" + contactFirstName + " " + contactLastName + "|" + contactPhone + "|" + foundAt + "|"
                + returned + "|" + sold + "|" + MSRP + "|" + resaleValue;
    }
}
