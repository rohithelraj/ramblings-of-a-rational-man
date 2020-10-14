package com.elraj.ramblings.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.elraj.ramblings.domain.enumeration.JournalType;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.ZonedDateTimeFilter;

/**
 * Criteria class for the {@link com.elraj.ramblings.domain.Journal} entity. This class is used
 * in {@link com.elraj.ramblings.web.rest.JournalResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /journals?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class JournalCriteria implements Serializable, Criteria {
    /**
     * Class for filtering JournalType
     */
    public static class JournalTypeFilter extends Filter<JournalType> {

        public JournalTypeFilter() {
        }

        public JournalTypeFilter(JournalTypeFilter filter) {
            super(filter);
        }

        @Override
        public JournalTypeFilter copy() {
            return new JournalTypeFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter title;

    private JournalTypeFilter tags;

    private ZonedDateTimeFilter journalDate;

    private StringFilter text;

    public JournalCriteria() {
    }

    public JournalCriteria(JournalCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.title = other.title == null ? null : other.title.copy();
        this.tags = other.tags == null ? null : other.tags.copy();
        this.journalDate = other.journalDate == null ? null : other.journalDate.copy();
        this.text = other.text == null ? null : other.text.copy();
    }

    @Override
    public JournalCriteria copy() {
        return new JournalCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getTitle() {
        return title;
    }

    public void setTitle(StringFilter title) {
        this.title = title;
    }

    public JournalTypeFilter getTags() {
        return tags;
    }

    public void setTags(JournalTypeFilter tags) {
        this.tags = tags;
    }

    public ZonedDateTimeFilter getJournalDate() {
        return journalDate;
    }

    public void setJournalDate(ZonedDateTimeFilter journalDate) {
        this.journalDate = journalDate;
    }

    public StringFilter getText() {
        return text;
    }

    public void setText(StringFilter text) {
        this.text = text;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final JournalCriteria that = (JournalCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(title, that.title) &&
            Objects.equals(tags, that.tags) &&
            Objects.equals(journalDate, that.journalDate) &&
            Objects.equals(text, that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        title,
        tags,
        journalDate,
        text
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "JournalCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (title != null ? "title=" + title + ", " : "") +
                (tags != null ? "tags=" + tags + ", " : "") +
                (journalDate != null ? "journalDate=" + journalDate + ", " : "") +
                (text != null ? "text=" + text + ", " : "") +
            "}";
    }

}
