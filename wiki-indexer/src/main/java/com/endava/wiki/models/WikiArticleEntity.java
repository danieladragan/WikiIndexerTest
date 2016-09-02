package com.endava.wiki.models;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by vsima on 8/10/2016.
 */
@Entity
@Table(name = "wiki_article", schema = "wiki", catalog = "")
public class WikiArticleEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    private String title;
    private java.sql.Timestamp date;

    @OneToMany (targetEntity = com.endava.wiki.models.WordFrequencyEntity.class, mappedBy = "article")
    private List<WordFrequencyEntity> words;

    public List<WordFrequencyEntity> getWords() {
        return words;
    }

    public void setWords(List<WordFrequencyEntity> words) {
        this.words = words;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WikiArticleEntity that = (WikiArticleEntity) o;

        if (id != that.id) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        return words != null ? words.equals(that.words) : that.words == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (words != null ? words.hashCode() : 0);
        return result;
    }
}
