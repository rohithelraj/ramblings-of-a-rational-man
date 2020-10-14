package com.elraj.ramblings.service.mapper;


import com.elraj.ramblings.domain.*;
import com.elraj.ramblings.service.dto.JournalDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Journal} and its DTO {@link JournalDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface JournalMapper extends EntityMapper<JournalDTO, Journal> {



    default Journal fromId(Long id) {
        if (id == null) {
            return null;
        }
        Journal journal = new Journal();
        journal.setId(id);
        return journal;
    }
}
