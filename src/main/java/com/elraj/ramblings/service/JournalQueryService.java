
package com.elraj.ramblings.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.elraj.ramblings.domain.Journal;
import com.elraj.ramblings.domain.*; // for static metamodels
import com.elraj.ramblings.repository.JournalRepository;
import com.elraj.ramblings.service.dto.JournalCriteria;
import com.elraj.ramblings.service.dto.JournalDTO;
import com.elraj.ramblings.service.mapper.JournalMapper;

/**
 * Service for executing complex queries for {@link Journal} entities in the database.
 * The main input is a {@link JournalCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link JournalDTO} or a {@link Page} of {@link JournalDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class JournalQueryService extends QueryService<Journal> {

    private final Logger log = LoggerFactory.getLogger(JournalQueryService.class);

    private final JournalRepository journalRepository;

    private final JournalMapper journalMapper;

    public JournalQueryService(JournalRepository journalRepository, JournalMapper journalMapper) {
        this.journalRepository = journalRepository;
        this.journalMapper = journalMapper;
    }

    /**
     * Return a {@link List} of {@link JournalDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<JournalDTO> findByCriteria(JournalCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Journal> specification = createSpecification(criteria);
        return journalMapper.toDto(journalRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link JournalDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<JournalDTO> findByCriteria(JournalCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Journal> specification = createSpecification(criteria);
        return journalRepository.findAll(specification, page)
            .map(journalMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(JournalCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Journal> specification = createSpecification(criteria);
        return journalRepository.count(specification);
    }

    /**
     * Function to convert {@link JournalCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Journal> createSpecification(JournalCriteria criteria) {
        Specification<Journal> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Journal_.id));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitle(), Journal_.title));
            }
            if (criteria.getTags() != null) {
                specification = specification.and(buildSpecification(criteria.getTags(), Journal_.tags));
            }
            if (criteria.getJournalDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getJournalDate(), Journal_.journalDate));
            }
            if (criteria.getText() != null) {
                specification = specification.and(buildStringSpecification(criteria.getText(), Journal_.text));
            }
        }
        return specification;
    }
}
