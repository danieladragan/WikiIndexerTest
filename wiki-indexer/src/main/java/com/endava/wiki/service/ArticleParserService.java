package com.endava.wiki.service;

import java.util.Map;

/**
 * Created by aancuta on 8/10/2016.
 */
public interface ArticleParserService {
    /**
     * Parse an wikipedia article and returns the current state of the map
     * with words and number of occurrences
     * @param articleName Title of an wikipedia article
     * @return Map
     */
    Map<String, Integer> countWordsInArticle(String articleName, boolean all);

    /**
     * Returns a map with words and their number of occurrences
     * @return Map
     */
    Map<String, Integer> getWordFrequency();

    /**
     * Instantiate an empty map to hold the words and their number of occurrences
     */
    void refreshWordMap();
}
