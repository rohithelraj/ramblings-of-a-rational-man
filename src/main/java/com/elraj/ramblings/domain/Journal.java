package com.elraj.ramblings.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

import com.elraj.ramblings.domain.enumeration.JournalType;

/**
 * A Journal.
 */
@Entity
@Table(name = "journal")
public class Journal implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tags", nullable = false)
    private JournalType tags;

    @NotNull
    @Column(name = "journal_date", nullable = false)
    private ZonedDateTime journalDate;

    @NotNull
    @Column(name = "text", nullable = false)
    private String text;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Journal title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public JournalType getTags() {
        return tags;
    }

    public Journal tags(JournalType tags) {
        this.tags = tags;
        return this;
    }

    public void setTags(JournalType tags) {
        this.tags = tags;
    }

    public ZonedDateTime getJournalDate() {
        return journalDate;
    }

    public Journal journalDate(ZonedDateTime journalDate) {
        this.journalDate = journalDate;
        return this;
    }

    public void setJournalDate(ZonedDateTime journalDate) {
        this.journalDate = journalDate;
    }

    public String getText() {
        return text;
    }

    public Journal text(String text) {
        this.text = text;
        return this;
    }

    public void setText(String text) {
        this.text = text;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Journal)) {
            return false;
        }
        return id != null && id.equals(((Journal) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Journal{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", tags='" + getTags() + "'" +
            ", journalDate='" + getJournalDate() + "'" +
            ", text='" + getText() + "'" +
            "}";
    }
}
