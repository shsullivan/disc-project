package com.sullivan.disc.mapper;

import com.sullivan.disc.dto.*;
import com.sullivan.disc.model.Contact;
import com.sullivan.disc.model.Disc;
import com.sullivan.disc.model.Manufacturer;
import com.sullivan.disc.model.Mold;
import org.springframework.stereotype.Component;

@Component
public class DiscMapper {

    public DiscDTO discToDiscDTO(Disc disc) {
        if (disc == null) return null;

        return new DiscDTO(
                disc.getDiscID(),
                new ManufacturerDTO(
                        disc.getManufacturer().getManufacturerId(),
                        disc.getManufacturer().getManufacturer()),
                new MoldDTO(
                        disc.getMold().getMoldID(),
                        disc.getMold().getManufacturer().getManufacturerId(),
                        disc.getMold().getMold()),
                disc.getPlastic(),
                disc.getColor(),
                disc.getCondition(),
                disc.getDescription(),
                new ContactDTO(
                        disc.getContact().getContact_id(),
                        disc.getContact().getFirstName(),
                        disc.getContact().getLastName(),
                        disc.getContact().getPhoneNumber()),
                disc.getFoundAt(),
                disc.isReturned(),
                disc.isSold(),
                disc.getMsrp(),
                disc.getResaleValue()
                );
    }

    public Disc discCreateDTOtoDisc(DiscCreateDTO dto, Manufacturer manufacturer, Mold mold, Contact contact) {
        return new Disc(
                null,
                manufacturer,
                mold,
                dto.getPlastic(),
                dto.getColor(),
                dto.getCondition(),
                dto.getDescription(),
                contact,
                dto.getFoundAt(),
                false,  // Returned is set to false by default
                false,          // Sold is set to false by default
                dto.getMSRP(),
                null            // Resale value is calculated at the DB level
        );
    }
}
