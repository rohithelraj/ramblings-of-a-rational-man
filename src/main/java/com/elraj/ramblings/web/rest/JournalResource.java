package com.elraj.ramblings.web.rest;

import com.elraj.ramblings.service.JournalService;
import com.elraj.ramblings.web.rest.errors.BadRequestAlertException;
import com.elraj.ramblings.service.dto.JournalDTO;
import com.elraj.ramblings.service.dto.JournalCriteria;
import com.elraj.ramblings.service.JournalQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.elraj.ramblings.domain.Journal}.
 */
@RestController
@RequestMapping("/api")
public class JournalResource {

    private final Logger log = LoggerFactory.getLogger(JournalResource.class);

    private static final String ENTITY_NAME = "journal";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final JournalService journalService;

    private final JournalQueryService journalQueryService;

    public JournalResource(JournalService journalService, JournalQueryService journalQueryService) {
        this.journalService = journalService;
        this.journalQueryService = journalQueryService;
    }

    /**
     * {@code POST  /journals} : Create a new journal.
     *
     * @param journalDTO the journalDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new journalDTO, or with status {@code 400 (Bad Request)} if the journal has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/journals")
    public ResponseEntity<JournalDTO> createJournal(@Valid @RequestBody JournalDTO journalDTO) throws URISyntaxException {
        log.debug("REST request to save Journal : {}", journalDTO);
        if (journalDTO.getId() != null) {
            throw new BadRequestAlertException("A new journal cannot already have an ID", ENTITY_NAME, "idexists");
        }
        JournalDTO result = journalService.save(journalDTO);
        return ResponseEntity.created(new URI("/api/journals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /journals} : Updates an existing journal.
     *
     * @param journalDTO the journalDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated journalDTO,
     * or with status {@code 400 (Bad Request)} if the journalDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the journalDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/journals")
    public ResponseEntity<JournalDTO> updateJournal(@Valid @RequestBody JournalDTO journalDTO) throws URISyntaxException {
        log.debug("REST request to update Journal : {}", journalDTO);
        if (journalDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        JournalDTO result = journalService.save(journalDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, journalDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /journals} : get all the journals.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of journals in body.
     */
    @GetMapping("/journals")
    public ResponseEntity<List<JournalDTO>> getAllJournals(JournalCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Journals by criteria: {}", criteria);
        Page<JournalDTO> page = journalQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /journals/count} : count all the journals.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/journals/count")
    public ResponseEntity<Long> countJournals(JournalCriteria criteria) {
        log.debug("REST request to count Journals by criteria: {}", criteria);
        return ResponseEntity.ok().body(journalQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /journals/:id} : get the "id" journal.
     *
     * @param id the id of the journalDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the journalDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/journals/{id}")
    public ResponseEntity<JournalDTO> getJournal(@PathVariable Long id) {
        log.debug("REST request to get Journal : {}", id);
        Optional<JournalDTO> journalDTO = journalService.findOne(id);
        return ResponseUtil.wrapOrNotFound(journalDTO);
    }

    /**
     * {@code DELETE  /journals/:id} : delete the "id" journal.
     *
     * @param id the id of the journalDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/journals/{id}")
    public ResponseEntity<Void> deleteJournal(@PathVariable Long id) {
        log.debug("REST request to delete Journal : {}", id);
        journalService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
