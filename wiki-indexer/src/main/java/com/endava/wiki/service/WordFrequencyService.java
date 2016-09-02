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
     * @return InputStream
     */
    ArticleDTO getWordsByFrequency(String articleName);

    /**
     * Returns an ArticleDTO for multiple wikipedia articles
     * @param articleNames List of wikipedia articles names
     * @return InputStream
     */
    ArticleDTO getWordsByFrequencyInMultipleArticles(List<String> articleNames);
}
