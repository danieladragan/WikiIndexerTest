package com.endava.wiki.dto;

/**
 * Created by aancuta on 8/18/2016.
 */
public class WordDTO {
    private String word;

    private int occurrences;

    public WordDTO() {}

    public WordDTO(String word, int occurrences) {
        this.word = word;
        this.occurrences = occurrences;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getOccurrences() {
        return occurrences;
    }

    public void setOccurrences(int occurrences) {
        this.occurrences = occurrences;
    }

    @Override
    public String toString() {
        return "WordDTO{" +
                "word='" + word + '\'' +
                ", occurrences=" + occurrences +
                '}';
    }
}
