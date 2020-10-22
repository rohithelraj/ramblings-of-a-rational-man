package com.elraj.ramblings.web.rest;

import com.elraj.ramblings.RamblingsofarationalmanApp;
import com.elraj.ramblings.domain.Journal;
import com.elraj.ramblings.repository.JournalRepository;
import com.elraj.ramblings.service.JournalService;
import com.elraj.ramblings.service.dto.JournalDTO;
import com.elraj.ramblings.service.mapper.JournalMapper;
import com.elraj.ramblings.service.dto.JournalCriteria;
import com.elraj.ramblings.service.JournalQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.elraj.ramblings.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.elraj.ramblings.domain.enumeration.journalType;
/**
 * Integration tests for the {@link JournalResource} REST controller.
 */
@SpringBootTest(classes = RamblingsofarationalmanApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class JournalResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final journalType DEFAULT_TAGS = journalType.RAMBLING;
    private static final journalType UPDATED_TAGS = journalType.PURPOSE;

    private static final ZonedDateTime DEFAULT_JOURNAL_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_JOURNAL_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_JOURNAL_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String DEFAULT_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_TEXT = "BBBBBBBBBB";

    @Autowired
    private JournalRepository journalRepository;

    @Autowired
    private JournalMapper journalMapper;

    @Autowired
    private JournalService journalService;

    @Autowired
    private JournalQueryService journalQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restJournalMockMvc;

    private Journal journal;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Journal createEntity(EntityManager em) {
        Journal journal = new Journal()
            .title(DEFAULT_TITLE)
            .tags(DEFAULT_TAGS)
            .journalDate(DEFAULT_JOURNAL_DATE)
            .text(DEFAULT_TEXT);
        return journal;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Journal createUpdatedEntity(EntityManager em) {
        Journal journal = new Journal()
            .title(UPDATED_TITLE)
            .tags(UPDATED_TAGS)
            .journalDate(UPDATED_JOURNAL_DATE)
            .text(UPDATED_TEXT);
        return journal;
    }

    @BeforeEach
    public void initTest() {
        journal = createEntity(em);
    }

    @Test
    @Transactional
    public void createJournal() throws Exception {
        int databaseSizeBeforeCreate = journalRepository.findAll().size();
        // Create the Journal
        JournalDTO journalDTO = journalMapper.toDto(journal);
        restJournalMockMvc.perform(post("/api/journals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(journalDTO)))
            .andExpect(status().isCreated());

        // Validate the Journal in the database
        List<Journal> journalList = journalRepository.findAll();
        assertThat(journalList).hasSize(databaseSizeBeforeCreate + 1);
        Journal testJournal = journalList.get(journalList.size() - 1);
        assertThat(testJournal.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testJournal.getTags()).isEqualTo(DEFAULT_TAGS);
        assertThat(testJournal.getJournalDate()).isEqualTo(DEFAULT_JOURNAL_DATE);
        assertThat(testJournal.getText()).isEqualTo(DEFAULT_TEXT);
    }

    @Test
    @Transactional
    public void createJournalWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = journalRepository.findAll().size();

        // Create the Journal with an existing ID
        journal.setId(1L);
        JournalDTO journalDTO = journalMapper.toDto(journal);

        // An entity with an existing ID cannot be created, so this API call must fail
        restJournalMockMvc.perform(post("/api/journals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(journalDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Journal in the database
        List<Journal> journalList = journalRepository.findAll();
        assertThat(journalList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = journalRepository.findAll().size();
        // set the field null
        journal.setTitle(null);

        // Create the Journal, which fails.
        JournalDTO journalDTO = journalMapper.toDto(journal);


        restJournalMockMvc.perform(post("/api/journals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(journalDTO)))
            .andExpect(status().isBadRequest());

        List<Journal> journalList = journalRepository.findAll();
        assertThat(journalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTagsIsRequired() throws Exception {
        int databaseSizeBeforeTest = journalRepository.findAll().size();
        // set the field null
        journal.setTags(null);

        // Create the Journal, which fails.
        JournalDTO journalDTO = journalMapper.toDto(journal);


        restJournalMockMvc.perform(post("/api/journals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(journalDTO)))
            .andExpect(status().isBadRequest());

        List<Journal> journalList = journalRepository.findAll();
        assertThat(journalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkJournalDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = journalRepository.findAll().size();
        // set the field null
        journal.setJournalDate(null);

        // Create the Journal, which fails.
        JournalDTO journalDTO = journalMapper.toDto(journal);


        restJournalMockMvc.perform(post("/api/journals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(journalDTO)))
            .andExpect(status().isBadRequest());

        List<Journal> journalList = journalRepository.findAll();
        assertThat(journalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTextIsRequired() throws Exception {
        int databaseSizeBeforeTest = journalRepository.findAll().size();
        // set the field null
        journal.setText(null);

        // Create the Journal, which fails.
        JournalDTO journalDTO = journalMapper.toDto(journal);


        restJournalMockMvc.perform(post("/api/journals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(journalDTO)))
            .andExpect(status().isBadRequest());

        List<Journal> journalList = journalRepository.findAll();
        assertThat(journalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllJournals() throws Exception {
        // Initialize the database
        journalRepository.saveAndFlush(journal);

        // Get all the journalList
        restJournalMockMvc.perform(get("/api/journals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(journal.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].tags").value(hasItem(DEFAULT_TAGS.toString())))
            .andExpect(jsonPath("$.[*].journalDate").value(hasItem(sameInstant(DEFAULT_JOURNAL_DATE))))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT)));
    }
    
    @Test
    @Transactional
    public void getJournal() throws Exception {
        // Initialize the database
        journalRepository.saveAndFlush(journal);

        // Get the journal
        restJournalMockMvc.perform(get("/api/journals/{id}", journal.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(journal.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.tags").value(DEFAULT_TAGS.toString()))
            .andExpect(jsonPath("$.journalDate").value(sameInstant(DEFAULT_JOURNAL_DATE)))
            .andExpect(jsonPath("$.text").value(DEFAULT_TEXT));
    }


    @Test
    @Transactional
    public void getJournalsByIdFiltering() throws Exception {
        // Initialize the database
        journalRepository.saveAndFlush(journal);

        Long id = journal.getId();

        defaultJournalShouldBeFound("id.equals=" + id);
        defaultJournalShouldNotBeFound("id.notEquals=" + id);

        defaultJournalShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultJournalShouldNotBeFound("id.greaterThan=" + id);

        defaultJournalShouldBeFound("id.lessThanOrEqual=" + id);
        defaultJournalShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllJournalsByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        journalRepository.saveAndFlush(journal);

        // Get all the journalList where title equals to DEFAULT_TITLE
        defaultJournalShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the journalList where title equals to UPDATED_TITLE
        defaultJournalShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllJournalsByTitleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        journalRepository.saveAndFlush(journal);

        // Get all the journalList where title not equals to DEFAULT_TITLE
        defaultJournalShouldNotBeFound("title.notEquals=" + DEFAULT_TITLE);

        // Get all the journalList where title not equals to UPDATED_TITLE
        defaultJournalShouldBeFound("title.notEquals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllJournalsByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        journalRepository.saveAndFlush(journal);

        // Get all the journalList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultJournalShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the journalList where title equals to UPDATED_TITLE
        defaultJournalShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllJournalsByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        journalRepository.saveAndFlush(journal);

        // Get all the journalList where title is not null
        defaultJournalShouldBeFound("title.specified=true");

        // Get all the journalList where title is null
        defaultJournalShouldNotBeFound("title.specified=false");
    }
                @Test
    @Transactional
    public void getAllJournalsByTitleContainsSomething() throws Exception {
        // Initialize the database
        journalRepository.saveAndFlush(journal);

        // Get all the journalList where title contains DEFAULT_TITLE
        defaultJournalShouldBeFound("title.contains=" + DEFAULT_TITLE);

        // Get all the journalList where title contains UPDATED_TITLE
        defaultJournalShouldNotBeFound("title.contains=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllJournalsByTitleNotContainsSomething() throws Exception {
        // Initialize the database
        journalRepository.saveAndFlush(journal);

        // Get all the journalList where title does not contain DEFAULT_TITLE
        defaultJournalShouldNotBeFound("title.doesNotContain=" + DEFAULT_TITLE);

        // Get all the journalList where title does not contain UPDATED_TITLE
        defaultJournalShouldBeFound("title.doesNotContain=" + UPDATED_TITLE);
    }


    @Test
    @Transactional
    public void getAllJournalsByTagsIsEqualToSomething() throws Exception {
        // Initialize the database
        journalRepository.saveAndFlush(journal);

        // Get all the journalList where tags equals to DEFAULT_TAGS
        defaultJournalShouldBeFound("tags.equals=" + DEFAULT_TAGS);

        // Get all the journalList where tags equals to UPDATED_TAGS
        defaultJournalShouldNotBeFound("tags.equals=" + UPDATED_TAGS);
    }

    @Test
    @Transactional
    public void getAllJournalsByTagsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        journalRepository.saveAndFlush(journal);

        // Get all the journalList where tags not equals to DEFAULT_TAGS
        defaultJournalShouldNotBeFound("tags.notEquals=" + DEFAULT_TAGS);

        // Get all the journalList where tags not equals to UPDATED_TAGS
        defaultJournalShouldBeFound("tags.notEquals=" + UPDATED_TAGS);
    }

    @Test
    @Transactional
    public void getAllJournalsByTagsIsInShouldWork() throws Exception {
        // Initialize the database
        journalRepository.saveAndFlush(journal);

        // Get all the journalList where tags in DEFAULT_TAGS or UPDATED_TAGS
        defaultJournalShouldBeFound("tags.in=" + DEFAULT_TAGS + "," + UPDATED_TAGS);

        // Get all the journalList where tags equals to UPDATED_TAGS
        defaultJournalShouldNotBeFound("tags.in=" + UPDATED_TAGS);
    }

    @Test
    @Transactional
    public void getAllJournalsByTagsIsNullOrNotNull() throws Exception {
        // Initialize the database
        journalRepository.saveAndFlush(journal);

        // Get all the journalList where tags is not null
        defaultJournalShouldBeFound("tags.specified=true");

        // Get all the journalList where tags is null
        defaultJournalShouldNotBeFound("tags.specified=false");
    }

    @Test
    @Transactional
    public void getAllJournalsByJournalDateIsEqualToSomething() throws Exception {
        // Initialize the database
        journalRepository.saveAndFlush(journal);

        // Get all the journalList where journalDate equals to DEFAULT_JOURNAL_DATE
        defaultJournalShouldBeFound("journalDate.equals=" + DEFAULT_JOURNAL_DATE);

        // Get all the journalList where journalDate equals to UPDATED_JOURNAL_DATE
        defaultJournalShouldNotBeFound("journalDate.equals=" + UPDATED_JOURNAL_DATE);
    }

    @Test
    @Transactional
    public void getAllJournalsByJournalDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        journalRepository.saveAndFlush(journal);

        // Get all the journalList where journalDate not equals to DEFAULT_JOURNAL_DATE
        defaultJournalShouldNotBeFound("journalDate.notEquals=" + DEFAULT_JOURNAL_DATE);

        // Get all the journalList where journalDate not equals to UPDATED_JOURNAL_DATE
        defaultJournalShouldBeFound("journalDate.notEquals=" + UPDATED_JOURNAL_DATE);
    }

    @Test
    @Transactional
    public void getAllJournalsByJournalDateIsInShouldWork() throws Exception {
        // Initialize the database
        journalRepository.saveAndFlush(journal);

        // Get all the journalList where journalDate in DEFAULT_JOURNAL_DATE or UPDATED_JOURNAL_DATE
        defaultJournalShouldBeFound("journalDate.in=" + DEFAULT_JOURNAL_DATE + "," + UPDATED_JOURNAL_DATE);

        // Get all the journalList where journalDate equals to UPDATED_JOURNAL_DATE
        defaultJournalShouldNotBeFound("journalDate.in=" + UPDATED_JOURNAL_DATE);
    }

    @Test
    @Transactional
    public void getAllJournalsByJournalDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        journalRepository.saveAndFlush(journal);

        // Get all the journalList where journalDate is not null
        defaultJournalShouldBeFound("journalDate.specified=true");

        // Get all the journalList where journalDate is null
        defaultJournalShouldNotBeFound("journalDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllJournalsByJournalDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        journalRepository.saveAndFlush(journal);

        // Get all the journalList where journalDate is greater than or equal to DEFAULT_JOURNAL_DATE
        defaultJournalShouldBeFound("journalDate.greaterThanOrEqual=" + DEFAULT_JOURNAL_DATE);

        // Get all the journalList where journalDate is greater than or equal to UPDATED_JOURNAL_DATE
        defaultJournalShouldNotBeFound("journalDate.greaterThanOrEqual=" + UPDATED_JOURNAL_DATE);
    }

    @Test
    @Transactional
    public void getAllJournalsByJournalDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        journalRepository.saveAndFlush(journal);

        // Get all the journalList where journalDate is less than or equal to DEFAULT_JOURNAL_DATE
        defaultJournalShouldBeFound("journalDate.lessThanOrEqual=" + DEFAULT_JOURNAL_DATE);

        // Get all the journalList where journalDate is less than or equal to SMALLER_JOURNAL_DATE
        defaultJournalShouldNotBeFound("journalDate.lessThanOrEqual=" + SMALLER_JOURNAL_DATE);
    }

    @Test
    @Transactional
    public void getAllJournalsByJournalDateIsLessThanSomething() throws Exception {
        // Initialize the database
        journalRepository.saveAndFlush(journal);

        // Get all the journalList where journalDate is less than DEFAULT_JOURNAL_DATE
        defaultJournalShouldNotBeFound("journalDate.lessThan=" + DEFAULT_JOURNAL_DATE);

        // Get all the journalList where journalDate is less than UPDATED_JOURNAL_DATE
        defaultJournalShouldBeFound("journalDate.lessThan=" + UPDATED_JOURNAL_DATE);
    }

    @Test
    @Transactional
    public void getAllJournalsByJournalDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        journalRepository.saveAndFlush(journal);

        // Get all the journalList where journalDate is greater than DEFAULT_JOURNAL_DATE
        defaultJournalShouldNotBeFound("journalDate.greaterThan=" + DEFAULT_JOURNAL_DATE);

        // Get all the journalList where journalDate is greater than SMALLER_JOURNAL_DATE
        defaultJournalShouldBeFound("journalDate.greaterThan=" + SMALLER_JOURNAL_DATE);
    }


    @Test
    @Transactional
    public void getAllJournalsByTextIsEqualToSomething() throws Exception {
        // Initialize the database
        journalRepository.saveAndFlush(journal);

        // Get all the journalList where text equals to DEFAULT_TEXT
        defaultJournalShouldBeFound("text.equals=" + DEFAULT_TEXT);

        // Get all the journalList where text equals to UPDATED_TEXT
        defaultJournalShouldNotBeFound("text.equals=" + UPDATED_TEXT);
    }

    @Test
    @Transactional
    public void getAllJournalsByTextIsNotEqualToSomething() throws Exception {
        // Initialize the database
        journalRepository.saveAndFlush(journal);

        // Get all the journalList where text not equals to DEFAULT_TEXT
        defaultJournalShouldNotBeFound("text.notEquals=" + DEFAULT_TEXT);

        // Get all the journalList where text not equals to UPDATED_TEXT
        defaultJournalShouldBeFound("text.notEquals=" + UPDATED_TEXT);
    }

    @Test
    @Transactional
    public void getAllJournalsByTextIsInShouldWork() throws Exception {
        // Initialize the database
        journalRepository.saveAndFlush(journal);

        // Get all the journalList where text in DEFAULT_TEXT or UPDATED_TEXT
        defaultJournalShouldBeFound("text.in=" + DEFAULT_TEXT + "," + UPDATED_TEXT);

        // Get all the journalList where text equals to UPDATED_TEXT
        defaultJournalShouldNotBeFound("text.in=" + UPDATED_TEXT);
    }

    @Test
    @Transactional
    public void getAllJournalsByTextIsNullOrNotNull() throws Exception {
        // Initialize the database
        journalRepository.saveAndFlush(journal);

        // Get all the journalList where text is not null
        defaultJournalShouldBeFound("text.specified=true");

        // Get all the journalList where text is null
        defaultJournalShouldNotBeFound("text.specified=false");
    }
                @Test
    @Transactional
    public void getAllJournalsByTextContainsSomething() throws Exception {
        // Initialize the database
        journalRepository.saveAndFlush(journal);

        // Get all the journalList where text contains DEFAULT_TEXT
        defaultJournalShouldBeFound("text.contains=" + DEFAULT_TEXT);

        // Get all the journalList where text contains UPDATED_TEXT
        defaultJournalShouldNotBeFound("text.contains=" + UPDATED_TEXT);
    }

    @Test
    @Transactional
    public void getAllJournalsByTextNotContainsSomething() throws Exception {
        // Initialize the database
        journalRepository.saveAndFlush(journal);

        // Get all the journalList where text does not contain DEFAULT_TEXT
        defaultJournalShouldNotBeFound("text.doesNotContain=" + DEFAULT_TEXT);

        // Get all the journalList where text does not contain UPDATED_TEXT
        defaultJournalShouldBeFound("text.doesNotContain=" + UPDATED_TEXT);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultJournalShouldBeFound(String filter) throws Exception {
        restJournalMockMvc.perform(get("/api/journals?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(journal.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].tags").value(hasItem(DEFAULT_TAGS.toString())))
            .andExpect(jsonPath("$.[*].journalDate").value(hasItem(sameInstant(DEFAULT_JOURNAL_DATE))))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT)));

        // Check, that the count call also returns 1
        restJournalMockMvc.perform(get("/api/journals/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultJournalShouldNotBeFound(String filter) throws Exception {
        restJournalMockMvc.perform(get("/api/journals?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restJournalMockMvc.perform(get("/api/journals/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingJournal() throws Exception {
        // Get the journal
        restJournalMockMvc.perform(get("/api/journals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJournal() throws Exception {
        // Initialize the database
        journalRepository.saveAndFlush(journal);

        int databaseSizeBeforeUpdate = journalRepository.findAll().size();

        // Update the journal
        Journal updatedJournal = journalRepository.findById(journal.getId()).get();
        // Disconnect from session so that the updates on updatedJournal are not directly saved in db
        em.detach(updatedJournal);
        updatedJournal
            .title(UPDATED_TITLE)
            .tags(UPDATED_TAGS)
            .journalDate(UPDATED_JOURNAL_DATE)
            .text(UPDATED_TEXT);
        JournalDTO journalDTO = journalMapper.toDto(updatedJournal);

        restJournalMockMvc.perform(put("/api/journals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(journalDTO)))
            .andExpect(status().isOk());

        // Validate the Journal in the database
        List<Journal> journalList = journalRepository.findAll();
        assertThat(journalList).hasSize(databaseSizeBeforeUpdate);
        Journal testJournal = journalList.get(journalList.size() - 1);
        assertThat(testJournal.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testJournal.getTags()).isEqualTo(UPDATED_TAGS);
        assertThat(testJournal.getJournalDate()).isEqualTo(UPDATED_JOURNAL_DATE);
        assertThat(testJournal.getText()).isEqualTo(UPDATED_TEXT);
    }

    @Test
    @Transactional
    public void updateNonExistingJournal() throws Exception {
        int databaseSizeBeforeUpdate = journalRepository.findAll().size();

        // Create the Journal
        JournalDTO journalDTO = journalMapper.toDto(journal);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJournalMockMvc.perform(put("/api/journals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(journalDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Journal in the database
        List<Journal> journalList = journalRepository.findAll();
        assertThat(journalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteJournal() throws Exception {
        // Initialize the database
        journalRepository.saveAndFlush(journal);

        int databaseSizeBeforeDelete = journalRepository.findAll().size();

        // Delete the journal
        restJournalMockMvc.perform(delete("/api/journals/{id}", journal.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Journal> journalList = journalRepository.findAll();
        assertThat(journalList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
