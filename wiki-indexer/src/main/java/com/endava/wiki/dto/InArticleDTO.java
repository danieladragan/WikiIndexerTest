package com.endava.wiki.dto;

/**
 * Created by ddragan on 8/31/2016.
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InArticleDTO {
    private String title;

    private List<WordDTO> wordsList;

    private String source;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setWordsList(Map<String, Integer> words) {
        this.wordsList = new ArrayList<>();
        for (String key: words.keySet())
            wordsList.add(new WordDTO(key, words.get(key)));
    }

    public List<WordDTO> getWordsList() {
        return wordsList;
    }

    public void setWordsList(List<WordDTO> wordsList) {
        this.wordsList = wordsList;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public String toString() {
        return "ArticleDTO{" +
                "titles=" + title +
                ", wordsList=" + wordsList +
                ", source='" + source + '\'' +
                '}';
    }
}

