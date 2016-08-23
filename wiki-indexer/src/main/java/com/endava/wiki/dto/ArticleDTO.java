package com.endava.wiki.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by aancuta on 8/10/2016.
 */
public class ArticleDTO {
    private List<String> titles;

    private List<WordDTO> wordsList;

    private int duration;

    private String source;

    public List<String> getTitles() {
        return titles;
    }

    public void setTitles(List<String> titles) {
        this.titles = titles;
    }

    public void setTitles(String title) {
        List<String> titles = new ArrayList<>();
        titles.add(title);
        this.titles = titles;
    }

    public void setWordsList(Map<String, Integer> words) {
        this.wordsList = new ArrayList<>();
        for (String key: words.keySet()) {
            wordsList.add(new WordDTO(key, words.get(key)));
        }
    }

    public List<WordDTO> getWordsList() {
        return wordsList;
    }

    public void setWordsList(List<WordDTO> wordsList) {
        this.wordsList = wordsList;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
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
                "titles=" + titles +
                ", wordsList=" + wordsList +
                ", duration=" + duration +
                ", source='" + source + '\'' +
                '}';
    }
}
