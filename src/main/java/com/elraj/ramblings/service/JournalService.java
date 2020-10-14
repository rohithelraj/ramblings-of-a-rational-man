package com.elraj.ramblings.service;

import com.elraj.ramblings.domain.Journal;
import com.elraj.ramblings.repository.JournalRepository;
import com.elraj.ramblings.service.dto.JournalDTO;
import com.elraj.ramblings.service.mapper.JournalMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Journal}.
 */
@Service
@Transactional
public class JournalService {

    private final Logger log = LoggerFactory.getLogger(JournalService.class);

    private final JournalRepository journalRepository;

    private final JournalMapper journalMapper;

    public JournalService(JournalRepository journalRepository, JournalMapper journalMapper) {
        this.journalRepository = journalRepository;
        this.journalMapper = journalMapper;
    }

    /**
     * Save a journal.
     *
     * @param journalDTO the entity to save.
     * @return the persisted entity.
     */
    public JournalDTO save(JournalDTO journalDTO) {
        log.debug("Request to save Journal : {}", journalDTO);
        Journal journal = journalMapper.toEntity(journalDTO);
        journal = journalRepository.save(journal);
        return journalMapper.toDto(journal);
    }

    /**
     * Get all the journals.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<JournalDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Journals");
        return journalRepository.findAll(pageable)
            .map(journalMapper::toDto);
    }


    /**
     * Get one journal by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<JournalDTO> findOne(Long id) {
        log.debug("Request to get Journal : {}", id);
        return journalRepository.findById(id)
            .map(journalMapper::toDto);
    }

    /**
     * Delete the journal by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Journal : {}", id);
        journalRepository.deleteById(id);
    }
}
