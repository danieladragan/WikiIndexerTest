package com.endava.wiki.service;

import com.endava.wiki.dto.ArticleDTO;

import java.util.List;

/**
 * Created by aancuta on 8/10/2016.
 */
public interface WordFrequencyService {
    /**
     * Returns an ArticleDTO for a single wikipedia article
     * @param articleName List of wikipedia articles names
     * @param numberOfWords Number of words to include in the most frequent list
     * @return InputStream
     */
    ArticleDTO getWordsByFrequency(String articleName, int numberOfWords);

    /**
     * Returns an ArticleDTO for multiple wikipedia articles
     * @param articleNames List of wikipedia articles names
     * @param numberOfWords Number of words to include in the most frequent list
     * @return InputStream
     */
    ArticleDTO getWordsByFrequencyInMultipleArticles(List<String> articleNames, int numberOfWords);
}
