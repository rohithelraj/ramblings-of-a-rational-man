package com.elraj.ramblings.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import com.elraj.ramblings.domain.enumeration.journalType;

/**
 * A DTO for the {@link com.elraj.ramblings.domain.Journal} entity.
 */
public class JournalDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String title;

    @NotNull
    private journalType tags;

    @NotNull
    private ZonedDateTime journalDate;

    @NotNull
    private String text;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public journalType getTags() {
        return tags;
    }

    public void setTags(journalType tags) {
        this.tags = tags;
    }

    public ZonedDateTime getJournalDate() {
        return journalDate;
    }

    public void setJournalDate(ZonedDateTime journalDate) {
        this.journalDate = journalDate;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof JournalDTO)) {
            return false;
        }

        return id != null && id.equals(((JournalDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "JournalDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", tags='" + getTags() + "'" +
            ", journalDate='" + getJournalDate() + "'" +
            ", text='" + getText() + "'" +
            "}";
    }
}
