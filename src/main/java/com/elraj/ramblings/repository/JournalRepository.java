package com.elraj.ramblings.repository;

import com.elraj.ramblings.domain.Journal;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Journal entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JournalRepository extends JpaRepository<Journal, Long>, JpaSpecificationExecutor<Journal> {
}
