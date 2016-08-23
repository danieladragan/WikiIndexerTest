package com.endava.wiki.models;

import com.endava.wiki.models.WikiArticleEntity;

import javax.persistence.*;

/**
 * Created by vsima on 8/10/2016.
 */
@Entity
@Table(name = "word_frequency", schema = "wiki", catalog = "")
public class WordFrequencyEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    private String word;
    private int wordCount;

    public WikiArticleEntity getArticle() {
        return article;
    }

    public void setArticle(WikiArticleEntity article) {
        this.article = article;
    }

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "wiki_article_id")
    private WikiArticleEntity article;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "word")
    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    @Basic
    @Column(name = "word_count")
    public int getWordCount() {
        return wordCount;
    }

    public void setWordCount(int wordCount) {
        this.wordCount = wordCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WordFrequencyEntity that = (WordFrequencyEntity) o;

        if (id != that.id) return false;
        if (wordCount != that.wordCount) return false;
        if (word != null ? !word.equals(that.word) : that.word != null) return false;
        return article != null ? article.equals(that.article) : that.article == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (word != null ? word.hashCode() : 0);
        result = 31 * result + wordCount;
        result = 31 * result + (article != null ? article.hashCode() : 0);
        return result;
    }
}
