package com.endava.wiki.service.impl;

import com.endava.wiki.dto.ArticleDTO;
import com.endava.wiki.models.WikiArticleEntity;
import com.endava.wiki.models.WordFrequencyEntity;
import com.endava.wiki.service.ArticleParserService;
import com.endava.wiki.service.WikiArticleService;
import com.endava.wiki.service.WordFrequencyService;
import org.apache.commons.lang3.text.WordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Created by aancuta on 8/10/2016.
 */
@Service
public class WordFrequencyServiceImpl implements WordFrequencyService {

    private static final String DATA_SOURCE_WIKIPEDIA = "Wikipedia";
    private static final String DATA_SOURCE_BD ="BD";
    private static final int THREAD_POOL_SIZE = 5;
    private static final int WAITING_TIME_SECONDS = 30;

    @Autowired
    ArticleParserService articleParserService;

    @Autowired
    WikiArticleService wikiArticleService;

    @Override
    public ArticleDTO getWordsByFrequency(String articleName) {
        long startTime, endTime;
        articleParserService.refreshWordMap();

        articleName = WordUtils.capitalizeFully(articleName);

        startTime = System.currentTimeMillis();

        WikiArticleEntity dbArticle = wikiArticleService.findByTitle(articleName);

        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setTitles(articleName);

        if(dbArticle == null){
            articleDTO.setSource(DATA_SOURCE_WIKIPEDIA);
            boolean countCommonWords = false;

            Map<String, Integer> wordFrequency = articleParserService.countWordsInArticle(articleName, countCommonWords);
            articleDTO.setWordsList(getTopWords(wordFrequency, 10));
            if (wordFrequency.size() > 0) {
                wikiArticleService.saveArticle(articleDTO);
            }
        } else {
            articleDTO.setSource(DATA_SOURCE_BD);

            List<WordFrequencyEntity> wordFrequencyEntities = dbArticle.getWords();
            Map<String, Integer> wordFrequency = new LinkedHashMap<>();

            for (WordFrequencyEntity element : wordFrequencyEntities){
                wordFrequency.put(element.getWord(),element.getWordCount());
            }

            articleDTO.setWordsList(getTopWords(wordFrequency, 10));
        }

        endTime = System.currentTimeMillis();

        articleDTO.setDuration((int)(endTime - startTime));

        return articleDTO;
    }

    @Override
    public ArticleDTO getWordsByFrequencyInMultipleArticles(List<String> articleNames) {
        long startTime, endTime;
        articleParserService.refreshWordMap();
        startTime = System.currentTimeMillis();

        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setTitles(articleNames);
        articleDTO.setSource(DATA_SOURCE_WIKIPEDIA);

        //Process multiple titles using multithreading
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        boolean countCommonWords = false;

        articleNames.forEach(articleName ->
                executorService.execute(() -> articleParserService.countWordsInArticle(articleName, countCommonWords)));

        try {
            executorService.shutdown();
            executorService.awaitTermination(WAITING_TIME_SECONDS, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            // empty body
        }

        //Get the map with words aggregated from all the articles
        Map<String, Integer> wordFrequency = articleParserService.getWordFrequency();

        articleDTO.setWordsList(getTopWords(wordFrequency, 10));

        endTime = System.currentTimeMillis();

        articleDTO.setDuration((int)(endTime - startTime));

        return articleDTO;
    }

    /**
     * Extracts the first numberOfWords words from the allWords map in the descending
     * order of their occurrence
     * @param allWords Map with words and their occurrence
     * @param numberOfWords Number of elements to include in the returned map
     * @return  Map
     */
    private Map<String, Integer> getTopWords(Map<String, Integer> allWords, int numberOfWords) {
        return allWords.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(numberOfWords)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));
    }
}
