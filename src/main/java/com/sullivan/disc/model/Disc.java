package com.sullivan.disc.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(force = true)
@AllArgsConstructor
@Getter
@Setter
public class Disc {

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

    // Moved resale value to a getter only to avoid stale data
    public double getResaleValue () {
        double factor;

        if (condition <= 5) {
            factor = 0.4;
        }
        else if ((condition == 6) || (condition == 7)) {
            factor = 0.6;
        }
        else {
            factor = 0.8;
        }

        return sold ? MSRP * factor : 0.0;
    }

    @Override
    public String toString() {
        return discID + "|" + manufacturer + "|" + mold + "|" + plastic + "|" + color + "|" + condition + "|" +
                description + "|" + contactFirstName + " " + contactLastName + "|" + contactPhone + "|" + foundAt + "|"
                + returned + "|" + sold + "|" + MSRP + "|" + getResaleValue();
    }
}
