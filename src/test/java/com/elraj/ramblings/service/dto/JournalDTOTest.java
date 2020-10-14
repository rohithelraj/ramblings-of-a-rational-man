package com.elraj.ramblings.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.elraj.ramblings.web.rest.TestUtil;

public class JournalDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(JournalDTO.class);
        JournalDTO journalDTO1 = new JournalDTO();
        journalDTO1.setId(1L);
        JournalDTO journalDTO2 = new JournalDTO();
        assertThat(journalDTO1).isNotEqualTo(journalDTO2);
        journalDTO2.setId(journalDTO1.getId());
        assertThat(journalDTO1).isEqualTo(journalDTO2);
        journalDTO2.setId(2L);
        assertThat(journalDTO1).isNotEqualTo(journalDTO2);
        journalDTO1.setId(null);
        assertThat(journalDTO1).isNotEqualTo(journalDTO2);
    }
}
