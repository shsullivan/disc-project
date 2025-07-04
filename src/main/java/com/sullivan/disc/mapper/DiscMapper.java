package com.sullivan.disc.mapper;

import com.sullivan.disc.dto.DiscCreateDTO;
import com.sullivan.disc.dto.DiscDTO;
import com.sullivan.disc.model.Disc;
import org.springframework.stereotype.Component;

@Component
public class DiscMapper {

    public DiscDTO discToDiscDTO(Disc disc) {
        return new DiscDTO(disc);
    }

    public Disc discCreateDTOtoDisc(DiscCreateDTO dto, int generatedID) {
        return new Disc(
                generatedID,
                dto.getManufacturer(),
                dto.getMold(),
                dto.getPlastic(),
                dto.getColor(),
                dto.getCondition(),
                dto.getDescription(),
                dto.getContactFirstName(),
                dto.getContactLastName(),
                dto.getContactPhone(),
                dto.getFoundAt(),
                false, // Default for returned
                false, // Default for sold
                dto.getMSRP()
        );
    }
}
