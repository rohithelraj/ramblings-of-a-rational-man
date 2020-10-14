package com.elraj.ramblings.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class JournalMapperTest {

    private JournalMapper journalMapper;

    @BeforeEach
    public void setUp() {
        journalMapper = new JournalMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(journalMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(journalMapper.fromId(null)).isNull();
    }
}
