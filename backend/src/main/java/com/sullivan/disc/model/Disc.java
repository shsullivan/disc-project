package com.sullivan.disc.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor(force = true)
@AllArgsConstructor
@Data
@Entity
@Table(name = "discs")
public class Disc {

    // Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "disc_id", nullable = false)
    private Integer discID;

    @ManyToOne
    @JoinColumn(name = "manufacturer_id", nullable = false)
    private Manufacturer manufacturer;

    @ManyToOne
    @JoinColumn(name = "mold_id", nullable = false)
    private Mold mold;

    @Column(name = "plastic", nullable = false)
    private String plastic;

    @Column(name = "color", nullable = false)
    private String color;

    @Column(name = "`condition`", nullable = false)
    private Integer condition;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "contact_id", nullable = false)
    private Contact contact;

    @Column(name = "found_at", nullable = false)
    private String foundAt; // Course where disc was found

    @Column(name = "returned", nullable = false)
    private boolean returned;

    @Column(name = "sold", nullable = false)
    private boolean sold;

    @Column(name = "msrp", nullable = false)
    private BigDecimal msrp;

    @Column(name = "resale_value", insertable = false, updatable = false) // Resale value is now calculated @ the DB level
    private BigDecimal resaleValue;
}
