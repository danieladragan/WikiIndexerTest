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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WikiArticleEntity that = (WikiArticleEntity) o;

        if (id != that.id) return false;
        if (!title.equals(that.title)) return false;
        return words.equals(that.words);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (title != null ? title.hashCode() : 0);

        return result;
    }
}
